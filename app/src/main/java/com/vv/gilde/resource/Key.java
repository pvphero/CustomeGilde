package com.vv.gilde.resource;


import com.vv.gilde.Tool;

/**
 * key  ----->  Bitmap封装   === Value
 */
public class Key {

    private String key; // 合格：唯一 加密的  ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668


    public Key(String path) {
        this.key = Tool.getSHA256StrJava(path);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
