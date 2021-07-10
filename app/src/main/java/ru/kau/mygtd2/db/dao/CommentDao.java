package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Comment;

@Dao
public interface CommentDao {

    @Query("SELECT * FROM comments")
    List<Comment> getAll();

    /*@Query("SELECT * FROM comments WHERE task_id = :task_id ORDER BY dateCreate")
    List<Comment> getAllOfTask(long task_id);*/

    @Query("SELECT * FROM comments WHERE taskguid = :taskGuid ORDER BY dateCreate")
    List<Comment> getAllByTaskGuid(String taskGuid);

    @Query("SELECT * FROM comments WHERE taskguid = :taskGuid ORDER BY dateCreate")
    List<Comment> getAllOfTaskGuid(String taskGuid);

    @Query("SELECT * FROM comments WHERE infoguid = :infoGuid ORDER BY dateCreate")
    List<Comment> getAllOfInfoGuid(String infoGuid);

    //@Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Comment comment);

    @Update
    void update(Comment comment);

    @Delete
    void delete(Comment comment);

    @Query("SELECT * FROM comments WHERE id = :id")
    Comment getById(long id);

}
