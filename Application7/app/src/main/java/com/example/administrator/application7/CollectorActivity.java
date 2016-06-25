package com.example.administrator.application7;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import beans.News;
import utils.MyDBHelper;


public class CollectorActivity extends Activity {

	private static final String TAG = "CollectorActivity";
	private String DBPath = Environment
			.getExternalStorageDirectory() + File.separator + "xinwen.db";

	private ListView listView;
	private List<News> list = new ArrayList<>();
	private dbAdapter adapter;
	private TextView title;
	//handler
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coll);

		initView();
	}

	private void initView() {

		listView = (ListView) findViewById(R.id.coll_lv);

		title = (TextView) findViewById(R.id.coll_tv_title);

		getDataFromTable();

		adapter = new dbAdapter();

		listView.setAdapter(adapter);

	}

	private void getDataFromTable(){
		Intent intent = getIntent();
		String tag = intent.getStringExtra("tag");
		Log.i(TAG, "tag = " + tag);

		if (tag.equals("collector")){
			title.setText("我的收藏");

			list = readDataBase("coll");

		}else if (tag.equals("history")){
			title.setText("浏览记录");

			list = readDataBase("hist");

		}else{
			Toast.makeText(this,"传值失败!",Toast.LENGTH_SHORT).show();
		}
	}

	private List<News> readDataBase(String dbName){

		List<News> newsList = new ArrayList<>();

		News news = null;
		MyDBHelper helper = new MyDBHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query(dbName, null, null, null, null, null, null);
		while (cursor.moveToNext()){
			String title1 = cursor.getString(cursor.getColumnIndex("title"));

			String time1 = cursor.getString(cursor.getColumnIndex("time"));
			news = new News(title1,time1);
			newsList.add(news);
			Log.i(TAG,news.toString());
		}

		return newsList;
	}


	class dbAdapter extends BaseAdapter{


		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public News getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View cv, ViewGroup parent) {
			ViewHolder vh = null;
			if (cv == null){
				vh = new ViewHolder();
				cv = getLayoutInflater().inflate(R.layout.coll_lv_item,null);
				vh.tv_title = (TextView) cv.findViewById(R.id.coll_lv_tv_title);
				vh.tv_time = (TextView) cv.findViewById(R.id.coll_lv_tv_time);

				cv.setTag(vh);
			}else{
				vh = (ViewHolder) cv.getTag();
			}

			News news = list.get(position);
			vh.tv_title.setText(news.getTitle());
			vh.tv_time.setText(news.getTime());

			return cv;
		}
	}
	class ViewHolder{
		TextView tv_title,tv_time;
	}
}
