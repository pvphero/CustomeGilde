package com.vv.gilde.cache;

import com.vv.gilde.resource.Value;

import org.junit.Test;


/**
 * @author ShenZhenWei
 * @date 2020-03-19
 */
public class MemoryCacheTest {

    @Test
    public void test() {
        MemoryCache memoryCache = new MemoryCache(4);

        memoryCache.put("a", new Value());
        memoryCache.put("ac", new Value());
        memoryCache.put("ac0", new Value());
        memoryCache.put("ac03", new Value());
        memoryCache.put("ac037", new Value());
        memoryCache.put("ac037d", new Value());

        Value value = memoryCache.get("ac037dhsdsdsjhdshds");
        memoryCache.setMemoryCacheCallback(new MemoryCacheCallback() {

            @Override
            public void entryRemovedMemoryCache(String key, Value oldValue) {
                System.out.println("被移除了" + oldValue);
            }

        });
    }

}