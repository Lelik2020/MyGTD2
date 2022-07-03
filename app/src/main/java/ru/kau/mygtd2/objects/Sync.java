package ru.kau.mygtd2.objects;

public class Sync {

    private String guid;

    private String deviceguid;

    private Long datebegin;
    private Long dateend;

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

    public Long getDatebegin() {
        return datebegin;
    }

    public void setDatebegin(Long datebegin) {
        this.datebegin = datebegin;
    }

    public Long getDateend() {
        return dateend;
    }

    public void setDateend(Long dateend) {
        this.dateend = dateend;
    }

    public String getDatebeginstr() {
        return datebeginstr;
    }

    public void setDatebeginstr(String datebeginstr) {
        this.datebeginstr = datebeginstr;
    }

    public String getDateendstr() {
        return dateendstr;
    }

    public void setDateendstr(String dateendstr) {
        this.dateendstr = dateendstr;
    }
}
