package com.example.administrator.application7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import adapter.ContentAdapter;
import beans.NewsKind;
import utils.ActionBarUtil;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private ViewPager viewPager;
    private TabLayout tab;
    private ActionBarUtil util = new ActionBarUtil();
    //toolbar

    private NewsKind[] nk = new NewsKind[]{
            new NewsKind("社会百态",1),
            new NewsKind("娱情八卦",2),
            new NewsKind("天下财经",3),
            new NewsKind("运动激情",4),
            new NewsKind("高考专题",5),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        initView();
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        tab = (TabLayout) findViewById(R.id.home_tab);

        //viewPager的初始化
        ContentAdapter adapter = new ContentAdapter(getSupportFragmentManager(),nk);
        Log.i(TAG, "initView()");
        viewPager.setAdapter(adapter);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(util.getDrawableDIY());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"更多");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()){
            case 1:
                intent.setClass(HomeActivity.this,SettingsActivity.class);
                break;

        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
