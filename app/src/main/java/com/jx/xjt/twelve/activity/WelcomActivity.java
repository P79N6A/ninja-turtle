package com.jx.xjt.twelve.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.jx.xjt.twelve.R;
import com.jx.xjt.twelve.presenter.WelcomPresenter;
import com.jx.xjt.twelve.view.IWelcomView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomActivity extends MVPActivity<WelcomPresenter> implements IWelcomView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tvCount)
    TextView tvCount;


    private Handler mHandler = new Handler();
    @Override
    protected WelcomPresenter createPresenter() {
        return new WelcomPresenter(this);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_welcom;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        new Thread(new MyCounter()).start();
    }


    private class MyCounter implements Runnable {
        @Override
        public void run() {
            int i= 5;
            while (i>0){//

                final int finalI = i;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvCount.setText(finalI +"  跳过");
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i--;
            }
            startActivity(new Intent(WelcomActivity.this,LoginActivity.class));
        }

    }
}
