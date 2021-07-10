package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "taskcontexts",
        primaryKeys = { "taskguid", "idcontext" },
        indices = {
                @Index(value = {"taskguid", "idcontext"}, unique = true)

        },
        foreignKeys = {
                @ForeignKey(entity = Task.class,
                        parentColumns = "guid",
                        childColumns = "taskguid"),
                @ForeignKey(entity = Contekst.class,
                        parentColumns = "id",
                        childColumns = "idcontext")
        })
public class TaskContextJoin {
    private final long idtask;
    private final long idcontext;

    public void setTaskguid(@NonNull String taskguid) {
        this.taskguid = taskguid;
    }

    @NonNull
    private String taskguid;

    public TaskContextJoin(long idtask, long idcontext, String taskguid) {
        this.idtask = idtask;
        this.idcontext = idcontext;
        this.taskguid = taskguid;
    }

    public long getIdtask() {
        return idtask;
    }

    public long getIdcontext() {
        return idcontext;
    }

    @NonNull
    public String getTaskguid() {
        return taskguid;
    }
}
