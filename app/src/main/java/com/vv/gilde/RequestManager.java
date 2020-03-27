package com.vv.gilde;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.vv.gilde.fragment.ActivityFragmentManager;
import com.vv.gilde.fragment.FragmentActivityFragmentManager;

/**
 * 生命周期 管理
 */
public class RequestManager {

    private final String TAG = RequestManager.class.getSimpleName();

    private final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_NAME";
    private final String ACTIVITY_NAME = "Activity_NAME";

    // 总的环境
    private Context requestManagerContext;

    private static RequestTargetEngine callback;

    private final int NEXT_HANDLER_MSG = 995465; // Handler 标记

    { // 构造代码块，不用再所有的构造方法里面去实例化了，统一的去写
        if (callback == null) {
            callback = new RequestTargetEngine();
        }
    }

    /**
     * 可以管理生命周期 -- FragmentActivity是有生命周期方法
     *
     * @param fragmentActivity
     */
    FragmentActivity fragmentActivity;

    public RequestManager(FragmentActivity fragmentActivity) { // this == fragmentActivity
        this.fragmentActivity = fragmentActivity;
        requestManagerContext = fragmentActivity;

        // 开始绑定操作
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();

        // 拿到Fragment
        Fragment fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if (null == fragment) { // 如果等于null，就要去创建Fragment
            fragment = new FragmentActivityFragmentManager(callback);

            // 添加到管理器 -- fragmentManager.beginTransaction().add..
            supportFragmentManager.beginTransaction().add(fragment, FRAGMENT_ACTIVITY_NAME).commitAllowingStateLoss(); // 提交
        }

        // TODO
        // 测试下面的话，这种测试，不能完全准确
        // 证明是不是排队状态
        Fragment fragment2 = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragment2" + fragment2); // null ： 还在排队中，还没有消费

        // 添加进去 提交之后 可能还处于 排队状态，想让马上干活 （Android 消息机制管理），快速干活，发了一次Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }

    /**
     * 可以管理生命周期 -- Activity是有生命周期方法的(Fragment)
     *
     * @param activity
     */
    public RequestManager(Activity activity) { // @2
        this.requestManagerContext = activity;

        // 开始绑定操作
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();

        // 拿到Fragment
        android.app.Fragment fragment = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        if (null == fragment) { // 如果等于null，就要去创建Fragment
            fragment = new ActivityFragmentManager(callback);

            // 添加到管理器 -- fragmentManager.beginTransaction().add..
            fragmentManager.beginTransaction().add(fragment, ACTIVITY_NAME).commitAllowingStateLoss(); // 提交  @3
        }

        // TODO 测试
        android.app.Fragment fragment2 = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragment2" + fragment2); // null ： 还在排队中，还没有消费

        // 发送一次Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }

    /**
     * 目前没有做管理  第三个构造函数
     *
     * @param context
     */
    public RequestManager(Context context) {
        this.requestManagerContext = context;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Fragment fragment2 = fragmentActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
            Log.d(TAG, "Handler: fragment2" + fragment2); // 有值 ： 不在排队中，所以有值
            return false;
        }
    });

    /**
     * TODO 用户调用的
     * load 拿到要显示的图片路径
     *
     * @param path
     * @return
     */
    public RequestTargetEngine load(String path) {
        // 移除掉
        mHandler.removeMessages(NEXT_HANDLER_MSG);

        // 下次 全部串起来
        // ...

        // 把值传递给 资源加载引擎
        callback.loadValueInitAction(path, requestManagerContext);

        return callback;
    }

}
