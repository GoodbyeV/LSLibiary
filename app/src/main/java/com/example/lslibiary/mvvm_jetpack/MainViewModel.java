package com.example.lslibiary.mvvm_jetpack;

import com.example.lslibiary.livedata.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * author  : Liushuai
 * time    : 2021/2/28 14:45
 * desc    :
 */
class MainViewModel extends ViewModel {
    public LiveData<User> getUserInfo(){
        MutableLiveData<User> liveData = new MutableLiveData<>();

        User info = new User();
        liveData.postValue(info);
        return  liveData;
    }
}
