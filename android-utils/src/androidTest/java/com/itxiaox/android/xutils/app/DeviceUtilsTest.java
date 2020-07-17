package com.itxiaox.android.xutils.app;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.itxiaox.android.xutils.log.LogUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DeviceUtilsTest {

    private static final String TAG = "DeviceUtilsTest";


    //    @Rule
//    public final ActivityTestRule<MainActivity> main =
//            new ActivityTestRule<>(MainActivity.class, true);
    private static Context mContext;

    @Before
    public void useAppContext() {
        // Context of the app under test.
//        mContext = InstrumentationRegistry.getTargetContext();
        mContext = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testDeviceInfo() {
//        getSystemLanguage();
//        getSystemLanguageList();
//        getSystemVersion();
//        getSystemModel();
//        getDeviceBrand();
//        getIMEI();
        String device_guid = DeviceUtils.getPhoneSign(mContext);
        Log.d(TAG, "device_guid: " + device_guid);
        //aid429ae2c3-2a1b-44f3-b744-6bd82ee30ed0
        //aid40090c44-e2ca-4c05-ac6c-cd421d47e751
        //aid842f2b58-fc8b-49fc-8797-afb357e4960b
    }

    @Test
    public void getSystemLanguage() {
        Log.d(TAG, "" + DeviceUtils.getSystemLanguage());
    }

    @Test
    public void getSystemLanguageList() {
        Log.d(TAG, "" + DeviceUtils.getSystemLanguageList());
    }

    @Test
    public void getSystemVersion() {
        Log.d(TAG, "" + DeviceUtils.getSystemVersion());
    }

    @Test
    public void getSystemModel() {
        Log.d(TAG, "" + DeviceUtils.getSystemModel());
    }

    @Test
    public void getDeviceBrand() {
        Log.d(TAG, "" + DeviceUtils.getDeviceBrand());
    }

    @Test
    public void getIMEI() {
        Log.d(TAG, "" + DeviceUtils.getIMEI(mContext));
    }

    @Test
    public void test() {
        Context context = mContext;
        String result = DeviceUtils.getDeviceInfo(context);
        System.out.println("result:" + result);
        LogUtils.d("device-test:" + result);
    }
}