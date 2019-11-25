package com.example.p0651alertdialogcustom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    final int DIALOG = 1;

    int btn;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    ArrayList<TextView> textViews;
    TextView tvCount;
    LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViews = new ArrayList<>(10);

    }

    public void onclick(View view) {
        btn = view.getId();
        showDialog(DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Custom dialog");
        view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog,null);
        adb.setView(view);
        return adb.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG){
            tvCount = dialog.getWindow().findViewById(R.id.tvCount);
            TextView tvTime = dialog.getWindow().findViewById(R.id.tvTime);
            tvTime.setText(sdf.format(new Date()));
            if (btn == R.id.btnAdd) {
                TextView tv = new TextView(this);
                view.addView(tv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textViews.add(tv);
                tv.setText("TextView " + (textViews.size()));
            } else {
                if (textViews.size()>0) {
                    TextView tv = textViews.get(textViews.size()-1);
                    view.removeView(tv);
                    textViews.remove(tv);
                }
            }
            tvCount.setText("Кол-во TextView = " + textViews.size());

        }
    }
}
