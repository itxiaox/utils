package com.itxiaox.android.xutils.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class DrawableUtils {

    /**
     *对EditText,TextView 设置左侧图标
     * @param context
     * @param view 仅针对EditText,TextView两种
     * @param drawableId
     */
    public void setDrawableLeft(Context context, View view, int drawableId){
        Drawable drawable = context.getResources().getDrawable(drawableId);
        //这一步必须要做，否则不会显示
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        if(view instanceof TextView){
            ((TextView)view).setCompoundDrawables(drawable,null,null,null);
        }else if(view instanceof EditText){
            ((EditText)view).setCompoundDrawables(drawable,null,null,null);
        }
    }


    /**
     *对EditText,TextView 设置上边图标
     * @param context
     * @param view 仅针对EditText,TextView两种
     * @param drawableId
     */
    public void setDrawableTop(Context context, View view, int drawableId){
        Drawable drawable = context.getResources().getDrawable(drawableId);
        //这一步必须要做，否则不会显示
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        if(view instanceof TextView){
            ((TextView)view).setCompoundDrawables(null,drawable,null,null);
        }else if(view instanceof EditText){
            ((EditText)view).setCompoundDrawables(null,drawable,null,null);
        }
    }
    /**
     *对EditText,TextView 设置右侧图标
     * @param context
     * @param view 仅针对EditText,TextView两种
     * @param drawableId
     */
    public void setDrawableRight(Context context, View view, int drawableId){
        Drawable drawable = context.getResources().getDrawable(drawableId);
        //这一步必须要做，否则不会显示
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        if(view instanceof TextView){
            ((TextView)view).setCompoundDrawables(null,null,drawable,null);
        }else if(view instanceof EditText){
            ((EditText)view).setCompoundDrawables(null,null,drawable,null);
        }
    }


    /**
     *对EditText,TextView 设置右侧图标
     * @param context
     * @param view 仅针对EditText,TextView两种
     * @param drawableId
     */
    public void setDrawableBottom(Context context, View view, int drawableId){
        Drawable drawable = context.getResources().getDrawable(drawableId);
        //这一步必须要做，否则不会显示
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        if(view instanceof TextView){
            ((TextView)view).setCompoundDrawables(null,null,null,drawable);
        }else if(view instanceof EditText){
            ((EditText)view).setCompoundDrawables(null,null,null,drawable);
        }
    }

    /** 设置Selector。 本次只增加点击变暗的效果，注释的代码为更多的效果 */
    public static StateListDrawable createSLD(Context context, Drawable drawable) {
        StateListDrawable bg = new StateListDrawable();
        int brightness = 50 - 127;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Drawable normal = drawable;
        Drawable pressed = createDrawable(drawable, paint);
        bg.addState(new int[] { android.R.attr.state_pressed, }, pressed);
        bg.addState(new int[] { android.R.attr.state_focused, }, pressed);
        bg.addState(new int[] { android.R.attr.state_selected }, pressed);
        bg.addState(new int[] {}, normal);
        return bg;
    }

    @SuppressWarnings("deprecation")
    private static Drawable createDrawable(Drawable d, Paint p) {

        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap b = bd.getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(bd.getIntrinsicWidth(), bd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(b, 0, 0, p); // 关键代码，使用新的Paint画原图，

        return new BitmapDrawable(bitmap);
    }
}
