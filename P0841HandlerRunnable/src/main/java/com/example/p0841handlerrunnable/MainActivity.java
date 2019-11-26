package com.example.p0841handlerrunnable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ProgressBar pbCount;
    CheckBox chbInfo;
    TextView tvInfo;
    int cnt;

    final String LOG_TAG = "myLogs";
    final int max = 100;

    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        h = new Handler();

        pbCount = findViewById(R.id.pbCount);
        pbCount.setMax(max);
        pbCount.setProgress(0);
        tvInfo = findViewById(R.id.tvInfo);

        chbInfo = findViewById(R.id.chbInfo);
        chbInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvInfo.setVisibility(View.VISIBLE);
                    h.post(showInfo);
                } else {
                    tvInfo.setVisibility(View.GONE);
                    h.removeCallbacks(showInfo);
                }
            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (cnt = 1; cnt < max; cnt++) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        h.post(upgradeInfo);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    Runnable upgradeInfo = new Runnable() {
        @Override
        public void run() {
            pbCount.setProgress(cnt);
        }
    };

    Runnable showInfo = new Runnable() {
        @Override
        public void run() {
            Log.d(LOG_TAG, "showInfo");
            tvInfo.setText("Count = " + cnt);
            h.postDelayed(showInfo,1000);
        }
    };
}
