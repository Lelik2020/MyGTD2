package ru.kau.mygtd2.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;

import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.common.enums.TypeOfTask;
import ru.kau.mygtd2.jsonconvert.StatusConverter;
import ru.kau.mygtd2.jsonconvert.TypeOfTaskConverter;
import ru.kau.mygtd2.utils.Converters;


@Entity(tableName = "tasktemplate",
        indices = {        @Index(name = "index_tasktemplate_guid", value = {"templateguid"}, unique = true)}
)
public class TaskTemplate implements Serializable {

    @NonNull
    @ColumnInfo(name = "title")
    private String title;
    private String searchtitle;
    private String description;
    private long project_id;

    private long target_id;
    @ColumnInfo(name = "priority_id")
    private long priority_id;

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }
    private String bgColor;

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }


    @TypeConverters(Converters.class)
    @JsonAdapter(StatusConverter.class)
    private Status status;


    public Status getStatus() {
        return (status == null) ? Status.NOTHING : status ;
    }

    public void setStatus( Status status) {
        if (status == null) {
            status = Status.NOTHING;
        }
        this.status = status;
    }

    public TypeOfTask getTypeOfTask() {
        return typeOfTask;
    }

    public void setTypeOfTask(TypeOfTask typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    @TypeConverters(Converters.class)
    @JsonAdapter(TypeOfTaskConverter.class)
    private TypeOfTask typeOfTask = TypeOfTask.TASK;

    public long getPriority_id() {
        return priority_id;
    }

    public void setPriority_id(long priority_id) {
        this.priority_id = priority_id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }


    public long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(long target_id) {
        this.target_id = target_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @PrimaryKey
    private String templateguid;

    @NonNull
    private String deviceguid;

    @NonNull
    public String getDeviceguid() {
        return deviceguid;
    }

    public void setDeviceguid(@NonNull String deviceguid) {
        this.deviceguid = deviceguid;
    }

    @NonNull
    private int category = 3;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @NonNull
    public String getTemplateguid() {
        return templateguid;
    }

    public void setTemplateguid(@NonNull String templateguid) {
        this.templateguid = templateguid;
    }





}
