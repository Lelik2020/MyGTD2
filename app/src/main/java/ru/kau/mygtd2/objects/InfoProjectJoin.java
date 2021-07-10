package ru.kau.mygtd2.objects;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "infoproject",
        primaryKeys = { "infoguid", "idproject" },
        indices = {
                @Index(value = {"infoguid"}),
                @Index(value = {"idproject"})
        },
        foreignKeys = {
                @ForeignKey(entity = Information.class,
                        parentColumns = "guid",
                        childColumns = "infoguid"),
                @ForeignKey(entity = Project.class,
                        parentColumns = "id",
                        childColumns = "idproject")
        }
        )
public class InfoProjectJoin {

    private final long idinformation;
    private final long idproject;

    @NonNull
    private final String infoguid;

    public InfoProjectJoin(long idinformation, long idproject, String infoguid) {
        this.idinformation = idinformation;
        this.idproject = idproject;
        this.infoguid = infoguid;
    }

    public long getIdinformation() {
        return idinformation;
    }

    public long getIdproject() {
        return idproject;
    }

    @NonNull
    public String getInfoguid() {
        return infoguid;
    }
}
