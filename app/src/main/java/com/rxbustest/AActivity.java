package com.rxbustest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.kw.rxbus.RxBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AActivity extends AppCompatActivity {
    @Bind(R.id.btnNext)
    Button btnNext;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        ButterKnife.bind(this);
        disposable = RxBus.getInstance().register(UserEvent.class, AndroidSchedulers.mainThread(), new Consumer<UserEvent>() {
            @Override
            public void accept(UserEvent userEvent) {
                btnNext.setText(userEvent.getName());
                Toast.makeText(getBaseContext(), userEvent.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AActivity", "onNext:" + Thread.currentThread().getName());
                throw new NullPointerException("空指针错误");//发生错误之后，会取消订阅
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //发生错误后仅仅会进入一次，因为发生错误之后，会取消订阅
                Toast.makeText(getBaseContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //上下两段代码具有相同意义
//        disposable=RxBus.getInstance().toObservable(UserEvent.class)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<UserEvent>() {
//            @Override
//            public void accept(UserEvent userEvent) throws Exception {
//                btnNext.setText(userEvent.getName());
//                Toast.makeText(getBaseContext(),userEvent.toString(),Toast.LENGTH_SHORT).show();
//                Log.d("AActivity","onNext:"+Thread.currentThread().getName());
//                throw new NullPointerException("空指针错误");//发生错误之后，会取消订阅
//            }
//        },new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                //发生错误后仅仅会进入一次，因为发生错误之后，会取消订阅
//                Toast.makeText(getBaseContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @OnClick(R.id.btnNext)
    public void OnBtnNext() {
        startActivity(new Intent(getBaseContext(), BActivity.class));
    }

    @OnClick(R.id.btnSend)
    public void OnBtnSend() {
        RxBus.getInstance().send(new UserEvent(1, "名字A"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(disposable);
    }
}
