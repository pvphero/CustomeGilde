package com.vv.gilde.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;

/**
 * Activity 生命周期 关联 管理
 */
public class ActivityFragmentManager extends Fragment {

    public ActivityFragmentManager() {
    }

    private LifecycleCallback callback;

    @SuppressLint("ValidFragment")
    public ActivityFragmentManager(LifecycleCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 回调回去
        if (callback != null) {
            callback.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 回调回去
        if (callback != null) {
            callback.glideStopAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 回调回去
        if (callback != null) {
            callback.glideRecycleAction();
        }
    }
}
