package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.TaskTagJoin;

@Dao
public interface TaskTagJoinDao {

    //
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskTagJoin taskTagJoin);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<TaskTagJoin> taskTagsJoin);

    // Получение тэгов для задачи
    @Query("select * from tags inner join tasktags on id = idtag " +
            "where idtask = :taskId")
    List<Tag> getTagsForTask(final long taskId);

    @Query("select * from tasktags where taskguid = :taskGuid")
    List<TaskTagJoin> getTagsForTask(final String taskGuid);

    // Получение задач для тега
    @Query("select * from tasktags where idtag = :tagId")
    List<TaskTagJoin> getTaskForTag(final long tagId);

    @Query("SELECT * FROM tasktags")
    List<TaskTagJoin> getAll();

    @Delete
    void delete(TaskTagJoin taskTagJoin);



    @Query("DELETE FROM tasktags WHERE idtask = :taskId")
    void deleteTaskTags(final long taskId);

    @Update
    void update(TaskTagJoin taskTagJoin);

}
