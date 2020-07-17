package com.itxiaox.android.xutils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.ColorRes;

public class ContextUtils {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    /**
     * 初始化Context 在Application的onCreate方法中
     * @param context
     */
    public static void init(Context context){
        mContext = context.getApplicationContext();
    }

    /**
     * 获取全局Context </br>
     * 使用之前注意调用init </br>
     * 在其它需要使用Context的地方，可以通过这个获取
     * @return 返回全局Context
     */
    public static Context getContext(){
        return mContext;
    }

    /**
     * 获取颜色资源
     * @param colorId
     * @return
     */
    public static int getColor(@ColorRes  int colorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mContext.getResources().getColor(colorId,null);
        }else {
            return mContext.getResources().getColor(colorId);
        }
    }

    /**
     * 获取应用包名
     * @return
     */
    public static String getPackName(){
       return mContext.getPackageName();
    }

    /**
     * 获取MainActivity类名
     * </br>
     * <package>.MainActivity
     * @return
     */
    public static String getActivityName(){
        return getPackName()+".MainActivity";
    }
    /**
     * 获取MainActivity
     * </br>
     * <package>.MainActivity
     * @return
     */
    public static Activity getActivity() throws Exception {
        Class claz = Class.forName(getActivityName());
        return (Activity) claz.newInstance();
    }

    private static PackageInfo packageInfo;

    private static PackageInfo createPackageInfo(){
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = mContext.getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            packageInfo = packageManager.getPackageInfo(
                    mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }
    /**
     *获取版本名称
     * @return
     */
    public static String getVersionName() {
        if (packageInfo==null) {
           packageInfo = createPackageInfo();
        }
        return packageInfo!=null?packageInfo.versionName:"";
    }

    /**
     * 获取版本号
     * @return
     */
    public static int getVersionCode(){
        if (packageInfo==null){
            packageInfo = createPackageInfo();
        }
        return packageInfo!=null?packageInfo.versionCode:0;
    }
    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public static String getString(int resId,Object... formatArgs){
       return mContext.getString(resId,formatArgs);
    }
}
