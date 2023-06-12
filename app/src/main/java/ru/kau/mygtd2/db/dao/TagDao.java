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

    @Query("SELECT * FROM tags ORDER BY :sort")
    List<Tag> getAll(String sort);

    @Query("SELECT * FROM tags WHERE title <> '' AND id NOT IN (:lstTop) ORDER BY isarchive, title ")
    List<Tag> getAllSortByTitle(List<Long> lstTop);

    @Query("SELECT * FROM tags WHERE title <> '' AND isarchive = 0 AND id NOT IN (:lstTop)  ORDER BY title")
    List<Tag> getNoArchiveSortByTitle(List<Long> lstTop);

    @Query("SELECT * FROM ( "
            + "SELECT DISTINCT tags.* "
            + "FROM tags, tasktags, tasks "
            + "WHERE tags.id = idtag AND taskguid = guid "
            + "ORDER BY dateedit "
            + "DESC "
            + "LIMIT :top "
            + ") "
            + "ORDER BY title"
            )
    List<Tag> getTopTagsSortByTitle(int top);
    //List<Tag> getTopTagsSortByTitle();

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

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tag tag);

    @Update
    void update(Tag tag);

    @Delete
    void delete(Tag tag);


}
