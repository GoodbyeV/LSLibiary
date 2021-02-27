package com.example.lslibiary.mvp;

import android.os.Bundle;

import com.example.lslibiary.R;
import com.example.lslibiary.livedata.UserInfo;

public class MvpActivity extends MVPBaseActivity<MainPresenter> implements MainContract.View {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        mPresenter.getUserInfo();
    }

    @Override
    public void setUser(UserInfo user) {

    }
}