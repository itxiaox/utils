package com.itxiaox.java.utils.data;

import org.junit.Test;

public class StringUtils {

    @Test
    public void testCreateRandomStr(){
        String result = StringUtil.createRandomStr(9);
//        String result = StringUtil.createRandomStr(-1);

        System.out.println("result="+result);
    }
}
