package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button Savebtn,Clickbtn,WebMobbtn,TaskListBtn;
    EditText text;
    SharedPreferences sharedPreferences;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Savebtn = (Button) findViewById(R.id.save);
        Clickbtn = (Button) findViewById(R.id.click);
        text = (EditText) findViewById(R.id.text);
        WebMobbtn=(Button) findViewById(R.id.webMobile);
        TaskListBtn =(Button) findViewById(R.id.taskListBtn);

        sharedPreferences = getSharedPreferences("MyPref",MODE_APPEND);
        String str = sharedPreferences.getString("text","");
        if(str!=null){
            text.setText(str);
        }

        Clickbtn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Open StandardWebView.class
                Intent intent = new Intent(MainActivity.this,
                        StandardWebView.class);
                startActivity(intent);
            }
        });

        Savebtn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("text", text.getText().toString());
                myEdit.apply();
            }
        });

        WebMobbtn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,
                        WebMobileView.class);
                startActivity(intent);
                //Save pref
            }
        });

        TaskListBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,
                        TaskAcitivity.class);
                startActivity(intent);
                //Save pref
            }
        });
    }


    @Override
    protected void onPause(){
        super.onPause();
        text = (EditText) findViewById(R.id.text);
        sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("text", text.getText().toString());
        myEdit.apply();
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onResume(){
        super.onResume();
        text = (EditText) findViewById(R.id.text);
        sharedPreferences = getSharedPreferences("MyPref",MODE_APPEND);
        String str = sharedPreferences.getString("text",null);
        if(str!=null){
            text.setText(str);
        }

    }
}
