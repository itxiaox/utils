package com.itxiaox.android.xutils.app;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Activity 管理类
 * </br>
 * 主要管理Activity堆栈
 * 适用于多Activity的模式，针对现在的单Activity多Fragment开发模式，此工具类作用不大
 * @author: xiao
 * @date:  2019/5/7 11:16
 * @version v1.0
 */
public class ActivityManager {
	
	private static CopyOnWriteArrayList<Activity> mActivityStack;
	private static ActivityManager mAppManager;

	/**
	 * 屏蔽直接new对象
	 */
	private ActivityManager() {

	}

	/**
	 * 单一实例
	 */
	public static ActivityManager getInstance() {
		if (mAppManager == null) {
			mAppManager = new ActivityManager();
		}
		return mAppManager;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new CopyOnWriteArrayList<Activity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * 获取栈顶Activity（堆栈中最后一个压入的）
	 */
	public Activity getTopActivity() {
		Activity activity = null;
		if(mActivityStack!=null&&mActivityStack.size()>0)
		 activity = mActivityStack.get(0);
		return activity;
	}

	/**
	 * 结束栈顶Activity（堆栈中最后一个压入的）
	 */
	public void killTopActivity() {
		killActivity(getTopActivity());
	}

	/**
	 * 结束指定的Activity
	 */
	public void killActivity(Activity activity) {
		try {
			if (activity != null) {
				mActivityStack.remove(activity);
				activity.finish();
				activity = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void killActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				killActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void killAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}
	/**
	 * 结束所有Activity除了Login
	 * @param claz 传入登录界面的Activity.class
	 */
	public void killAllActivityNoLogin(Class claz) {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				if (mActivityStack.get(i).getClass()!=claz) {
					mActivityStack.get(i).finish();
				}
			}
		}
		mActivityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			killAllActivity();
			android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出登录， 注意退出登录，需要清除当前账号的用户信息，相关数据，
	 * 可以在Application中提供一个清空数据的方法
	 * @param context 上下文对象，需要跳转的activity,一般为登录Activity
	 * @param claz
     */
	public void exitLogin(Context context,Class claz){
		Intent intent = new Intent(context,claz);
		context.startActivity(intent);
		killAllActivityNoLogin(claz);
	}
}
