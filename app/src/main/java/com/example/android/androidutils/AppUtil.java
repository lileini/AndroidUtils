package com.example.android.androidutils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by android on 2017/9/26.
 */

public class AppUtil {
    private static Context mContext;
    private static Thread mUiThread;

    public static void init(Context context){
        mContext = context;
        mUiThread = Thread.currentThread();
    }
    private static Handler sHandler = new Handler(Looper.getMainLooper());



    public static Context getAppContext(){
        return mContext;
    }

    public static AssetManager getAssets() {
        return mContext.getAssets();
    }

    public static Resources getResource() {
        return mContext.getResources();
    }

    public static boolean isUIThread() {
        return Thread.currentThread() == mUiThread;
    }

    public static void runOnUI(Runnable r) {
        sHandler.post(r);
    }

    public static void runOnUIDelayed(Runnable r, long delayMills) {
        sHandler.postDelayed(r, delayMills);
    }

    public static void removeRunnable(Runnable r) {
        if (r == null) {
            sHandler.removeCallbacksAndMessages(null);
        } else {
            sHandler.removeCallbacks(r);
        }
    }

    public static int getColor(int colorId) {
        return mContext.getResources().getColor(colorId);
    }
}
