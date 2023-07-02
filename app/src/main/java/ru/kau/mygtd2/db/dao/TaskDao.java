package ru.kau.mygtd2.db.dao;

//import android.arch.persistence.room.Dao;

//import android.arch.persistence.room.Delete;

//import android.arch.persistence.room.Insert;

//import android.arch.persistence.room.OnConflictStrategy;

//import android.arch.persistence.room.Query;

//import android.arch.persistence.room.Update;

import static ru.kau.mygtd2.utils.Const.HIERARCHY_TASKS;
import static ru.kau.mygtd2.utils.Const.HIERARCHY_TASKS2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

import ru.kau.mygtd2.objects.Task;

@Dao
public interface TaskDao {

    @Query(HIERARCHY_TASKS)
    List<Task> getAll();

    @Query(HIERARCHY_TASKS + " ORDER BY status")
    List<Task> getAllTasks();

    @Query( "SELECT DISTINCT * FROM ( "
            + HIERARCHY_TASKS2 + ") "

            + "WHERE title LIKE :txt OR searchtitle LIKE UPPER(:txt) "
            + "OR (cast(id as text) LIKE :txt) "
            + "ORDER BY id"
    )
    List<Task> getAllTasksOrderById(String txt);

    @Query( "SELECT COUNT(*) FROM ( "
            + "SELECT DISTINCT * FROM ( "
            + HIERARCHY_TASKS2 + ") "

            + "WHERE title LIKE :txt OR searchtitle LIKE :txt "
            + "ORDER BY id "
            + ")"
    )
    long getCountTasks(String txt);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks2();

    @Query("SELECT * FROM tasks WHERE dateEdit >= :dateEdit")
    List<Task> getTasksForUpdate(long dateEdit);

    @Query("SELECT Max(id) FROM tasks")
    long getMaxId();

    @Query(HIERARCHY_TASKS + " ORDER BY :orderby")
    List<Task> getAllTasks(int orderby);

    @Query("SELECT count(*) FROM tasks WHERE 1 = 1 AND status IN (:idStatus) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")
    long getCountTasks(List<Integer> idStatus, List<Integer> lstProjects, List<Integer> lstTargets);

    @Query("SELECT count(*) FROM tasks WHERE 1 = 1 AND status IN (:idStatus) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND id IN (SELECT idtask FROM tasktags WHERE idtag IN (:lstTags))")
    long getCountTasksWithTags(List<Integer> idStatus, List<Integer> lstProjects, List<Integer> lstTargets, List<Integer> lstTags);

    @Query("SELECT count(*) FROM tasks WHERE 1 = 1 AND status IN (:idStatus) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND id NOT IN (SELECT idtask FROM tasktags)")
    long getCountTasksWithoutTags(List<Integer> idStatus, List<Integer> lstProjects, List<Integer> lstTargets);

    @Query("SELECT count(*) FROM tasks")
    long getCountAllTasks();

    @Query("SELECT count(*) FROM tasks WHERE id IN (SELECT idtask FROM tasktags WHERE idtag = :tag_id)")
    long getCountAllTasksByTag(long tag_id);

    @Query("SELECT count(*) FROM tasks WHERE category = :category_id")
    long getCountAllTasksByCategory(long category_id);

    @Query("SELECT count(*) FROM tasks WHERE id IN (SELECT idtask FROM taskcontexts WHERE idcontext = :context_id)")
    long getCountAllTasksByContekst(long context_id);

    @Query("SELECT count(*) FROM tasks WHERE target_id = :target_id")
    long getCountAllTasksByTarget(long target_id);

    @Query("SELECT count(*) FROM tasks WHERE project_id NOT IN (SELECT ID FROM projects)")
    long getCountAllTasksWithoutProject();


