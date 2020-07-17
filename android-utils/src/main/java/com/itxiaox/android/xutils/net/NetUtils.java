package com.itxiaox.android.xutils.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;

import com.itxiaox.android.xutils.log.LogUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

/**
 * 网络工具类
 *
 * <pre>
 *     // 静态注册
 * 	  <receiver
 *     android:name="com.test.NetworkBroadcast"
 *     android:label="NetworkConnection" >
 *     <intent-filter>
 *         <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
 *     </intent-filter>
 *     </receiver>
 *
 *    //动态注册
 *       private BroadcastReceiver networkBroadcast=new BroadcastReceiver();
 * 	        private void registerNetworkReceiver() {
 * 	    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
 * 	    this.registerReceiver(networkBroadcast, filter);
 * 	    }
 *
 * 	     private void unRegisterNetworkReceiver() {
 *   	        this.unregisterReceiver(networkBroadcast);
 *   	}
 *  </pre>
 * @ClassName: NetUtils
 * @Description: TODO
 * @author xiaoxiao
 * @date modify by 2015-7-3 下午2:58:31
 * 
 */
public class NetUtils {
	/**
	 * 当前网络是否连接
	 * @param context
	 * @return true:有网络，false:无网络
	 */
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				@SuppressLint("MissingPermission") NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error", e.toString());
		}
		return false;
	}

	/**
	 * 判定网络状态
	 *
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		// 检查网络状态
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		@SuppressLint("MissingPermission") NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			Toast.makeText(context, "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * 判断是否连接wifi
	 * @param context
	 * @return
	 */
	@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
	public static boolean isWifiConnected(Context context){
		boolean flag = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
		   State wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		   if(wifi == State.CONNECTED || wifi == State.CONNECTING){
			   flag = true;
	      }  
		}
		return flag;
	}
	
	/**
	 * 判断是否连接移动网络
	 * @param context
	 * @return
	 */
	public static boolean isGPRSConnected(Context context){
		boolean flag = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
		   @SuppressLint("MissingPermission") State gprs = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		   if(gprs == State.CONNECTED || gprs == State.CONNECTING){
//	             Toast.makeText(this, "wifi is open! gprs", Toast.LENGTH_SHORT).show();  
			   flag = true;
	      }  
		}
		return flag;
	}
	

	/**
	 * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
	 *
	 * @param context
	 * @return
	 */
	public static int isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return 0;
		} else {
			@SuppressLint("MissingPermission") NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == State.CONNECTED) {
						NetworkInfo netWorkInfo = info[i];
						if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
							return 1;
						} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
							String extraInfo = netWorkInfo.getExtraInfo();
							if ("cmwap".equalsIgnoreCase(extraInfo) || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
								return 2;
							}
							return 3;
						}
					}
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * @ClassName: NetworkBroadcast 
	 * @Description: TODO
	 * @author xiaoxiao
	 * @date modify by 2015-10-20 下午5:31:48 
	 *
	 */
//	public class NetworkBroadcast extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			State wifiState = null;
//	        State mobileState = null;
//	        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//	        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//	        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//	        if (wifiState != null && mobileState != null
//	                && State.CONNECTED != wifiState
//	                && State.CONNECTED == mobileState) {  //// 手机网络连接成功
//	            Toast.makeText(context, "手机网络连接成功", Toast.LENGTH_SHORT).show();
//	        } else if (wifiState != null && mobileState != null
//	                && State.CONNECTED != wifiState
//	                && State.CONNECTED != mobileState) {
//	        	Toast.makeText(context, "无网络", Toast.LENGTH_SHORT).show();
//	            // 手机没有任何的网络
//	        } else if (wifiState != null && State.CONNECTED == wifiState) {    // 无线网络连接成功
//	        	Toast.makeText(context, "无线网络连接成功", Toast.LENGTH_SHORT).show();
//	        }
//
//		}
//
//	}

	/**
	 * 获取IP地址,私网id,本地局域网IP
	 *
	 * @return
	 * @throws SocketException
	 */
	public static String getLocalIPAddress() throws SocketException {
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
					return inetAddress.getHostAddress().toString();
				}
			}
		}
		return "null";
	}

	/**
	 * 获取外网IP地址
	 *
	 * @return
	 */
	public static String getNetIP() {
		String IP = "";
		try {
			String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
			URL url = new URL(address);

			//URLConnection htpurl=url.openConnection();

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36"); //设置浏览器ua 保证不出现503

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();

				// 将流转化为字符串
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));

				String tmpString = "";
				StringBuilder retJSON = new StringBuilder();
				while ((tmpString = reader.readLine()) != null) {
					retJSON.append(tmpString + "\n");
				}

				JSONObject jsonObject = new JSONObject(retJSON.toString());
				String code = jsonObject.getString("code");

				if (code.equals("0")) {
					JSONObject data = jsonObject.getJSONObject("data");
					IP = data.getString("ip") + "(" + data.getString("country")
							+ data.getString("area") + "区"
							+ data.getString("region") + data.getString("city")
							+ data.getString("isp") + ")";

					Log.e("提示", "您的IP地址是：" + IP);
				} else {
					IP = "";
					Log.e("提示", "IP接口异常，无法获取IP地址！");
				}
			} else {
				IP = "";
				Log.e("提示", "网络连接异常，无法获取IP地址！");
			}
		} catch (Exception e) {
			IP = "";
			Log.e("提示", "获取IP地址时出现异常，异常信息是：" + e.toString());
		}
		return IP;
	}

	@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
	public static String getIPAddress(Context context) {
		NetworkInfo info = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//        LogUtils.writeToFile("当前网络信息：info="+info);
		if (info != null && info.isConnected()) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
				WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				//    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
//                SystemLog.add(0," 当前无线网络，ipAddress="+ipAddress);
//              LogUtils.writeToFile(" 当前无线网络，ipAddress="+ipAddress);
				return ipAddress;
			}else {
				try {
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
//                                SystemLog.add(0," ipv4 inetAddress="+inetAddress.getHostAddress());
//                                LogUtils.writeToFile(" ipv4 inetAddress="+inetAddress.getHostAddress());
								return inetAddress.getHostAddress();
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
//                    LogUtils.writeToFile("当前移动网络，枚举网络信息异常");
				}
			}
		} else {
			//当前无网络连接,请在设置中打开网络
//            LogUtils.writeToFile("当前无网络");
		}
		return null;
	}

	/**
	 *  * 将得到的int类型的IP转换为String类型
	 *  *
	 *  * @param ip
	 *  * @return
	 *  
	 */
	public static String intIP2StringIP(int ip) {
		return (ip & 0xFF) + "." +
				((ip >> 8) & 0xFF) + "." +
				((ip >> 16) & 0xFF) + "." +
				(ip >> 24 & 0xFF);
	}


}
