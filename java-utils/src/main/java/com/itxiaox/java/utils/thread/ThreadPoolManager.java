package com.itxiaox.java.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 线程池管理
 * @author zwy
 *
 */
public class ThreadPoolManager {
	private ExecutorService service;

	private ThreadPoolManager() {
		//获得可用的处理器个数
		int num = Runtime.getRuntime().availableProcessors();
		// 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程
		service = Executors.newFixedThreadPool(num * 4);
	}

	private static final ThreadPoolManager manager = new ThreadPoolManager();

	/**
	 * 获取单利对象
	 * @return
	 */
	public static ThreadPoolManager getInstance() {
		return manager;
	}

	/**
	 * 添加任务（线程任务）
	 * @param runnable
	 */
	public void addTask(Runnable runnable) {
		service.execute(runnable);
	}

}
