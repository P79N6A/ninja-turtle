package com.jx.xjt.twelve.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jx.xjt.twelve.R;
import com.jx.xjt.twelve.adapter.HomeFragmentAdapter;
import com.jx.xjt.twelve.presenter.HomeFragmentPresenter;
import com.jx.xjt.twelve.view.IHomeFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends MVPFragment<HomeFragmentPresenter> implements IHomeFragmentView, View.OnClickListener {


    HomeFragmentAdapter adapter;
    PackageManager packageManager;
    String packageName;
    @BindView(R.id.tv_finance)
    TextView tvFinance;
    @BindView(R.id.tv_news)
    TextView tvNews;
    @BindView(R.id.tv_mall)
    TextView tvMall;
    @BindView(R.id.tv_plane)
    TextView tvPlane;
    @BindView(R.id.tv_func)
    TextView tvFunc;
    @BindView(R.id.tv_douyin)
    TextView tvDouyin;
    Unbinder unbinder;

    @Override
    protected HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter(this);
    }

    @Override
    protected void bindListener() {
        tvFinance.setOnClickListener(this);
        tvFunc.setOnClickListener(this);
    }

    @Override
    protected void bindData() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        adapter = new HomeFragmentAdapter();
        packageManager = getActivity().getPackageManager();

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_finance:
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_test);
                dialog.show();
               dialog.setCancelable(true);
               dialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = (int) (getActivity().getWindowManager().getDefaultDisplay().getWidth()*0.8);
                dialog.getWindow().setAttributes(params);

                break;
            case R.id.tv_func:

                break;
        }
    }
}
