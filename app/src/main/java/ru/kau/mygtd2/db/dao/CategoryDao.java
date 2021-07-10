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

import ru.kau.mygtd2.objects.Category;

//import ru.kau.mygtd.objects.Project;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories WHERE grp = :groupId")
    List<Category> getAllByGroup(int groupId);



    //@Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category Category);

}
