package com.example.lslibiary.mvvm;

import com.example.lslibiary.livedata.UserInfo;
import com.example.lslibiary.roomDemo.User;

import androidx.databinding.ObservableField;

/**
 * author  : Liushuai
 * time    : 2021/2/28 14:17
 * desc    :传统MVVM
 */
class MainViewModel {
    public ObservableField<UserInfo> userFiled = new ObservableField<>();

    public void getUserInfo(){
        UserInfo userInfo = new UserInfo("joker","sh");
        userFiled.set(userInfo);
    }
}
