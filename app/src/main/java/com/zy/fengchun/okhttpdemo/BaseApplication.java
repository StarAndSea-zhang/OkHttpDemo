package com.zy.fengchun.okhttpdemo;

import android.app.Application;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class BaseApplication extends Application{
    private static BaseApplication mApplication;

    public static BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
