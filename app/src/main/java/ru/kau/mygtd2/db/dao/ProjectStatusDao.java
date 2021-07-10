package ru.kau.mygtd2.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.ProjectStatus;

@Dao
public interface ProjectStatusDao {

    @Query("SELECT * FROM projectstatus")
    List<ProjectStatus> getAll();

    @Query("SELECT * FROM projectstatus WHERE id = :idstatus")
    ProjectStatus getById(int idstatus);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProjectStatus projectstatus);

    @Update
    void update(ProjectStatus projectstatus);

    @Delete
    void delete(ProjectStatus projectstatus);

}
