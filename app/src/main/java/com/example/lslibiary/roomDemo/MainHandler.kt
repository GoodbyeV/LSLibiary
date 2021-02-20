package com.example.lslibiary.roomDemo

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.lslibiary.livedata.AccountManager

/**
 * author  : Liushuai
 * time    : 2021/2/19 21:51
 * desc    :
 */
object MainHandler {
    private val handler=Handler(Looper.getMainLooper())

    fun post(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelay(delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable,delayMills)
    }

    //添加到队列最前面优先执行
    fun sendAtFrontOfQueue(runnable: Runnable){
        val msg=Message.obtain(handler,runnable)
        handler.sendMessageAtFrontOfQueue(msg)
    }


}