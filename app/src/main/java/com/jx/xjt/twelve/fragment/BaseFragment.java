package com.jx.xjt.twelve.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected DisplayMetrics metrics;
    private Unbinder unbinder;
    private ProgressDialog dialog;
    public BaseFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        dialog = new ProgressDialog(context);
        dialog.setMessage("加载中...");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        bindData();
        bindListener();
    }

    protected abstract void bindListener();

    protected abstract void bindData();

    public ProgressDialog getDialog() {
        return dialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(unbinder!=null){
            unbinder = null;
        }
    }

}
