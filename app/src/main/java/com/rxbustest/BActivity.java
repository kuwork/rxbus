package com.rxbustest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.kw.rxbus.RxBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BActivity extends AppCompatActivity {
    @Bind(R.id.btnNext)
    Button btnNext;
    private Disposable disposable;
    private Disposable disposableSticky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        setTitle("B");
        ButterKnife.bind(this);
        disposable = RxBus.getInstance().register(UserEvent.class, Schedulers.io(), new Consumer<UserEvent>() {
            @Override
            public void accept(UserEvent o) throws Exception {
                Log.d("BActivity", "onNext:" + Thread.currentThread().getName());

            }
        });
        disposableSticky = RxBus.getInstance().registerSticky(UserEvent.class, new Consumer<UserEvent>() {
            @Override
            public void accept(UserEvent o) throws Exception {
                Log.d("BActivity", "onNext:" + Thread.currentThread().getName());
                Toast.makeText(getBaseContext(), "收到粘性事件：" + o.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(disposable);
        RxBus.getInstance().unregister(disposableSticky);
    }

    @OnClick(R.id.btnNext)
    public void OnBtnNext() {
        RxBus.getInstance().hasObservers();
        RxBus.getInstance().send(new UserEvent(1, "前往B:名字B"));
    }
}
