package com.itxiaox.java.utils.data;

import java.util.Arrays;

/**
 * 数组工具类
 */
public class ArraysUtils {

    /**
     * 判断数组中是否存在某个元素
     * @param objects 要判断的数值
     * @param o 要查找的元素
     * @return true:存在，false：不存在
     */
    public static boolean contains(Object[] objects,Object o){
        boolean result = false;
        for (Object obj: objects) {
            if (obj.equals(o)){
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     *  可以使用二分搜索法来搜索指定的数组，以获得指定对象
     * @param objects 要查询的数组
     * @param o 要查询的对象
     * @return 返回搜到对象的索引值 </br>
     * 如果
     */
    public static int search(Object[] objects,Object o){
        return Arrays.binarySearch(objects,o);
    }
}
