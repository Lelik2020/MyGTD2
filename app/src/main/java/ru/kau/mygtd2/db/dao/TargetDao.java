package ru.kau.mygtd2.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Target;

@Dao
public interface TargetDao {

    @Query("SELECT * FROM targets")
    List<Target> getAll();

    @Query("SELECT * FROM targets WHERE id = :target_id")
    Target getById(long target_id);

    @Query("SELECT count(*) FROM targets")
    int countOfTargets();

    @Query("SELECT id FROM targets")
    List<Integer> getAllId();


    //@Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Target target);

    @Update
    void update(Target target);

    @Delete
    void delete(Target target);

}
