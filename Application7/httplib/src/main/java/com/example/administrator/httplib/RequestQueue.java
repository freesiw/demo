package com.example.administrator.httplib;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Administrator on 16/6/20.
 */
public class RequestQueue {


	/**
	 * 阻塞的请求队列
	 */
	private BlockingDeque<Request> requesQueue = new LinkedBlockingDeque<>();
	/**
	 * 后台访问网络的线程个数
	 */
	private final int MAX_THREADS = 3;


	/**
	 *后台线程的引用
	 */
	private NetworkDispatcher[] workers = new NetworkDispatcher[MAX_THREADS];

	public RequestQueue(){
		initDispatcher();

	}


	private void initDispatcher(){
		for (int i = 0; i < workers.length; i++) {
			/**
			 * 创建一个访问网络的线程类
			 */
			workers[i] = new NetworkDispatcher(requesQueue);
			workers[i].start();
		}
	}

	public void addRequest(Request request){
		requesQueue.add(request);
	}


	private void  stop(){
		for (int i = 0; i < workers.length; i++) {
			workers[i].flag = false;
			workers[i].interrupt();
		}
	}



}
