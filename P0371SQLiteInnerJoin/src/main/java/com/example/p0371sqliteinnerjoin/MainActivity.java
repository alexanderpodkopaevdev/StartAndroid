package com.example.p0371sqliteinnerjoin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    // данные для таблицы должностей
    int[] position_id = {1, 2, 3, 4};
    String[] position_name = {"Директор", "Программер", "Бухгалтер", "Охранник"};
    int[] position_salary = {15000, 13000, 10000, 8000};

    // данные для таблицы людей
    String[] people_name = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь"};
    int[] people_posid = {2, 3, 2, 2, 3, 1, 2, 4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c;

        Log.d(LOG_TAG, "--- Table position ---");
        c = db.query("position", null, null, null, null, null, null);
        logCursor(c);
        c.close();
        Log.d(LOG_TAG, "--- ---");

        Log.d(LOG_TAG, "--- Table people ---");
        c = db.query("people", null, null, null, null, null, null);
        logCursor(c);
        c.close();
        Log.d(LOG_TAG, "--- ---");

        Log.d(LOG_TAG, "--- INNER JOIN with rawQuery---");

        Log.d(LOG_TAG, "--- INNER JOIN with rawQuery---");
        String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
                + "from people as PL "
                + "inner join position as PS "
                + "on PL.posid = PS.id "
                + "where salary > ?";
        c = db.rawQuery(sqlQuery, new String[] {"12000"});
        logCursor(c);
        c.close();
        Log.d(LOG_TAG, "--- ---");

        // выводим результат объединения
        // используем query
        Log.d(LOG_TAG, "--- INNER JOIN with query---");
        String table = "people as PL inner join position as PS on PL.posid = PS.id";
        String columns[] = { "PL.name as Name", "PS.name as Position", "salary as Salary" };
        String selection = "salary < ?";
        String[] selectionArgs = {"12000"};
        c = db.query(table, columns, selection, selectionArgs, null, null, null);
        logCursor(c);
        c.close();
        Log.d(LOG_TAG, "--- ---");

        // закрываем БД
        dbHelper.close();

    }

    private void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context) {
            super(context, "MyDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            ContentValues cv = new ContentValues();

            db.execSQL("create table position ("
                    + "id integer primary key,"
                    + "name text," + "salary integer"
                    + ");");

            for (int i = 0; i < position_id.length; i++) {
                cv.clear();
                cv.put("id", position_id[i]);
                cv.put("name", position_name[i]);
                cv.put("salary", position_salary[i]);
                db.insert("position", null, cv);
            }

            db.execSQL("create table people ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "posid integer"
                    + ");");

            for (int i = 0; i < people_name.length; i++) {
                cv.clear();
                cv.put("name", people_name[i]);
                cv.put("posid", people_posid[i]);
                db.insert("people", null, cv);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
