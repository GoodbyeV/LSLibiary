package com.example.loglibrary;

/**
 * author  : Liushuai
 * time    : 2020/12/20 20:03
 * desc    :线程格式化器
 */
class ThreadFormatter implements LogFormatter<Thread>{
    @Override
    public String format(Thread data) {
        return "Thread :"+data.getName();
    }
}
