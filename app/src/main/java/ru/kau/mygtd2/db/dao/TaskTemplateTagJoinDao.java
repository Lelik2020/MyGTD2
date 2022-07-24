package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.kau.mygtd2.objects.TaskTemplateTagJoin;

@Dao
public interface TaskTemplateTagJoinDao {
    @Query("SELECT * FROM tasktemplatetags")
    List<TaskTemplateTagJoin> getAll();
    @Query("DELETE FROM tasktemplatetags WHERE tasktemplateguid = :taskGuid")
    void deleteTaskTemplateTags(final String taskGuid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskTemplateTagJoin taskTemplateTagJoin);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<TaskTemplateTagJoin> taskTagsJoin);

}
