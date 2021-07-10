package ru.kau.mygtd2.db.dao;

//import android.arch.persistence.room.Dao;

//import android.arch.persistence.room.Delete;

//import android.arch.persistence.room.Insert;

//import android.arch.persistence.room.OnConflictStrategy;

//import android.arch.persistence.room.Query;

//import android.arch.persistence.room.Update;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Project;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM projects")
    List<Project> getAll();

    @Query("SELECT id FROM projects")
    List<Integer> getAllId();


    @Query("SELECT * FROM projects WHERE parentid = :idparentproject")
    List<Project> getChild(long idparentproject);

    @Query("SELECT * FROM projects WHERE id = :idProject")
    Project getProjectById(long idProject);

    //@Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);
}
