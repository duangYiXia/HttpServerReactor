package com.core.InitServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InitThreadPool {

	private ExecutorService executorServicePool;
	private static final int corePoolSize = 2;//单个CPU线程池大小

	public InitThreadPool() {
		int allPoolSize = Runtime.getRuntime().availableProcessors()*corePoolSize;
		executorServicePool = Executors.newFixedThreadPool(allPoolSize);
		System.out.println("已经创建线程池，大小=" + allPoolSize);
	}

	public ExecutorService getExecutorServicePool() {
		return executorServicePool;
	}

	public void setExecutorServicePool(ExecutorService executorServicePool) {
		this.executorServicePool = executorServicePool;
	}
}
