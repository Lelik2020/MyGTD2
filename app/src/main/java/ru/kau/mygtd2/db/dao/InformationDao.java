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

import ru.kau.mygtd2.objects.Information;

@Dao
public interface InformationDao {

    @Query("SELECT * FROM informations")
    List<Information> getAll();

    @Query("SELECT count(*) FROM informations")
    long getCountAll();

    @Query("SELECT count(*) FROM informations WHERE idstatus = :idstatus")
    long getCountByStatus(long idstatus);

    @Query("SELECT * FROM informations WHERE id = :id")
    Information getById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Information information);

    @Update
    void update(Information information);

    @Delete
    void delete(Information information);

    @Query("SELECT guid FROM informations WHERE id = :id AND deviceguid = :deviceID")
    String getGuidById(long id, String deviceID);

    @Query("SELECT Max(id) FROM informations")
    long getMaxId();

}
