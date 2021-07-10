package ru.kau.mygtd2.objects;


//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.Index;

//import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.kau.mygtd2.utils.Const;

@Entity(tableName = "taskstatus", indices = {
        @Index(value = {"title"}, unique = true)
})
public class TaskStatus {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getColor() {
        return (color == null) ? Const.DEFAULT_TASKSTATUS_COLOR : color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    private String title;
    private String description;
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
