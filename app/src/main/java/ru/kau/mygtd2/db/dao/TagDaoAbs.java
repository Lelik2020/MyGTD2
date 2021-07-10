package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.TaskTagJoin;

@Dao
public abstract class TagDaoAbs {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(Tag tag);

    @Update
    public abstract void update(Tag tag);

    @Delete
    public static void delete(Tag tag) {
    }

    @Transaction
    public static void deleteTag(Tag tag) {

        List<TaskTagJoin> taskTagJoinList = MyApplication.getDatabase().taskTagJoinDao().getTaskForTag(tag.getId());
        for (TaskTagJoin taskTagJoin: taskTagJoinList){
            MyApplication.getDatabase().taskTagJoinDao().delete(taskTagJoin);
        }
        MyApplication.getDatabase().tagDao().delete(tag);
    }

}
