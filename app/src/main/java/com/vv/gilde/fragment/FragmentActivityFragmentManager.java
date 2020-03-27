package com.vv.gilde.fragment;

import androidx.fragment.app.Fragment;

/**
 * FragmentActivity 生命周期 关联 管理
 */
public class FragmentActivityFragmentManager extends Fragment {

    public FragmentActivityFragmentManager() {
    }

    private LifecycleCallback callback; // 回调接口

    public FragmentActivityFragmentManager(LifecycleCallback callback) {
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
