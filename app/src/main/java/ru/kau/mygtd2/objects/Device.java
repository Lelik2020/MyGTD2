package ru.kau.mygtd2.objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "devices",
        indices = {
                @Index(value = {"guid"}, unique = true)
        })
public class Device {

    @NotNull
    private int id;

    //@PrimaryKey
    @NonNull
    private String title;

    @NonNull
    @PrimaryKey
    private String guid;

    @NonNull
    private int iscurrent = 0;

    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    @NonNull
    private int devicetype = 1; // 1 - Планшет, 2 - Телефон, 3 - Компьютер

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
    public int getIscurrent() {
        return iscurrent;
    }

    public void setIscurrent(@NonNull int iscurrent) {
        this.iscurrent = iscurrent;
    }

    @NonNull
    public String getGuid() {
        return guid;
    }

    public void setGuid(@NonNull String guid) {
        this.guid = guid;
    }
}
