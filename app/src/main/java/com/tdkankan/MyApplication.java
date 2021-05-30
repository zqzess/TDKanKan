package com.tdkankan;

import android.app.Application;

/**
 * @author ZQZESS
 * @date 1/7/2021.
 * @file MyApplication
 * GitHub：https://github.com/zqzess
 * 不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class MyApplication extends Application {
    //Application类，提供全局上下文对象
    public static String TAG;
    public static MyApplication myApplication;

    public static MyApplication newInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
        myApplication = this;

    }
}
