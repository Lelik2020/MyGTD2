package ru.kau.mygtd2.objects;

public class Sync {

    private String guid;

    private String deviceguid;

    private long datebegin;
    private long dateend;

    private String datebeginstr;
    private String dateendstr;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDeviceguid() {
        return deviceguid;
    }

    public void setDeviceguid(String deviceguid) {
        this.deviceguid = deviceguid;
    }

    public long getDateBegin() {
        return datebegin;
    }

    public void setDateBegin(long dateBegin) {
        this.datebegin = dateBegin;
    }

    public long getDateEnd() {
        return dateend;
    }

    public void setDateEnd(long dateEnd) {
        this.dateend = dateEnd;
    }

    public String getDateBeginStr() {
        return datebeginstr;
    }

    public void setDateBeginStr(String dateBeginStr) {
        this.datebeginstr = dateBeginStr;
    }

    public String getDateEndStr() {
        return dateendstr;
    }

    public void setDateEndStr(String dateEndStr) {
        this.dateendstr = dateEndStr;
    }




}
