package com.zhengsr.zwebhelper;

import android.view.View;

/**
 * Created by zhengshaorui
 * Time on 2019/2/23
 */

public class TestManager {
    private static class Holder {
        static final TestManager INSTANCE = new TestManager();
    }

    public static TestManager getInstance() {
        return Holder.INSTANCE;
    }

    private TestManager() {
    }
    private View mTestView;
    public void setView(View view){
        mTestView = view;
    }
}
