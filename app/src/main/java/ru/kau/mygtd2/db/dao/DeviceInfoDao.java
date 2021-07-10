package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.DeviceInfo;

@Dao
public interface DeviceInfoDao {

    @Query("SELECT * FROM devicesinfo WHERE deviceid = :id")
    List<DeviceInfo> getById(int id);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(DeviceInfo deviceInfo);

    @Update
    void update(DeviceInfo deviceInfo);

    @Delete
    void delete(DeviceInfo deviceInfo);


}
