package com.zhengsr.zweblib.callback;

/**
 * Created by zhengshaorui
 * Time on 2019/2/22
 */

public interface ZwebLoadListener {
    void onPageStart();
    void onPageProgress(int progress);
    void onPageFinish();
}
