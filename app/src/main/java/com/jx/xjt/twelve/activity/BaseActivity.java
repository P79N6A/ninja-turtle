package com.jx.xjt.twelve.activity;

import android.app.Activity;



import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.jx.xjt.twelve.R;
import com.jx.xjt.twelve.config.ZbrApplication;
import com.jx.xjt.twelve.utils.ToastUtil;


import butterknife.ButterKnife;


/**
 * <h3>Activity通用操作</h3>
 * @author LQC
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Intent mIntent;
    protected Context mContext;
    protected ProgressDialog mProgressDialog;

    protected boolean hasToolbar = true;
    protected boolean hasBackButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = new Intent();
        mContext = this;
        initView();
    }

    protected final void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(0x33000000);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置导航栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        setContentView(getContentViewLayoutId());
        if (hasToolbar) {
            initToolbar((Toolbar) findViewById(R.id.toolbar));
        }
        mProgressDialog = new ProgressDialog(mContext, "正在加载中...　");
        //ButterKnife.bind(this);
    }

    /**
     * 获取ContentViewLayoutId
     */
    protected abstract int getContentViewLayoutId();

    /**
     * 初始化toolbar
     */
    protected void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(hasBackButton);
            toolbar.setBackgroundColor(getColorPrimary());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // 返回
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取应用主色调
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    protected ZbrApplication getCustomApp() {
        return (ZbrApplication) getApplication();
    }

    /**
     * 显示加载进度框
     */
    public void showLoadingDialog() {
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    /**
     * 隐藏加载进度框
     */
    public void hideLoadingDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 显示吐司
     */
    public void showToast(String msg, int... duration) {
        if (duration != null) { // 不传duration，默认显示短时间的Toast
            ToastUtil.showMessageLong(mContext, msg);
        } else {
            ToastUtil.showMessage(mContext, msg);
        }
    }

    /**
     * 带动画效果的跳转界面
     */
    public final void startActivity(Class<? extends Activity> clazz) {
        mIntent.setClass(this, clazz);
        startActivity(mIntent);
        showActivityInAnim();
    }

    /**
     * 带参数的跳转界面
     */
    public final void startActivity(Class<? extends Activity> clazz, Intent intent) {
        intent.setClass(this, clazz);
        startActivity(intent);
        showActivityInAnim();
    }

    /**
     * Activity进入的动画效果
     */
    protected void showActivityInAnim() {
        overridePendingTransition(R.anim.activity_right_left_anim, R.anim.activity_exit_anim);
    }

    /**
     * Activity退出的动画效果
     */
    protected void showActivityExitAnim() {
        overridePendingTransition(R.anim.activity_exit_anim, R.anim.activity_left_right_anim);
    }

    /**
     * 重写退出Activity，附带动画效果
     */
    @Override
    public void finish() {
        super.finish();
        showActivityExitAnim();
    }

    /**
     * 无动画重启Activity
     */
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        super.finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}
