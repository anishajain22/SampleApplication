package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;





public class TaskAcitivity extends AppCompatActivity  {

    private static final String JAVASCRIPT_OBJ ="javascript_obj" ;
    WebView webview;
    private static String BASE_URL = "file:///android_asset/taskListhtml.html";
    private static String server_url = "http://192.168.43.109:8000/dbconfig.php";
    ProgressDialog pDialog ;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TASKS = "tasks";
    private static final String TAG_TITLE = "title";
    private static final String TAG_STATUS = "status";
    ArrayList<HashMap<String, String>> tasksList;
    private static String get_url = "http://192.168.43.109:8000/dbGet.php";
    JSONParser jParser = new JSONParser();
    JSONArray tasks= null;
    AlertDialog.Builder builder ;


    JavaScriptInterface myJavaScriptInterface;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("JavascriptInterface")
    public void onCreate(Bundle saveInstaceState) {
        //new backGround().execute();
        super.onCreate(saveInstaceState);
        setContentView(R.layout.tasklist);
        tasksList = new ArrayList<HashMap<String, String>>();

        builder = new AlertDialog.Builder(this);
        webview = (WebView) findViewById(R.id.taskList);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        myJavaScriptInterface = new JavaScriptInterface(this);

        webview.addJavascriptInterface(myJavaScriptInterface, "Android");
        webview.loadUrl(BASE_URL);
        Log.d("myTask", "errorhere");
        webview.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onPageFinished(View webview, String url) {
                injectJavaScriptFunction();
            }

        });

        myJavaScriptInterface.getValue();
        Intent intent = new Intent(TaskAcitivity.this , SyncService.class);
        //startService(intent);



    }

    @Override
    public void onDestroy() {
        webview.removeJavascriptInterface(JAVASCRIPT_OBJ);
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void injectJavaScriptFunction() {
        webview.evaluateJavascript("javascript: setEmpty()", null);
    }


    public class JavaScriptInterface {
        Context mContext;
        Timer timer = new Timer();

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @JavascriptInterface
        public void setValue(final String title, final String status) {
            class SaveTask extends AsyncTask<Void, Void, Void> {
                @Override
                protected Void doInBackground(Void... voids) {
                    Task task = new Task();
                    task.setTitle(title);
                    task.setStatus(status);
                    task.setSync(0);
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .taskDao()
                            .insert(task);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }

            SaveTask st = new SaveTask();
            st.execute();
            getValue();
        }


        public void getValue() {
            webview = (WebView) findViewById(R.id.taskList);


            class GetTasks extends AsyncTask<Void, Void, List<Task>> {

                @Override
                protected List<Task> doInBackground(Void... voids) {
                    List<Task> taskList = DatabaseClient
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .taskDao()
                            .getAll();
                    return taskList;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                protected void onPostExecute(List<Task> tasks) {
                    String res="";
                    super.onPostExecute(tasks);
                    for(int i=0;i<tasks.size();i++){
                        Task temp= tasks.get(i);
                        res = res + "["+String.valueOf(temp.getId()) + " " +temp.getTitle() + " - " + temp.getStatus()+" "+temp.getSync()+"]";
                    }
                    final String finalRes = res;
                    webview.post(new Runnable() {
                        @Override
                        public void run() {
                            webview.evaluateJavascript("javascript: wrapObj.getFunc(\""+ finalRes +"\")",null);
                        }
                    });
                }
            }
            GetTasks gt = new GetTasks();
            gt.execute();

        }

    }

}
