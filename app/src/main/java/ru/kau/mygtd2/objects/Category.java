package ru.kau.mygtd2.objects;


//import android.arch.persistence.room.Entity;

//import android.arch.persistence.room.Index;

//import android.arch.persistence.room.PrimaryKey;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories",
        indices = {
                @Index(value = {"title"}, unique = true)
        })
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int grp;

    public int getGrp() {
        return grp;
    }

    public void setGrp(int grp) {
        this.grp = grp;
    }

    private String title;


    private String description;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
