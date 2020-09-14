package com.itxiaox.android.xutils.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

/**
 * 设备信息管理类
 */
public class DeviceUtils {


    /**
     * 获取手机的唯一标识, 此方法是拼接的UUID, 通过IMEI, 序列号，随机码等综合生成，
     * 当没有READ_PHONE_STATE READ_PHONE_STATE
     * 权限的时候，获取的是guid，然后将guid保存到sp中，app卸载重装或者清理sp都会到账guid不同
     * <p>Requires Permission:
     *  {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     * 若没有手机访问权限，则按照随机码生成
     * <p>
     * eg:运行3次，每次生成的不一样 </br>
     * aid40090c44-e2ca-4c05-ac6c-cd421d47e751
     */

    public static String getPhoneSign(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                String imei = tm.getDeviceId();//次方法需要READ_PHONE_STATE READ_PHONE_STATE权限，此为动态权限，需要用户动态授予
                if (!TextUtils.isEmpty(imei)) {
                    deviceId.append("imei");
                    deviceId.append(imei);
                    return deviceId.toString();
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            try {
                //序列号（sn）
                String sn = tm.getSimSerialNumber();//此方法需要动态权限，需要动态授予
                if (!TextUtils.isEmpty(sn)) {
                    deviceId.append("sn");
                    deviceId.append(sn);
                    return deviceId.toString();
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!TextUtils.isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        return deviceId.toString();
    }

    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getSimSerialNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 获取ANDROID_ID
     * <p>
     * 设备在第一次启动时，系统会随机产生一个64位的数字，然后以16进制的形式保存在设备上，且API提供了获取这一参数的方法：
     * 这就是android_id,当设备重新初始化或者刷机的时候，会被重置。
     * 存在的问题
     * 1.不同的设备可能会产生相同的android_id。
     * 2.有的厂商设备无法获取android_id,会返回null。
     * 3.对于CDMA的设备，ANDROID_ID和TelephonyManager.getDeviceId() 的值相同。
     * 4.不同的android系统版本稳定性不同。
     * <p>
     * 作者：繁天涯
     * 链接：https://www.jianshu.com/p/76e4b213307b
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param context 上下文对象
     * @return ANDROID_ID
     */
    public static String getAndroidId(Context context) {
        return android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getTelephoneDeviceId(Context context) {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();//获取设备唯一标识
    }

    /**
     * 获取手机物理地址mac
     * <p>Requires Permission:
     * {@link android.Manifest.permission#ACCESS_WIFI_STATE}
     *
     * @param context 上下文对象
     * @return 返回物理地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getMacAddress(Context context) {
        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager)
                context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.getConnectionInfo()
                .getMacAddress();//获取设备物理地址
    }

    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取当前手机系统语言。
     * eg :zh
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     * eg: [Ljava.util.Locale;@674c29f
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     * eg: 9
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     * eg:SM-T820
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     * eg : samsung
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm.getImei();
        } else {
            return "获取Imei需要 API>=26，即Android 8.0以上版本";
        }
    }

    /**
     * 获取应用内存大小
     * </br>
     * APIs >= 16
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static long getMemorySize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem;

    }

    /**
     * 获取手机分辨率，这是定值
     *
     * @param context
     * @return
     */
    public static String getWH(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels + "x" + outMetrics.heightPixels;
    }

    /**
     * 获取设备名称
     * </br>
     *要求：API>=17
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getDeviceName(Context context) {
        return Settings.Global.getString(context.getContentResolver(),
                Settings.Global.DEVICE_NAME);
    }

    /**
     * 获取CPU信息
     *
     * @return
     */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo;
    }

    /**
     * 得到全局唯一UUID,随机生成的
     */
    private static String getUUID(Context context) {
        String uuid = null;
        SharedPreferences mShare = context.getSharedPreferences("uuid", Context.MODE_PRIVATE);
        if (mShare != null) {
            uuid = mShare.getString("uuid", "");
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            assert mShare != null;
            mShare.edit().putString("uuid", uuid).commit();
        }
        return uuid;
    }

    public static StringBuffer buildInfo(StringBuffer buffer) {
        //主板 示例：FRD-AL00
        buffer.append("\nBOARD:" + Build.BOARD);
        // hardware serial number, if available.（一个硬件的序列码，如果有效的话）
        //设备序列号（有的设备无法获取） 示例：WTK7N16923005607
        buffer.append("\nSERIAL:" + Build.SERIAL);
        //系统启动程序版本号 示例：unknown
        buffer.append("\nBOOTLOADER:" + Build.BOOTLOADER);
        //系统定制商 示例：honor
        buffer.append("\nBRAND:" + Build.BRAND);
        //CPU指令集 示例：arm64-v8a
        buffer.append("\nCPU_ABI:" + Build.CPU_ABI);
        //CPU指令集2 示例：空值
        buffer.append("\nCPU_ABI2:" + Build.CPU_ABI2);
        //设备参数 示例：HWFRD
        buffer.append("\nDEVICE:" + Build.DEVICE);
        //A build ID string meant for displaying to the user 示例：FRD-AL00C00B171
        //30.Build.VERSION.CODENAME 当前开发代号，或者字符串“REL”（如果是正式的发布版本） 示例： REL
        //
        //作者：繁天涯
        //链接：https://www.jianshu.com/p/76e4b213307b
        //来源：简书
        //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
        buffer.append("\nDISPLAY:" + Build.DISPLAY);
        //fingerprint:设备的唯一标识。由设备的多个信息拼接合成。
        //设备指纹
        //理论上讲用这个属性是可以标识一个设备的，但是其实并不是，两台一摸一样的新手机，这个值相同的可能性是很多的。
        //设备指纹（同样的新设备该值应该是一样的） 示例：honor/FRD-AL00/HWFRD:6.0/HUAWEIFRD-AL00/C00B171:user/release-keys
        buffer.append("\nFINGERPRINT:" + Build.FINGERPRINT);
        //硬件名称 示例：hi3650
        buffer.append("\nHARDWARE:" + Build.HARDWARE);
        //示例：huawei-RH2288H-V2-12L
        buffer.append("\nHOST:" + Build.HOST);
        //修订版本列表 示例：HUAWEIFRD-AL00
        buffer.append("\nID:" + Build.ID);
        //产品/硬件的制造商 示例：HUAWEI
        buffer.append("\nMANUFACTURER:" + Build.MANUFACTURER);
        //示例：FRD-AL00
        buffer.append("\nMODEL:" + Build.MODEL);
        //产品的名称 示例：FRD-AL00
        buffer.append("\nPRODUCT:" + Build.PRODUCT);
        //获取无线电固件版本 示例：21.210.03.00.031,21.210.03.00.031
        buffer.append("\nRADIO:" + Build.RADIO);
        // 描述Build的标签（Comma-separated tags describing the build, like "unsigned,debug".） 示例：release-keys
        buffer.append("\nTAGS:" + Build.TAGS);
        //固件推出日期 示例：1477442228000
        buffer.append("\nTIME:" + Build.TIME);
        buffer.append("\nTYPE:" + Build.TYPE);
        buffer.append("\nUNKNOWN:" + Build.UNKNOWN);
        //描述Build的USER 示例：jslave
        buffer.append("\nUSER:" + Build.USER);
        buffer.append("\nVERSION.CODENAME:" + Build.VERSION.CODENAME);
        //源码控制版本号 示例： C00B171
        buffer.append("\nVERSION.INCREMENTAL:" + Build.VERSION.INCREMENTAL);
        //用户可见版本
        buffer.append("\nVERSION.RELEASE:" + Build.VERSION.RELEASE);
        buffer.append("\nVERSION.SDK:" + Build.VERSION.SDK);
        //SDK版本号
        buffer.append("\nVERSION.SDK_INT:" + Build.VERSION.SDK_INT);
        //基带版本 The base OS build the product is based on. 示例：空值
        buffer.append("\nVERSION.BASE_OS:" + Build.VERSION.BASE_OS);
        return buffer;
    }


    /**
     * 获取设备信息</br>
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getDeviceInfo(Context context){
        StringBuffer buffer;
        StringBuffer error;
        buffer = new StringBuffer();
        error = new StringBuffer();

        String deviceName = DeviceUtils.getDeviceName(context);//拿到的是手机型号
        buffer.append("\ndeviceName:");
        buffer.append(deviceName);
        try {
            @SuppressLint("MissingPermission")
            String deviceId = DeviceUtils.getTelephoneDeviceId(context);
            buffer.append("\nTelephoneDeviceId:");
            buffer.append(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
            error.append("\nTelephoneDeviceId:");
            error.append("TelephoneDeviceId:获取失败" + e);

        }

        String brand = DeviceUtils.getDeviceBrand();
        buffer.append("\nbrand:");//获取产品品牌
        buffer.append(brand);

        try {
            @SuppressLint("MissingPermission")
            String imei = DeviceUtils.getIMEI(context);
            buffer.append("\nimei:");//获取手机IMEI
            buffer.append(imei);
        } catch (Exception e) {
            e.printStackTrace();
            error.append("\nimei:");
            error.append("imei:获取失败" + e);
        }

        String android_id = DeviceUtils.getAndroidId(context);
        buffer.append("\nandroid_id:");//获取手机IMEI
        buffer.append(android_id);


        String manufacturer = DeviceUtils.getDeviceManufacturer();
        buffer.append("\nmanufacturer:");//获取手机制造商
        buffer.append(manufacturer);

        try{
            @SuppressLint("MissingPermission")
            String macAddress = DeviceUtils.getMacAddress(context);
            buffer.append("\nmacAddress:");//获取手机物理地址
            buffer.append(macAddress);
        }catch(Exception e){
            error.append("\ngetMacAddress:fail:"+e.getMessage());
        }
        String language = DeviceUtils.getSystemLanguage();
        buffer.append("\nlanguage:");//获取手机语言
        buffer.append(language);

        String getSystemVersion = DeviceUtils.getSystemVersion();
        buffer.append("\nsystemVersion:");//获取手机系统版本
        buffer.append(getSystemVersion);

        String systemModel = DeviceUtils.getSystemModel();
        buffer.append("\nsystemModel(型号):");//获取手机型号
        buffer.append(systemModel);

        try {
            @SuppressLint("MissingPermission")
            String simserialNumber = DeviceUtils.getSimSerialNumber(context);
            buffer.append("\nsimSerialNumber:");//获取手机型号
            buffer.append(simserialNumber);
        } catch (Exception e) {
            e.printStackTrace();
            error.append("\nsimSerialNumber:");
            error.append("simSerialNumber:获取失败" + e);
        }

        long MemorySize = DeviceUtils.getMemorySize(context);
        buffer.append("\nMemorySize:");//获取手机型号
        buffer.append(MemorySize);

        String wh = DeviceUtils.getWH(context);
        buffer.append("\n分辨率:");//获取手机型号
        buffer.append(wh);

        String[] cpuInfo = DeviceUtils.getCpuInfo();
        buffer.append("\ncpu:");//获取手机型号
        if (cpuInfo != null && cpuInfo.length > 0) {
            for (int i = 0; i < cpuInfo.length; i++) {
                buffer.append(cpuInfo[i]);
            }
        }

        buffer = DeviceUtils.buildInfo(buffer);
        return buffer.toString();
    }

    /**
     * 开启震动
     */
    public static void  startDeviceVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        // 判断当前设备是否有震动器
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // long milliseconds：震动毫秒数, int amplitude：震动强度，
                // 该值必须介于 1 ～ 255 之间，或者 DEFAULT_AMPLITUDE
                vibrator.vibrate(VibrationEffect.createOneShot(100,
                        VibrationEffect.DEFAULT_AMPLITUDE));
//              vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        }
    }
}
