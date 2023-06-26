package ru.kau.mygtd2.objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "targets",
        indices = {
                @Index(value = {"title"}, unique = true)
        })
public class Target implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;


    private String description;

    private String color;

    //@ColumnInfo(name = "isarchive")
    @ColumnInfo(defaultValue = "0")
    private int isarchive = 0;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsarchive() {
        return isarchive;
    }

    public void setIsarchive(int isarchive) {
        this.isarchive = isarchive;
    }
}
