package com.loyo.library.util;

import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;


/**
 *
 * Created by android on 2017/12/13.
 */

public class CustomUrlSpan extends URLSpan {
    private String url;
    private OnLinkClickListener mListener;
    public CustomUrlSpan(@NonNull String url){
        super(url);
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        // 在这里可以做任何自己想要的处理
        if (mListener != null){
            mListener.onClick(url);
        }
    }
    public void setOnLinkClickListener(OnLinkClickListener mListener){
        this.mListener =mListener;
    }

    public interface OnLinkClickListener{
        void onClick(String url);
    }
}
