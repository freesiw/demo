package com.example.administrator.httplib;

import java.util.HashMap;

/**
 * Created by wukai on 16/6/20.
 */
abstract public class Request<T> {

	private String url;

	private Method method;

	private Callback callback;


	public Request(String url, Method method,Callback callback) {
		this.url = url;
		this.method = method;
		this.callback = callback;
	}


	public  enum Method{
		GET,POST
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}



	public Callback getCallback() {
		return callback;
	}



	public void onError(Exception e) {
		callback.onEror(e);
	}

	protected void onResponse(T res){
		callback.onResonse(res);
	}


	public  interface  Callback<T>{

		 void onEror(Exception e);
		 void onResonse(T response);
	}


	public HashMap<String,String> getPostParams(){
		return null;
	}



	abstract public void dispatchContent(byte[] content);
}
