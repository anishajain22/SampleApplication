package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.renderscript.ScriptGroup;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class TabClass extends Fragment {

    WebView mTabHostView;
    final String AppID = "myWebview";
    String webUrl;

    TabClass(String url){
        webUrl=url;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Log.d("Debug","Create");
//        mTabHostView = findViewById();
//        mTabHostView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        mTabHostView.setWebViewClient(new AndroidWebClient());
//        mTabHostView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
//        Log.d("Debug", getContext().getCacheDir().getAbsolutePath());
//
//
//        if (!isNetworkAvailable()) { // loading offline
//            Log.d("Debug", "Offline");
//            mTabHostView.loadUrl("file:///"+getContext().getCacheDir().getAbsolutePath()
//                    + File.separator+hashAppID(AppID)+".mht");
//        } else {
//            Log.d("Debug", "Online");
//            mTabHostView.loadUrl(url);
//        }
//    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Debug","InTab");
        View v=inflater.inflate(R.layout.tab_display, container, false);
        mTabHostView = (WebView) v.findViewById(R.id.WebView);
        mTabHostView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mTabHostView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        mTabHostView.setWebViewClient(new AndroidWebClient());
        mTabHostView.getSettings().setJavaScriptEnabled(true);
        if(isNetworkAvailable()) {
            Log.d("Debug","Online");
            mTabHostView.loadUrl(webUrl);
        }else{
            Log.d("Debug","Offline");
            Log.d("Debug",getActivity().getApplicationContext().getFilesDir().getAbsolutePath()
                    + File.separator + hashAppID(webUrl) + ".mht");
            mTabHostView.loadUrl("file://"+getActivity().getApplicationContext().getFilesDir().getAbsolutePath()
                    + File.separator + hashAppID(webUrl) + ".mht");
        }

        // Force links and redirects to open in the WebView instead of in a browser
        return v;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private class AndroidWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url,
                                  android.graphics.Bitmap favicon) {
        }
        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            view.saveWebArchive(getActivity().getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + hashAppID(webUrl) + ".mht");
            Log.d("Debug",getActivity().getApplicationContext().getFilesDir().getAbsolutePath()
                    + File.separator + hashAppID(webUrl) + ".mht");
            Log.d("Debug", "onPageFinish"+ view.getContentHeight());
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
