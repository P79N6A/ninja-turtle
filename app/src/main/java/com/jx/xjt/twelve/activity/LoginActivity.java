package com.jx.xjt.twelve.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jx.xjt.twelve.MainActivity;
import com.jx.xjt.twelve.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_logo)
    TextView tvLogo;
    @BindView(R.id.v_divider1)
    View vDivider1;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.ll_divider)
    LinearLayout llDivider;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.ll_password)
    LinearLayout llPassword;
    @BindView(R.id.v_divider2)
    View vDivider2;
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_offline)
    TextView tvOffline;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btn_login:
                    login();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }

    private void login() {
        String user =etAccount.getText().toString();
        String password = etPassword.getText().toString();
    }


}
