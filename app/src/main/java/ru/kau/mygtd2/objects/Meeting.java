package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ru.kau.mygtd2.utils.Converters;

@Entity(tableName = "meetings")
public class Meeting {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String title;


    @TypeConverters(Converters.class)
    @NonNull
    private Date dateBegin;
    @TypeConverters(Converters.class)
    @NonNull
    private Date dateEnd;


    private String dateBeginStr;
    private String dateEndStr;

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

    @NonNull
    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(@NonNull Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    @NonNull
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(@NonNull Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateBeginStr() {
        return dateBeginStr;
    }

    public void setDateBeginStr(String dateBeginStr) {
        this.dateBeginStr = dateBeginStr;
    }

    public String getDateEndStr() {
        return dateEndStr;
    }

    public void setDateEndStr(String dateEndStr) {
        this.dateEndStr = dateEndStr;
    }
}
