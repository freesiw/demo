package com.example.administrator.application7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import utils.ActionBarUtil;


public class SettingsActivity extends AppCompatActivity {

	private ActionBarUtil util = new ActionBarUtil();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		ActionBar bar = getSupportActionBar();
		bar.setBackgroundDrawable(util.getDrawableDIY());

	}

	//点击事件
	public void jump(View view){
		Intent intent = new Intent();
		switch (view.getId()){
			case R.id.set_tv_collector:
				intent.setClass(SettingsActivity.this,CollectorActivity.class);
				intent.putExtra("tag","collector");
				break;
			case R.id.set_tv_history:
				intent.setClass(SettingsActivity.this,CollectorActivity.class);
				intent.putExtra("tag", "history");
				break;
			case R.id.set_tv_vs_info:
				intent.setClass(SettingsActivity.this,AboutActivity.class);
				break;
			case R.id.set_tv_fedback:
				intent.setClass(SettingsActivity.this,FedbackActivity.class);
				break;
		}
		startActivity(intent);

	}

}


