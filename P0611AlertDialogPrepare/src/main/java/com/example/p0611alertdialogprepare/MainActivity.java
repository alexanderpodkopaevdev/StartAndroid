package com.example.p0611alertdialogprepare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = "myLogs";
    final static int DIALOG = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        showDialog(DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG) {
            AlertDialog.Builder abd = new AlertDialog.Builder(this);
            abd.setTitle("Current time");
            abd.setMessage(getTime());
            return abd.create();
        } else
            return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        Log.d(LOG_TAG, "onPrepareDialog");
        if (id == DIALOG) {
            ((AlertDialog) dialog).setMessage(getTime());
        }
    }


    private CharSequence getTime() {
        return sdf.format(new Date());
    }
}
