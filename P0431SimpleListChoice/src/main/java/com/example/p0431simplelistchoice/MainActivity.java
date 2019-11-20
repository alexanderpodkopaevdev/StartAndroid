package com.example.p0431simplelistchoice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";

    ListView listView;
    String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvMain);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.names,android.R.layout.simple_list_item_multiple_choice);
        listView.setAdapter(adapter);
        names = getResources().getStringArray(R.array.names);
    }

    public void getItems(View view) {
        Log.d(LOG_TAG, "checked: ");
        SparseBooleanArray sbArray = listView.getCheckedItemPositions();
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)) {
                Log.d(LOG_TAG, names[key]);
            }
        }
    }
}
