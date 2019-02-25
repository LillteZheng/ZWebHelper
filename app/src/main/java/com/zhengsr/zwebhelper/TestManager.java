package com.zhengsr.zwebhelper;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by zhengshaorui
 * Time on 2019/2/23
 */

public class TestManager {
    private static final String TAG = "TestManager";
    private static class Holder {
        static final TestManager INSTANCE = new TestManager();
    }

    public static TestManager getInstance() {
        return Holder.INSTANCE;
    }

    private TestManager() {
    }
    private View mTestView;
    private Context mContext;
    public void setView(View view){
        WeakReference<View> weakReference = new WeakReference<View>(view);
        mTestView = weakReference.get();
    }

    public void onStop(){
        mTestView = null;
        mContext = null;
    }

    public void register(Context context){
        WeakReference<Context> weakReference = new WeakReference<Context>(context);
        mContext = weakReference.get();
        Log.d(TAG, "zsr --> register: "+mContext);
    }
}
