package com.minsx.schedule;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
	//总线程池
	private final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
	//总线程池
	private final static Map<String, Thread> THREAD_POOL = new HashMap<String, Thread>();
    //socket线程
    public static ServerSocket serverSocket = null;
    //socket监听线程
    public static Thread serverSocketThread = null;
    //主服务线程
    public static Thread mainWebThread = null;
    //主服务进程
    public static Process mainWebProcess = null;
    
    @SuppressWarnings("deprecation")
	public static void shutStopThread(String threadName) {
    	THREAD_POOL.get(threadName).stop();
	}
    
	public static void shutdown() {
		EXECUTOR_SERVICE.shutdown();
	}
	
	public static void shutdownNow() {
		EXECUTOR_SERVICE.shutdownNow();
	}

	public static ExecutorService getExecutorService() {
		return EXECUTOR_SERVICE;
	}

	public static Map<String, Thread> getThreadPool() {
		return THREAD_POOL;
	}
	
    
}
