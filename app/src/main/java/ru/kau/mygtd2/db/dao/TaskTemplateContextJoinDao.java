package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.kau.mygtd2.objects.TaskTemplateContextJoin;

@Dao
public interface TaskTemplateContextJoinDao {

    @Query("DELETE FROM tasktemplatecontexts WHERE tasktemplateguid = :taskGyid")
    void deleteTaskTemplateContekst(final String taskGyid);

    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<TaskTemplateContextJoin> taskTemplateContextJoins);*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskTemplateContextJoin taskTemplateContextJoin);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<TaskTemplateContextJoin> taskTemplateContextJoin);

}
