package com.example.loglibrary;

/**
 * author  : Liushuai
 * time    : 2020/12/20 20:04
 * desc    :
 */
class StackTraceFormatter implements LogFormatter<StackTraceElement[]>{
    @Override
    public String format(StackTraceElement[] data) {
        StringBuilder sb = new StringBuilder(128);
        if(data == null || data.length == 0){
           return null;
        } else if (data.length == 1) {
            return "\t- "+data[0].toString();
        }else{
            for (int i = 0; i < data.length; i++) {
                if (i == 0) {
                    sb.append("stackTrace: \n");
                }
                if(i!=data.length-1){
                    sb.append("\t -");
                    sb.append(data[i].toString());
                    sb.append("\t ");
                }else{
                    sb.append("\t ");
                    sb.append(data[i].toString());
                }
            }
           return sb.toString();
        }
    }
}
