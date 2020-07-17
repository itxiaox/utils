package com.itxiaox.android.xutils.app;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 内存管理工具类
 * @ClassName: MemoryUtils 
 * @Description: TODO
 * @author xiaoxiao
 * @date modify by 2015-9-22 下午7:32:35 
 *
 */
public class MemoryUtils {
	/**
	 * 获取当前应用的Heap Size阈值 </br>
	 * allocated + 新分配的内存 >= getMemoryClass()的时候就会发生OOM
	 * @param context
	 * @return
	 */
	public static int getHeapSize(Context context){
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return activityManager.getMemoryClass();
	}
}
