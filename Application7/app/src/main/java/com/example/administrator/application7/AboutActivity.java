package com.example.administrator.application7;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import utils.ActionBarUtil;

/**
 * Created by wukai on 16/6/21.
 *
 *
 */
public class AboutActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		ActionBar bar = getSupportActionBar();
		bar.setTitle("热点新闻");
		bar.setBackgroundDrawable(new ActionBarUtil().getDrawableDIY());

		PackageManager manager = this.getPackageManager();
		PackageInfo info = null;

		try {
			info = manager.getPackageInfo(this.getPackageName(),0);

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		int versionCode = info.versionCode;
		String versionName = info.versionName;

		TextView version = (TextView) findViewById(R.id.ab_version);
		version.setText("version:  " +versionName+ versionCode);

	}

}
