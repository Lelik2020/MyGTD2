package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "infotypes", indices = {
        @Index(value = {"title"}, unique = true)
})
public class InfoTypes {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @NonNull
    private String title = "";

    @NonNull
    private String visualTitle = "";

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

    @NonNull
    public String getVisualTitle() {
        return visualTitle;
    }

    public void setVisualTitle(@NonNull String visualTitle) {
        this.visualTitle = visualTitle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }








}
