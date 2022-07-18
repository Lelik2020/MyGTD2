package ru.kau.mygtd2.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.TaskTemplate;

@Dao
public interface TaskTemplateDao {

    @Update
    void update(TaskTemplate taskTemplate);

    @Insert
    void insert(TaskTemplate taskTemplate);

    @Query("SELECT * FROM tasktemplate ")
    List<TaskTemplate> getAll();


}
