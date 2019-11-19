package com.example.p0321simplebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        WebView webview = findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient());
        Uri data = getIntent().getData();
        webview.loadUrl(data.toString());
    }
}
