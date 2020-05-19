package com.example.myapplication;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class StandardWebView extends AppCompatActivity {

    // Declare Variables
    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Prepare the progress bar
        requestWindowFeature(Window.FEATURE_PROGRESS);

        // Get the view from webview.xml
        setContentView(R.layout.webview);

        // Locate the WebView in webview.xml
        webview = (WebView) findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient());

        // Enable Javascript to run in WebView
        webview.getSettings().setJavaScriptEnabled(true);

        //Enabling local cache for webview
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setAppCachePath(StandardWebView.this.getCacheDir().getPath());

        // Allow Zoom in/out controls
        webview.getSettings().setBuiltInZoomControls(true);

        // Zoom out the best fit your screen
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        // Load URL
        webview.loadUrl("https://www.google.com");

        // Show the progress bar
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setProgress(progress * 100);
            }
        });

        // Call private class InsideWebViewClient
        webview.setWebViewClient(new InsideWebViewClient());

    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }

    }

}