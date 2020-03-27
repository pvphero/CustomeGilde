package com.vv.gilde.resource;

import android.graphics.Bitmap;
import android.util.Log;

import com.vv.gilde.Tool;


/**
 * Bitmap的封装
 */
public class Value {

    private final static String TAG = Value.class.getSimpleName();

    // 单例模式
    private static Value value;

    public static Value getInstance() {
        if (null == value) {
            synchronized (Value.class) {
                if (null == value) {
                    value = new Value();
                }
            }
        }
        return value;
    }

    private Bitmap mBitmap; // 位图
    private int count; // 使用计数
    private ValueCallback callback; // 监听回调
    private String key; // key标记 唯一的

    // TODO　下面是 Get Set 系列
    public static Value getValue() {
        return value;
    }

    public static void setValue(Value value) {
        Value.value = value;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ValueCallback getCallback() {
        return callback;
    }

    public void setCallback(ValueCallback callback) {
        this.callback = callback;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    /**
     * TODO 使用一次 计数一次 + 1
     */
    public void useAction() {
        Tool.checkNotEmpty(mBitmap); // 如果为null，就抛出异常

        if (mBitmap.isRecycled()) { // 已经被回收了
            Log.d(TAG, "useAction: 已经被回收了");
            return;
        }
        Log.d(TAG, "useAction: 加一 count:" + count);
        count++;
    }

    /**
     * TODO  不使用一次(使用完成) 计数一次 - 1
     */
    public void nonUseAction() {
        // + 1 = 1
        // - 1 = 0

        count--;
        if (count <= 0 && callback != null) {
            // 证明我们的Value没有使用（管理回收）
            // 告诉外界，回调接口
            callback.valueNonUseListener(key, this); // 活动缓存管理监听
        }
        Log.d(TAG, "nonUseAction: 减一 count:" + count);
    }

    /**
     * TODO　释放
     */
    public void recycleBitmap() {
        if (count > 0) { // 正在使用中...
            Log.d(TAG, "recycleBitmap: 引用计数大于0，正在使用中...，不能释放");
            return;
        }

        if (mBitmap.isRecycled()) {
            Log.d(TAG, "recycleBitmap: 都已经被回收了，不能释放");
            return;
        }

        // 同学们自己增加
        // ...

        mBitmap.recycle();

        value = null;

        System.gc();
    }

}
