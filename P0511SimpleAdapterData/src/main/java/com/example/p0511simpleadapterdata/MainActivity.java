package com.example.p0511simpleadapterdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;

    // имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    ListView lvSimple;
    SimpleAdapter adapter;
    ArrayList<Map<String,Object>> data;
    Map<String,Object> m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NAME_TEXT, "sometext " + i);
            m.put(ATTRIBUTE_NAME_IMAGE, R.drawable.ic_launcher_foreground);
            data.add(m);
        }

        String[] from = new String[] {ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_IMAGE};
        int[] to = new int[] {R.id.tvText,R.id.ivImg};

        adapter = new SimpleAdapter(this,data,R.layout.item,from,to);
        lvSimple = findViewById(R.id.lvSimple);
        lvSimple.setAdapter(adapter);
        registerForContextMenu(lvSimple);
    }

    public void onButtonClick(View view) {
        m = new HashMap<String, Object>();
        m.put(ATTRIBUTE_NAME_TEXT, "sometext " + (data.size() + 1));
        m.put(ATTRIBUTE_NAME_IMAGE, R.drawable.ic_launcher_foreground);
        data.add(m);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,CM_DELETE_ID,0,"Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            data.remove(acmi.position);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
