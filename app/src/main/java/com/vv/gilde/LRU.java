package com.vv.gilde;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU {

    public static void main(String[] args) {

        // 访问排序
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(0, 0.75f, true);

        map.put("一", 1); // 最开始添加的，它的LRU算法移除是最高的（越容易被回收）
        map.put("二", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);// 最开始添加的，它的LRU算法移除是最高的（越难被回收）

        map.get("三"); // 目前最难被回收了，因为使用了，属于非最少使用

        for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
            System.out.println(stringIntegerEntry.getValue());
        }

    }

}
