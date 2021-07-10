package ru.kau.mygtd2.objects;

public class BackupItem {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String Title;
    private float percent;

    private long count1;
    private long count2;

    public long getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public long getCount2() {
        return count2;
    }

    public void setCount2(long count2) {
        this.count2 = count2;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
