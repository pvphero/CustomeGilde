package com.vv.gilde;

import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * @author ShenZhenWei
 * @date 2020-03-19
 */
public class Tool {


    public static void chechNotEmpty(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalStateException("不能为null");
        }
    }

    public static void chechNotEmpty(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalStateException("bitmap 不能为null");
        }
    }
}
