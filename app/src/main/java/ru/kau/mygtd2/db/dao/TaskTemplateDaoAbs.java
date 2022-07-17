package ru.kau.mygtd2.db.dao;

import androidx.room.Dao;
import androidx.room.Transaction;

import java.util.List;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.TaskTemplate;
import ru.kau.mygtd2.objects.TaskTemplateContextJoin;
import ru.kau.mygtd2.objects.TaskTemplateTagJoin;

@Dao
public abstract class TaskTemplateDaoAbs {

    @Transaction
    public static void updateTaskTemplate(TaskTemplate taskTemplate, List<Tag> lstTags, List<Contekst> lstConteksts){

        MyApplication.getDatabase().taskTemplateDao().update(taskTemplate);

        MyApplication.getDatabase().taskTemplateContextJoinDao().deleteTaskTemplateContekst(taskTemplate.getTemplateguid());
        MyApplication.getDatabase().taskTemplateTagJoinDao().deleteTaskTemplateTags(taskTemplate.getTemplateguid());

        for (Tag tag: lstTags) {
            TaskTemplateTagJoin taskTemplateTagJoin = new TaskTemplateTagJoin(tag.getId(), taskTemplate.getTemplateguid());
            MyApplication.getDatabase().taskTemplateTagJoinDao().insert(taskTemplateTagJoin);
        }

        for (Contekst contekst: lstConteksts) {
            TaskTemplateContextJoin taskTemplateContextJoin = new TaskTemplateContextJoin(contekst.getId(), taskTemplate.getTemplateguid());
            MyApplication.getDatabase().taskTemplateContextJoinDao().insert(taskTemplateContextJoin);
        }




    }


}
