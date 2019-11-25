package com.example.p0621alertdialogitems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Currency;

public class DB {
    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mytable";

    public static final String COLUMN_ID = "_id";
    public static final String COLUNM_TEXT = "text";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME +
            " (" + COLUMN_ID + " integer primary key, " +
            COLUNM_TEXT + " text);";

    private final Context ctx;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DB(Context ctx) {
        this.ctx = ctx;
    }
    public void open() {
        dbHelper = new DBHelper(ctx,DB_NAME,null,DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }

    public void changeRec(int id, String text) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID,id);
        cv.put(COLUNM_TEXT,text);
        db.update(TABLE_NAME,cv,COLUMN_ID + "=" + id,null);
    }


    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            ContentValues cv = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv.clear();
                cv.put(COLUMN_ID,i);
                cv.put(COLUNM_TEXT, "sometext " + i);
                db.insert(TABLE_NAME,null,cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
