package com.vv.gilde.cache;

import com.vv.gilde.resource.Value;

/**
 * @author ShenZhenWei
 * @date 2020-03-19
 * 内存缓存中，LRU移除，回调的接口
 */
public interface MemoryCacheCallback {

    void entryRemoveMemoryCache(String key, Value value);
}
