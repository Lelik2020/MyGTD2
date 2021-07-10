package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasktypes", indices = {
        @Index(value = {"title"}, unique = true)
})
public class TaskTypes {
    @PrimaryKey(autoGenerate = true)
    private int id;


    @NonNull
    private String title;

    @NonNull
    private String vusualTitle;

    @NonNull
    public String getVusualTitle() {
        return vusualTitle;
    }

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setVusualTitle(@NonNull String vusualTitle) {
        this.vusualTitle = vusualTitle;
    }

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
}
