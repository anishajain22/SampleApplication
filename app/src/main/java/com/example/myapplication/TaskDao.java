package com.example.myapplication;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Insert
    void insert(Task task);


    @Query("SELECT * FROM task WHERE sync = 0 " )
    List<Task> getUnsynced();

    //    @Delete
//    void delete(Task task);
//
    @Update
    void update(Task task);

}

