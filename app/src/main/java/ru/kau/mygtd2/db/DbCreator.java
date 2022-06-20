package ru.kau.mygtd2.db;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHSECONDS;
import static ru.kau.mygtd2.utils.Const.MYDEVICEID;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.PrStatus;
import ru.kau.mygtd2.objects.Category;
import ru.kau.mygtd2.objects.Comment;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.objects.DeviceInfo;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.InfoTypes;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Priority;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.ProjectStatus;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskCategory;
import ru.kau.mygtd2.objects.TaskContextJoin;
import ru.kau.mygtd2.objects.TaskStatus;
import ru.kau.mygtd2.objects.TaskTagJoin;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.Utils;

public class DbCreator {

    public static void firstInit(){


        firstInitSpr();

        firstInitData();

        firstInitSpr2();

        firstInitData3();

        firstInitSpr4();

        firstInitSpr5();

        firstInitSpr6();

        firstInitSpr7();
    }

    public static void taskUpdate() {
        List<Task> lstInfo = MyApplication.getDatabase().taskDao().getAllTasks();
        //String deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();

        for (int i = 0; i < lstInfo.size(); i++){
            Task task = lstInfo.get(i);
            Project project = MyApplication.getDatabase().projectDao().getProjectById(task.getProject_id());
            if (project != null) {
                if (project.getId() == 1 || project.getParentid() == 1) {
                    task.setCategory(2);
                }

                if (project.getId() == 2 || project.getParentid() == 2) {
                    task.setCategory(1);
                }
            } else {
                task.setCategory(2);
            }
            MyApplication.getDatabase().taskDao().update(task);
        }
    }

