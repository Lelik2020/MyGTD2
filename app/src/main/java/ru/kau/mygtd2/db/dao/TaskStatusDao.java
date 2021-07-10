package ru.kau.mygtd2.db.dao;

//import android.arch.persistence.room.Dao;

//import android.arch.persistence.room.Delete;

//import android.arch.persistence.room.Insert;

//import android.arch.persistence.room.OnConflictStrategy;

//import android.arch.persistence.room.Query;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.TaskStatus;

//mport android.arch.persistence.room.Update;

@Dao
public interface TaskStatusDao {

    @Query("SELECT * FROM taskstatus")
    List<TaskStatus> getAll();

    @Query("SELECT id FROM taskstatus")
    List<Integer> getAllId();

    @Query("SELECT * FROM taskstatus WHERE id = :idstatus")
    TaskStatus getById(long idstatus);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskStatus task);

    @Update
    void update(TaskStatus task);

    @Delete
    void delete(TaskStatus task);

}
