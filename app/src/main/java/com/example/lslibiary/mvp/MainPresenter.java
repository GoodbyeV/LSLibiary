package com.example.lslibiary.mvp;

import com.example.lslibiary.livedata.UserInfo;

/**
 * author  : Liushuai
 * time    : 2021/2/27 21:07
 * desc    :
 */
class MainPresenter extends MainContract.Presenter {
    @Override
    void getUserInfo() {
        /**
         * ..............
         */
        if (view != null && view.isAlive()) {
            view.setUser(new UserInfo("Joker"));
        }
    }
}
