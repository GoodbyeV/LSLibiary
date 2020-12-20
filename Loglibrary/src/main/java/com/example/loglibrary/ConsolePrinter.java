package com.example.loglibrary;

import android.util.Log;

import androidx.annotation.NonNull;

import static com.example.loglibrary.LogConfig.MAX_LEN;

/**
 * author  : Liushuai
 * time    : 2020/12/20 21:23
 * desc    :
 */
class ConsolePrinter implements LogPrinter {
    @Override
    public void print(@NonNull LogConfig config, int level, String tag, @NonNull String printString) {
        int len=printString.length();
        //行
        int countOfSub=len/MAX_LEN;
        if (countOfSub > 0) {
            int index=0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + MAX_LEN));
                index+=MAX_LEN;
            }
            //剩余打印出来
            if (index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }
        }
    }
}
