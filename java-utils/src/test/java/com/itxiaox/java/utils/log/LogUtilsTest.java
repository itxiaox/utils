package com.itxiaox.java.utils.log;

import org.junit.Test;

import static org.junit.Assert.*;

public class LogUtilsTest {

    @Test
    public void print() {
        LogUtils.print("打印输出不换行");
        LogUtils.print("2打印输出不换行");
    }

    @Test
    public void println() {
        LogUtils.println("打印输出换行");
        LogUtils.println("2打印输出换行");
    }

    @Test
    public void testPrintln() {
        LogUtils.println("tag","打印输出换行");
        LogUtils.println("tag","2打印输出换行");
    }
}