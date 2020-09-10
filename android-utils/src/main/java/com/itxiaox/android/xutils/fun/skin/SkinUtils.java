package com.itxiaox.android.xutils.fun.skin;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;

/**
 * App皮肤工具类
 */
public class SkinUtils {

    /**
     * App黑白化方案
     * 通过ColorMatrix颜色矩阵， 设置ColorMatrix.setSaturation(0)
     * @param activity 当前Activity
     */
    public static void skinBackAndWhite(Activity activity){
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        View view = window.getDecorView();
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        // 关键起作用的代码，Saturation，翻译成中文就是饱和度的意思。
        // 官方文档说明：A value of 0 maps the color to gray-scale. 1 is identity.
        // 原来如此，666
        cm.setSaturation(0f);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        view.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }
}
