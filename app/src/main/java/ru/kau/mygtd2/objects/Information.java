package ru.kau.mygtd2.objects;

//import android.arch.persistence.room.PrimaryKey;

//import android.arch.persistence.room.TypeConverters;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import ru.kau.mygtd2.common.enums.TypeOfInfo;
import ru.kau.mygtd2.utils.Converters;

@Entity(tableName = "informations",
        indices = {@Index(value = {"idstatus"})},
        foreignKeys = {
                @ForeignKey(entity = InfoStatus.class,
                        parentColumns = "id",
                        childColumns = "idstatus")
        })
public class Information implements Serializable {

    //@PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String title;
    private String searchtitle;
    private String description;

    @NonNull
    @PrimaryKey
    private String guid;

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
    public String getGuid() {
        return guid;
    }

    public void setGuid(@NonNull String guid) {
        this.guid = guid;
    }

    public TypeOfInfo getTypeOfInfo() {
        return typeOfInfo;
    }

    public void setTypeOfInfo(TypeOfInfo typeOfInfo) {
        this.typeOfInfo = typeOfInfo;
    }

    @TypeConverters(Converters.class)
    private TypeOfInfo typeOfInfo = TypeOfInfo.INFO;


    @TypeConverters(Converters.class)
    private Date dateCreate;

    private String dateCreateStr;

    public String getDateCreateStr() {
        return dateCreateStr;
    }

    public void setDateCreateStr(String dateCreateStr) {
        this.dateCreateStr = dateCreateStr;
    }

    @ColumnInfo(name = "idstatus")
    private long idstatus;

    public long getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(long idstatus) {
        this.idstatus = idstatus;
    }



    /*
    @Relation(parentColumn = "idstatus", entityColumn = "id", entity = InfoStatus.class)
    public Set<InfoStatus> infoStatus;
    */

    //private String dateCreateString;

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

    @Ignore
    private String backupguid;

    public String getBackupguid() {
        return backupguid;
    }

    public void setBackupguid(String backupguid) {
        this.backupguid = backupguid;
    }
/*
    public String getDateCreateString() {
        return dateCreateString;
    }

    public void setDateCreateString(String dateCreateString) {
        this.dateCreateString = dateCreateString;
    }
    */
}
