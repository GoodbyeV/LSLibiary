package jnRes

import android.app.Application
import java.lang.Exception

/**
 * author  : Liushuai
 * time    : 2021/2/27 16:04
 * desc    :
 */
object AppGlobals {
    private var application:Application?=null
    fun get():Application?{
        if (application == null) {
            try {
               application=Class.forName("android.app.ActivityThread")
                       .getMethod("currentApplication")
                       .invoke(null) as Application
            } catch (ex: Exception) {
               ex.printStackTrace()
            }
        }
        return application
    }
}