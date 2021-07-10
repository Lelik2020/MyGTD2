package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Meeting;

@Dao
public interface MeetingDao {

    @Query("SELECT * FROM meetings ORDER BY dateBegin")
    List<Meeting> getAllMeetings();

    @Query("SELECT * FROM meetings WHERE id = :id")
    Meeting getById(long id);

    //@Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Meeting meeting);

    @Update
    void update(Meeting meeting);

    @Delete
    void delete(Meeting meeting);

}
