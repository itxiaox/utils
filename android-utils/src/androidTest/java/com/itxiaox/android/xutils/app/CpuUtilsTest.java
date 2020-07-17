package com.itxiaox.android.xutils.app;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class CpuUtilsTest {

    private static Context mContext;
//    @Rule
//    public final ActivityTestRule<MainActivity> main =
//            new ActivityTestRule<>(MainActivity.class, true);

    @Before
    public void useAppContext() {
        // Context of the app under test.
//        mContext = InstrumentationRegistry.getTargetContext();
        mContext = ApplicationProvider.getApplicationContext();

    }
    @Test
    public void testGetMaxCpuFreq() {
       String cpuName =  CpuUtils.getCpuName();
       //"0"
        System.out.println("cpuName:"+cpuName);
    }
    @Test
    public void testGetMinCpuFreq() {
        String minCpuFreq =  CpuUtils.getMinCpuFreq();
        System.out.println("minCpuFreq:"+minCpuFreq);
    }

    @Test
    public void testGetCurCpuFreq() {
        String currCpuFreq =  CpuUtils.getCurCpuFreq();
        System.out.println("currCpuFreq:"+currCpuFreq);
    }

    public void testGetCpuName() {
    }
}