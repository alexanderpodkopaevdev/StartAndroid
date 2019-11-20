package com.example.p0381sqlitetransaction;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";

    DBHelper dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "--- onCreate Activity ---");
        dbh = new DBHelper(this);
        myAction();
    }

    private void myAction() {
        db = dbh.getWritableDatabase();
        delete(db, "mytable");
        db.beginTransaction();
        insert(db, "mytable", "val1");
        db.setTransactionSuccessful();
        insert(db,"mytable","val2");
        db.endTransaction();
        insert(db,"mytable","val3");
        read(db, "mytable");
        dbh.close();
    }

    private void read(SQLiteDatabase db, String table) {
        Log.d(LOG_TAG, "Read table " + table);
        Cursor c = db.query(table,null,null,null,null,null,null);
        if (c!=null) {
            Log.d(LOG_TAG, "Records count = " + c.getCount());
            do {
                Log.d(LOG_TAG,c.getString(c.getColumnIndex("val")));
            } while (c.moveToNext());
        }
        c.close();
    }

    private void insert(SQLiteDatabase db, String table, String value) {
        Log.d(LOG_TAG, "Insert in table " + table + " value = " + value);
        ContentValues cv = new ContentValues();
        cv.put("val",value);
        db.insert(table,null,cv);
    }

    private void delete(SQLiteDatabase db, String table) {
        Log.d(LOG_TAG, "Delete all from table " + table);
        db.delete(table,null,null);
    }

    class DBHelper extends SQLiteOpenHelper {
        private DBHelper(@Nullable Context context) {
            super(context, "myDB", null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            db.execSQL("create table MYTABLE (" +
                    "id integer primary key autoincrement," +
                    "val text)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
