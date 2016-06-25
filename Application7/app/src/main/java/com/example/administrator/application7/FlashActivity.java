package com.example.administrator.application7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import utils.Pref_Utils;

public class FlashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        jumpToActivity();

    }

    private void jumpToActivity(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(FlashActivity.this, WelcomeActivity.class);

                if (!getFirstOpenFlag()){
                    intent.setClass(FlashActivity.this,HomeActivity.class);
                }
                startActivity(intent);

                finish();
            }
        },3000l);
    }

    public boolean getFirstOpenFlag(){
        return Pref_Utils.getBoolean(this, "FIRST_OPEN", true);
    }
}
