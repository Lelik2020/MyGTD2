package ru.kau.mygtd2.objects;

public class SQLCondition {

    private String source1;
    private String cond;
    private String source2;

    public SQLCondition(String source1, String cond, String source2) {
        this.source1 = source1;
        this.cond = cond;
        this.source2 = source2;
    }

    public String getSource1() {
        return source1;
    }

    public void setSource1(String source1) {
        this.source1 = source1;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getSource2() {
        return source2;
    }

    public void setSource2(String source2) {
        this.source2 = source2;
    }
}
