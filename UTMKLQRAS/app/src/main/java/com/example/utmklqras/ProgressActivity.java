package com.example.utmklqras;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressActivity extends AppCompatActivity {

    private Timer timer;
    private ProgressBar progressBar;
    private int i = 0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        textView = findViewById(R.id.textView);
        textView.setText("");

        final long period = 100;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i < 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i) + "%");
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                } else {
                    timer.cancel();
                    Intent intent = new Intent(ProgressActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        }, 0,period);
    }
}