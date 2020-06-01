package com.example.myapplication;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SyncService<T> extends Service {

    private static final String TAG_SUCCESS = "success" ;
    Timer timer =new Timer();
    JSONParser jParser =  new JSONParser();
    private static String server_url = "http://192.168.43.109:8000/dbconfig.php";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("Service","onStartComm");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //Your code here
                SyncFunction();
            }
        }, 0, 1000);//5 Minutes
        return START_STICKY;
    }



    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d("Service","onCreate");

    }

    public void SyncFunction(){
        List<Task> UnSynced = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().getUnsynced();
        for(int i=0;i<UnSynced.size();i++) {
            Log.d("Syncing","for loop");
            sendTaskFunc(UnSynced.get(i));
        }
    }

    private void sendTaskFunc(Task task) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", ((Task) task).getTitle()));
        params.add(new BasicNameValuePair("status", ((Task) task).getStatus()));


        JSONObject json = jParser.makeHttpRequest(server_url, "POST", params);

        try {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                ((Task) task).setSync(1);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().update((Task) task);
                //callGetValue();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("Service","onDestroy");
        Intent restartService = new Intent("InternetConnectionChangeReceiver");
        LocalBroadcastManager.getInstance(this).sendBroadcast(restartService);
    }

}

