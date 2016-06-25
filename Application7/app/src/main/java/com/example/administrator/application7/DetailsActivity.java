package com.example.administrator.application7;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import utils.ActionBarUtil;
import utils.MyDBHelper;


public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";
    private TextView text_title;
    private TextView text_rcount;
    private TextView text_time;
    private TextView text_desc;

    private String fromUrl = "";
    private String title = "";
    private String desc = "";
    private String rcount = "";
    private String time = "";


    private ActionBarUtil util = new ActionBarUtil();
    private MyDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("热点新闻");
        bar.setBackgroundDrawable(util.getDrawableDIY());
        initView();


    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.detail_title);
        text_rcount = (TextView) findViewById(R.id.detail_rcount);
        text_time = (TextView) findViewById(R.id.detail_time);
        text_desc = (TextView) findViewById(R.id.detail_desc);

        //设置text
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("news");
            title = bundle.getString("title");
            desc = bundle.getString("desc");
            rcount = bundle.getString("rcount");
            time = bundle.getString("time");
            //获取url,用于分享
            fromUrl = bundle.getString("fromurl");
            //设置文字
            text_title.setText(title);
            text_rcount.setText(rcount);
            text_time.setText(time);
            text_desc.setText(desc);

        } else {
            Log.i(TAG, "intent为空");
        }
        //实例化helper对象
        helper = new MyDBHelper(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_share:

                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.putExtra("url", fromUrl);
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "分享到"));
                break;
            case R.id.menu_collect:

                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values_coll = new ContentValues();
                values_coll.put("title",title);
                values_coll.put("desc",desc);
                values_coll.put("rcount",rcount);
                values_coll.put("time", time);
                long count = db.insert("coll",null,values_coll);
                Log.i(TAG,"coll 添加条数:"+count);

                Toast.makeText(DetailsActivity.this,"添加成功!",Toast.LENGTH_SHORT).show();


                break;
            case R.id.menu_more:

                Intent intent2 = new Intent();
                intent2.setClass(DetailsActivity.this, SettingsActivity.class);
                startActivity(intent2);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //保存浏览记录
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values_hist = new ContentValues();
        values_hist.put("title",title);

        values_hist.put("time", time);
        Log.i(TAG,"title = "+title+",desc = "+desc);
        long count = database.insert("hist", null, values_hist);
        Log.i(TAG,"hist 添加条数:"+count);

    }
}


