package com.example.administrator.httplib;

/**
 * Created by wukai on 16/6/21.
 */
public class StringRequest extends Request<String> {

	public StringRequest(String url, Method method, Callback callback) {
		super(url, method, callback);
	}



	@Override
	public void dispatchContent(byte[] content) {
		onResponse(new String(content));
	}

}
