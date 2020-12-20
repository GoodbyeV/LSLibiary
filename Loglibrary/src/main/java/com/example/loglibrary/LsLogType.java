package com.example.loglibrary;

import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * author  : Liushuai
 * time    : 2020/12/19 17:17
 * desc    :
 */
class LsLogType {
    @IntDef({V,D,I,W,E,A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE{

    }
    public static final int V= Log.VERBOSE;
    public static final int D= Log.DEBUG;
    public static final int I= Log.INFO;
    public static final int W= Log.WARN;
    public static final int E= Log.ERROR;
    public static final int A= Log.ASSERT;
}
