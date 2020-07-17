package com.itxiaox.java.utils.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * MathUtils工具类
 * </r>
 *  常用的math转换，如保留两位小数，保留给金额格式
 * @author: xiaox
 * @date:  2019/4/29 12:40
 * @version v1.0
 */
public class MathUtils {

    /**
     * 将double 类型转换为2位小数的字符串
     * @return
     */
    public static String getDouble(double d){
        BigDecimal bd=new BigDecimal(d);
        return parseMoney(",###,##0.00",bd);
    }

    /**
     * 将double 类型转换为2位小数的字符串
     */
    public static String getDouble2(double d){
        BigDecimal bd = new BigDecimal(d);
        BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd2.toString();
    }

    private static String parseMoney(String pattern, BigDecimal bd) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }

    /**
     * 用于格式化小数点,默认为四舍五入
     * BigDecimal.ROUND_HALF_UP 四舍五入
     * @param f
     * @param newScale 代表保留几位小数
     */
    public static double roundTo2(double f, int newScale) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
