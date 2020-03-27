package com.vv.gilde.fragment;

// Fragment 监听
public interface LifecycleCallback {

    // 生命周期 开始初始化了
    void glideInitAction();

    // 生命周期 停止了
    void glideStopAction();

    // 生命周期 释放操作
    void glideRecycleAction();

}
