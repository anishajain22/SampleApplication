package com.example.myapplication;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "sync")
    private int sync;

//    @ColumnInfo(name = "finish_by")
//    private String finishBy;
//
//    @ColumnInfo(name = "finished")
//    private boolean finished;


    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //
    public String getTitle() {
        return title;
    }
    //
    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }
    //
    public void setStatus(String status) {
        this.status = status;
    }

    public int  getSync(){ return this.sync; }

    public void setSync(int sync){ this.sync=sync; }
//
//    public String getFinishBy() {
//        return finishBy;
//    }
//
//    public void setFinishBy(String finishBy) {
//        this.finishBy = finishBy;
//    }
//
//    public boolean isFinished() {
//        return finished;
//    }
//
//    public void setFinished(boolean finished) {
//        this.finished = finished;
//    }
}

