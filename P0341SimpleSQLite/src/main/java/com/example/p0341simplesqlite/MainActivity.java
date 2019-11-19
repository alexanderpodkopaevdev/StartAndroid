package com.example.p0341simplesqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;
    DBHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail =  findViewById(R.id.etEmail);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                ContentValues cv = new ContentValues();
                cv.put("NAME",etName.getText().toString());
                cv.put("EMAIL",etEmail.getText().toString());
                long rowID = db.insert("MYTABLE",null,cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);

            }
        });

        btnRead =  findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                Cursor cursor = db.query("MYTABLE",null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("NAME");
                    int emailIndex = cursor.getColumnIndex("EMAIL");

                    do {
                        Log.d(LOG_TAG,
                                "ID = " + cursor.getString(idIndex)  +
                                        ", name = " + cursor.getString(nameIndex) +
                                        ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                }
                cursor.close();

            }
        });

        btnClear =  findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                int clearCount = db.delete("MYTABLE",null,null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);

            }
        });


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
                    "EMAIL TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
