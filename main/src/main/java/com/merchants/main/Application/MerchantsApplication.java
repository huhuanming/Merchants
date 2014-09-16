package com.merchants.main.Application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by chen on 14-9-12.
 */
public class MerchantsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
