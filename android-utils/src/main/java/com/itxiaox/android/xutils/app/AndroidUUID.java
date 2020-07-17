package com.itxiaox.android.xutils.app;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Random;
import java.util.UUID;

/**
 * 获取Android 唯一ID
 */
public class AndroidUUID {
    /**
     * 获取Android Id
     *
     * @param context
     * @return
     */
    private static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取Build的部分信息
     *
     * @return
     */
    private static String getBuildInfo() {
        //这里选用了几个不会随系统更新而改变的值
        StringBuffer buildSB = new StringBuffer();
        buildSB.append(Build.BRAND).append("/");
        buildSB.append(Build.PRODUCT).append("/");
        buildSB.append(Build.DEVICE).append("/");
        buildSB.append(Build.ID).append("/");
        buildSB.append(Build.VERSION.INCREMENTAL);
        return buildSB.toString();
//        return Build.FINGERPRINT;
    }

    /**
     * 最终方案，获取设备ID
     *@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
     * API > 9
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static String getDeviceUUID(Context context) {
        String uuid = loadDeviceUUID(context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = buildDeviceUUID(context);
            saveDeviceUUID(context, uuid);
        }
        return uuid;
    }

    private static String buildDeviceUUID(Context context) {
        String androidId = getAndroidId(context);
        if (!"9774d56d682e549c".equals(androidId)) {
            Random random = new Random();
            androidId = Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt());
        }
        return new UUID(androidId.hashCode(), getBuildInfo().hashCode()).toString();
    }


    /**
     *  @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private static void saveDeviceUUID(Context context, String uuid) {
        //保存本地的SP,会因为删除apk导致，sp被清空
        context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE)
                .edit()
                .putString("uuid", uuid)
                .apply();
    }

    @Nullable
    private static String loadDeviceUUID(Context context) {
        return context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE)
                .getString("uuid", null);
    }
}
