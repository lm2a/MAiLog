package com.lm2a.mailoglibrary;

import android.content.Context;

public class LogSingleton {
   private static LogSingleton instance = null;

   private Context context;
   private LogConfig logConfig;

   protected LogSingleton() {
      // Exists only to defeat instantiation.
   }

   public void init(Context context){
      this.context = context;
      logConfig = new LogConfig(context);
   }

   public static LogSingleton getInstance() {
      if(instance == null) {
         instance = new LogSingleton();
      }
      return instance;
   }

   public LogConfig getLogConfig() {
      return logConfig;
   }
}