package com.example.android.androidutils;

import android.widget.Toast;

/**
 * Created by android on 2017/9/27.
 */

public class ToastUtil {
    static Toast mCurrentToast;

    public static void toast(String msg) {
        if (null != mCurrentToast) {
            mCurrentToast.cancel();
        }

        mCurrentToast = Toast.makeText(AppUtil.getAppContext(), msg, Toast.LENGTH_SHORT);
//        mCurrentToast.setGravity(Gravity.CENTER, 0, 0);
        mCurrentToast.show();
    }

    public static void toastLong(String msg) {
        if (null != mCurrentToast) {
            mCurrentToast.cancel();
        }

        mCurrentToast = Toast.makeText(AppUtil.getAppContext(), msg, Toast.LENGTH_LONG);
//        mCurrentToast.setGravity(Gravity.CENTER, 0, 0);
        mCurrentToast.show();
    }


    public static void toast(int resId) {
        toast(AppUtil.getAppContext().getString(resId));
    }
}
