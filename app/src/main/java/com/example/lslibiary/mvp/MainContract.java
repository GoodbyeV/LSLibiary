package com.example.lslibiary.mvp;

import com.example.lslibiary.livedata.UserInfo;
import com.example.lslibiary.roomDemo.User;

/**
 * author  : Liushuai
 * time    : 2021/2/27 21:04
 * desc    :
 */
public interface MainContract {

    interface View extends BaseView{
        void setUser(UserInfo user);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getUserInfo();
    }
}
