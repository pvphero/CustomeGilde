package com.vv.gilde.cache;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import com.vv.gilde.resource.Value;

/**
 * @author ShenZhenWei
 * @date 2020-03-19
 * <p>
 * LRU内存缓存机制
 */
public class MemoryCache extends LruCache<String, Value> {

    private MemoryCacheCallback memoryCacheCallback;

    private boolean isManualfRemove;

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        this.memoryCacheCallback = memoryCacheCallback;
    }

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    // TODO: 2020-03-19 手动移除
    public Value manualRemove(String key) {
        //被动移除失效
        isManualfRemove = true;
        Value value = remove(key);
        //恢复被动移除
        isManualfRemove = false;
        return value;
    }

    /**
     * 代表被移除了
     * 1.重复的KEY
     * 2.最近最少使用的元素移除（当我们的元素大于maxSize）
     *
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, @NonNull String key, @NonNull Value oldValue, @Nullable Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        //被移除了
        if (memoryCacheCallback != null && !isManualfRemove) {
            //被动移除
            memoryCacheCallback.entryRemoveMemoryCache(key, oldValue);
        }
    }

    /**
     * 每一个元素的大小 ==bitmap的大小
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    protected int sizeOf(@NonNull String key, @NonNull Value value) {
        Bitmap bitmap = value.getmBitmap();
        //最开始的时候 3.0之前
        //int result =bitmap.getRowBytes();

        //API Android 3.0
        //int result=bitmap.getByteCount();

        //API Android 19 4.4
        int result = bitmap.getAllocationByteCount();


        return result;
    }
}
