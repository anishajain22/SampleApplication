package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.renderscript.ScriptGroup;

import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class StandardWebView extends AppCompatActivity {

    WebView mTabHostView;
    final String AppID = "myWebview";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        mTabHostView =(WebView) findViewById(R.id.webview);
        Log.d("Debug","Create");
        mTabHostView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mTabHostView.setWebViewClient(new AndroidWebClient());
        Log.d("Path", getApplicationContext().getCacheDir().getAbsolutePath());


        if (!isNetworkAvailable()) { // loading offline
            Log.d("Debug", "Offline");
            mTabHostView.loadUrl("file:///"+getApplicationContext().getCacheDir().getAbsolutePath()
                    + File.separator+hashAppID(AppID)+".mht");
        } else {
            Log.d("Debug", "Online");
            mTabHostView.loadUrl("https://www.microsoft.com");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private class AndroidWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url,
                                  android.graphics.Bitmap favicon) {
        }
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.saveWebArchive(getApplicationContext().getCacheDir().getAbsolutePath()
                    + File.separator +hashAppID(AppID)+".mht");
            // our webarchive wull be available now at the above provided location with name "myArchive"+".mht"

        }
        public void onLoadResource(WebView view, String url) {

        }
    }

    public String hashAppID(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
