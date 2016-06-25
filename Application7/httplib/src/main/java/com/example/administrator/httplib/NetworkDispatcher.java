package com.example.administrator.httplib;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * Created by wukai on 16/6/20.
 *
 * 访问网络,把结果回调给调用者
 */
public class NetworkDispatcher extends Thread {

	private static final String TAG = NetworkDispatcher.class.getSimpleName();
	private BlockingDeque<Request> mQueue;
	public boolean flag = true;

	NetworkDispatcher(BlockingDeque<Request> queue){
		mQueue = queue;
	}

	@Override
	public void run() {

		/**
		 * 如果当线程的表记是可运行并且当前线程没有被打断 从请求队列里面取出请求进行网络访问
		 */
		while (flag && !isInterrupted()) {

			try {
				//从请求队列中取出一个请求
				final Request req = mQueue.take();
				byte[] result = null;
				try {
					//获取网络请求的内容
					result = getNetworkResponse(req);
					if (result != null) {
						//当内容不为空的时候回调正常的返回结果
						req.dispatchContent(result);
					}

				} catch (final Exception e) {
					e.printStackTrace();
					req.onError(e);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
				flag = false;
			}
		}
	}


	public byte[] getNetworkResponse(Request request) throws Exception {
		if (TextUtils.isEmpty(request.getUrl())) {
			throw new Exception("url is null");
		}

		if (request.getMethod() == Request.Method.GET) {
			return getResponseByGET(request);
		}

		if (request.getMethod() == Request.Method.POST) {
			return getResponseByPost(request);
		}
		return null;
	}

	public byte[] getResponseByPost(Request request) throws Exception{

		URL url = null;
		HttpURLConnection conn = null;
		InputStream iis = null;
		OutputStream oos = null;
		url = new URL(request.getUrl());

		conn = (HttpURLConnection) url.openConnection();
		//设置请求方式为post
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5000);
		conn.setDoOutput(true);


		//设置上传的参数的字节长度
		String str = getPostEncodeString(request);
		byte[] content = null;
		if (str != null){
			content = str.getBytes();
			conn.setRequestProperty("content-length",""+content.length);
		}

		oos = conn.getOutputStream();
		if (content != null){
			oos.write(content);
			oos.flush();
		}

		int code = conn.getResponseCode();
		if (code != 200){

			Log.d(TAG, "getNetworkResponse() returned: response code error code=" +code );
			throw new Exception("http code eror");
		}

		iis = conn.getInputStream();
		ByteArrayOutputStream bos =new ByteArrayOutputStream();

		int len = 0;
		byte[] buf = new byte[2048];

		while ((len=iis.read(buf))!=-1){
			bos.write(buf,0,len);
		}
		iis.close();
		oos.close();
		return bos.toByteArray();

	}


	private byte[] getResponseByGET(Request request)throws Exception{
		URL url = null;
		HttpURLConnection conn = null;
		InputStream iis = null;
		url = new URL(request.getUrl());
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		int code = conn.getResponseCode();

		if (code != 200){
			Log.d(TAG, "getNetworkResponse() returned: response code error code=" +code );
			throw new Exception("code eror");
		}
		iis = conn.getInputStream();
		ByteArrayOutputStream bos =new ByteArrayOutputStream();
		int len = 0;
		byte[] buf = new byte[2048];
		while ((len=iis.read(buf))!=-1){
			bos.write(buf,0,len);
		}


		iis.close();
		return bos.toByteArray();
	}



	public String getPostEncodeString(Request request){
		HashMap<String,String> params = request.getPostParams();
		StringBuilder buf = new StringBuilder();
		if (params != null) {

			Iterator<Map.Entry<String, String>> iterator
					= params.entrySet().iterator();

			int i = 0;
			while (iterator.hasNext()) {
				if (i > 0) {
					buf.append("&");
				}

				Map.Entry<String, String> value = iterator.next();
				String str = value.getKey() + "=" + value.getValue();
				buf.append(str);
				i++;
			}
			return buf.toString();
		}
		return null;
	}
}
