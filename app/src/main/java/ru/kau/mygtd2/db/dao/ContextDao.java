package ru.kau.mygtd2.db.dao;

//import android.arch.persistence.room.Dao;

//import android.arch.persistence.room.Delete;

//import android.arch.persistence.room.Insert;

//import android.arch.persistence.room.OnConflictStrategy;

//import android.arch.persistence.room.Query;

//import android.arch.persistence.room.Update;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kau.mygtd2.objects.Contekst;

@Dao
public interface ContextDao {

    @Query("SELECT * FROM contexts")
    List<Contekst> getAll();

    @Query("SELECT * FROM contexts WHERE id = :idContext")
    Contekst getContextById(int idContext);

    @Query("SELECT count() FROM contexts")
    int countOfcontexts();

    @Query("SELECT * FROM contexts INNER JOIN taskcontexts ON id = idcontext WHERE idtask = :taskId")
    List<Contekst> getAllByTask(final long taskId);

    @Query("SELECT * FROM contexts INNER JOIN taskcontexts ON id = idcontext WHERE taskguid = :taskGuid")
    List<Contekst> getAllByTaskGuid(final String taskGuid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Contekst contekst);

    @Update
    void update(Contekst contekst);

    @Delete
    void delete(Contekst contekst);
}
