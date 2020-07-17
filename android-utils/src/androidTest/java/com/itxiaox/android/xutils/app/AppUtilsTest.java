package com.itxiaox.android.xutils.app;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppUtilsTest {
    private static final String TAG = "AppUtilsTest";
    private Context mContext;
    @Before
    public void useAppContext() {
        // Context of the app under test.
        mContext = InstrumentationRegistry.getTargetContext();

    }
    @Test
    public void getAppSign() {
        String sign = AppUtils.getAppSign(mContext);
        Log.i(TAG, "getAppSign: "+sign);
    }
}