package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "tasktemplatetags",
        primaryKeys = { "tasktemplateguid", "idtag" }

        ,indices = {
        @Index(name = "index_tasktemplatetags_tasktemplateguid_idtag", value = {"tasktemplateguid", "idtag"}, unique = true)
},
        foreignKeys = {
                @ForeignKey(entity = TaskTemplate.class,
                        parentColumns = "templateguid",
                        childColumns = "tasktemplateguid"),
                @ForeignKey(entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "idtag")
        })
public class TaskTemplateTagJoin {

        //private final long idtask;
        private final long idtag;
        @NonNull
        private String tasktemplateguid = "";

        public TaskTemplateTagJoin(long idtag,@NonNull String tasktemplateguid) {
                //this.idtask = idtask;
                this.idtag = idtag;
                this.tasktemplateguid = tasktemplateguid;
        }

        /*public long getIdtask() {
                return idtask;
        }*/

        public long getIdtag() {
                return idtag;
        }

        @NonNull
        public String getTasktemplateguid() {
                return tasktemplateguid;
        }

        public void setTasktemplateguid(@NonNull String tasktemplateguid) {
                this.tasktemplateguid = tasktemplateguid;
        }
}
