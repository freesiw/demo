package com.example.administrator.httplib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by wukai on 16/6/21.
 * 请求图片的类
 */
public class BitmapRequest extends Request<Bitmap> {

	public BitmapRequest(String url, Method method, Callback callback) {
		super(url, method, callback);
	}


	@Override
	public void dispatchContent(byte[] content) {
		Bitmap bm = BitmapFactory.decodeByteArray(content,0,content.length);
		onResponse(bm);
	}
}
