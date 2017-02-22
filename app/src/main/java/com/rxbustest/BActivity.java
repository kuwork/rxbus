package com.rxbustest;

import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.kw.rxbus.RxBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BActivity extends AppCompatActivity {
    @Bind(R.id.btnNext)
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ButterKnife.bind(this);
        RxBus.getInstance().register(UserEvent.class, Schedulers.io(), new Consumer<UserEvent>() {
            @Override
            public void accept(UserEvent o) throws Exception {
                Log.d("BActivity","onNext:"+Thread.currentThread().getName());

            }
        });
    }

    @OnClick(R.id.btnNext)
    public void OnBtnNext() {
        RxBus.getInstance().hasObservers();

        RxBus.getInstance().send(new UserEvent(1,"名字"));
    }
}
