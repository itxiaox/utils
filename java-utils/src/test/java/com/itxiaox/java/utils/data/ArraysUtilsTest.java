package com.itxiaox.java.utils.data;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ArraysUtilsTest {

    @Test
    public void contains() {
        Integer[] data = {0,-1,1,3,5,2,7,9};
        boolean contains = ArraysUtils.contains(data,-1);
        boolean contains2 = ArraysUtils.contains(data,0);
        System.out.println("contains:"+contains);
        System.out.println("contains2:"+contains2);
    }

    @Test
    public void search() {
    }
}