package com.loyo.library.util;

import android.text.TextUtils;

import java.util.List;

/**
 * 把数组或者List的元素链接成字符串，使用特定的分割符。
 * Created by zjie on 2017/8/23.
 */

public class ArrayToStringWithSplitTool {
    /**
     * 把数组的链接成字符串，用split分割
     *
     * @param args  数组，必须复写 #toString()方法
     * @param split 分割符
     */
    public static String format(Object[] args, String split) {
        if(args==null||args.length==0)return "";
        StringBuilder sb = new StringBuilder();
        for (Object item : args) {
            //空字符串跳过
            if(item==null|| TextUtils.isEmpty(item.toString())){
                continue;
            }
            if (sb.length() == 0) {
                sb.append(item.toString());
            } else {
                sb.append(split);
                sb.append(item.toString());
            }
        }
        return sb.toString();
    }

    /**
     * 把数组的链接成字符串，用split分割,默认用，分割
     *
     * @param args  数组，必须复写 #toString()方法
     */
    public static String format(Object[] args) {
        return  format(args,",");
    }

    /**
     * 把数组的链接成字符串，用split分割
     *
     * @param args  数组，必须复写 #toString()方法
     * @param split 分割符
     */
    public static String format(List args, String split) {
        if(args==null||args.size()==0)return "";
        StringBuilder sb = new StringBuilder();
        for (Object item : args) {
            //空字符串跳过
            if(item==null|| TextUtils.isEmpty(item.toString())){
                continue;
            }
            if (sb.length() == 0) {
                sb.append(item.toString());
            } else {
                sb.append(split);
                sb.append(item.toString());
            }
        }
        return sb.toString();
    }
    /**
     * 把数组的链接成字符串，用split分割,默认用，分割
     *
     * @param args  数组，必须复写 #toString()方法
     */
    public static String format(List args) {
        return  format(args,",");
    }

}
