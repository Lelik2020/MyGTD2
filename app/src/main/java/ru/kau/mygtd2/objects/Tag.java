package ru.kau.mygtd2.objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tags",
        indices = {
                @Index(value = {"title"}, unique = true)
        })

public class Tag implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String title;
    private String description;
    private String color;

    private int isarchive;

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

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public int getIsarchive() {
        return isarchive;
    }

    public void setIsarchive(int isarchive) {
        this.isarchive = isarchive;
    }
}
