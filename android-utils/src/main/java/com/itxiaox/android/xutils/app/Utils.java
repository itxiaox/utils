package com.itxiaox.android.xutils.app;

import android.content.Context;

import com.itxiaox.android.xutils.ui.DensityUtils;

/**
 *
 * android-utils统一初始化类
 * Author:xiao
 * Time: 2020/7/17 21:27
 * Description:This is Utils
 */
public class Utils {
    /**
     * 初始化，在Application#onCreate中调用
     * @param context
     */
    public static void init(Context context){
        //初始化context
        ContextUtils.init(context.getApplicationContext());
        DensityUtils.init(context.getApplicationContext());
    }

}
