package com.vv.gilde.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vv.gilde.resource.Value;

/**
 * LRU 内存缓存
 * 回收机制：LRU最少使用
 */
public class MemoryCache extends LruCache<String, Value> {

    private MemoryCacheCallback memoryCacheCallback;
    private boolean shoudonRemove;

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        this.memoryCacheCallback = memoryCacheCallback;
    }

    /**
     * 传入元素最大值，给LruCache
     * @param maxSize
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    // TODO　手动的移除
    public Value shoudonRemove(String key) {
        shoudonRemove = true; // 被动移除失效
        Value value = remove(key);
        shoudonRemove = false; // 恢复被动移除   // !shoudonRemove == 被动的
        return value;
    }

    /**
     * 代表被移除了
     * 1.重复的key
     * 2.最近最少使用的元素移除（当我们的元素 大于了 maxsize）
     *
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, @NonNull String key, @NonNull Value oldValue, @Nullable Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);

        // 被移除
        if (memoryCacheCallback != null && !shoudonRemove) { // 同学们注意： !shoudonRemove == 被动的
            memoryCacheCallback.entryRemovedMemoryCache(key, oldValue); // 被动移除
        }
    }

    /**
     * 每一个元素的大小  == Bitmap 的 大小
     * @param key
     * @param value
     * @return
     */
    // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected int sizeOf(@NonNull String key, @NonNull Value value) {
        Bitmap bitmap = value.getmBitmap();

        // 最开始的时候
        // int result = bitmap.getRowBytes();

        // API 12  3.0
        // int result = bitmap.getByteCount();

        // API  19 4.4
        // int result = bitmap.getAllocationByteCount();

        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }

        return bitmap.getByteCount();
    }
}
