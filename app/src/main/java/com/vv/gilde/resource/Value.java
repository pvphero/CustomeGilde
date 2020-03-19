package com.vv.gilde.resource;

import android.graphics.Bitmap;
import android.util.Log;

import com.vv.gilde.Tool;

/**
 * @author ShenZhenWei
 * @date 2020-03-19
 * <p>
 * Bitmap的封装
 */
public class Value {

    private static final String TAG = "Value";
    //单例模式
    //静态方法区的
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

    private Bitmap mBitmap;
    private int count;//使用计数
    private ValueCallback callback;//监听回调
    private String key;//标记唯一，加密的

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


    //使用一次技术一次 +1

    /**
     * 使用一次计数+1
     */
    public void useAction() {
        Tool.chechNotEmpty(mBitmap);
        if (mBitmap.isRecycled()) {
            Log.i(TAG, "useAction:已经被回收了");
            return;
        }
        Log.i(TAG, "useAction:加一");
        count++;


    }

    //不使用一次，计数一次-1
    public void nonUseAction() {
        if (count-- <= 0) {
            //证明value没有使用了（管理回收操作）
            //告诉外界，回调接口
            callback.valueNonUseListener(key, this);
        }
        Log.i(TAG, "nonUseAction:减一");
    }

    /**
     * 释放
     */
    public void recycleBitmap() {
        if (count > 0) {
            //正在使用中
            Log.i(TAG, "recycleBitmap:引用计数大于0.。。正在使用中，不能释放");
            return;
        }

        if (mBitmap.isRecycled()) {
            Log.i(TAG, "recycleBitmap:引用计数大于0.。。已经被回收了，不能释放");
            return;
        }

        // TODO: 2020-03-19 自己增加校验

        mBitmap.recycle();

        value = null;
        System.gc();
    }

}
