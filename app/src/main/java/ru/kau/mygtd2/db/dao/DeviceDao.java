package ru.kau.mygtd2.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Device;

@Dao
public interface DeviceDao {



    @Query("SELECT * FROM devices WHERE id = :id")
    Device getById(int id);

    @Query("SELECT * FROM devices WHERE guid = :guid")
    Device getByGuid(String guid);

    @Query("SELECT * FROM devices")
    List<Device> getAll();

    @Query("SELECT guid FROM devices WHERE iscurrent = 1")
    String getGuidCurrentDevice();

    @Query("SELECT * FROM devices WHERE iscurrent = 1")
    Device getCurrentDevice();

    @Query("SELECT devicetype FROM devices WHERE iscurrent = 1")
    int getCurrentDeviceType();

    @Query("SELECT devicetype FROM devices WHERE guid = :guid")
    int getDeviceType(String guid);

    //@Query("SELECT devicetype FROM devices WHERE guid = :guid")
    //int setDeviceType(String guid, int devicetype);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Device device);

    @Update
    void update(Device device);

    @Delete
    void delete(Device device);

    @Query("DELETE FROM devices")
    int deleteAll();

    @Query("DELETE FROM devices WHERE iscurrent = 1")
    int deleteCorrentDevices();

}
