package com.android.siggy_siggy.newprojectassignment2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.android.siggy_siggy.newprojectassignment2.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // gets url from mainactivity / listview
        String URLFromRss = getIntent().getStringExtra("list");

        // start a web view from the link
        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl(URLFromRss);
    }

}
