package com.example.lslibiary.livedata

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.lslibiary.MyApplication
import com.example.lslibiary.roomDemo.User
import java.lang.IllegalStateException

/**
 * author  : Liushuai
 * time    : 2021/2/20 21:58
 * desc    :liveData实现登录结果通知 刷新页面
 */
object AccountManager  {
    private val loginLiveData=MutableLiveData<Boolean>()
    private val loginForeverObservers= mutableListOf<Observer<Boolean>>()

    private val userInfoLiveData=MutableLiveData<UserInfo>()
    private val userInfoForeverObservers= mutableListOf<Observer<UserInfo>>()


    //登录监听
    fun login(context: Context? = MyApplication.instance,observer:Observer<Boolean>) {
        if (context is LifecycleOwner) {
            loginLiveData.observe(context,observer)
        }else{
            //forever需要手动移除
            loginLiveData.observeForever(observer)
            loginForeverObservers.add(observer)
        }

        //........
        if (context == null) {
            throw IllegalStateException("context must not null")
        }
    }

    //登录成功 发送消息给观察者
    fun loginSuccess(){
        loginLiveData.postValue(true)
    }

    //获取用户信息监听
    fun getUserInfo(lifecycleOwner: LifecycleOwner?,observer: Observer<UserInfo>){
        if (lifecycleOwner == null) {
            userInfoLiveData.observeForever(observer)
            userInfoForeverObservers.add(observer)
        }else{
            userInfoLiveData.observe(lifecycleOwner,observer)
        }

        //........
        userInfoLiveData.value=UserInfo("joker")
    }

}