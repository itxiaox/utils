package com.itxiaox.android.xutils.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.text.TextUtils;

import com.itxiaox.android.xutils.file.SPUtils;
import com.itxiaox.android.xutils.log.LogUtils;

import java.security.MessageDigest;
import java.util.List;

/**
 * 跟App相关的辅助类
 * @author xiaoxiao
 * created at 2016/5/4 0004 上午 10:25
 */

public class AppUtils {

    /**
     * 获取应用程序名称
     * test ok
     * @param context 上下文对象
     * @return 返回app的名字
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *test ok
     * @param context 上下文对象
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序内部版本号，用于程序版本更新比较
     *test ok
     * @param context 上下文对象
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 重启APP，Test ok
     * 延迟1s后重启
     * 方法一：使用AlarmManger
     */
    public static void rebootApp(Context mContext) {
        Intent intent = mContext.getPackageManager()
                .getLaunchIntentForPackage(mContext.getPackageName());
        //与正常页面跳转一样可传递序列化数据,在Launch页面内获得
        intent.putExtra("REBOOT", "reboot");
        PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 重启APP，Test ok
     * 方法二：通过设置FLAG_ACTIVITY_CLEAR_TOP
     */
    public static void rebootApp2(Context mContext) {
        Intent intent = mContext.getPackageManager()
                .getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //与正常页面跳转一样可传递序列化数据,在Launch页面内获得
        intent.putExtra("REBOOT", "reboot");
        mContext.startActivity(intent);
    }

    /**
     * 获取应用的签名字符串
     * eg: test by 2019/12/10, eg: 6242e9afac3e75c8a86b81fdf637e49a
     *
     * @param context
     * @return
     */
    public static String getAppSign(Context context) {
        PackageManager manager = context.getPackageManager();
        /** 通过包管理器获得指定包名包含签名的包信息 **/
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        /******* 通过返回的包信息获得签名数组 *******/
        Signature[] signatures = packageInfo.signatures;
        String ss = hexdigest(signatures[0].toByteArray());
        System.out.println("签名：" + ss);
        return ss;
    }


    /**
     * 判断某个界面是否在前台
     * test ok
     *
     * @param activity 要判断的Activity
     * @return 是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }

    /**
     * 判断某个界面是否在前台
     * test ok
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }

    /**
     * 判断特定服务是否存在
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;
            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否安装
     *
     * @param context     上下文对象
     * @param packageName 要判断的应用包名
     * @return 是否安装
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                if (pinfo.get(i).packageName.contains(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将隐式启动转换为显式启动,兼容编译sdk5.0以后版本
     */
    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(implicitIntent, 0);
        if (resolveInfos == null || resolveInfos.size() != 1) {
            return null;
        }
        Intent explicitIntent = null;
        ResolveInfo info = resolveInfos.get(0);
        String packageName = info.serviceInfo.packageName;
        String className = info.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    /**
     * 退出APP
     *
     * </br>
     * 针对单个Activity
     * test ok
     * @param activity
     */
    public static void exitApp(Activity activity) {
        activity.finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 重启Activity
     *test ok
     * @param context 上下文对象
     */
    public static void reStartActivity(Context context) {
        LogUtils.d("reStartActivity");
        //这里用到反射，注意包名
        Class clazz = null;
        try {
            String packName = context.getPackageName();
            clazz = Class.forName(packName+".MainActivity");
            Intent thisIntent = new Intent(context, clazz);
            thisIntent.setAction("android.intent.action.MAIN");
            thisIntent.addCategory("android.intent.category.LAUNCHER");
            thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(thisIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static String hexdigest(byte[] paramArrayOfByte) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            int i = 0;
            int j = 0;
            while (true) {
                if (i >= 16)
                    return new String(arrayOfChar);
                int k = arrayOfByte[i];
                int m = j + 1;
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                j = m + 1;
                arrayOfChar[m] = hexDigits[(k & 0xF)];
                i++;
            }
        } catch (Exception localException) {
        }
        return null;
    }
    private static String hexdigest(String paramString) {
        try {
            String str = hexdigest(paramString.getBytes());
            return str;
        } catch (Exception localException) {
        }
        return null;
    }

    private static final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55,
            56, 57, 97, 98, 99, 100, 101, 102};
}

