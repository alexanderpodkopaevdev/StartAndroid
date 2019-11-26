package com.example.p0801handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    Handler handler;
    TextView tvInfo;
    Button btnStart;
    ProgressBar pbBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
        btnStart = findViewById(R.id.btnStart);
        pbBar = findViewById(R.id.pbBar);
        pbBar.setVisibility(View.INVISIBLE);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                tvInfo.setText("Закачано файлов: " + msg.what);
                if (msg.what == 10) {
                    btnStart.setEnabled(true);
                    pbBar.setVisibility(View.INVISIBLE);
                }

            }
        };

    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btnTest:
                Log.d(LOG_TAG, "TEST");
                break;
            case R.id.btnStart:
                pbBar.setVisibility(View.VISIBLE);
                btnStart.setEnabled(false);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            downloadFile();
                            handler.sendEmptyMessage(i);
                            Log.d(LOG_TAG, "Downloaded files: " + i);
                        }

                    }
                });
                thread.start();

                break;
            default:
                break;
        }
    }

    private void downloadFile() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
