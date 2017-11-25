package com.example.robert.pacetunes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class Help extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Intent intent = getIntent();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        finish();
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

    }
}
