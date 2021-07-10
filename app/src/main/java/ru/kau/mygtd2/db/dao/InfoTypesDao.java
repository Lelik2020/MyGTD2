package ru.kau.mygtd2.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.InfoTypes;

@Dao
public interface InfoTypesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(InfoTypes infoTypes);

    @Update
    void update(InfoTypes infoTypes);

    @Delete
    void delete(InfoTypes infoTypes);

    @Query("SELECT * FROM infotypes")
    List<InfoTypes> getAll();

    @Query("SELECT * FROM infotypes WHERE id = :infotype_id")
    InfoTypes getById(long infotype_id);

}
