package com.itxiaox.java.utils.log;


import com.itxiaox.java.utils.data.StringUtil;

/**
 * @Title:
 * @Description:
 *
 * @author:
 * @date:
 * @version v1.0
 */
public class LogUtils {

    /**
     * 打印输出,不换行
     * @param msg
     */
    public static void print(String msg){
        System.out.print(msg);
    }

    /**
     * 打印输出，换行
     * @param msg
     */
    public static void println(String msg){
        System.out.println(msg);
    }

    /**
     * 打印输出，换行，带tag
     * @param tag
     * @param msg
     */
    public static void println(String tag,String msg){
        System.out.println(StringUtil.format("%s %s",tag,msg));
    }
}
