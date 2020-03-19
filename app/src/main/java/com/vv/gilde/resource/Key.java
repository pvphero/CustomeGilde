package com.vv.gilde.resource;

import com.vv.gilde.utils.EncryptUtils;

/**
 * @author ShenZhenWei
 * @date 2020-03-19
 */
public class Key {
    //合格加密类型
    private String key;

    public Key(String key) {
        this.key = EncryptUtils.encryptSHA256ToString(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
