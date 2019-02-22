package com.zhengsr.zweblib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by zhengshaorui
 * Time on 2019/2/21
 */

public class ZwebView extends WebView {
    private ScrollListener mOnScrollChangedCallback;
    public ZwebView(Context context) {
        super(context);
    }

    public ZwebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(this,l,t,oldl,oldt);
        }
    }

    public ScrollListener getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setScrollListener(
            final ScrollListener onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public static interface ScrollListener {
        public void onScroll(View view, int left, int top , int oldLeft, int oldTop);
    }
}
