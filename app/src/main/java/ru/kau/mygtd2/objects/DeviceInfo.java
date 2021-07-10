package ru.kau.mygtd2.objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "devicesinfo",
        primaryKeys = {"deviceid", "key"})
public class DeviceInfo {


    private int deviceid;

    @NonNull
    private String key;

    @NonNull
    private String value;

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }
}
