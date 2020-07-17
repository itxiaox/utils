package com.itxiaox.java.utils.validate;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateUtilsTest {

    @Test
    public void isIDCard() {
        String id = "421124199101034514";
        System.out.println(ValidateUtils.isIDCard(id));
    }
}