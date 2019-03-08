package com.loyo.library.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;

/**
 * 拦截textview中的网址链接，自定义跳转
 * Created by android on 2017/12/13.
 */

public class HyperLinkUtil {
    public static void interceptHyperLink(TextView tv, String content, CustomUrlSpan.OnLinkClickListener listener) {
        tv.setText(content);
        //这里让其自动生成UrlSpan，方便后面遍历UrlSpan覆盖使用
        Linkify.addLinks(tv,Linkify.WEB_URLS);
//        tv.setAutoLinkMask(Linkify.WEB_URLS);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {

            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();

            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
            // 循环遍历并拦截 所有http://开头的链接
            for (URLSpan uri : urlSpans) {
//                spannable.removeSpan(uri);
                String url = uri.getURL();

                CustomUrlSpan customUrlSpan = new CustomUrlSpan(url);
                customUrlSpan.setOnLinkClickListener(listener);
                spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                        spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            }
            //这里需要移除自动生成的链接，以免覆盖我们自定义的链接逻辑
            Linkify.addLinks(tv,0);
            tv.setText(spannableStringBuilder);
        }
    }
}
