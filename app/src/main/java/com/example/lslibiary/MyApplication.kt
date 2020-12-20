package com.example.lslibiary

import android.app.Application
import com.example.loglibrary.LogConfig
import com.example.loglibrary.LogManager

/**
 * author  : Liushuai
 * time    : 2020/12/19 21:41
 * desc    :
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        LogManager.init(object : LogConfig() {
            override fun getGlobalTag(): String {
                return super.getGlobalTag()
            }

            override fun enable(): Boolean {
                return true
            }
        })
    }

}