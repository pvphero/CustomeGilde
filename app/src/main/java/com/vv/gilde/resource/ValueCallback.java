package com.vv.gilde.resource;

/**
 * @author ShenZhenWei
 * @date 2020-03-19
 * 专门给Value使用的，如果不在使用的回调接口
 */
public interface ValueCallback {

    /**
     * 监听的方法(Value不在使用了)
     *
     * @param key
     * @param value
     */
    void valueNonUseListener(String key, Value value);

}
