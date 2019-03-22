package com.jx.xjt.twelve.presenter;


import com.jx.xjt.twelve.view.IView;


import java.util.Observable;

import rx.Subscriber;
import rx.Subscription;

public class BasePresenter<V extends IView> implements Presenter<V> {
    private V view;

    public BasePresenter(V view){
        attachActivity(view);
    }

    @Override
    public void attachActivity(V view) {
        this.view = view;
    }

    @Override
    public void detachActivity() {
        if(view != null){
            view = null;
        }
        onUnSubscribe();
    }

    @Override
    public void addSubscribe(Subscription subscripton) {

    }

    @Override
    public void addSubscribe(Observable observable, Subscriber subscriber) {

    }

    @Override
    public void onUnSubscribe() {

    }
}
