package com.jx.xjt.twelve.activity;

import android.os.Bundle;
import android.os.Handler;


import com.jx.xjt.twelve.presenter.BasePresenter;

import java.util.Date;

public abstract class MVPActivity<P extends BasePresenter> extends BaseActivity implements IMVP {
    private P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
    }

    protected abstract P createPresenter();

    public P getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachActivity();
        }
    }


    /**
     * 设置持续时间 至少800ms  否则一闪而过 效果不好
     */
    private long time;
    //最小持续时间  vc                   v
    private static final long LEAST_TIME = 1000;


}
