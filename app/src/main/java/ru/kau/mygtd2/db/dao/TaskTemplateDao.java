package ru.kau.mygtd2.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import ru.kau.mygtd2.objects.TaskTemplate;

@Dao
public interface TaskTemplateDao {

    @Update
    void update(TaskTemplate taskTemplate);

    @Insert
    void insert(TaskTemplate taskTemplate);


}
