package com.kw.rxbus;

import android.util.Log;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.jakewharton.rxrelay2.ReplayRelay;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.ReplaySubject;


/**
 * @author <a href="mailto:kuwork@126.com">Jerry</a>
 */
public enum RxBus {
    INSTANCE;
    //粘性事件(一个粘性事件被发布在较早之前,注册为订阅者后,将会收到之前发布的粘性事件)
    private ReplayRelay<Object> busSticky = null;
    private Relay<Object> bus = null;
    private static RxBus instance;

    //禁用构造方法
    private RxBus() {
        bus = PublishRelay.create().toSerialized();
        busSticky = ReplayRelay.create();
        ReplaySubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        return RxBus.INSTANCE;
    }

    public void send(Object event) {
        bus.accept(event);
    }

    public void sendSticky(Object event) {
        busSticky.accept(event);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public <T> Observable<T> toObservableSticky(Class<T> eventType) {
        return busSticky.ofType(eventType);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public boolean hasObserversSticky() {
        return busSticky.hasObservers();
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext);
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError,
                                   Action onComplete, Consumer onSubscribe) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext, onError, onComplete, onSubscribe);
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError,
                                   Action onComplete) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext, onError, onComplete);
    }

    public <T> Disposable register(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError) {
        return toObservable(eventType).observeOn(scheduler).subscribe(onNext, onError);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext, Consumer onError,
                                   Action onComplete, Consumer onSubscribe) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete, onSubscribe);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext, Consumer onError,
                                   Action onComplete) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete);
    }

    public <T> Disposable register(Class<T> eventType, Consumer<T> onNext, Consumer onError) {
        return toObservable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError);
    }

    //注册粘性事件
    public <T> Disposable registerSticky(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext) {
        return toObservableSticky(eventType).observeOn(scheduler).subscribe(onNext);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError,
                                         Action onComplete, Consumer onSubscribe) {
        return toObservableSticky(eventType).observeOn(scheduler).subscribe(onNext, onError, onComplete, onSubscribe);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError,
                                         Action onComplete) {
        return toObservableSticky(eventType).observeOn(scheduler).subscribe(onNext, onError, onComplete);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Scheduler scheduler, Consumer<T> onNext, Consumer onError) {
        return toObservableSticky(eventType).observeOn(scheduler).subscribe(onNext, onError);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Consumer<T> onNext) {
        return toObservableSticky(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Consumer<T> onNext, Consumer onError,
                                         Action onComplete, Consumer onSubscribe) {
        return toObservableSticky(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete, onSubscribe);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Consumer<T> onNext, Consumer onError,
                                         Action onComplete) {
        return toObservableSticky(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete);
    }

    public <T> Disposable registerSticky(Class<T> eventType, Consumer<T> onNext, Consumer onError) {
        return toObservableSticky(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError);
    }

    public synchronized void removeSticky(Object event) {
        if (busSticky != null) {
            Log.d("Sticky", busSticky.getValues().toString() + ",size = " + busSticky.getValues().length);
            busSticky.remove(event);
        }
    }


    public void unregister(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


}