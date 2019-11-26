package com.example.p0881asynctaskresult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    MyTask mt;
    TextView tvInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                mt = new MyTask();
                mt.execute();
                break;
            case R.id.btnGet:
                showResult();
                break;
        }
    }

    private void showResult() {
        if (mt == null) return;
        int result = -1;
        try {
            Log.d(LOG_TAG, "Try to get result");
            result = mt.get(1, TimeUnit.SECONDS);
            Log.d(LOG_TAG, "get returns " + result);
            Toast.makeText(this, "get returns " + result, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.d(LOG_TAG, "get timeout, result = " + result);
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<Void,Void,Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
            Log.d(LOG_TAG, "Begin");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            tvInfo.setText("End. Result = " + integer);
            Log.d(LOG_TAG, "End. Result = " + integer);

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } return 100500;
        }
    }
}
