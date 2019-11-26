package com.example.p0811handlersimplemessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final int STATUS_NONE = 0; // нет подключения
    final int STATUS_CONNECTING = 1; // подключаемся
    final int STATUS_CONNECTED = 2; // подключено

    Handler h;
    TextView tvStatus;
    Button btnConnect;
    ProgressBar pbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStatus = findViewById(R.id.tvStatus);
        pbConnect = findViewById(R.id.pbConnect);
        btnConnect = findViewById(R.id.btnConnect);

        h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        btnConnect.setEnabled(true);
                        tvStatus.setText("Not connected");
                        break;
                    case STATUS_CONNECTING:
                        btnConnect.setEnabled(false);
                        pbConnect.setVisibility(View.VISIBLE);
                        tvStatus.setText("Connecting");
                        break;
                    case STATUS_CONNECTED:
                        pbConnect.setVisibility(View.GONE);
                        tvStatus.setText("Connected");
                        break;
                }
            }
        };
        h.sendEmptyMessage(STATUS_NONE);

    }

    public void onclick(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                h.sendEmptyMessage(STATUS_CONNECTING);
                try {
                    TimeUnit.SECONDS.sleep(2);
                    h.sendEmptyMessage(STATUS_CONNECTED);
                    TimeUnit.SECONDS.sleep(3);
                    h.sendEmptyMessage(STATUS_NONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }
}
