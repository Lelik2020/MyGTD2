package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface CommonDao {

    @Query("SELECT count(*) FROM tasks")
    long getCountTask();




}
