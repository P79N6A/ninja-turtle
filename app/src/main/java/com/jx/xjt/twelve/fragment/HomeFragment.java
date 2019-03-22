package com.jx.xjt.twelve.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jx.xjt.twelve.R;
import com.jx.xjt.twelve.adapter.HomeFragmentAdapter;
import com.jx.xjt.twelve.presenter.HomeFragmentPresenter;
import com.jx.xjt.twelve.view.IHomeFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends MVPFragment<HomeFragmentPresenter> implements IHomeFragmentView, View.OnClickListener {

    @BindView(R.id.btn_CG)
    Button btnCG;
    @BindView(R.id.btn_CE)
    Button btnCE;
    @BindView(R.id.btn_H4)
    Button btnH4;
    @BindView(R.id.btn_H9)
    Button btnH9;
    @BindView(R.id.btn_U6)
    Button btnU6;
    @BindView(R.id.btn_U8)
    Button btnU8;
    @BindView(R.id.btn_U9)
    Button btnU9;
    @BindView(R.id.btn_BD)
    Button btnBD;
    @BindView(R.id.btn_BE)
    Button btnBE;
    @BindView(R.id.btn_BH)
    Button btnBH;


    HomeFragmentAdapter adapter;
    PackageManager packageManager;
    String packageName;

    @Override
    protected HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter(this);
    }

    @Override
    protected void bindListener() {
        btnH4.setOnClickListener(this);
        btnH9.setOnClickListener(this);
        btnU6.setOnClickListener(this);
        btnU8.setOnClickListener(this);
        btnU9.setOnClickListener(this);
        btnBD.setOnClickListener(this);
        btnBE.setOnClickListener(this);
        btnBH.setOnClickListener(this);
        btnCE.setOnClickListener(this);
        btnCG.setOnClickListener(this);
    }

    @Override
    protected void bindData() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        adapter = new HomeFragmentAdapter();
        packageManager = getActivity().getPackageManager();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_H4:
 //跳转到H4二代证模块，目前二代证这个模块源码存在问题，报错缺少SO文件。
                packageName = "com.fntech.idcard";
                onPenModel(packageName);
                break;
            case R.id.btn_H9:
//跳转到H9 NFC模块
                packageName = "com.nfc_demo";
                onPenModel(packageName);
                break;
            case R.id.btn_U6:
//跳转到U6模块
                packageName = "com.fn.u6";
                onPenModel(packageName);
                break;
            case R.id.btn_U8:
//跳转到U8模块
                packageName = "com.fn.useries.u8";
                onPenModel(packageName);
                break;
            case R.id.btn_U9:
//跳转到U8模块
                packageName = "com.fn.useries.u8";
                onPenModel(packageName);
                break;
            case R.id.btn_BD:
//跳转到一维条码模块APP
                packageName = "com.example.m10_1d";
                onPenModel(packageName);
                break;
            case R.id.btn_BE:
//打开二维软解模块APP
                packageName = "com.M10.BE";
                onPenModel(packageName);
                break;
            case R.id.btn_BH:
//打开二维硬解模块APP
                packageName = "com.example.m10_1d";
                onPenModel(packageName);
                break;
            case R.id.btn_CE:
//打开PSAM卡单
                packageName = "com.example.m10_psam";
                onPenModel(packageName);
                break;
            case R.id.btn_CG:
//打开PSAM卡单
                packageName = "com.example.m10_psam";
                onPenModel(packageName);
                break;
        }

    }



    //检测PDA上是否安装了对应模块
    private boolean isInstalled(String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else return true;
    }

    //打开对应模块的APP
    private void onPenModel(String packageName) {
        if (isInstalled(packageName)) {
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "尚未安装该模块APP", Toast.LENGTH_SHORT).show();
        }
    }


}
