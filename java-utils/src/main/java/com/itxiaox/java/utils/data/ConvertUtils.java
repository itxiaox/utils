package com.itxiaox.java.utils.data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 类型转换工具类
 */
public class ConvertUtils {

    /**
     * 转换成Int , 类型安全转换</br>
     * test ok
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static int toInt(Object value, int defaultValue) {
        if (null == value || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            System.err.println("ConvertUtils-toInt:e=" + e);
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                System.err.println("ConvertUtils-toInt:e=" + e1);
                return defaultValue;
            }
        }
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 将数组转为String
     * 可用于日志打印输出
     *
     * @param args
     * @return
     */
    public static String arraysToString(Object[] args) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                buffer.append(args[i]);
                if (i != args.length - 1) {
                    buffer.append(",");
                }
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * 将集合为String
     * 可用于日志打印输出
     *
     * @param list
     * @return
     */
    public static <T> String listToString(List<T> list) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                buffer.append(list.get(i));
                if (i != list.size() - 1) {
                    buffer.append(",");
                }
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
    /**
     * 将map转为String可用于打印输出
     * 可用于日志打印输出
     * @param map
     * @return
     */
    public static <K, V> String mapToString(Map<K, V> map) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");

        if (map != null && map.size() > 0) {
            int index = 0;
            for (K key : map.keySet()) {
                V value = map.get(key);
                buffer.append(key);
                buffer.append("=");
                buffer.append(value);
                if (index != map.keySet().size() - 1)
                    buffer.append(",");
                index++;
            }
        }
        buffer.append("}");
        return buffer.toString();
    }


    /**
     * float数据转double数据
     * </br>
     * 先将float型转换为字符串型，再转换为精度更高的BigDecimal型，再将其转换为double型。
     *
     * @param d 传入double类型数据
     * @return 返回float类型数据
     * </br>
     * 　JAVA中float为四个字节，double为八个字节，float--->double时候会补位，如果这里补位不出现误差的话应该可以实现。
     * 你先将float类型数据包装成BigDecimal数据，然后调用其floatValue()方法可以实现。
     *      float f = 1.2f;
     *      double d =  (double)f;
     *      像这样强转得到的是：1.2000000476837158
     */
    public static float doubleToFloat(double d) {
        return (float) d;
    }

    /**
     * double数据转float数据
     * @param f 传入float类型数据
     * @return 返回double类型数据
     */
    public static double floatToDouble(float f) {
        BigDecimal b = new BigDecimal(String.valueOf(f));
        return b.doubleValue();
    }
}
