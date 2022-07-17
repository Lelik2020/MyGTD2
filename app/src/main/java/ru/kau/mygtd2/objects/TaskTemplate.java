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
import ru.kau.mygtd2.jsonconvert.TypeOfTaskConverter;
import ru.kau.mygtd2.utils.Converters;


@Entity(tableName = "tasktemplate",
        indices = {        @Index(name = "index_tasktemplate_guid", value = {"templateguid"}, unique = true)}

        //columns={deviceguid=Column{name='deviceguid', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, description=Column{name='description', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, target_id=Column{name='target_id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, title=Column{name='title', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, searchtitle=Column{name='searchtitle', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, priority_id=Column{name='priority_id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, bgColor=Column{name='bgColor', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, project_id=Column{name='project_id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, typeOfTask=Column{name='typeOfTask', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='null'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, category=Column{name='category', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, status=Column{name='status', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='null'}, templateguid=Column{name='templateguid', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=1, defaultValue='null'}}, foreignKeys=[], indices=[Index{name='index_tasktemplate_templateguid', unique=true, columns=[templateguid], orders=[ASC]}]}



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
