package com.example.p0691parcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static com.example.p0691parcelable.MyObject.LOG_TAG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        MyObject myObj = new MyObject("text",1);
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra(MyObject.class.getCanonicalName(),myObj);
        Log.d(LOG_TAG, "startActivity");
        startActivity(intent);
    }
}
