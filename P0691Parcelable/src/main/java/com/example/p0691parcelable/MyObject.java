package com.example.p0691parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MyObject implements Parcelable {
    final static String LOG_TAG = "myLogs";

    public String s;
    public int i;

    public MyObject(String s, int i) {
        Log.d(LOG_TAG, "MyObject(String s, int i)");
        this.s = s;
        this.i = i;
    }

    protected MyObject(Parcel in) {
        Log.d(LOG_TAG, "MyObject(Parcel in)");
        s = in.readString();
        i = in.readInt();
    }

    public static final Creator<MyObject> CREATOR = new Creator<MyObject>() {
        @Override
        public MyObject createFromParcel(Parcel in) {
            Log.d(LOG_TAG, "createFromParcel");
            return new MyObject(in);
        }

        @Override
        public MyObject[] newArray(int size) {
            return new MyObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d(LOG_TAG, "writeToParcel");
        dest.writeString(s);
        dest.writeInt(i);
    }
}
