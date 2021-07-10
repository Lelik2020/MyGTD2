package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskContextJoin;
import ru.kau.mygtd2.objects.TaskTagJoin;

@Dao
public abstract class TaskDaoAbs {

    public static List<Task> lstReturn = null;

    @Transaction
    public static void updateTask(Task task, List<Tag> lstTags, List<Contekst> lstConteksts) {

        // Удаляем тэги и контексты по задаче

        //List<TaskTagJoin> lstTaskTagJoin = MyApplication.getDatabase().taskTagJoinDao().getTaskForTag(task.getId());

        /*for (TaskTagJoin taskTagJoin: lstTaskTagJoin){
            MyApplication.getDatabase().taskTagJoinDao().delete(taskTagJoin);
        }*/

        MyApplication.getDatabase().taskContextJoinDao().deleteTaskContekst(task.getId());
        MyApplication.getDatabase().taskTagJoinDao().deleteTaskTags(task.getId());



        for (Tag tag: lstTags) {

            TaskTagJoin taskTagJoin = new TaskTagJoin(task.getId(), tag.getId(), task.getGuid());
            MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoin);

        }

        for (Contekst contekst: lstConteksts) {

            TaskContextJoin taskContextJoin = new TaskContextJoin(task.getId(), contekst.getId(), task.getGuid());
            MyApplication.getDatabase().taskContextJoinDao().insert(taskContextJoin);

        }

        MyApplication.getDatabase().taskDao().update(task);


        // ------------------------------------------------------------------


    }

    public static List<Task> getParentTasks(Task task){
        long parentTaskId = task.getParenttask_id();
        if (parentTaskId > 0){
            List<Task> lstTask= new ArrayList<Task>();
            while(parentTaskId > 0) {
                Task task2 = MyApplication.getDatabase().taskDao().getById(parentTaskId);
                lstTask.add(0, task2);
                parentTaskId = task2.getParenttask_id();
            }
            //Collections.sort(lstTask, );
            return lstTask;
        } else {
            return null;
        }
    }

    public static List<Task> getChildTasks(Task task){
        /*List<Task> lstTask =*/
        return null;
    }



    public static List<Task> getAllChildTasks(Task task){
        List<Task> lstTask = MyApplication.getDatabase().taskDao().getSubTasksByParent(task.getId());

        if (lstTask != null) {
            if (lstReturn == null) {
                lstReturn = new ArrayList<>();
            }
            //int
            for(Task t: lstTask) {
                lstReturn.add(t);
                lstTask = MyApplication.getDatabase().taskDao().getSubTasksByParent(t.getId());
                if (lstTask != null) {
                    getAllChildTasks(t);
                }
            }
        }
        return lstReturn;
    }


}