    public static void taskUpdate2() {
        List<Task> lstInfo = MyApplication.getDatabase().taskDao().getAllTasks();
        //String deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();

        for (int i = 0; i < lstInfo.size(); i++){
            Task task = lstInfo.get(i);
            Date date = new Date();
            task.setDateEdit(date);
            task.setDateEditStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));
            MyApplication.getDatabase().taskDao().update(task);
        }
    }

    public static void infoUpdate2() {
        List<Information> lstInfo = MyApplication.getDatabase().informationDao().getAll();
        //String deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();

        for (int i = 0; i < lstInfo.size(); i++){
            Information info = lstInfo.get(i);

            if (info.getId() == 0) {
                info.setId(Utils.getLastInfoId());
                MyApplication.getDatabase().informationDao().update(info);
            }
        }
    }

    public static void infoUpdate() {
        List<Information> lstInfo = MyApplication.getDatabase().informationDao().getAll();
        String deviceID = MYDEVICEID;

        for (int i = 0; i < lstInfo.size(); i++){
            Information info = lstInfo.get(i);
            info.setDeviceguid(deviceID);
            if (info.getGuid().equals("") || info.getGuid() == null) {
                info.setGuid(UUID.randomUUID().toString());
                MyApplication.getDatabase().informationDao().update(info);
            }
        }
    }

    /*public static void infoUpdate2() {
        List<TaskTagJoin> lstTasksJoin = MyApplication.getDatabase().taskTagJoinDao().getAll();
        for (int i = 0; i < lstTasksJoin.size(); i++){
            String guid = MyApplication.getDatabase().taskDao().getGuidById(lstTasksJoin.get(i).getIdtask(), MYDEVICEID);
            TaskTagJoin ts = new TaskTagJoin(lstTasksJoin.get(i).getIdtask(), lstTasksJoin.get(i).getIdtag(), guid);
            MyApplication.getDatabase().taskTagJoinDao().update(ts);
        }

        List<TaskContextJoin> lstTasksJoin2 = MyApplication.getDatabase().taskContextJoinDao().getAll();
        for (int i = 0; i < lstTasksJoin2.size(); i++){
            String guid = MyApplication.getDatabase().taskDao().getGuidById(lstTasksJoin2.get(i).getIdtask(), MYDEVICEID);
            TaskContextJoin ts = new TaskContextJoin(lstTasksJoin2.get(i).getIdtask(), lstTasksJoin2.get(i).getIdcontext(), guid);
            MyApplication.getDatabase().taskContextJoinDao().update(ts);
        }
    }*/


    public static void commentUpdate() {
        List<Comment> lstComments = MyApplication.getDatabase().commentDao().getAll();
        //String deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();

        for (int i = 0; i < lstComments.size(); i++){
            Comment comment = lstComments.get(i);
            //comment.setInfoguid();
            /*if (comment.getInfoguid() == null){

            }*/

            if (comment.getInfo_id() > 0 ) {

                comment.setInfoguid(MyApplication.getDatabase().informationDao().getGuidById(comment.getInfo_id(), MYDEVICEID));
                MyApplication.getDatabase().commentDao().update(comment);
            }

            if (comment.getTask_id() > 0) {
                comment.setTaskguid(MyApplication.getDatabase().taskDao().getGuidById(comment.getTask_id(), MYDEVICEID));
                MyApplication.getDatabase().commentDao().update(comment);
            }
        }
    }

    public static void firstInitSpr7() {

        TaskCategory taskCategory = new TaskCategory();

        taskCategory.setId(1);
        taskCategory.setTitle("Зеленая");
        taskCategory.setDescription("Зеленая категория");
        taskCategory.setColor("#3CB371");
        MyApplication.getDatabase().taskCategoryDao().insert(taskCategory);

        taskCategory.setId(2);
        taskCategory.setTitle("Красная");
        taskCategory.setDescription("Красная категория");
        taskCategory.setColor("#FF0000");
        MyApplication.getDatabase().taskCategoryDao().insert(taskCategory);

        taskCategory.setId(3);
        taskCategory.setTitle("Коричневая");
        taskCategory.setDescription("Коричневая категория");
        taskCategory.setColor("#8B4513");
        MyApplication.getDatabase().taskCategoryDao().insert(taskCategory);

    }

    public static void firstInitSpr6() {

        Category category = new Category();
        category.setId(22L);
        category.setTitle("Статистика");
        category.setDescription("Статистика");
        category.setGrp(3);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);

        //TaskCategory

    }

    public static void tasksUpdate3() {
        List<Task> lstTasks = MyApplication.getDatabase().taskDao().getAllTasks2();
        //String deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();

        for (int i = 0; i < lstTasks.size(); i++){
            Task task = lstTasks.get(i);

            if (task.getId() == 0) {
                task.setId(Utils.getLastTaskId());
                MyApplication.getDatabase().taskDao().update(task);
            }
        }
    }

    public static void tasksUpdate2() {
        List<TaskTagJoin> lstTasksJoin = MyApplication.getDatabase().taskTagJoinDao().getAll();
        for (int i = 0; i < lstTasksJoin.size(); i++){
            String guid = MyApplication.getDatabase().taskDao().getGuidById(lstTasksJoin.get(i).getIdtask(), MYDEVICEID);
            TaskTagJoin ts = new TaskTagJoin(lstTasksJoin.get(i).getIdtask(), lstTasksJoin.get(i).getIdtag(), guid);
            MyApplication.getDatabase().taskTagJoinDao().update(ts);
        }

        List<TaskContextJoin> lstTasksJoin2 = MyApplication.getDatabase().taskContextJoinDao().getAll();
        for (int i = 0; i < lstTasksJoin2.size(); i++){
            String guid = MyApplication.getDatabase().taskDao().getGuidById(lstTasksJoin2.get(i).getIdtask(), MYDEVICEID);
            TaskContextJoin ts = new TaskContextJoin(lstTasksJoin2.get(i).getIdtask(), lstTasksJoin2.get(i).getIdcontext(), guid);
            MyApplication.getDatabase().taskContextJoinDao().update(ts);
        }
    }

    public static void tasksUpdate() {
        List<Task> lstTasks = MyApplication.getDatabase().taskDao().getAllTasks();
        String deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();

        for (int i = 0; i < lstTasks.size(); i++){
            Task task = lstTasks.get(i);
            task.setDeviceguid(deviceID);
            if (task.getGuid().equals("") || task.getGuid() == null) {
                task.setGuid(UUID.randomUUID().toString());
                MyApplication.getDatabase().taskDao().update(task);
            }
        }
    }

    /*public static void upd(){
        Comment comment = MyApplication.getDatabase().commentDao().getById(1L);
        comment.setTask_id(132L);
        MyApplication.getDatabase().commentDao().delete(comment);
        comment = MyApplication.getDatabase().commentDao().getById(2L);
        comment.setTask_id(132L);
        MyApplication.getDatabase().commentDao().delete(comment);
        comment = MyApplication.getDatabase().commentDao().getById(3L);
        comment.setTask_id(132L);
        MyApplication.getDatabase().commentDao().update(comment);
        comment = MyApplication.getDatabase().commentDao().getById(4L);
        comment.setTask_id(132L);
        MyApplication.getDatabase().commentDao().update(comment);
        comment = MyApplication.getDatabase().commentDao().getById(5L);
        comment.setTask_id(132L);
        MyApplication.getDatabase().commentDao().update(comment);
    }*/

    public static void firstInitSpr4(){

        Category category = new Category();
        category.setId(18L);
        category.setTitle("Шаблоны задач");
        category.setDescription("Шаблоны задач");
        category.setGrp(3);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(19L);
        category.setTitle("Резервное копирование");
        category.setDescription("Резервное копирование");
        category.setGrp(3);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(20L);
        category.setTitle("Настройки");
        category.setDescription("Настройки");
        category.setGrp(4);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(21L);
        category.setTitle("Об устройстве");
        category.setDescription("Об устройстве");
        category.setGrp(4);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(23L);
        category.setTitle("Синхронизация");
        category.setDescription("Синхронизация");
        category.setGrp(3);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);

    }

    public static void firstInitSpr5(){
        Category category = new Category();
        category.setId(22L);
        category.setTitle("Резервные копии");
        category.setDescription("Работа с резервными копиями");
        category.setGrp(5);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);
    }

    public static void firstInitSpr2(){

        InfoTypes infoType = new InfoTypes();
        infoType.setId(1);
        infoType.setTitle("Вопрос");
        infoType.setVisualTitle("QUESTION");
        infoType.setColor("#808080");

        MyApplication.getDatabase().infoTypesDao().insert(infoType);

        infoType = new InfoTypes();
        infoType.setId(2);
        infoType.setTitle("Идея");
        infoType.setVisualTitle("IDEA");
        infoType.setColor("#0033FF");

        MyApplication.getDatabase().infoTypesDao().insert(infoType);

        infoType = new InfoTypes();
        infoType.setId(3);
        infoType.setTitle("Информация");
        infoType.setVisualTitle("INFO");
        infoType.setColor("#00CC99");

        MyApplication.getDatabase().infoTypesDao().insert(infoType);


    }

    public static void firstInitSpr(){

        // Справочник типов задач

        TaskTypes taskType = new TaskTypes();
        taskType.setId(1);
        taskType.setTitle("Эпик");
        taskType.setVusualTitle("EPIC");
        taskType.setColor("#6A5ACD");

        MyApplication.getDatabase().taskTypesDao().insert(taskType);

        taskType = new TaskTypes();
        taskType.setId(2);
        taskType.setTitle("История");
        taskType.setVusualTitle("STORY");
        taskType.setColor("#008000");

        MyApplication.getDatabase().taskTypesDao().insert(taskType);

        taskType = new TaskTypes();
        taskType.setId(3);
        taskType.setTitle("Задача");
        taskType.setVusualTitle("TASK");
        taskType.setColor("#0000FF");

        MyApplication.getDatabase().taskTypesDao().insert(taskType);

        taskType = new TaskTypes();
        taskType.setId(4);
        taskType.setTitle("Дефект");
        taskType.setVusualTitle("BUG");
        taskType.setColor("#FF0000");

        MyApplication.getDatabase().taskTypesDao().insert(taskType);

        taskType = new TaskTypes();
        taskType.setId(5);
        taskType.setTitle("Вопрос");
        taskType.setVusualTitle("QUESTION");
        taskType.setColor("#808080");

        MyApplication.getDatabase().taskTypesDao().insert(taskType);

        // ----------------------------------------------------------


        Category category = new Category();
        category.setId(1L);
        category.setTitle("Входящие");
        category.setDescription("9999");
        category.setGrp(1);
        //category.setUsed();
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(2L);
        category.setTitle("Все задачи");
        category.setDescription("8888");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(3L);
        category.setTitle("Сегодня");
        category.setDescription("8888");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(4L);
        category.setTitle("Горящие");
        category.setDescription("8888");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(5L);
        category.setTitle("Избранные");
        category.setDescription("8888");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(6L);
        category.setTitle("Завершенные");
        category.setDescription("8888");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);



        // --------------------------------------------------
        category = new Category();
        category.setId(7L);
        category.setTitle("Проекты");
        category.setDescription("7777");
        category.setGrp(2);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(8L);
        category.setTitle("Цели");
        category.setDescription("7777");
        category.setGrp(2);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(9L);
        category.setTitle("Тэги");
        category.setDescription("7777");
        category.setGrp(2);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(10L);
        category.setTitle("Контексты");
        category.setDescription("7777");
        category.setGrp(2);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(11L);
        category.setTitle("Календарь");
        category.setDescription("5555");
        category.setGrp(2);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(12L);
        category.setTitle("Следующие действия");
        category.setDescription("4444");
        category.setGrp(2);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(13L);
        category.setTitle("Когда-нибудь");
        category.setDescription("4444");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(14L);
        category.setTitle("Все задачи (тест)");
        category.setDescription("999999");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(15L);
        category.setTitle("Все задачи (new)");
        category.setDescription("7777777");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(16L);
        category.setTitle("Приостановленные");
        category.setDescription("999999");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);

        category = new Category();
        category.setId(17L);
        category.setTitle("Отложенные");
        category.setDescription("999999");
        category.setGrp(1);
        MyApplication.getDatabase().categoryDao().insert(category);


        InfoStatus infoStatus = new InfoStatus();
        infoStatus.setId(1L);
        infoStatus.setTitle("Новая");
        infoStatus.setDescription("");
        infoStatus.setColor("#FF0000");
        MyApplication.getDatabase().infoStatusDao().insert(infoStatus);
        infoStatus = new InfoStatus();
        infoStatus.setId(2L);
        infoStatus.setTitle("Обработана");
        infoStatus.setDescription("");
        infoStatus.setColor("#67E667");
        MyApplication.getDatabase().infoStatusDao().insert(infoStatus);
        infoStatus = new InfoStatus();
        infoStatus.setId(3L);
        infoStatus.setTitle("В архиве");
        infoStatus.setDescription("");
        infoStatus.setColor("#808080");
        MyApplication.getDatabase().infoStatusDao().insert(infoStatus);


        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setId(1);
        taskStatus.setTitle("Статус не определен");
        taskStatus.setColor("#FFA500");
        MyApplication.getDatabase().taskStatusDao().insert(taskStatus);

        taskStatus = new TaskStatus();
        taskStatus.setId(2);
        taskStatus.setTitle("Новая");
        taskStatus.setColor("#67E667");
        MyApplication.getDatabase().taskStatusDao().insert(taskStatus);
        taskStatus = new TaskStatus();
        taskStatus.setId(3);
        taskStatus.setTitle("В работе");
        taskStatus.setColor("#2196F3");
        MyApplication.getDatabase().taskStatusDao().insert(taskStatus);
        taskStatus = new TaskStatus();
        taskStatus.setId(4);
        taskStatus.setTitle("Пауза");
        taskStatus.setColor("#808080");
        MyApplication.getDatabase().taskStatusDao().insert(taskStatus);
        taskStatus = new TaskStatus();
        taskStatus.setId(5);
        taskStatus.setTitle("Отложено");
        taskStatus.setColor("#C71585");
        MyApplication.getDatabase().taskStatusDao().insert(taskStatus);
        taskStatus = new TaskStatus();
        taskStatus.setId(6);
        taskStatus.setTitle("Выполнено");
        taskStatus.setColor("#ff669900");
        MyApplication.getDatabase().taskStatusDao().insert(taskStatus);

        taskStatus = new TaskStatus();
        taskStatus.setId(-1);
        taskStatus.setTitle("Когда-нибудь");
        taskStatus.setColor("#FF4081");
        MyApplication.getDatabase().taskStatusDao().insert(taskStatus);

        // ---------------------------------------------------------------------

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(1);
        projectStatus.setTitle("Активный");
        projectStatus.setColor("#00FF7F");
        MyApplication.getDatabase().projectStatusDao().insert(projectStatus);

        projectStatus = new ProjectStatus();
        projectStatus.setId(2);
        projectStatus.setTitle("Отменен");
        projectStatus.setColor("#AFEEEE");
        MyApplication.getDatabase().projectStatusDao().insert(projectStatus);

        projectStatus = new ProjectStatus();
        projectStatus.setId(3);
        projectStatus.setTitle("Архивный");
        projectStatus.setColor("#C0C0C0");
        MyApplication.getDatabase().projectStatusDao().insert(projectStatus);

        projectStatus = new ProjectStatus();
        projectStatus.setId(4);
        projectStatus.setTitle("Отложен");
        projectStatus.setColor("#00BFFF");
        MyApplication.getDatabase().projectStatusDao().insert(projectStatus);

        Priority priority = new Priority();
        priority.setId(1);
        priority.setTitle("Наивысший");
        priority.setColor("#FF0000");
        MyApplication.getDatabase().priorityDao().insert(priority);

        priority = new Priority();
        priority.setId(2);
        priority.setTitle("Высокий");
        priority.setColor("#FF6600");
        MyApplication.getDatabase().priorityDao().insert(priority);

        priority = new Priority();
        priority.setId(3);
        priority.setTitle("Средний");
        priority.setColor("#00FF00");
        MyApplication.getDatabase().priorityDao().insert(priority);

        priority = new Priority();
        priority.setId(4);
        priority.setTitle("Низкий");
        priority.setColor("#3333FF");
        MyApplication.getDatabase().priorityDao().insert(priority);

        priority = new Priority();
        priority.setId(5);
        priority.setTitle("Без приоритета");
        priority.setColor("#999999");
        MyApplication.getDatabase().priorityDao().insert(priority);

    }

    public static void firstInitData(){
        Target target = new Target();


        target = new Target();
        target.setId(1L);
        target.setTitle("Изучение английского языка");
        target.setDescription("3333333");
        MyApplication.getDatabase().targetDao().insert(target);

        target.setId(2L);
        target.setTitle("Обучение");
        target.setDescription("1111");
        MyApplication.getDatabase().targetDao().insert(target);

        target = new Target();
        target.setId(3L);
        target.setTitle("Утвердить КА ФО");
        target.setDescription("222222");
        MyApplication.getDatabase().targetDao().insert(target);

        target = new Target();
        target.setId(4L);
        target.setTitle("Выход на арх. совет");
        target.setDescription("222222");
        MyApplication.getDatabase().targetDao().insert(target);

        /*
        category = new Category();
        category.setId(7L);
        category.setTitle("Лист ожидания");
        category.setDescription("3333");
        MyApplication.getDatabase().categoryDao().insert(category);

         */



        // ---------------------------------------------------------------------





        Project project = new Project();
        project.setId(1L);
        project.setTitle("Работа");
        project.setSearchtitle(project.getTitle());
        project.setDescription("Здесь будут находиться все рабочие проекты");
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);

        project = new Project();
        project.setId(2L);
        project.setTitle("Личное");
        project.setSearchtitle(project.getTitle());
        project.setDescription("Здесь будут находиться все личные проекты");
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);

        /*project = new Project();
        project.setId(3L);
        project.setTitle("Логистика банковских продуктов");
        project.setSearchtitle(project.getTitle());
        project.setDescription("333333");
        project.setParentid(1L);
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);*/

        /*project = new Project();
        project.setId(4L);
        project.setTitle("Персонализация сайта");
        project.setSearchtitle(project.getTitle());
        project.setDescription("Проект \"Персонализация сайта \"");
        project.setParentid(1L);
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);*/

        /*project = new Project();
        project.setId(5L);
        project.setTitle("Performance Management");
        project.setSearchtitle(project.getTitle());
        project.setDescription("Проект Performance Management");
        project.setParentid(1L);
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);*/




        project = new Project();
        project.setId(3L);
        project.setTitle("MyReader");
        project.setSearchtitle(project.getTitle());
        project.setDescription("Проект MyReader. ");
        project.setParentid(2L);
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);

        project = new Project();
        project.setId(4L);
        project.setTitle("MyGTD");
        project.setSearchtitle(project.getTitle());
        project.setDescription("Проект MyGTD. ");
        project.setParentid(2L);
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);

        /*project = new Project();
        project.setId(8L);
        project.setTitle("Маркетинговый оптимизатор");
        project.setSearchtitle(project.getTitle());
        project.setDescription("Проект Performance Managment");
        project.setParentid(1L);
        project.setPrStatus(PrStatus.ACTIVE);
        MyApplication.getDatabase().projectDao().insert(project);*/

        /*Task task = new Task();
        task.setId(1L);
        task.setTitle("ОТАР. Разработка и согласование");
        task.setSearchtitle(task.getTitle().toUpperCase());
        task.setDescription("Разработать и согласовать ОТАР");
        task.setPriority_id(1L);
        task.setStatus(Status.INPROGRESS);
        task.setDateCreate(new Date());
        task.setDateBeginStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), task.getDateCreate()));
        task.setTarget_id(2L);
        task.setProject_id(3L);
        MyApplication.getDatabase().taskDao().insert(task);

        task = new Task();
        task.setId(2L);
        task.setTitle("Второе задание");
        task.setSearchtitle(task.getTitle().toUpperCase());
        task.setDescription("Описание второго задания");
        task.setPriority_id(4L);
        task.setStatus(Status.COMPLETED);
        task.setProject_id(2L);
        task.setTarget_id(2L);
        task.getDateEnd();
        task.setTypeOfTask(TypeOfTask.BUG);
        MyApplication.getDatabase().taskDao().insert(task);



        task = new Task();
        task.setId(3L);
        task.setParenttask_id(2L);
        task.setTitle("Третье задание");
        task.setSearchtitle(task.getTitle().toUpperCase());
        task.setDescription("Описание третьего задания");
        task.setPriority_id(1L);
        task.setStatus(Status.INPROGRESS);
        task.setDateEnd(new GregorianCalendar(2020, 0 , 21).getTime());
        task.setDateEndStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), task.getDateEnd()));
        MyApplication.getDatabase().taskDao().insert(task);

        task = new Task();
        task.setId(4L);
        task.setTitle("Четвертое задание");
        task.setSearchtitle(task.getTitle().toUpperCase());
        task.setDescription("Описание четвертого задания");
        task.setPriority_id(3L);
        task.setStatus(Status.INPROGRESS);
        task.setDateEnd(new GregorianCalendar(2020, 0 , 22).getTime());
        task.setDateEndStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), task.getDateEnd()));
        task.setTypeOfTask(TypeOfTask.BUG);

        task.setTarget_id(1L);
        MyApplication.getDatabase().taskDao().insert(task);

        task = new Task();
        task.setId(5L);
        task.setTitle("Пятое задание");
        task.setSearchtitle(task.getTitle().toUpperCase());
        task.setDescription("Описание пятого задания");
        task.setPriority_id(1L);
        task.setStatus(Status.NEW);
        task.setProject_id(4L);
        task.setDateEndStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()));
        task.setTypeOfTask(TypeOfTask.BUG);
        MyApplication.getDatabase().taskDao().insert(task);

        task = new Task();
        task.setId(6L);
        task.setTitle("Задание когда-нибудь");
        task.setSearchtitle(task.getTitle().toUpperCase());
        task.setDescription("9999999");
        //task.setPriority_id(1L);
        task.setStatus(Status.SOMETIME);
        //task.setProject_id(4L);
        //task.setDateEndStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()));
        MyApplication.getDatabase().taskDao().insert(task);




        Information information = new Information();
        information.setId(1L);
        information.setTitle("Информация 1");
        information.setSearchtitle(information.getTitle().toUpperCase());
        Date date = new Date(System.currentTimeMillis());
        //date = System.currentTimeMillis();
        information.setDateCreate(date);
        information.setDescription("1111");
        //information.setInfoStatus(infoStatus);
        information.setIdstatus(3);
        //information.setDateCreateString(Utils.dateToString(null, date));
        MyApplication.getDatabase().informationDao().insert(information);

        information = new Information();
        information.setId(2L);
        information.setTitle("Информация 2");
        information.setSearchtitle(information.getTitle().toUpperCase());
        date = new Date(System.currentTimeMillis());
        //date = System.currentTimeMillis();
        information.setDateCreate(date);
        information.setDescription("2222");
        information.setIdstatus(1);
        //information.setDateCreateString(Utils.dateToString(null, date));
        MyApplication.getDatabase().informationDao().insert(information);

        information = new Information();
        information.setId(3L);
        information.setTitle("Информация 3");
        information.setSearchtitle(information.getTitle().toUpperCase());
        date = new Date(System.currentTimeMillis());
        //date = System.currentTimeMillis();
        information.setDateCreate(date);
        information.setDescription("3333");
        information.setIdstatus(3);
        //information.setDateCreateString(Utils.dateToString(null, date));
        MyApplication.getDatabase().informationDao().insert(information);*/



        Tag tag = new Tag();
        tag.setId(1L);
        tag.setTitle("ОТАР");
        tag.setDescription("11111");
        tag.setColor("#FF0000");
        MyApplication.getDatabase().tagDao().insert(tag);

        tag = new Tag();
        tag.setId(2L);
        tag.setTitle("Согласование");
        tag.setDescription("11111");
        tag.setColor("#aa03A9F4");
        MyApplication.getDatabase().tagDao().insert(tag);

        /*TaskTagJoin taskTagJoin = new TaskTagJoin(2L, 1L);
        MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoin);
        taskTagJoin = new TaskTagJoin(2L, 2L);
        MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoin);
        taskTagJoin = new TaskTagJoin(3L, 1L);
        MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoin);
        taskTagJoin = new TaskTagJoin(4L, 2L);
        MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoin);*/

        Contekst contekst = new Contekst();
        contekst.setId(1L);
        contekst.setTitle("Дом");
        contekst.setColor("#2196F3");
        MyApplication.getDatabase().contextDao().insert(contekst);

        contekst = new Contekst();
        contekst.setId(2L);
        contekst.setTitle("Работа");
        contekst.setColor("#33FF99");
        MyApplication.getDatabase().contextDao().insert(contekst);

        contekst = new Contekst();
        contekst.setId(3L);
        contekst.setTitle("В метро");
        contekst.setColor("#C71585");
        MyApplication.getDatabase().contextDao().insert(contekst);

        /*TaskContextJoin taskContextJoin = new TaskContextJoin(2L, 1L);
        MyApplication.getDatabase().taskContextJoinDao().insert(taskContextJoin);

        taskContextJoin = new TaskContextJoin(5L, 1L);
        MyApplication.getDatabase().taskContextJoinDao().insert(taskContextJoin);
        taskContextJoin = new TaskContextJoin(5L, 3L);
        MyApplication.getDatabase().taskContextJoinDao().insert(taskContextJoin);*/
    }

    public static void firstInitData3(){
        Device device = new Device();
        String tmDevice;
        String tmSerial;
        final TelephonyManager tm = (TelephonyManager) (MyApplication.getContext()).getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        //tmDevice = "" + tm.getImei();
        tmDevice = "" + Utils.getIMEIDeviceId(MyApplication.getContext());
        //tmSerial = "" + tm.getSimSerialNumber();
        tmSerial = "" + Utils.getSerialDeviceId(MyApplication.getContext());
        final String ANDROID_ID = Settings.Secure.getString(MyApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(ANDROID_ID.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        device.setId(1);
        device.setTitle(android.os.Build.MODEL);
        device.setGuid(deviceId);
        device.setIscurrent(1);
        MyApplication.getDatabase().deviceDao().insert(device);




        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("IMEI".toUpperCase());
        deviceInfo.setValue(tmDevice);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("SimSerialNumber".toUpperCase());
        //deviceInfo.setValue(tm.getSimSerialNumber());
        deviceInfo.setValue(Utils.getSerialDeviceId(MyApplication.getContext()));
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        final String androidId;
        androidId = "" + android.provider.Settings.Secure.getString(MyApplication.getContext().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        deviceInfo.setKey("IDCDMA".toUpperCase());
        deviceInfo.setValue(androidId);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("ModelName".toUpperCase());
        deviceInfo.setValue(android.os.Build.MODEL);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("NameUSER".toUpperCase());
        deviceInfo.setValue(android.os.Build.USER);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("PRODUCT".toUpperCase());
        deviceInfo.setValue(Build.PRODUCT);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("HARDWARE".toUpperCase());
        deviceInfo.setValue(Build.HARDWARE);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("BRAND".toUpperCase());
        deviceInfo.setValue(Build.BRAND);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("VERSION.RELEASE".toUpperCase());
        deviceInfo.setValue(android.os.Build.VERSION.RELEASE);
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("VERSION.SDK_INT".toUpperCase());
        deviceInfo.setValue(String.valueOf(Build.VERSION.SDK_INT));
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

        deviceInfo.setDeviceid(1);
        deviceInfo.setKey("Serial".toUpperCase());
        //deviceInfo.setValue(android.os.Build.getSerial());
        deviceInfo.setValue(Utils.getSerial());
        MyApplication.getDatabase().deviceInfoDao().insert(deviceInfo);

    }

}
