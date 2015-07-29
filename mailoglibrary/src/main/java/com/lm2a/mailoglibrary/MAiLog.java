package com.lm2a.mailoglibrary;

import android.util.Log;

public class MAiLog {

    public static final String TAG = "MAiLog";

    public static void v(String message)  {
        LogConfig logConfig = LogSingleton.getInstance().getLogConfig();
        boolean logOn = logConfig.getMailConfiguration().isLogOn();
        if(logOn && message != null){
            Log.v(TAG, message);
            logConfig.sendLogcatMail();
        }
    }

    public static void d(String message) {
        LogConfig logConfig = LogSingleton.getInstance().getLogConfig();
        boolean logOn = logConfig.getMailConfiguration().isLogOn();
        if(logOn && message != null){
            Log.d(TAG, message);
            logConfig.sendLogcatMail();
        }
    }

    public static void e(String message) {
        LogConfig logConfig = LogSingleton.getInstance().getLogConfig();
        boolean logOn = logConfig.getMailConfiguration().isLogOn();
        if(logOn && message != null){
            Log.e(TAG, message);
            logConfig.sendLogcatMail();
        }
    }

    public static void i(String message) {
        LogConfig logConfig = LogSingleton.getInstance().getLogConfig();
        boolean logOn = logConfig.getMailConfiguration().isLogOn();
        if(logOn && message != null){
            Log.i(TAG, message);
            logConfig.sendLogcatMail();
        }
    }


}