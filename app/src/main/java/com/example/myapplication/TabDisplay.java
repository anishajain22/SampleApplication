package com.example.myapplication;
        ;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TabDisplay extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        Log.d("Debug","1");
        tabLayout.addTab(tabLayout.newTab().setText("Teams"));
        tabLayout.addTab(tabLayout.newTab().setText("Bing"));
        tabLayout.addTab(tabLayout.newTab().setText("Azure"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

//    private boolean isNetworkAvailable() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        return isConnected;
//    }
//
//    private class AndroidWebClient extends WebViewClient {
//        @Override
//        public void onPageStarted(WebView view, String url,
//                                  android.graphics.Bitmap favicon) {
//        }
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            view.saveWebArchive(getApplicationContext().getCacheDir().getAbsolutePath()
//                    + File.separator +hashAppID("")+".mht");
//            // our webarchive wull be available now at the above provided location with name "myArchive"+".mht"
//
//        }
//        public void onLoadResource(WebView view, String url) {
//
//        }
//    }
//
//    public String hashAppID(String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


}
