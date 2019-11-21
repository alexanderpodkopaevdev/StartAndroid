package com.example.p0501simpleadaptercustom2;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_PB = "pb";
    final String ATTRIBUTE_NAME_LL = "ll";

    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int load[] = {41, 48, 22, 35, 30, 67, 51, 88};

        ArrayList<Map<String, Object>> data = new ArrayList<>(load.length);
        Map<String, Object> m;

        for (int i = 0; i < load.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NAME_TEXT, "Day: " + (i + 1) + ". Load: " + load[i] + "%");
            m.put(ATTRIBUTE_NAME_PB, load[i]);
            m.put(ATTRIBUTE_NAME_LL, load[i]);
            data.add(m);
        }

        String[] from = new String[]{ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_PB, ATTRIBUTE_NAME_LL};
        int[] to = new int[]{R.id.tvLoad, R.id.pbLoad, R.id.llLoad};

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        adapter.setViewBinder(new MySimpleBinder());

        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(adapter);
    }

    private class MySimpleBinder implements SimpleAdapter.ViewBinder {

        int red = getResources().getColor(R.color.Red);
        int orange = getResources().getColor(R.color.Orange);
        int green = getResources().getColor(R.color.Green);

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            int i = 0;
            switch (view.getId()) {
                case R.id.llLoad:
                    i = ((Integer) data).intValue();
                    if (i < 40) view.setBackgroundColor(green);
                    else if (i < 70) view.setBackgroundColor(orange);
                    else view.setBackgroundColor(red);
                    return true;
                case R.id.pbLoad:
                    i = ((Integer) data).intValue();
                    ((ProgressBar) view).setProgress(i);
                    return true;

            }
            return false;
        }
    }
}
