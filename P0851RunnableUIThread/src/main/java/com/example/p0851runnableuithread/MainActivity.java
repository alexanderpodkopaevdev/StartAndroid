package com.example.p0851runnableuithread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(2);
                    tvInfo.postDelayed(runn2,2000);
                    tvInfo.post(runn3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    Runnable runn1 = new Runnable() {
        @Override
        public void run() {
            tvInfo.setText("runn1");
        }
    };
    Runnable runn2 = new Runnable() {
        @Override
        public void run() {
            tvInfo.setText("runn2");
        }
    };
    Runnable runn3 = new Runnable() {
        @Override
        public void run() {
            tvInfo.setText("runn3");
        }
    };
}
