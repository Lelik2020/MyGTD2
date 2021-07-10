package ru.kau.mygtd2.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.TaskContextJoin;

@Dao
public interface TaskContextJoinDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskContextJoin taskContextJoin);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<TaskContextJoin> taskTagsJoin);

    // Получение контекстов для задачи
    @Query("select * from contexts inner join taskcontexts on id = idcontext " +
            "where idtask = :taskId")
    List<Contekst> getCotextsForTask(final long taskId);

    @Query("DELETE FROM taskcontexts WHERE idtask = :taskId")
    void deleteTaskContekst(final long taskId);

    @Query("SELECT * FROM taskcontexts")
    List<TaskContextJoin> getAll();

    @Update
    void update(TaskContextJoin taskContextJoin);


}
