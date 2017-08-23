package com.codepath.simpletodo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Kirill Zhukov on 8/22/17.
 */

public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}
