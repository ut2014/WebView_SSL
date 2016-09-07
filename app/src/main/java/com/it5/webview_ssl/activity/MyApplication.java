package com.it5.webview_ssl.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by IT5 on 2016/8/31.
 */
public class MyApplication extends Application {
    public static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext=base;
//        MultiDex.install(this);

     /*   try {
            OkHttpClientManager.setCertificates(getAssets().open("srca.cer")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
