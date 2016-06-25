package com.example.administrator.application7;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import utils.ActionBarUtil;

/**
 * Created by Administrator on 2016/6/23.
 */
public class FedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable( new ActionBarUtil().getDrawableDIY());
    }
    public void submit(View view){
        Toast.makeText(FedbackActivity.this,"感谢您的支持与厚爱！",Toast.LENGTH_SHORT).show();

    }
}
