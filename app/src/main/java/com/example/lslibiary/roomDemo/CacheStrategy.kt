package com.example.lslibiary.roomDemo

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

/**
 * author  : Liushuai
 * time    : 2021/2/19 21:38
 * desc    :
 */
@Target(AnnotationTarget.FUNCTION,AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class CacheStrategy {
    companion object{
        const val CACHE_FIRST=0
        const val NET_ONLY=1
        const val NET_CACHE=2
    }
}