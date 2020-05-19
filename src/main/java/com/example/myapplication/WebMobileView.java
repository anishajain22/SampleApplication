package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class WebMobileView extends AppCompatActivity {

    private static final String JAVASCRIPT_OBJ = "javascript_obj";
    private static String BASE_URL = "file:///android_asset/webview.html";

    WebView webview;
    Button SendToWeb;
    EditText textToWeb;
    TextView textToMob;
    final Handler myHandler = new Handler();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("JavascriptInterface")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webmobile);

        webview = (WebView) findViewById(R.id.my_web_view);
        SendToWeb = (Button) findViewById(R.id.btn_send_to_web);
        textToWeb = (EditText) findViewById(R.id.edit_text_to_web);
        textToMob= (TextView) findViewById(R.id.txt_from_web);


        webview.getSettings().setJavaScriptEnabled(true);

        JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        webview.addJavascriptInterface(myJavaScriptInterface, "Android");
        webview.loadUrl(BASE_URL);

        webview.setWebViewClient(new WebViewClient(){
            public void onPageFinished(View webview, String url){
                injectJavaScriptFunction();
            }

        });


        SendToWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.evaluateJavascript("javascript: " +
                        "updateFromAndroid(\"" + textToWeb.getText().toString() + "\")", null);
            }
        });



    }
    @Override
    public void onDestroy() {
        webview.removeJavascriptInterface(JAVASCRIPT_OBJ);
        super.onDestroy();
    }



    private void injectJavaScriptFunction() {
        webview.loadUrl("javascript: " +
                "window.androidObj.textToAndroid = function(message) { " +
                JAVASCRIPT_OBJ + ".textFromWeb(message) }");
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        public void showToast(String webMessage){
            final String msgeToast = webMessage;
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    // This gets executed on the UI thread so it can safely modify Views
                    textToMob.setText(msgeToast);
                }
            });

            Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void textToAndroid(String data) {
            //Get the string value to process
            textToMob = (TextView) findViewById(R.id.txt_from_web);
            textToMob.setText(data);
        }
    }
}
