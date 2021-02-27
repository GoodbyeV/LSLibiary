package jnRes

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * author  : Liushuai
 * time    : 2021/2/27 16:01
 * desc    :
 */
object JnRes {
    fun getString(@StringRes id: Int):String {
         return context().getString(id)
    }

    fun getString(@StringRes id: Int,vararg formatArgs:Any?):String {
        return context().getString(id,*formatArgs)
    }

    fun getColor(@ColorRes id:Int):Int {
        return ContextCompat.getColor(context(),id)
    }

    fun getDrawable(@DrawableRes drawableId:Int):Drawable?{
        return ContextCompat.getDrawable(context(),drawableId)
    }

    private fun context(): Context {
         return  AppGlobals.get() as Context
    }
}