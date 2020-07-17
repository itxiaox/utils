package com.itxiaox.android.xutils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * 图像处理
 * @ClassName: ImaegColorHandler 
 * @Description: TODO
 * @author xiaoxiao
 * @date modify by 2016-1-15 下午3:32:22 
 *
 */
public class ImageColorHandler {

	
	/** 
     * 怀旧效果(相对之前做了优化快一倍) 
     * @param bmp 
     * @return 
     */  
    public static Bitmap oldRemeber(Bitmap bmp){
        // 速度测试  
        long start = System.currentTimeMillis();
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        int pixColor = 0;  
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 0; i < height; i++)  
        {  
            for (int k = 0; k < width; k++)  
            {  
                pixColor = pixels[width * i + k];  
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);  
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);  
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);  
                int newColor = Color.argb(255, newR > 255 ? 255 : newR, newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
                pixels[width * i + k] = newColor;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();
        Log.d("may", "used time="+(end - start));
        return bitmap;  
    }  
    
    /** 
     * 柔化效果(高斯模糊)(优化后比上面快三倍) 
     * @param bmp 
     * @return 
     */  
    public static Bitmap blurImageAmeliorate(Bitmap bmp)
    {  
        long start = System.currentTimeMillis();
        // 高斯矩阵  
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int delta = 16; // 值越小图片会越亮，越大则越暗  
          
        int idx = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                for (int m = -1; m <= 1; m++)  
                {  
                    for (int n = -1; n <= 1; n++)  
                    {  
                        pixColor = pixels[(i + m) * width + k + n];  
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);
                          
                        newR = newR + (int) (pixR * gauss[idx]);  
                        newG = newG + (int) (pixG * gauss[idx]);  
                        newB = newB + (int) (pixB * gauss[idx]);  
                        idx++;  
                    }  
                }  
                  
                newR /= delta;  
                newG /= delta;  
                newB /= delta;  
                  
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                  
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                  
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();
        Log.d("may", "used time="+(end - start));
        return bitmap;  
    }  
    
    /** 
     * 图片锐化（拉普拉斯变换） 
     * @param bmp 
     * @return 
     */  
    public static Bitmap sharpenImageAmeliorate(Bitmap bmp)
    {  
        long start = System.currentTimeMillis();
        // 拉普拉斯矩阵  
        int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int idx = 0;  
        float alpha = 0.3F;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                for (int m = -1; m <= 1; m++)  
                {  
                    for (int n = -1; n <= 1; n++)  
                    {  
                        pixColor = pixels[(i + n) * width + k + m];  
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);
                          
                        newR = newR + (int) (pixR * laplacian[idx] * alpha);  
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);  
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);  
                        idx++;  
                    }  
                }  
                  
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                  
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();
        Log.d("may", "used time="+(end - start));
        return bitmap;  
    }  
    /**
     * 浮雕效果
     * @param bmp
     * @return
     */
    public static Bitmap reliefImage(Bitmap bmp){
    	int[] oldPixels;    
        int[] newPixels;    
        int color,color2;  
        int pixelsR,pixelsG,pixelsB,pixelsA,pixelsR2,pixelsG2,pixelsB2;  
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        oldPixels = new int[width*height];   
        newPixels = new int[width*height];  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        pixelsA = 0;
      //获取像素  
        bitmap.getPixels(oldPixels, 0, width, 0, 0, width, height);  
        for(int i = 1;i < height*width; i++){  
                color = oldPixels[i-1];  
                //前一个像素  
                pixelsR = Color.red(color);
                pixelsG = Color.green(color);
                pixelsB = Color.blue(color);
                //当前像素  
                color2 = oldPixels[i];  
                pixelsR2 = Color.red(color2);
                pixelsG2 = Color.green(color2);
                pixelsB2 = Color.blue(color2);
                  
                pixelsR = (pixelsR - pixelsR2 + 127);  
                pixelsG = (pixelsG - pixelsG2 + 127);  
                pixelsB = (pixelsB - pixelsB2 + 127);  
                //均小于等于255  
                if(pixelsR > 255){  
                    pixelsR = 255;  
                }  
                  
                if(pixelsG > 255){  
                    pixelsG = 255;  
                }  
                  
                if(pixelsB > 255){  
                    pixelsB = 255;  
                }  
                  
                newPixels[i] = Color.argb(pixelsA, pixelsR, pixelsG, pixelsB);
                  
        }  
        bitmap.setPixels(newPixels, 0, width, 0, 0, width, height); 
        return bitmap;
    }
    
    
    /** 
     * 光晕效果 
     * @param bmp 
     * @param x 光晕中心点在bmp中的x坐标 
     * @param y 光晕中心点在bmp中的y坐标 
     * @param r 光晕的半径 
     * @return 
     */  
    public static Bitmap halo(Bitmap bmp, int x, int y, float r)
    {  
        long start = System.currentTimeMillis();
        // 高斯矩阵  
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int delta = 18; // 值越小图片会越亮，越大则越暗  
          
        int idx = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                int distance = (int) (Math.pow(k - x, 2) + Math.pow(i - y, 2));
                // 不是中心区域的点做模糊处理  
                if (distance > r * r)  
                {  
                    for (int m = -1; m <= 1; m++)  
                    {  
                        for (int n = -1; n <= 1; n++)  
                        {  
                            pixColor = pixels[(i + m) * width + k + n];  
                            pixR = Color.red(pixColor);
                            pixG = Color.green(pixColor);
                            pixB = Color.blue(pixColor);
                              
                            newR = newR + (int) (pixR * gauss[idx]);  
                            newG = newG + (int) (pixG * gauss[idx]);  
                            newB = newB + (int) (pixB * gauss[idx]);  
                            idx++;  
                        }  
                    }  
                      
                    newR /= delta;  
                    newG /= delta;  
                    newB /= delta;  
                      
                    newR = Math.min(255, Math.max(0, newR));
                    newG = Math.min(255, Math.max(0, newG));
                    newB = Math.min(255, Math.max(0, newB));
                      
                    pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                      
                    newR = 0;  
                    newG = 0;  
                    newB = 0;  
                }  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();
        Log.d("may", "used time="+(end - start));
        return bitmap;  
    }  


}
