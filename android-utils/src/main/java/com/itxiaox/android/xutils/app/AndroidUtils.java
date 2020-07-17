package com.itxiaox.android.xutils.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class AndroidUtils {
    /**
     * 获取Android Id
     *
     * @param context 上下文对象
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.
                getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * 判断当前设备是否是模拟器。
     * @param context 上下文对象
     * @return 如果返回TRUE，则当前是模拟器，不是返回FALSE
     */
    public static boolean isEmulator(Context context){
        try{
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imei = tm.getDeviceId();
            if (imei != null && imei.equals("000000000000000")){
                return true;
            }
            return  (Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"));
        }catch (Exception ioe) {

        }
        return false;
    }
    
}
