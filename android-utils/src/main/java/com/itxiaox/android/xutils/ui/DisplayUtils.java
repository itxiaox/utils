package com.itxiaox.android.xutils.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 有关获取屏幕长宽等相关参数
 */
public class DisplayUtils {
    private static Context mContext;
    private static DisplayMetrics outMetrics;

    /**
     * 初始化， 在application中初始化
     *
     * @param context
     */
    public void init(Context context) {
        if (outMetrics == null) {
            mContext = context.getApplicationContext();
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
    }

    /**
     * 获得屏幕高度(px), 调用之前先在application 中调用init(Context context)方法
     *
     * @return
     */
    public static int getScreenWidth() {
        if (outMetrics == null) {
            throw new NullPointerException("invoke init(Context context) first");
        }
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度(px)
     * 调用之前先在application 中调用init(Context context)方法
     *
     * @return
     */
    public static int getScreenHeight() {
        if (outMetrics == null) {
            throw new NullPointerException("invoke init(Context context) first");
        }
        return outMetrics.heightPixels;
    }

    /**
     * 屏幕密度
     * 调用之前先在application 中调用init(Context context)方法
     *
     * @return
     */
    public static float getScreenDensity() {
        if (outMetrics == null) {
            throw new NullPointerException("invoke init(Context context) first");
        }
        return outMetrics.density; // 屏幕密度（0.75 / 1.0 / 1.5）
    }

    /**
     * 获得长度
     *
     * @param context
     * @param id
     * @return
     */
    public static float getDimens(Context context, int id) {
        if (context != null) {
            return context.getResources().getDimension(id);
        } else {
            return 0;
        }
    }


    /**
     * 获得屏幕高度(px)
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        mContext = context.getApplicationContext();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        outMetrics = new DisplayMetrics();
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度(px)
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        mContext = context.getApplicationContext();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        outMetrics = new DisplayMetrics();
        return outMetrics.heightPixels;
    }


    /**
     * 获得状态栏的高度，方法一
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            @SuppressLint("PrivateApi")
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取状态的高度，方法二
     *
     * @param context
     * @return
     */
    public static int getStatusHeight2(Context context) {
        Rect frame = new Rect();
        Window window = ((Activity) context).getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            window.getDecorView().getWindowVisibleDisplayFrame(frame);
        }
        return frame.top;
    }

    /**
     * 获取标题栏的高度
     *
     * @param context
     * @return
     */
    public static int getTitleBarHeight(Context context) {
        Rect frame = new Rect();
        Window window = ((Activity) context).getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            window.getDecorView().getWindowVisibleDisplayFrame(frame);
        }
        int statusBarHeight = frame.top;
        // 获取标题栏高度
//        Window window = getWindow();
        int contentViewTop = window
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // statusBarHeight是上面所求的状态栏的高度
        return contentViewTop - statusBarHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        }
        int statusBarHeight = frame.top;
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取底部导航栏的高度
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId);
    }


    /**
     * 全屏设置
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        if (activity == null) return;
        int uiOptions = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
        }
//        uiOptions |=
////              View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        uiOptions |=View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }
    }
}
