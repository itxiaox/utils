package com.itxiaox.android.xutils.ui;

import android.content.Context;
import android.os.Looper;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.itxiaox.android.xutils.file.SPUtils;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Handler;

@RunWith(AndroidJUnit4.class)
public class UIHelperTest extends TestCase {
    Context appContext;
    @Before
    public void init() {
        appContext = InstrumentationRegistry.getTargetContext();
        SPUtils.init(appContext, "sp-test");
    }
    @Test
    public void testShowToast() {

//        UIHelper.showToast(appContext,"测试Toast工具类，%s","2332");
    }
}