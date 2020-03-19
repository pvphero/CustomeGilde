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
 * @author ShenZhenWei
 * @date 2020-03-19
 * 活动缓存-》》正在被使用的资源
 */
public class ActiveCache {

    //1.容器
    private Map<String, WeakReference<Value>> mapList = new HashMap<>();

    private ReferenceQueue<Value> queue;//监听弱引用

    private ValueCallback valueCallback;

    private Thread thread;
    private boolean isCloseThread;//死循环的标记
    private boolean isManualRemove;//为了控制手动被动

    public ActiveCache(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    //添加
    public void put(String key, Value value) {
        Tool.chechNotEmpty(key);

        //绑定value的监听（有依赖）
        value.setCallback(valueCallback);

        //储存--》容器
//        mapList.put(key, new CustomWeakReference(),key)//如果这么写，无法扩展
        mapList.put(key, new CustomWeakReference(value, getQueue(), key));
    }

    /**
     * 手动移除
     */
    public Value remove(String key) {
        isManualRemove = true;
        WeakReference<Value> remove = mapList.remove(key);
        //被动移除开始生效
        isManualRemove = false;

        if (null != remove) {
            return remove.get();
        }
        return null;
    }

    /**
     * 关闭线程
     */
    public void closeThread() {
        isCloseThread = true;
//        //中断线程
//        if (null!=thread){
//            thread.interrupt();//中断线程
//
//            try {
//                thread.join(TimeUnit.SECONDS.toMillis(5));
//                if (thread.isAlive()){
//                    //5秒还停不下来
//                    throw new IllegalSelectorException("活动缓存中，关闭线程，无法停下来");
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        mapList.clear();
        System.gc();
    }

    /**
     * 弱引用管理器
     */
    public class CustomWeakReference extends WeakReference<Value> {

        //没办法监听GC什么时候被回收了
//        public CustomWeakReference(Value referent) {
//            super(referent);
//        }
        private String key;

        public CustomWeakReference(Value referent, ReferenceQueue<? super Value> queue, String key) {
            super(referent, queue);
            this.key = key;
        }
    }

    private ReferenceQueue<Value> getQueue() {
        if (null == queue) {
            queue = new ReferenceQueue<>();
            //死循环不停跑，【加线程】
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (!isCloseThread) {
                        try {
                            //如果是 手动移除，不能干活
                            Reference<? extends Value> remove = queue.remove();//阻塞式，什么时候被回收，就释放
                            if (!isManualRemove) {

                                //开始干活吧  ---- TODO 被动移除
                                CustomWeakReference weakReference = (CustomWeakReference) remove;
                                if (mapList != null && !mapList.isEmpty()) {
                                    mapList.remove(weakReference.key);
                                }

                                // TODO: 2020-03-19
                                //扩展。。。。
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
        return queue;
    }


}
