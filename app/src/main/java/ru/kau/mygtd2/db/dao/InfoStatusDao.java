package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.InfoStatus;

@Dao
public interface InfoStatusDao {

    @Query("SELECT * FROM infostatus")
    List<InfoStatus> getAll();

    @Query("SELECT * FROM infostatus WHERE id = :idstatus")
    InfoStatus getById(long idstatus);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(InfoStatus infoStatus);

    @Update
    void update(InfoStatus infoStatus);

    @Delete
    void delete(InfoStatus infoStatus);

}
