package com.example.loglibrary;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * author  : Liushuai
 * time    : 2020/12/19 17:16
 * desc    :
 */
public class LsLog {
    public static void v(Object... contents) {
        log(LsLogType.V,contents);
    }

    public static void vt(String tag, Object... contents) {
        log(LsLogType.V,tag,contents);
    }
    public static void d(Object... contents) {
        log(LsLogType.D,contents);
    }

    public static void dt(String tag, Object... contents) {
        log(LsLogType.D,tag,contents);
    }

    public static void w(Object... contents) {
        log(LsLogType.W,contents);
    }

    public static void wt(String tag, Object... contents) {
        log(LsLogType.W,tag,contents);
    }

    public static void e(Object... contents) {
        log(LsLogType.E,contents);
    }

    public static void et(String tag, Object... contents) {
        log(LsLogType.E,tag,contents);
    }

    public static void a(Object... contents) {
        log(LsLogType.A,contents);
    }

    public static void at(String tag, Object... contents) {
        log(LsLogType.A,tag,contents);
    }


    public static void log(@LsLogType.TYPE int type, Object... contents) {
        log(type,LogManager.getInstance().getConfig().getGlobalTag(),contents);
    }
    public static void log(@LsLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(LogManager.getInstance().getConfig(),type,tag,contents);

    }

    public static void log(@NonNull LogConfig config,@LsLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (config.includeThread()) {
            String threadInfo = LogConfig.THREAD_FORMATTER.format(Thread.currentThread());
            stringBuilder.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            String stackTrace = LogConfig.STACK_TRACE_FORMATTER.format(new Throwable().getStackTrace());
            stringBuilder.append(stackTrace).append("\n");
        }
        String body = parseBody(contents,config);
        stringBuilder.append(body);
        List<LogPrinter> printers=config.printers()!=null? Arrays.asList(config.printers()):LogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        for (LogPrinter printer : printers) {
            printer.print(config,type,tag,stringBuilder.toString());
        }
    }

    public static String parseBody(@NonNull Object[] contents,@NonNull LogConfig config) {
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();

    }









}
