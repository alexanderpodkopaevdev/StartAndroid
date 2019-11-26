package com.example.p0791xmlpullparser;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String tmp = "";
        try {
            XmlPullParser xpp = prepareXml();
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG,"StartDocument");
                        break;
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "Start tag " + xpp.getName() + ", " +
                                "depth = " + xpp.getDepth() + ", " +
                                "attrCount = " + xpp.getAttributeCount());
                        tmp = "";
                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            tmp += xpp.getAttributeName(i) + " = " +
                                    xpp.getAttributeValue(i) + ", ";
                        }
                        if (!TextUtils.isEmpty(tmp)) {
                            Log.d(LOG_TAG,"Attributes: " + tmp);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        break;
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;
                    default:
                        break;
                }
                xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XmlPullParser prepareXml() {
        return getResources().getXml(R.xml.data);
    }
}
