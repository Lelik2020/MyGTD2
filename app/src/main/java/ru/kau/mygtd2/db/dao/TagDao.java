package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Tag;

@Dao
public interface TagDao {
    @Query("SELECT * FROM tags")
    List<Tag> getAll();

    @Query("SELECT * FROM tags INNER JOIN tasktags ON id = idtag WHERE idtask = :taskId")
    List<Tag> getAllByTask(final long taskId);

    @Query("SELECT * FROM tags INNER JOIN tasktags ON id = idtag WHERE taskguid = :taskGuid")
    List<Tag> getAllByTaskGuid(final String taskGuid);

    @Query("SELECT * FROM tags INNER JOIN tasktemplatetags ON id = idtag WHERE tasktemplateguid = :templateGuid")
    List<Tag> getAllByTemplateGuid(final String templateGuid);

    @Query("SELECT id FROM tags")
    List<Integer> getAllId();

    @Query("SELECT * FROM tags WHERE id = :idtag")
    Tag getById(int idtag);

    @Query("SELECT count() FROM tags")
    int countOfTags();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tag tag);

    @Update
    void update(Tag tag);

    @Delete
    void delete(Tag tag);


}
