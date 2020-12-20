package com.example.loglibrary;

/**
 * author  : Liushuai
 * time    : 2020/12/19 21:20
 * desc    :
 */
public abstract class LogConfig {
    //日志最大长度
    static int MAX_LEN=512;
    static StackTraceFormatter STACK_TRACE_FORMATTER = new StackTraceFormatter();
    static ThreadFormatter THREAD_FORMATTER = new ThreadFormatter();
    public String getGlobalTag(){
        return "LsLog";
    }

    /**
     * 是否启用  false关闭
     * @return
     */
    public boolean enable(){
        return true;
    }

    /**
     * 默认日志不包含线程信息
     * @return
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 堆栈深度
     * @return
     */
    public int stackTraceDepth(){
        return 5;
    }

    public LogPrinter[] printers(){
        return null;
    }

    public interface  JsonParser{
        String toJson(Object object);
    }
}
