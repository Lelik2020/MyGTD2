package ru.kau.mygtd2.objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "comments")
public class Comment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long info_id;
    private long task_id;
    private long meeting_id;

    @NonNull
    private String infoguid;

    @NonNull
    public String getInfoguid() {
        return infoguid;
    }

    public void setInfoguid(@NonNull String infoguid) {
        this.infoguid = infoguid;
    }

    @NonNull
    public String getTaskguid() {
        return taskguid;
    }

    public void setTaskguid(@NonNull String taskguid) {
        this.taskguid = taskguid;
    }

    @NonNull
    private String taskguid;

    public long getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(long meeting_id) {
        this.meeting_id = meeting_id;
    }

    @NonNull
    String title;

    @NonNull
    Date dateCreate;
    @NonNull
    String dateCreateStr;

    public String getDateCreateStr() {
        return dateCreateStr;
    }

    public void setDateCreateStr(String dateCreateStr) {
        this.dateCreateStr = dateCreateStr;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInfo_id() {
        return info_id;
    }

    public void setInfo_id(long info_id) {
        this.info_id = info_id;
    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }
}
