package com.jx.xjt.twelve.fragment;

import android.content.Context;

import com.jx.xjt.twelve.presenter.BasePresenter;
import com.jx.xjt.twelve.view.IView;

public abstract class MVPFragment<P extends BasePresenter> extends BaseFragment implements IView {
    protected P presenter;
public P getPresenter(){
    return this.presenter;
}
protected abstract P createPresenter();

    @Override
    protected void bindListener() {

    }

    @Override
    protected void bindData() {

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = createPresenter();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachActivity();
        }
    }

}