    @Query("SELECT count(*) FROM tasks WHERE (project_id NOT IN (SELECT ID FROM projects)) AND (status IN (:idStatus))")
    long getCountAllActiveTasksWithoutProject(List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE id NOT IN (SELECT idtask FROM tasktags)")
    long getCountAllTasksWithoutTag();

    @Query("SELECT count(*) FROM tasks WHERE id NOT IN (SELECT idtask FROM tasktags) AND (status IN (:idStatus))")
    long getCountAllActiveTasksWithoutTag(List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE target_id NOT IN (select ID FROM targets)")
    long getCountAllTasksWithoutTarget();

    @Query("SELECT count(*) FROM tasks WHERE target_id NOT IN (select ID FROM targets) AND (status IN (:idStatus))")
    long getCountAllActiveTasksWithoutTarget(List<Integer> idStatus);

    @Query(HIERARCHY_TASKS + " and id = :id")
    Task getById(long id);

    @Query(HIERARCHY_TASKS + " and guid = :guid")
    Task getByGuid(String guid);

    @Query("SELECT count(*) FROM tasks WHERE status in (:idStatus)")
    long getCountByStatus(List<Integer> idStatus);

    @Query("SELECT * FROM tasks WHERE status in (:idStatus)")
    List<Task> getAllByStatus(List<Integer> idStatus);

    @Query("SELECT * FROM tasks WHERE status = :idstatus")
    List<Task> getAllByStatus(long idstatus);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (:idStatus))")
    long getCountByDateOld(long dateEnd, String dateEndStr, List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (:idStatus)) AND project_id IN (:lstProjects)")
    long getCountByDate(long dateEnd, String dateEndStr, List<Integer> idStatus, List<Integer> lstProjects);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (1, 2, 3)) AND (id IN (SELECT idtask FROM taskcontexts WHERE idcontext = :context_id))")
    long getCountByDateByContekst(long dateEnd, String dateEndStr, long context_id);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (1, 2, 3)) AND (id IN (SELECT idtask FROM tasktags WHERE idtag = :tag_id))")
    long getCountByDateByTag(long dateEnd, String dateEndStr, long tag_id);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (1, 2, 3)) AND (target_id = :target_id)")
    long getCountByDateByTarget(long dateEnd, String dateEndStr, long target_id);

    @Query("SELECT count(*) FROM tasks WHERE id NOT IN (SELECT idtask FROM tasktags) AND (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (:idStatus))")
    long getCountByDateWithoutTag(long dateEnd, String dateEndStr, List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (1, 2, 3)) AND (project_id NOT IN (SELECT ID FROM projects))")
    long getCountByDateWithoutProject(long dateEnd, String dateEndStr);


    //@Query(HIERARCHY_TASKS + " and (dateEnd = :dateEnd or dateEndStr = :dateEndStr) and status NOT IN (:lstStatus) and isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects)")

    //List<Task> getTasksByDate(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority, List<Integer> lstProjects);
    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (1, 2, 3)) AND (project_id IN (SELECT ID FROM projects))")
    long getCountByDateWithProject(long dateEnd, String dateEndStr);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status IN (:lstStatus) " +
            "AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND guid NOT IN (SELECT taskguid FROM tasktags)")
    long getCountByDateWithoutTags(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                List<Integer> lstProjects, List<Integer> lstTargets);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status IN (:lstStatus) " +
            "AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND guid IN (SELECT taskguid FROM tasktags WHERE idtag IN (:lstTags))")
    long getCountByDateWithTags(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                        List<Integer> lstProjects, List<Integer> lstTargets, List<Integer> lstTags);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status IN (:lstStatus) "
            + "AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) "
            //+ "AND guid IN (SELECT taskguid FROM taskcontexts WHERE idcontext IN (:lstContext)) "
            + "AND category IN (:lstCategories)")
    long getCountByDateWithCategory(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                   List<Integer> lstProjects, List<Integer> lstTargets
                                    //, List<Integer> lstContext
                                    , List<Integer> lstCategories);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status IN (:lstStatus) " +
            "AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND guid IN (SELECT taskguid FROM taskcontexts WHERE idcontext IN (:lstContext))")
    long getCountByDateWithContext(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                List<Integer> lstProjects, List<Integer> lstTargets, List<Integer> lstContext);



    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status IN (:lstStatus) " +
            "AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")
    long getCountByDate(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                        List<Integer> lstProjects, List<Integer> lstTargets);

    @Query("SELECT count(*) FROM tasks WHERE target_id NOT IN (select ID FROM targets) AND (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND (status IN (:idStatus))")
    long getCountByDateWithoutTarget(long dateEnd, String dateEndStr, List<Integer> idStatus);

    // Просроченные задачи (количество)
    @Query("SELECT count(*) FROM tasks WHERE (dateEnd > 0 and dateEnd < :dateEnd) and status in (:lstStatus) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects)")
    long getCountOutstanding(long dateEnd, List<Integer> lstStatus, List<Integer> lstPriority, List<Integer> lstProjects);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd > 0 and dateEnd < :dateEnd) and status in (:lstStatus) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND isFavourite = 1")
    long getCountOutstandingFavourite(long dateEnd, List<Integer> lstStatus, List<Integer> lstPriority, List<Integer> lstProjects);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :dateEnd or dateEndStr < :dateEndStr) and status in (1, 2, 3) AND (id IN (SELECT idtask FROM tasktags WHERE idtag = :tag_id))")
    long getCountOutstandingByTag(long dateEnd, String dateEndStr, long tag_id);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :dateEnd or dateEndStr < :dateEndStr) and status in (1, 2, 3) AND (id IN (SELECT idtask FROM taskcontexts WHERE idcontext = :context_id))")
    long getCountOutstandingByContekst(long dateEnd, String dateEndStr, long context_id);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :dateEnd or dateEndStr < :dateEndStr) and status in (1, 2, 3) AND (target_id = :target_id)")
    long getCountOutstandingByTarget(long dateEnd, String dateEndStr, long target_id);

    @Query("SELECT count(*) FROM tasks WHERE id NOT IN (SELECT idtask FROM tasktags) AND (dateEnd < :dateEnd or dateEndStr < :dateEndStr) and status in (1, 2, 3)")
    long getCountOutstandingWithoutTag(long dateEnd, String dateEndStr);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :dateEnd or dateEndStr < :dateEndStr) and status in (1, 2, 3) AND (project_id NOT IN (SELECT ID FROM projects))")
    long getCountOutstandingWithoutProject(long dateEnd, String dateEndStr);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND guid NOT IN (SELECT taskguid FROM tasktags)")
    long getCountOutstandingWithoutTags(long date, List<Integer> lstStatus, List<Integer> lstFavour,
                                     List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> lstTargets);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND guid IN (SELECT taskguid FROM tasktags WHERE idtag IN (:lstTags))")
    long getCountOutstandingWithTags(long date, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                     List<Integer> lstProjects, List<Integer> lstTargets, List<Integer> lstTags);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) "
            + "AND category IN (:lstCategories) "
            + "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) "
            //+ "AND guid IN (SELECT taskguid FROM taskcontexts WHERE idcontext IN (:lstContexts)) "
            )
    long getCountOutstandingWithCategory(long date, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                         List<Integer> lstProjects, List<Integer> lstTargets,
                                         List<Integer> lstCategories);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND guid IN (SELECT taskguid FROM taskcontexts WHERE idcontext IN (:lstContexts))")
    long getCountOutstandingWithContext(long date, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                     List<Integer> lstProjects, List<Integer> lstTargets, List<Integer> lstContexts);


    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")
    long getCountOutstanding(long date, List<Integer> lstStatus, List<Integer> lstFavour,
                             List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> lstTargets);

    //@Query(HIERARCHY_TASKS + " AND (dateEnd < :date) and status IN (:lstStatus) and isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects)")
    //List<Task> getOverdueTasks(long date, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority, List<Integer> lstProjects);
    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects)")
    long getCountOutstandingWithProject(long date, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority, List<Integer> lstProjects);

    @Query("SELECT count(*) FROM tasks WHERE target_id NOT IN (select ID FROM targets) AND (dateEnd < :dateEnd or dateEndStr < :dateEndStr) and status in (1, 2, 3)")
    long getCountOutstandingWithoutTarget(long dateEnd, String dateEndStr);

    // Общее количество незакрытых и неотложенных задач (активных задач)
    @Query("SELECT count(*) FROM tasks WHERE status in (:idStatus) AND project_id IN (:lstProjects)")
    long getCountAllTasks(List<Integer> idStatus, List<Integer> lstProjects);

    @Query("SELECT count(*) FROM tasks WHERE status in (:idStatus) AND (id IN (SELECT idtask FROM tasktags WHERE idtag = :tag_id))")
    long getCountAllActiveTasksByTag(long tag_id, List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE status in (:idStatus) AND category = :category_id")
    long getCountAllActiveTasksByCaterory(long category_id, List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE status in (:idStatus) AND (id IN (SELECT idtask FROM taskcontexts WHERE idcontext = :context_id))")
    long getCountAllActiveTasksByContekst(long context_id, List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE status in (:idStatus) AND (target_id = :target_id)")
    long getCountAllActiveTasksByTarget(long target_id, List<Integer> idStatus);

    // Количество задач в проекте всего
    @Query("SELECT count(*) FROM tasks WHERE project_id = :idProject")
    long getCountAllTasksOfProject(long idProject);

    @Query("SELECT * FROM tasks WHERE project_id = :idProject")
    List<Task> getAllTasksOfProject(long idProject);

    //@Query("SELECT * FROM tasks WHERE status = -1")
    //List<Task> getSometimeTasks();

    // Количество задач в проекте активных (статусы 1 и 2)
    @Query("SELECT count(*) FROM tasks WHERE project_id = :idProject and status in (:idStatus)")
    long getCountAllActiveTasksOfProject(long idProject, List<Integer> idStatus);

    // Количество задач в проекте, которые надо закончить сегодня
    @Query("SELECT count(*) FROM tasks WHERE project_id = :idProject and (dateEnd = :dateEnd or dateEndStr = :dateEndStr) and status in (:idStatus)")
    long getCountAllTasksOfProjectToday(long idProject, long dateEnd, String dateEndStr, List<Integer> idStatus);

    // Количество задач в проекте, которые просрочены
    @Query("SELECT count(*) FROM tasks WHERE project_id = :idProject and (dateEnd < :dateEnd or dateEndStr < :dateEndStr) and status in (:idStatus)")
    long getCountAllTasksOfProjectOutstanding(long idProject, long dateEnd, String dateEndStr, List<Integer> idStatus);

    //@Insert
    @Insert   //(onConflict = OnConflictStrategy.IGNORE)
    long insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("SELECT * FROM tasks WHERE parenttask_id == null or parenttask_id = 0")
    List<Task> getAllTasksWithoutSubtask();

    @Query("SELECT * FROM tasks WHERE (id not in (select t.parenttask_id from tasks t)) and (dateEnd < :date)")
    List<Task> getOverdueTasksWithoutSubtask(long date);

    /*@Query(HIERARCHY_TASKS + " AND (dateEnd < :date) and status IN (:lstStatus) and isFavourite IN (0, 1)")
    List<Task> getOverdueTasks(long date, List<Integer> lstStatus);*/

    @Query(HIERARCHY_TASKS + " AND (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) "
            + "AND category IN (:lstTaskCategories) AND typeoftask IN (:lstTaskTypes)")
    List<Task> getOverdueTasks(long date, List<Integer> lstStatus, List<Integer> lstFavour,
                               List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> lstTargets
                               , List<Integer> lstTaskCategories, List<Integer> lstTaskTypes );

    //HIERARCHY_TASKS

    @RawQuery
    List<Task> getTasks(SupportSQLiteQuery query);

    @Query("SELECT * FROM tasks WHERE (parenttask_id == null or parenttask_id = 0) and (dateEnd = :dateEnd or dateEndStr = :dateEndStr)")
    List<Task> getTasksWithoutSubtaskByDate(long dateEnd, String dateEndStr);

    @Query(HIERARCHY_TASKS + " AND (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status NOT IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")
    //@Query(TASKS + " and (dateEnd = :dateEnd or dateEndStr = :dateEndStr) and status NOT IN (:lstStatus) and isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects)")
    List<Task> getTasksByDate(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour,
                              List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> lstTargets);

    @Query(HIERARCHY_TASKS + " and (dateEnd > :dateEnd1) and (dateEnd < :dateEnd2) " +
            "AND status NOT IN (:lstStatus) AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) " +
            "AND target_id IN (:lstTargets)")
    List<Task> getTasksByDate(long dateEnd1, long dateEnd2, List<Integer> lstStatus, List<Integer> lstFavour,
                              List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> lstTargets);


    @Query("SELECT * FROM tasks WHERE (parenttask_id == null or parenttask_id = 0) and (dateEnd > :dateEnd1) and (dateEnd < :dateEnd2)")
    List<Task> getTasksWithoutSubtaskByDate(long dateEnd1, long dateEnd2);


    @Query("SELECT * FROM tasks WHERE (parenttask_id == null or parenttask_id = 0) and (dateEnd >= :dateEnd1) ")
    List<Task> getTasksWithoutSubtaskInFuture(long dateEnd1);

    @Query(HIERARCHY_TASKS + " /*and (parenttask_id == null or parenttask_id = 0)*/ AND (dateEnd >= :dateEnd1) AND status NOT IN (:lstStatus) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")
    List<Task> getTasksByDate(long dateEnd1, List<Integer> lstStatus, List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> lstTargets);


    @Query(HIERARCHY_TASKS + " AND (dateEnd >= :dateEnd1 AND dateEnd <= :dateEnd2) AND status NOT IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")
    List<Task> getTasksBetweenDates(long dateEnd1, long dateEnd2, List<Integer> lstStatus, List<Integer> lstFavour,
                                    List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> lstTargets);


    @Query("SELECT * FROM tasks WHERE (parenttask_id == null or parenttask_id = 0) and (dateEnd is null)")
    List<Task> getTasksWithoutSubtaskNoDateEnd();

    @Query(HIERARCHY_TASKS + " AND ((dateEnd IS NULL) and ((dateEndStr is null) or (dateEndStr = ''))) AND status NOT IN (:lstStatus) " +
            "AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) " +
            "AND target_id IN (:lstTargets)")
    List<Task> getTasksNoDateEnd(List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                 List<Integer> lstProjects, List<Integer> lstTargets);

    @Query(HIERARCHY_TASKS + " and status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")
    List<Task> getAllClosedTasks(List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                 List<Integer> lstProjects, List<Integer> lstTargets);

    @Query(HIERARCHY_TASKS + " and parenttask_id = :parenttask_id")
    List<Task> getSubTasksByParent(long parenttask_id);

    @Query("SELECT count(*) FROM tasks WHERE parenttask_id = :parenttask_id")
    long getCountSubTasksByParent(long parenttask_id);

    // Задачи для пункта "Избранные"
    // Количество задач в проекте всего
    @Query("SELECT count(*) FROM tasks WHERE 1 = 1 AND isFavourite IN (:lstFavour)")
    long getCountAllTasksOfFavourite(List<Integer> lstFavour);

    // Количество задач в проекте активных (статусы 1 и 2)
    @Query("SELECT count(*) FROM tasks WHERE 1 = 1 AND status IN (:lstStatus) AND isFavourite IN (:lstFavour)")
    long getCountAllActiveTasksOfFavourite(List<Integer> lstStatus, List<Integer> lstFavour);

    // Количество задач в проекте, которые надо закончить сегодня
    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd or dateEndStr = :dateEndStr) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour)")
    long getCountAllTasksOfFavouriteToday(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour);

    // Количество задач в проекте, которые просрочены
    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :dateEnd or dateEndStr < :dateEndStr) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour)")
    long getCountAllTasksOfFavouriteOutstanding(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour);

    // Задачи для пункта "Горячие"
    // Количество задач всего
    @Query("SELECT count(*) FROM tasks WHERE priority_id = 1")
    long getCountAllTasksOfHot();

    // Количество горячих задач активных (статусы 1 и 2)
    @Query("SELECT count(*) FROM tasks WHERE priority_id = 1 and status in (:lstStatus)")
    long getCountAllActiveTasksOfHot(List<Integer> lstStatus);

    // Количество горящих задач в проекте, которые надо закончить сегодня
    @Query("SELECT count(*) FROM tasks WHERE priority_id IN (:lstPriority) AND (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status IN (:lstStatus)")
    long getCountAllTasksOfHotToday(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstPriority);

    // Количество задач в проекте, которые просрочены
    @Query("SELECT count(*) FROM tasks WHERE priority_id IN (:lstPriority) and (dateEnd < :dateEnd or dateEndStr < :dateEndStr) AND status IN (:lstStatus)")
    long getCountAllTasksOfHotOutstanding(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstPriority);

    // Количество закрытых задач всего
    @Query("SELECT count(*) FROM tasks WHERE status in (:lstStatus)")
    long getCountAllClosedTasks(List<Integer> lstStatus);

    // Статус задачи
    @Query("SELECT status FROM tasks WHERE id = :id")
    long getStatusByTask(long id);

    @Query("SELECT guid FROM tasks WHERE id = :id AND deviceguid = :deviceID")
    String getGuidById(long id, String deviceID);

    @Query("SELECT guid FROM tasks WHERE id = :id AND deviceguid = :deviceID")
    String getParentGuidById(long id, String deviceID);

    @Query("SELECT MIN(dateClose) FROM tasks")
    long getMinDateClose();

    @Query("SELECT MAX(dateClose) FROM tasks")
    long getMaxDateClose();

    @Query("SELECT COUNT() FROM tasks WHERE dateClose >= :d1 AND dateClose <= :d2 AND status IN (:lstStatus) AND category IN (:lstCategory)")
    long getCountTasks(long d1, long d2, List<Integer> lstStatus, List<Integer> lstCategory);

    @Query("SELECT count(*) FROM tasks WHERE typeOfTask = :typeTask")
    long getCountAllTasksByTypeTask(long typeTask);

    @Query("SELECT count(*) FROM tasks WHERE status in (:idStatus) AND typeOfTask = :typeTask")
    long getCountAllActiveTasksByTypeTask(long typeTask, List<Integer> idStatus);

    @Query("SELECT count(*) FROM tasks WHERE (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status IN (:lstStatus) " +
            "AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            //"AND guid IN (SELECT taskguid FROM taskcontexts WHERE idcontext IN (:lstContext)) " +
            "AND typeOfTask = :typeTask")
    long getCountByDateWithTypeTask(long dateEnd, String dateEndStr, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                   List<Integer> lstProjects, List<Integer> lstTargets, long typeTask);


    @Query("SELECT count(*) FROM tasks WHERE (dateEnd < :date) AND status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets) " +
            "AND typeOfTask = :typeTask")
    long getCountOutstandingWithTypeTask(long date, List<Integer> lstStatus, List<Integer> lstFavour, List<Integer> lstPriority,
                                        List<Integer> lstProjects, List<Integer> lstTargets, long typeTask);


}
