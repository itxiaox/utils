package com.itxiaox.java.utils.data;

/**
 * 进制转换工具类
 * 提供16进制，八进制，二进制，之间的相互转换，其他进制的转换都是通过十进制来进行的
 */
public class HexUtils {
    /**
     * 10进制转成16进制
     * @param n
     * @return
     */
    public String ten2sixteen(int n){
        return Integer.toHexString(n);
    }

    /**
     * @description:
     * @author: xiao
     * @date: 2019/4/28 17:36
     * @return:
     */
    /**
     * 十进制转成八进制
     * @param n
     * @return
     */
    public String ten2eight(int n){
        return Integer.toOctalString(n);
    }

    /**
     * 十进制转二进制
     * @param n
     * @return
     */
    public String ten2two(int n){
        return Integer.toBinaryString(n);
    }

    /**
     * 十六进制转成十进制
     */
    public int sixteen2ten(String txt){
        return  Integer.valueOf(txt,16);
    }

    /**
     * 八进制转成十进制
     * @param txt
     * @return
     */
    public int eight2ten(String txt){
        return Integer.valueOf(txt,8);
    }

    /**
     * 二进制转成十进制
     * @param txt
     * @return
     */
    public int two2ten(String txt){
        return Integer.valueOf(txt,2);
    }
}
