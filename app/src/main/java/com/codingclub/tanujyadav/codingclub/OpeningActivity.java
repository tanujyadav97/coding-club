package com.codingclub.tanujyadav.codingclub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class OpeningActivity extends Activity {







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_opening);

    }


    public void open(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }







}
