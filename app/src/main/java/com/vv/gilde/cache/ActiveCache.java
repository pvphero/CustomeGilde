package com.vv.gilde.cache;

import com.vv.gilde.Tool;
import com.vv.gilde.resource.Value;
import com.vv.gilde.resource.ValueCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动缓存 --》 正在被使用的资源
 */
public class ActiveCache {

    // 容器
    private Map<String, WeakReference<Value>> mapList = new HashMap<>();
    private ReferenceQueue<Value> queue; // 监听弱引用
    private Thread thread; // 线程--》 死循环
    private boolean isCloseThread; // 死循环的标记
    private boolean isShoudonRemove; // 为了控制 手动移除 和 被动移除 的冲突

    private ValueCallback valueCallback;

    public ActiveCache(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    /**
     * TODO 添加 活动缓存
     */
    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);

        // 绑定Value的监听（有依赖）
        value.setCallback(valueCallback);

        // 存储 --》 容器
        // mapList.put(key, new CustomWeakReference(), key); // 如果这样写了，无法扩展了
        mapList.put(key, new CustomWeakReference(value, getQueue(), key));
    }

    /**
     * TODO 给外界获取Value
     * @param key
     * @return
     */
    public Value get(String key) {
        WeakReference<Value> valueWeakReference = mapList.get(key);
        if (null != valueWeakReference) {
            return valueWeakReference.get(); // 返回Value
        }
        return null;
    }

    /**
     * TODO 手动 移除
     */
    public Value remove(String key) {
        isShoudonRemove = true; // 不要去被动移除
        WeakReference<Value> remove = mapList.remove(key);
        isShoudonRemove = false; // 被动移除开始生效

        if (null != remove) {
            return remove.get();
        }
        return null;
    }

    /**
     * TODO 释放 关闭 线程
     */
    public void coloseThread() {
        isCloseThread = true;

        // 中断线程 -- 高版本有问题，所以注释
        /*if (thread != null) {
            thread.interrupt(); // 中断线程

            try {
                thread.join(TimeUnit.SECONDS.toMillis(5)); // 线程稳定的停止下了

                if (thread.isAlive()) { // 5秒 还听不下了
                    throw new IllegalStateException("活动缓存中，关闭线程，无法停止下了");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        mapList.clear();

        System.gc();
    }

    /**
     * 弱引用管理器
     * 监听什么时候被回收了
     */
    public class CustomWeakReference extends WeakReference<Value> {

        // 没有办法去监听，什么时候GC回收了
        /*public CustomWeakReference(Value referent) {
            super(referent);
        }*/

        private String key;

        public CustomWeakReference(Value referent, ReferenceQueue<? super Value> queue, String key) {
            super(referent, queue);
            this.key = key;
        }
    }

    /**
     * 监听 什么 时候 被回收
     *
     * @return
     */
    private ReferenceQueue<Value> getQueue() {
        if (queue == null) {
            queue = new ReferenceQueue<>();

            // 死循环不停的跑【加线程】
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();

                    while (!isCloseThread) { // 这个循环如何结束？
                        try {

                            // 如果是 手动移除，不能干活
                            if (!isShoudonRemove) {
                                // queue.remove(); 同学们这是：阻塞式的方法

                                // TODO 后续一定要调试
                                Reference<? extends Value> remove = queue.remove(); // 阻塞式 等待：什么时候被回收 就释放

                                // 开始干活 -- TODO 被动移除
                                CustomWeakReference weakReference = (CustomWeakReference) remove;

                                if (mapList != null && !mapList.isEmpty()) {
                                    mapList.remove(weakReference.key); // 容器里面的内容移除
                                }

                                // .....
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            thread.start();
        }

        return queue;
    }
}
