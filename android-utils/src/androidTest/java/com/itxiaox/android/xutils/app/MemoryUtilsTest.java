package com.itxiaox.android.xutils.app;


import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MemoryUtilsTest   {
    private static Context mContext;

    @Before
    public void useAppContext() {
        // Context of the app under test.
//        mContext = InstrumentationRegistry.getTargetContext();
        mContext = ApplicationProvider.getApplicationContext();
    }
    @Test
    public void testGetHeapSize() {
        int heapSize = MemoryUtils.getHeapSize(mContext);
        android.util.Log.d("test", "heapSize:"+heapSize);
    }
}