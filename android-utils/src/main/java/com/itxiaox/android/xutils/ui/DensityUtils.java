package com.itxiaox.android.xutils.ui;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Objects;

/**
 * update by xiao on 2018/7/18
 */

public class DensityUtils {
    private static float density;
    private static int screenWidth;
    private static int screenHeight;

    /**
     * 初始化，推荐在Application#onCreate
     * @param context 上下文对象
     */
    public static void init(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(mWindowManager).getDefaultDisplay().getRealMetrics(metric);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mWindowManager.getDefaultDisplay().getRealMetrics(metric);
            }else {
                mWindowManager.getDefaultDisplay().getMetrics(metric);
            }
        }
        density =  metric.density;
        screenHeight = metric.heightPixels;
        screenWidth = metric.widthPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return Math.round(dpValue * density);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2pxF(float dpValue) {
        return dpValue * density;
    }

}