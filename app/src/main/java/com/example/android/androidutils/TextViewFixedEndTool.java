package com.loyo.oa.v2.tool;

import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by zjie on 2017/7/4.
 */

public class TextViewFixedEndTool {
    /**
     * 固定TexitView结尾字符串的一个工具类，如果原有的字符串显示不下，就显示原来的一部分加固定结尾
     * 【这里有一个bug，就是当中英文混排的时候，因为TextView的智能处理，不允许字母和数字开头，会导致字符串提前换行，所以行号可能不准确，推荐使用单行来处理】
     *
     * @param tv
     * @param text    本来要显示的字符串
     * @param end     固定结尾
     * @param maxLine 最大显示行数
     * @param split   分隔符，如果截取之后，最后一个是分隔符，就去掉
     */
    public static void setText(TextView tv, String text, String end, int maxLine,String split) {
        TextPaint paint = tv.getPaint();
        int tvWidth = (tv.getWidth()-tv.getPaddingLeft()-tv.getPaddingRight())*maxLine;//获取最大显示字符的宽度
        float realWidth = paint.measureText(text);
        if (realWidth > tvWidth) {
            float textWidth = paint.measureText(end);
            float canUseWidth = tvWidth - textWidth;
            for (int i = 1; i < text.length(); i++) {
                if (paint.measureText(text.substring(0, i)) > canUseWidth) {
                    String userS = text.substring(0, i - 1);
                    if(!TextUtils.isEmpty(split)){
                        userS = userS.endsWith(split) ? userS.substring(0, userS.length() - 1) : userS;//如果最后一个是、，就去掉
                    }
                    tv.setText(userS + end);
                    return;
                }
            }
        } else {
            tv.setText(text);
        }
    }
    /*public static void setText2(TextView tv, String text,String end, String split) {
        TextPaint paint = tv.getPaint();
        int tvWidth = (tv.getWidth()-tv.getPaddingLeft()-tv.getPaddingRight());//获取最大显示字符的宽度
        float realWidth = paint.measureText(text);
        if (realWidth > tvWidth) {
            String[] strings = text.split(split);
            String suffix = ""+split+strings[strings.length - 1];
            float textWidth = paint.measureText(suffix);
            float canUseWidth = tvWidth - textWidth;
            for (int i = 1; i < text.length(); i++) {
                if (paint.measureText(text.substring(0, i)) > canUseWidth) {
                    String userS = text.substring(0, i - 1);
                    if(!TextUtils.isEmpty(split)){
                        userS = userS.endsWith(split) ? userS.substring(0, userS.length() - 1) : userS;//如果最后一个是、，就去掉
                    }
                    tv.setText(userS + end);
                    return;
                }
            }
        } else {
            tv.setText(text);
        }
    }*/
    /**
     * 固定TexitView结尾字符串的一个工具类，如果原有的字符串显示不下，就显示原来的一部分加固定结尾
     * @param tv
     * @param text    本来要显示的字符串
     * @param end     固定结尾
     */
    public static void setText(TextView tv, String text, String end) {
        setText(tv, text, end,1,null);
    }
    /**
     * 固定TexitView结尾字符串的一个工具类，如果原有的字符串显示不下，就显示原来的一部分加固定结尾
     * @param tv
     * @param text    本来要显示的字符串
     * @param end     固定结尾
     * @param split   分隔符
     */
    public static void setText(TextView tv, String text, String end,String split) {
        setText(tv, text, end,1,split);
    }
}
