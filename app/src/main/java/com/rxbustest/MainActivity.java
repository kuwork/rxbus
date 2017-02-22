package com.rxbustest;

import android.content.Intent;
import android.content.res.ObbInfo;
import android.icu.lang.UCharacter;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.kw.rxbus.RxBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btnNext)
    Button btnNext;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        disposable=RxBus.getInstance().register(UserEvent.class, new Consumer<UserEvent>() {
            @Override
            public void accept(UserEvent userEvent) {
                btnNext.setText(userEvent.getName());
                Toast.makeText(getBaseContext(), userEvent.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getBaseContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(disposable);
    }

    @OnClick(R.id.btnNext)
    public void OnBtnNext() {
        startActivity(new Intent(getBaseContext(), AActivity.class));
    }
}
