package com.loyo.library.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * 获取当前app的版本号和版本code
 * Created by zjie on 2017/10/23.
 */

public class VersionTools {
    private static Integer versionCode;
    private static String versionName;

    public static String getVersionName() {
        if (TextUtils.isEmpty(versionName)) {
            getInfo();
        }
        return versionName;
    }

    public static int getVersionCode() {
        if (null == versionCode || 0 == versionCode) {
            getInfo();
        }
        return versionCode;
    }

    private static void getInfo() {
        try {
            PackageInfo packageInfo = AppUtil.getAppContext().getPackageManager().getPackageInfo(AppUtil.getAppContext().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}