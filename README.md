# 1.Gilde 资源封装

Key--对Value的唯一性描述 Value--Bitmap的封装（+1，-1，释放）

# 2.活动缓存


![avatar](./media/image/active_cache.png)

GC全盘扫描 判断 你的弱引用，没有被回收
- 1.回收机制：GC扫描的时候回收，监听弱引用（容器 移除）【被动移除】
- 2.手动移除
- 3.专门关闭线程
- 4.添加
- 5.Value 和Callback 进行关联

**为什要活动缓存** 活动缓存：正在使用的图片
内存：LRU管理的，maxSize，如果最少使用，内部算法会被回收（不稳定，不安全）

![avatar](./media/image/LRU.png)

你正在使用的图片---【活动缓存】 如果不用了，才会扫描回收【存入移除非常快】

活动缓存跟内存缓存两者只能选一，两者互斥
如果没有活动缓存，只有内存缓存，LRU最大容量控制，最少使用的话会被释放，不安全，不稳定

        //最开始的时候 3.0之前
        //int result =bitmap.getRowBytes();
        
        //API Android 3.0
        //int result=bitmap.getByteCount();
        
        //API Android 19 4.4
        //int result=bitmap.getAllocationByteCount();

内存是20 bitmap.getByteCount()复用内存红色区域的内存值==8；
bitmap.getAllocationByteCount()复用红色区域的内存值 +黑色区域剩余的值==
8+（20-8）==20

![avatar](./media/image/recycle_bitmap.png)

活动缓存：正在使用的图片，都放在活动缓存（弱引用 GC没有使用了 已回收 被回收） 内存:lru
管理 临时存放 活动缓存 不使用Value【资源封装 key value】

