package com.example.p0361sqlitequery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    String[] name = {"Китай", "США", "Бразилия", "Россия", "Япония",
            "Германия", "Египет", "Италия", "Франция", "Канада"};
    int[] people = {1400, 311, 195, 142, 128, 82, 80, 60, 66, 35};
    String[] region = {"Азия", "Америка", "Америка", "Европа", "Азия",
            "Европа", "Африка", "Европа", "Европа", "Америка"};

    Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving;
    EditText etFunc, etPeople, etRegionPeople;
    RadioGroup rgSort;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnPeople = findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);

        btnSort = findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        etFunc = findViewById(R.id.etFunc);
        etPeople = findViewById(R.id.etPeople);
        etRegionPeople = findViewById(R.id.etRegionPeople);

        rgSort = findViewById(R.id.rgSort);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query("MYTABLE", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < 10; i++) {
                cv.put("NAME", name[i]);
                cv.put("PEOPLE", people[i]);
                cv.put("REGION", region[i]);
                Log.d(LOG_TAG, "id = " + db.insert("MYTABLE", null, cv));
            }
        }
        c.close();
        //dbHelper.close();
        onClick(btnAll);
    }

    @Override
    public void onClick(View v) {

        // подключаемся к базе
        db = dbHelper.getWritableDatabase();

        // данные с экрана
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        // курсор
        Cursor c = null;

        // определяем нажатую кнопку
        switch (v.getId()) {
            // Все записи
            case R.id.btnAll:
                Log.d(LOG_TAG, "--- Все записи ---");
                c = db.query("mytable", null, null, null, null, null, null);
                break;
            // Функция
            case R.id.btnFunc:
                Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
                columns = new String[]{sFunc};
                c = db.query("mytable", columns, null, null, null, null, null);
                break;
            // Население больше, чем
            case R.id.btnPeople:
                Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[]{sPeople};
                c = db.query("mytable", null, selection, selectionArgs, null, null,
                        null);
                break;
            // Население по региону
            case R.id.btnGroup:
                Log.d(LOG_TAG, "--- Население по региону ---");
                columns = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                c = db.query("mytable", columns, null, null, groupBy, null, null);
                break;
            // Население по региону больше чем
            case R.id.btnHaving:
                Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople
                        + " ---");
                columns = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                c = db.query("mytable", columns, null, null, groupBy, having, null);
                break;
            // Сортировка
            case R.id.btnSort:
                // сортировка по
                switch (rgSort.getCheckedRadioButtonId()) {
                    // наименование
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    // население
                    case R.id.rPeople:
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    // регион
                    case R.id.rRegion:
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                c = db.query("mytable", null, null, null, null, null, orderBy);
                break;
        }
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);

                } while (c.moveToNext());
            }
            c.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");

        dbHelper.close();
    }



    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(@Nullable Context context) {
            super(context, "MyDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            db.execSQL("CREATE TABLE MYTABLE (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "PEOPLE INTEGER," +
                    "REGION TEXT);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
