package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "tasktags",
        primaryKeys = { "taskguid", "idtag" }

        ,indices = {
                @Index(name = "index_tasktags_taskguid_idtag", value = {"taskguid", "idtag"}, unique = true)
        },
        foreignKeys = {
                @ForeignKey(entity = Task.class,
                        parentColumns = "guid",
                        childColumns = "taskguid"),
                @ForeignKey(entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "idtag")
        })
public class TaskTagJoin {
    private final long idtask;
    private final long idtag;

    @NonNull
    public String getTaskguid() {
        return taskguid;
    }

    @NonNull
    private String taskguid = "";

    public TaskTagJoin(long idtask, long idtag,@NonNull String taskguid) {
        this.idtask = idtask;
        this.idtag = idtag;
        this.taskguid = taskguid;
    }

    public long getIdtask() {
        return idtask;
    }

    public long getIdtag() {
        return idtag;
    }
}
