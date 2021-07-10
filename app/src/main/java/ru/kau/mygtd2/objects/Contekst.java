package ru.kau.mygtd2.objects;

//import android.arch.persistence.room.Entity;

//import android.arch.persistence.room.Index;

//import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "contexts",
        indices = {
                @Index(value = {"title"}, unique = true)

        })
public class Contekst {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String title;

    private String description;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
