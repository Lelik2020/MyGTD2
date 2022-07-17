package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "tasktemplatecontexts",
        primaryKeys = { "tasktemplateguid", "idcontext" },
        indices = {
                @Index(value = {"tasktemplateguid", "idcontext"}, unique = true)

        },
        foreignKeys = {
                @ForeignKey(entity = TaskTemplate.class,
                        parentColumns = "templateguid",
                        childColumns = "tasktemplateguid"),
                @ForeignKey(entity = Contekst.class,
                        parentColumns = "id",
                        childColumns = "idcontext")
        })
public class TaskTemplateContextJoin {
    //private final long idtask;
    private final long idcontext;

    @NonNull
    private String tasktemplateguid;

    public TaskTemplateContextJoin(long idcontext, String tasktemplateguid) {
        //this.idtask = idtask;
        this.idcontext = idcontext;
        this.tasktemplateguid = tasktemplateguid;
    }

    /*public long getIdtask() {
        return idtask;
    }*/

    public long getIdcontext() {
        return idcontext;
    }

    @NonNull
    public String getTasktemplateguid() {
        return tasktemplateguid;
    }

    public void setTasktemplateguid(@NonNull String tasktemplateguid) {
        this.tasktemplateguid = tasktemplateguid;
    }
}
