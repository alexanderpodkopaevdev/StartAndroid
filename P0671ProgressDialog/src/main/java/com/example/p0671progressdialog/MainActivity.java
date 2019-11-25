package com.example.p0671progressdialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btnDefault:
                pd = new ProgressDialog(this);
                pd.setTitle("Title");
                pd.setMessage("Message");
                pd.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                pd.show();
                break;
            case R.id.btnHoriz:
                pd = new ProgressDialog(this);
                pd.setTitle("Title");
                pd.setMessage("Message");
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setMax(2148);
                pd.setIndeterminate(true);
                pd.show();
                h = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        pd.setIndeterminate(false);
                        if (pd.getProgress() < pd.getMax()) {
                            pd.incrementProgressBy(20);
                            pd.incrementSecondaryProgressBy(75);
                            h.sendEmptyMessageDelayed(0, 100);
                        } else {
                            Toast.makeText(MainActivity.this,"Downloaded",Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                };
                h.sendEmptyMessageDelayed(0, 2000);
                break;
            default:
                break;
        }
    }
}
