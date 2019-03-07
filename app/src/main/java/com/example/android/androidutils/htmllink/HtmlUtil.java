package com.loyo.oa.v2.tool;

import android.text.Html;
import android.widget.TextView;

import com.loyo.oa.v2.activityui.followup.HtmlImageGetter;

/**
 * Created by adnroid on 17/6/9.
 */

public class HtmlUtil {
    public static void showHtml(TextView textView, String resource, SpanClickListener listener) {
        HtmlImageGetter getter = new HtmlImageGetter(textView,resource);
//        textView.setText(Html.fromHtml(resource, getter, null));
        textView.setText(Html.fromHtml(resource,getter,null));
        textView.setMovementMethod(new LinkMovementMethodExt(listener));
    }
}
