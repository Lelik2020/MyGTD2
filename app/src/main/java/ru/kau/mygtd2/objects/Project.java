package ru.kau.mygtd2.objects;

//import android.arch.persistence.room.Entity;

//import android.arch.persistence.room.Index;

//import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

import ru.kau.mygtd2.common.enums.PrStatus;
import ru.kau.mygtd2.utils.Converters;

@Entity(tableName = "projects",
        indices = {
                @Index(value = {"title"}, unique = true),
                @Index(value = {"searchtitle"}, unique = true)
        })
public class Project implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long parentid;

    @NonNull
    private String title;
    private String searchtitle;

    private String description;

    public PrStatus getPrStatus() {
        return prStatus;
    }

    public void setPrStatus(PrStatus prStatus) {
        this.prStatus = prStatus;
    }

    public void setPrStatus(ProjectStatus projectStatus){
        this.prStatus = PrStatus.from(projectStatus.getId());
    }

    @TypeConverters(Converters.class)
    private PrStatus prStatus;

    private String color;

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

    public long getParentid() {
        return parentid;
    }

    public void setParentid(long parentid) {
        this.parentid = parentid;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getSearchtitle() {
        return searchtitle;
    }

    public void setSearchtitle(String searchtitle) {
        this.searchtitle = searchtitle;
    }

}
