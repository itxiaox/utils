package com.itxiaox.android.xutils.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.itxiaox.android.xutils.app.ContextUtils;
import com.itxiaox.java.utils.data.StringUtil;

/**
 * Created by Xiaox on 2016/4/4.
 */
public class UIHelper {


    /**
     * 给TextViev赋值
     *
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, String text) {
        if (textView != null) {
            if (!TextUtils.isEmpty(text)) {
                textView.setText(text);
            } else {
                textView.setText("");
            }
        }
    }

    /**
     * 给EditText赋值
     *
     * @param textView
     * @param text
     */
    public static void setText(EditText textView, String text) {
        if (textView != null) {
            if (!TextUtils.isEmpty(text)) {
                textView.setText(text);
            } else {
                textView.setText("");
            }
        }
    }

    /**
     * Toast显示
     * @param context
     * @param content
     */
    public static Toast toast;

    /**
     * 单例显示Toast, 只支持Android 4.2以下版本
     * Android 4.2之后不支持此种方法，使用会报错：
     * java.lang.RuntimeException: This Toast was not created with Toast.makeText()
     * 此方法被废弃掉
     * @param context
     * @param format
     * @param args
     * @return
     */
    @Deprecated
    public static Toast showToast(Context context, String format,
                                  Object... args) {
        if (toast==null){
            toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setText(StringUtil.format(format, args));
        toast.show();
        return toast;
    }


    /**
     * 单例显示Toast
     * @param context 上下文对象
     * @param format 字符串format
     * @param args 格式化参数
     */
    public static Toast toast(Context context,  String format,
                                 Object... args) {
        if (toast==null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(StringUtil.format(format, args));
        toast.show();
        return toast;
    }


    /**
     * 显示toast
     * @param format
     * @param args
     */
    public static Toast toast(String format,
                             Object... args){
        if (ContextUtils.getContext()==null){
            throw new NullPointerException("Context is null,ContextUtils#init() first ");
        }
        if (toast==null) {
            toast = Toast.makeText(ContextUtils.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(StringUtil.format(format, args));
        toast.show();
        return toast;
    }


    /**
     * 设置TextView 的Drawable left/start/right/end/top/bottom
     *
     * @param context
     * @param textView ，这里可以传TextView的子类，如Button
     * @param resId
     * @param gravity
     */
    public static void setTextDrawable(Context context, TextView textView,
                                       int resId, int gravity) {
        if (resId == 0) {
            textView.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(resId, null);
        }else {
            drawable = context.getResources().getDrawable(resId);;
        }
        /// 这一步必须要做,否则不会显示.
        DensityUtils.init(context);
        drawable.setBounds(0, 0, DensityUtils.dip2px(5),
                DensityUtils.dip2px(5));
        switch (gravity) {
            case Gravity.START | Gravity.LEFT:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case Gravity.TOP:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case Gravity.END | Gravity.RIGHT:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case Gravity.BOTTOM:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    /**
     * 设置TextView 的Drawable left/start/right/end/top/bottom
     *
     * @param context
     * @param textView ，这里可以传TextView的子类，如Button
     * @param resId
     * @param gravity
     */
    public static void setTextDrawable(Context context, TextView textView,
                                       int resId, int gravity, int drawablePadding) {
        if (resId == 0) {
            textView.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(resId, null);
        } else {
            drawable = context.getResources().getDrawable(resId);
        }
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawablePadding,
                drawablePadding);
        switch (gravity) {
            case Gravity.START | Gravity.LEFT:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case Gravity.TOP:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case Gravity.END | Gravity.RIGHT:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case Gravity.BOTTOM:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    private static long lastClickTime = 0;

    /**
     * 防重复点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
