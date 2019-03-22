package com.jx.xjt.twelve.presenter;

//import org.reactivestreams.Subscriber;
//import org.reactivestreams.Subscription;

import java.util.Observable;

import rx.Subscriber;
import rx.Subscription;

public interface Presenter<V> {
    void attachActivity(V view);
    void detachActivity();
    void addSubscribe(Subscription subscripton);
    void addSubscribe(Observable observable, Subscriber subscriber);
    void onUnSubscribe();
}
