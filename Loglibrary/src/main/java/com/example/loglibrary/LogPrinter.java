package com.example.loglibrary;

import androidx.annotation.NonNull;

/**
 * author  : Liushuai
 * time    : 2020/12/20 20:00
 * desc    :
 */
public interface LogPrinter {
    void print(@NonNull LogConfig config, int level, String tag, @NonNull String printString);
}
