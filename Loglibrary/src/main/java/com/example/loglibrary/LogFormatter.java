package com.example.loglibrary;

/**
 * author  : Liushuai
 * time    : 2020/12/20 20:02
 * desc    :日志格式化接口
 */
interface LogFormatter<T> {
    String format(T data);
}
