package com.example.p0481simpleadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_CHECKED = "checked";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] texts = { "sometext 1", "sometext 2", "sometext 3",
                "sometext 4", "sometext 5" };
        boolean[] checked = { true, false, false, true, false };
        int img = R.drawable.ic_launcher_foreground;

        ArrayList<Map<String,Object>> data = new ArrayList<>(texts.length);
        Map<String,Object> m;
        for (int i = 0; i < texts.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NAME_TEXT,texts[i]);
            m.put(ATTRIBUTE_NAME_CHECKED,checked[i]);
            m.put(ATTRIBUTE_NAME_IMAGE,img);
            data.add(m);
        }

        String[] from = new String[] {ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_CHECKED,ATTRIBUTE_NAME_IMAGE};
        int[] to = new int[] {R.id.tvText,R.id.cbChecked,R.id.ivImg};

        SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.item,from,to);
        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(adapter);
    }

}
