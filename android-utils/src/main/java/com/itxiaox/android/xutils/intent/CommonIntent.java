package com.itxiaox.android.xutils.intent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 信息工具类
 * @ClassName: MesssageUtil 
 * @Description: TODO
 * @author xiaoxiao
 * @date 2015-5-12 下午1:35:40 
 *
 */
public class CommonIntent {

	/**
	 *
	 * 打电话
	 * 需要权限
	 *<pre>
	 *     android:name="android.permission.CALL_PHONE"
	 *</pre>
	 * @param context
	 * @param phone
	 */
	public static void call(Context context, String phone){
		 Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:"+phone));//自定义的Intent
		 context.startActivity(intent);
	}

	/**
	 * 跳转到设置页面，设置网络，wifi
	 * @param context
     */

	public static void setNetwork(Context context){
		Intent intent = null;
		/**
		 * 判断手机系统的版本！如果API大于10 就是3.0+ 因为3.0以上的版本的设置和3.0以下的设置不一样，
		 调用的方法不同
		 */
		if (android.os.Build.VERSION.SDK_INT > 10) {
			intent = new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS);
		} else {
			intent = new Intent();
			ComponentName component = new ComponentName(
					"com.android.settings",
					"com.android.settings.WirelessSettings");
			intent.setComponent(component);
			intent.setAction("android.intent.action.VIEW");
		}
		context.startActivity(intent);
	}
}
