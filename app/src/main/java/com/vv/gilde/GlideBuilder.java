package com.vv.gilde;

import android.content.Context;

/**
 * 有很多参数
 * 无限次的添加
 * ....
 * <p>
 * Build ---》 结果
 */
public class GlideBuilder {

    public GlideBuilder(Context context) {

    }

    /**
     * 创建Glide
     *
     * @return
     */
    public Glide build() {
        RequestManagerRetriver requestManagerRetriver = new RequestManagerRetriver();
        Glide glide = new Glide(requestManagerRetriver);
        return glide;
    }

}
