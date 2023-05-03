package ru.kau.mygtd2.objects;

//import android.arch.persistence.room.Embedded;
//import android.arch.persistence.room.Entity;

//import android.arch.persistence.room.PrimaryKey;

//import android.arch.persistence.room.TypeConverters;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.Date;

import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.common.enums.TypeOfTask;
import ru.kau.mygtd2.jsonconvert.DateConverter;
import ru.kau.mygtd2.utils.Converters;
// https://stackoverflow.com/questions/44498616/android-architecture-components-using-enums

@Entity(tableName = "tasks",
        indices = {        @Index(value = {"guid"}, unique = true)}
        )
public class Task implements Serializable{  //Parcelable {



    //@PrimaryKey(autoGenerate = true)
    private long id;


    @NonNull
    private String title;
    private String searchtitle;
    private String description;

    private long info_id;
    private long meeting_id;

    private long project_id;

    private long target_id;

    private long priority_id;

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getParenttask_id() {
        return parenttask_id;
    }

    public void setParenttask_id(long parenttask_id) {
        this.parenttask_id = parenttask_id;
    }

    private long parenttask_id;

    private int isFavourite = 0;

    private String bgColor;

    @Ignore
    private int level;

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /*@NonNull
    private int previousStatus = 1;

    @NonNull
    public int getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(@NonNull int previousStatus) {
        this.previousStatus = previousStatus;
    }*/


    //@JsonAdapter(StatusConverter.class)
    @TypeConverters(Converters.class)
    private Status previousStatus;

    public Status getPreviousStatus() {
        return (previousStatus == null) ? Status.NOTHING : previousStatus ;
    }

    public void setPreviousStatus(Status previousStatus) {
        if (previousStatus == null) {
            previousStatus = Status.NOTHING;
        }
        this.previousStatus = previousStatus;
    }

    //@NonNull
    @TypeConverters(Converters.class)
    private Status status;

    //@NonNull
    public Status getStatus() {
        return (status == null) ? Status.NOTHING : status ;
    }

    public void setStatus( Status status) {
        if (status == null) {
            status = Status.NOTHING;
        }
        this.status = status;
    }


    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public long getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(long meeting_id) {
        this.meeting_id = meeting_id;
    }

    /*@NonNull
    private Integer status = 2;



    public void setStatus(@NonNull Integer status) {
        this.status = status;
    }

    @NonNull
    public Integer getStatus() {
        return status;
    }*/
    /*
    public void setStatus(@NonNull int status) {
        this.status = status;
    }*/

    /*@NonNull
    private int status = 2;*/

    public TypeOfTask getTypeoftask() {
        return (typeoftask == null) ? TypeOfTask.TASK : typeoftask;
    }

    public void setTypeoftask(TypeOfTask typeoftask) {
        this.typeoftask = typeoftask;
    }

    @TypeConverters(Converters.class)
    //@JsonAdapter(TypeOfTaskConverter.class)
    private TypeOfTask typeoftask = TypeOfTask.TASK;
    //private TypeOfTask typeoftask;


    @TypeConverters(Converters.class)
    @JsonAdapter(DateConverter.class)
    private Date dateCreate;     // Дата фактического создания задачи в программме

    @TypeConverters(Converters.class)
    @JsonAdapter(DateConverter.class)
    private Date dateBegin;      // Дата предполагаемого начала работ

    @TypeConverters(Converters.class)
    @JsonAdapter(DateConverter.class)
    private Date dateEnd;       // Дата предполагаемого окончания работ по задаче

    @TypeConverters(Converters.class)
    @JsonAdapter(DateConverter.class)
    private Date dateClose;     // Дата фактического окончания работ по задаче

    @TypeConverters(Converters.class)
    @JsonAdapter(DateConverter.class)
    private Date dateEdit;   // Дата последнего изменения по задаче

    //@TypeConverters(Converters.class)
    private String dateCreateStr;
    //@TypeConverters(Converters.class)
    private String dateBeginStr;
    //@TypeConverters(Converters.class)
    private String dateEndStr;

    private String dateCloseStr;

    private String dateEditStr;

    private String parenttaskguid = "";

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }

    public String getDateEditStr() {
        return dateEditStr;
    }

    public void setDateEditStr(String dateEditStr) {
        this.dateEditStr = dateEditStr;
    }

    public Date getDateClose() {
        return dateClose;
    }

    public void setDateClose(Date dateClose) {
        this.dateClose = dateClose;
    }

    public String getDateCloseStr() {
        return dateCloseStr;
    }

    public void setDateCloseStr(String dateCloseStr) {
        this.dateCloseStr = dateCloseStr;
    }

    //@TypeConverters(Converters.class)



    /*public Status getStatus() {

        return (status == null) ? Status.NOTHING : status;
    }*/

    /*public int getStatus() {
        return status;
    }*/

    public long getPriority_id() {
        return priority_id;
    }

    public void setPriority_id(long priority_id) {
        this.priority_id = priority_id;
    }

    /*public long getStatusId(){
        long id = Status.to(status);
        //return id;
        return status.Value;
    }*/

    /*public void setStatus(Status status) {
        this.status = status;
    }*/

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

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public long getInfo_id() {
        return info_id;
    }

    public void setInfo_id(long info_id) {
        this.info_id = info_id;
    }

    public String getDateCreateStr() {
        return dateCreateStr;
    }

    public void setDateCreateStr(String dateCreateStr) {
        this.dateCreateStr = dateCreateStr;
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

    @NonNull
    @PrimaryKey
    private String guid;

    @NonNull
    private String deviceguid;

    @NonNull
    public String getGuid() {
        return guid;
    }

    public void setGuid(@NonNull String guid) {
        this.guid = guid;
    }

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

    @Ignore
    private String backupguid;

    public String getBackupguid() {
        return backupguid;
    }

    public void setBackupguid(String backupguid) {
        this.backupguid = backupguid;
    }

    public String getParenttaskguid() {
        return parenttaskguid;
    }

    public void setParenttaskguid(String parenttaskguid) {
        this.parenttaskguid = parenttaskguid;
    }
}
