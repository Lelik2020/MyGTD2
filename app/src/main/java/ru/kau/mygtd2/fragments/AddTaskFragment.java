package ru.kau.mygtd2.fragments;

import static ru.kau.mygtd2.utils.Const.DEFAULT_CONTEXT_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHSECONDS;
import static ru.kau.mygtd2.utils.Const.DEFAULT_ICON_MARGIN;
import static ru.kau.mygtd2.utils.Const.DEFAULT_INVERTTEXT_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_PROJECT_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_RADIUS;
import static ru.kau.mygtd2.utils.Const.DEFAULT_RADIUS2;
import static ru.kau.mygtd2.utils.Const.DEFAULT_RTVPAGGING;
import static ru.kau.mygtd2.utils.Const.DEFAULT_RTV_HEIGHT;
import static ru.kau.mygtd2.utils.Const.DEFAULT_RTV_WIDTH;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TASKBG_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TASKCATEGORY_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TEXT_COLOR;
import static ru.kau.mygtd2.utils.Utils.getImageResourceTaskType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.CommentAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.common.enums.TypeOfTask;
import ru.kau.mygtd2.db.dao.TaskDaoAbs;
import ru.kau.mygtd2.dialogs.Dialogs;
import ru.kau.mygtd2.interfaces.DialogCategoryOfTaskChoice;
import ru.kau.mygtd2.interfaces.DialogContextsChoice;
import ru.kau.mygtd2.interfaces.DialogDateBeginChoice;
import ru.kau.mygtd2.interfaces.DialogDateEndChoice;
import ru.kau.mygtd2.interfaces.DialogParentTaskChoice;
import ru.kau.mygtd2.interfaces.DialogPriorityChoice;
import ru.kau.mygtd2.interfaces.DialogProjectChoice;
import ru.kau.mygtd2.interfaces.DialogStatusTaskChoice;
import ru.kau.mygtd2.interfaces.DialogTagsChoice;
import ru.kau.mygtd2.interfaces.DialogTargetChoice;
import ru.kau.mygtd2.interfaces.DialogTypeOfTaskChoice;
import ru.kau.mygtd2.listeners.DefaultListeners;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Priority;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskCategory;
import ru.kau.mygtd2.objects.TaskContextJoin;
import ru.kau.mygtd2.objects.TaskStatus;
import ru.kau.mygtd2.objects.TaskTagJoin;
import ru.kau.mygtd2.objects.TaskTemplate;
import ru.kau.mygtd2.objects.TaskTemplateContextJoin;
import ru.kau.mygtd2.objects.TaskTemplateTagJoin;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.BubbleFlag;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;

public class AddTaskFragment extends Fragment
        implements DialogProjectChoice, DialogTagsChoice, DialogTargetChoice,
        DialogPriorityChoice, DialogContextsChoice, DialogDateEndChoice,
        DialogDateBeginChoice, DialogTypeOfTaskChoice, DialogParentTaskChoice,
        DialogStatusTaskChoice, DialogCategoryOfTaskChoice {

    private TextView projectTitle;
    private TextView typeOfTaskTitle;
    //private TextView taskParentTypeTitle;
    private TextView taskTypeTitle;
    private TextView priorityTitle;
    //private TextView contextTitle;
    private TextView dateEndTitle;
    private TextView dateBeginTitle;

    private ImageView dbtodotoday;
    private ImageView dbtodotomorrow;
    private ImageView detodotoday;
    private ImageView detodotomorrow;

    private RoundTextView rtvstatusTaskTitle;


    private long projectId = 0L;
    private long targetId = 0L;
    private int taskCategoryId = 2;
    private long priorityId = 0L;
    private int taskTypeId = 3;
    private long parentTaskId = 0L;
    private String parentTaskGuid = "";
    private String guid = "";
    private String deviceID = "";
    //private int taskStatusId = 0;
    private static String taskBgColor;  //= DEFAULT_TASKBG_COLOR;

    private Target target;
    private Project project;
    private Priority priority;
    private TaskTypes taskTypes;
    private TaskStatus taskStatus;
    private TaskCategory taskCategory;

    private List<Tag> lsttags;
    private List<Contekst> lstConteksts;

    private Date dateEnd;
    private Date dateBegin;

    private TextView addComment;

    static ImageView iv;

    private LinearLayoutCompat ltasktags;
    private LinearLayoutCompat ltaskproject;
    private LinearLayoutCompat lparenttasktype;
    private LinearLayoutCompat ltasktype;
    private LinearLayoutCompat ltasktarget;
    private LinearLayoutCompat ltaskpriority;
    private LinearLayoutCompat ltaskcontext;
    private LinearLayoutCompat ltaskcategory;
    private Task taskUpdate = null;
    private TaskTemplate taskTemplate = null;

    private Task parentTask = null;




    public void bindAdapter(CommentAdapter commentAdapter) {
        DefaultListeners.bindAdapter4(getActivity(), commentAdapter);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.addtask_fragment, null);

        TextView txtTaskTitle = (TextView)rootView.findViewById(R.id.inputTaskTitle);
        TextView txtTaskInfoTitle = (TextView)rootView.findViewById(R.id.inputTaskinfoTitle);

        ltasktags = (LinearLayoutCompat) rootView.findViewById(R.id.addtasktags);
        ltaskproject = (LinearLayoutCompat) rootView.findViewById(R.id.addtaskproject);
        lparenttasktype = (LinearLayoutCompat) rootView.findViewById(R.id.addparenttasktype);
        ltasktype = (LinearLayoutCompat) rootView.findViewById(R.id.addtasktype);
        ltasktarget = (LinearLayoutCompat) rootView.findViewById(R.id.addtasktarget);
        ltaskpriority = (LinearLayoutCompat) rootView.findViewById(R.id.addtaskpriority);
        ltaskcontext = (LinearLayoutCompat) rootView.findViewById(R.id.addtaskcontexts);
        ltaskcategory = (LinearLayoutCompat) rootView.findViewById(R.id.addtaskcategory);

        //assert getArguments() != null;
        Bundle arguments = getArguments();
        Project parentProject = null;
        //Task task = null;
        if (arguments != null && arguments.containsKey("project")) {
            parentProject = (Project) arguments.getSerializable("project");
        }


        /*projectTitle = (TextView)rootView.findViewById(R.id.projectTitle);

        if (parentProject != null) {
            projectTitle.setText(parentProject.getTitle());
        }*/

        typeOfTaskTitle = (TextView)rootView.findViewById(R.id.typeOfTaskTitle);

        //targetTitle = (TextView)rootView.findViewById(R.id.targetTitle);

        //taskParentTypeTitle = (TextView)rootView.findViewById(R.id.taskParentTypeTitle);

        taskTypeTitle = (TextView)rootView.findViewById(R.id.taskTypeTitle);

        priorityTitle = (TextView)rootView.findViewById(R.id.priorityTitle);

        //contextTitle = (TextView)rootView.findViewById(R.id.contextTitle);

        dateEndTitle = (TextView)rootView.findViewById(R.id.dateendTitle);

        dateBeginTitle = (TextView)rootView.findViewById(R.id.datebeginTitle);

        dbtodotoday = (ImageView) rootView.findViewById(R.id.dbtodotoday);
        dbtodotomorrow = (ImageView) rootView.findViewById(R.id.dbtodotomorrow);
        detodotoday = (ImageView) rootView.findViewById(R.id.detodotoday);
        detodotomorrow = (ImageView) rootView.findViewById(R.id.detodotomorrow);

        rtvstatusTaskTitle = (RoundTextView) rootView.findViewById(R.id.rtvstatusTaskTitle);

        dbtodotoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDateBegin(Utils.dateToString(Utils.getStartOfDay(new Date())), Utils.getStartOfDay(new Date()).getTime());
                getDateBegin(Utils.dateToString(Utils.getStartOfDay(new Date(Utils.getCurrentApplicationDate()))), Utils.getStartOfDay(new Date(Utils.getCurrentApplicationDate())).getTime());
            }
        });

        detodotoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDateEnd(Utils.dateToString(Utils.getEndOfDay(new Date(), 0)), Utils.getEndOfDay(new Date(), 0).getTime());
                getDateEnd(Utils.dateToString(Utils.getEndOfDay(new Date(Utils.getCurrentApplicationDate()), 0)), Utils.getEndOfDay(new Date(Utils.getCurrentApplicationDate()), 0).getTime());
            }
        });

        dbtodotomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Date dt = new Date();
                Date dt = new Date(Utils.getCurrentApplicationDate());
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, 1);
                dt = c.getTime();
                getDateBegin(Utils.dateToString(Utils.getStartOfDay(dt)), Utils.getStartOfDay(dt).getTime());
            }
        });

        detodotomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Date dt = new Date();
                Date dt = new Date(Utils.getCurrentApplicationDate());
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, 1);
                dt = c.getTime();
                getDateEnd(Utils.dateToString(Utils.getEndOfDay(dt, 0)), Utils.getEndOfDay(dt, 0).getTime());
            }
        });


        addComment = (TextView) rootView.findViewById(R.id.addComment);

        taskBgColor = DEFAULT_TASKBG_COLOR;

        // ----------------------------------------------------------------

        taskStatus = MyApplication.getDatabase().taskStatusDao().getById(2);

        getStatusTask(taskStatus);



        final ImageView statustaskchoise = (ImageView) rootView.findViewById(R.id.statustaskchoise);

        statustaskchoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.showStatusTaskDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //TagDaoAbs.deleteTag(tagName);
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });


        if (arguments != null && arguments.containsKey("template")) {

            taskTemplate = (TaskTemplate) arguments.getSerializable("template");
            txtTaskTitle.setText(taskTemplate.getTitle());
            txtTaskInfoTitle.setText(taskTemplate.getDescription());

            taskStatus = MyApplication.getDatabase().taskStatusDao().getById(taskTemplate.getStatus().Value);
            //taskStatus = MyApplication.getDatabase().taskStatusDao().getById(taskUpdate.getStatus());
            getStatusTask(taskStatus);
            taskBgColor = taskTemplate.getBgColor();

            projectId = taskTemplate.getProject_id();
            ltaskproject.removeAllViews();
            if (projectId > 0) {
                getProject(MyApplication.getDatabase().projectDao().getProjectById(projectId));
            }
            taskTypeId = taskTemplate.getTypeOfTask().Value;
            getTypeOfTask(MyApplication.getDatabase().taskTypesDao().getById(taskTypeId));

            List<Tag> lstTags = MyApplication.getDatabase().tagDao().getAllByTemplateGuid(taskTemplate.getTemplateguid());

            getTags(lstTags);

            // Собираем контексты

            List<Contekst> lstConteksts = MyApplication.getDatabase().contextDao().getAllByTemplateGuid(taskTemplate.getTemplateguid());

            getContexts(lstConteksts);


            //
            targetId = taskTemplate.getTarget_id();
            target = MyApplication.getDatabase().targetDao().getById(targetId);


            getTarget(target);

            taskCategoryId = taskTemplate.getCategory();
            taskCategory = MyApplication.getDatabase().taskCategoryDao().getById(taskCategoryId);

            getTaskCategory(taskCategory);



            priorityId = taskTemplate.getPriority_id();
            priorityId = (priorityId <= 0 ? 3 : priorityId);
            /*if (priorityId <= 0) {
                priorityId = 3;
            }*/
            priority = MyApplication.getDatabase().priorityDao().getById(priorityId);

            getPriority(priority);


        }

        if (arguments != null && arguments.containsKey("task")) {
            taskUpdate = (Task) arguments.getSerializable("task");
            txtTaskTitle.setText(taskUpdate.getTitle());
            txtTaskInfoTitle.setText(taskUpdate.getDescription());

            // Если есть родительская задача, то берем проект оттуда.
            if (taskUpdate.getParenttask_id() > 0) {
                projectId = MyApplication.getDatabase().taskDao().getById(taskUpdate.getParenttask_id()).getProject_id();
                parentProject = MyApplication.getDatabase().projectDao().getProjectById(projectId);
            }

            parentTaskId = taskUpdate.getParenttask_id();
            parentTaskGuid = taskUpdate.getParenttaskguid();
            //getParentTask(MyApplication.getDatabase().taskDao().getById(parentTaskId));
            getParentTask(MyApplication.getDatabase().taskDao().getByGuid(parentTaskGuid));

            dateBegin = taskUpdate.getDateBegin();
            dateEnd = taskUpdate.getDateEnd();
            guid = taskUpdate.getGuid();
            deviceID = taskUpdate.getDeviceguid();

            taskStatus = MyApplication.getDatabase().taskStatusDao().getById(taskUpdate.getStatus().Value);
            //taskStatus = MyApplication.getDatabase().taskStatusDao().getById(taskUpdate.getStatus());
            getStatusTask(taskStatus);

            taskBgColor = taskUpdate.getBgColor();

            projectId = taskUpdate.getProject_id();
            ltaskproject.removeAllViews();
            if (projectId > 0) {
                getProject(MyApplication.getDatabase().projectDao().getProjectById(projectId));
            }
            taskTypeId = taskUpdate.getTypeoftask().Value;
            getTypeOfTask(MyApplication.getDatabase().taskTypesDao().getById(taskTypeId));

            // Собираем тэги

            List<Tag> lstTags = MyApplication.getDatabase().tagDao().getAllByTaskGuid(taskUpdate.getGuid());

            getTags(lstTags);

            // ------------------------------------------------------------------

            // Собираем контексты

            List<Contekst> lstConteksts = MyApplication.getDatabase().contextDao().getAllByTaskGuid(taskUpdate.getGuid());

            getContexts(lstConteksts);


            //
            targetId = taskUpdate.getTarget_id();
            target = MyApplication.getDatabase().targetDao().getById(targetId);


            getTarget(target);

            taskCategoryId = taskUpdate.getCategory();
            taskCategory = MyApplication.getDatabase().taskCategoryDao().getById(taskCategoryId);

            getTaskCategory(taskCategory);



            priorityId = taskUpdate.getPriority_id();
            priorityId = (priorityId <= 0 ? 3 : priorityId);

            priority = MyApplication.getDatabase().priorityDao().getById(priorityId);

            getPriority(priority);

            dateBeginTitle.setText(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, taskUpdate.getDateBegin()));
            dateEndTitle.setText(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, taskUpdate.getDateEnd()));

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.comments_recyclerview);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
            //TasksAdapter tasksAdapter = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasks());



            CommentAdapter commentAdapter = new CommentAdapter(getActivity(), MyApplication.getDatabase().commentDao().getAllByTaskGuid(taskUpdate.getGuid()));

            bindAdapter(commentAdapter);
            recyclerView.setAdapter(commentAdapter);



            //priorityTitle.setText((priority == null) ? "" : priority.getTitle());
            //lstConteksts =
            //contextTitle
        }

        if (arguments != null && arguments.containsKey("subtask")) {
            parentTask = (Task) arguments.getSerializable("subtask");
            parentTaskId = parentTask.getId();
            parentTaskGuid = parentTask.getParenttaskguid();
            taskCategoryId = parentTask.getCategory();
            parentProject = MyApplication.getDatabase().projectDao().getProjectById(parentTask.getProject_id());
            getParentTask(parentTask);
            // Получаем тэги из parentTask
            getTags(MyApplication.getDatabase().tagDao().getAllByTaskGuid(parentTask.getGuid()));



        }

        if (arguments != null && arguments.containsKey("clonetask")) {
            Task task = (Task) arguments.getSerializable("clonetask");
            txtTaskTitle.setText(task.getTitle());
            txtTaskInfoTitle.setText(task.getDescription());
            task.setId(Utils.getLastTaskId());

            parentTaskId = task.getParenttask_id();
            parentTaskGuid = task.getParenttaskguid();

            // Если есть родительская задача, то берем проект оттуда.
            /*if (task.getParenttask_id() > 0) {
                projectId = MyApplication.getDatabase().taskDao().getById(task.getParenttask_id()).getProject_id();
                parentProject = MyApplication.getDatabase().projectDao().getProjectById(projectId);
            }*/
            if (!task.getParenttaskguid().equals("") && task.getParenttaskguid() != null) {
                projectId = MyApplication.getDatabase().taskDao().getById(task.getParenttask_id()).getProject_id();
                parentProject = MyApplication.getDatabase().projectDao().getProjectById(projectId);
            }


            dateBegin = task.getDateBegin();
            dateEnd = task.getDateEnd();
            taskBgColor = task.getBgColor();

            projectId = task.getProject_id();
            taskCategoryId = task.getCategory();

            lsttags = MyApplication.getDatabase().tagDao().getAllByTaskGuid(task.getGuid());


            lstConteksts =  MyApplication.getDatabase().contextDao().getAllByTaskGuid(task.getGuid());
            taskTypeId = task.getTypeoftask().Value;

            getContexts(lstConteksts);
            if (task.getDateBegin() != null) {
                getDateBegin(task.getDateBeginStr(), task.getDateBegin().getTime());
            }
            if (task.getDateEnd() != null) {
                getDateEnd(task.getDateEndStr(), task.getDateEnd().getTime());
            }
            getPriority(MyApplication.getDatabase().priorityDao().getById(task.getPriority_id()));
            getProject(MyApplication.getDatabase().projectDao().getProjectById(task.getProject_id()));
            getTags(lsttags);
            getTarget(MyApplication.getDatabase().targetDao().getById(task.getTarget_id()));
            getTypeOfTask(MyApplication.getDatabase().taskTypesDao().getById(taskTypeId));

            //getParentTask(MyApplication.getDatabase().taskDao().getById(parentTaskId));
            getParentTask(MyApplication.getDatabase().taskDao().getByGuid(task.getParenttaskguid()));
            //вап

        }


        getTypeOfTask(MyApplication.getDatabase().taskTypesDao().getById(taskTypeId));

        priorityId = (priorityId <= 0 ? 3 : priorityId);

        priority = MyApplication.getDatabase().priorityDao().getById(priorityId);

        getPriority(priority);


        final ImageView parenttaskchoise = (ImageView) rootView.findViewById(R.id.parenttaskchoise);
        parenttaskchoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.choiseParentTaskDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        final ImageView projectchoise = (ImageView) rootView.findViewById(R.id.projectchoise);



        projectchoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.choiseProjectDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        final ImageView projectclear = (ImageView) rootView.findViewById(R.id.projectclear);
        projectclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (parentProject != null){

            //
            getProject(parentProject);

            projectchoise.setVisibility(View.INVISIBLE);
            projectclear.setVisibility(View.INVISIBLE);
            //projectTitle.setText(parentProject.getTitle());
        }


        final ImageView typesoftaskchoise = (ImageView) rootView.findViewById(R.id.typesoftaskchoise);

        typesoftaskchoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showTypesTaskDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //TagDaoAbs.deleteTag(tagName);
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });

            }
        });

        getTaskCategory(MyApplication.getDatabase().taskCategoryDao().getById(taskCategoryId));

        final ImageView categoryoftaskchoise = (ImageView) rootView.findViewById(R.id.categorychoise);

        categoryoftaskchoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogs.showCategoriesTaskDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //TagDaoAbs.deleteTag(tagName);
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });

            }
        });

        final ImageView tagschoise = (ImageView) rootView.findViewById(R.id.tagschoise);

        tagschoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Dialogs.showTagsDialog(getActivity(), new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/

                Dialogs.showTagsDialogn(getActivity(), new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
        });

        final ImageView targetchoise = (ImageView) rootView.findViewById(R.id.targetchoise);

        targetchoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.showTargetsDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        final ImageView prioritychoise = (ImageView) rootView.findViewById(R.id.prioritychoise);

        prioritychoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.showPriorityDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        final ImageView contextschoise = (ImageView) rootView.findViewById(R.id.contextchoise);

        contextschoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.showContextsDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        final ImageView datebeginchoise = (ImageView) rootView.findViewById(R.id.datebeginchoise);

        datebeginchoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.showDateBeginDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        final ImageView dateendchoise = (ImageView) rootView.findViewById(R.id.dateendchoise);

        dateendchoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Dialogs.showDateEndDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });

        final ImageView datebeginclear = (ImageView) rootView.findViewById(R.id.datebeginclear);

        datebeginclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateBeginTitle.setText("");
                dateBegin = null;
            }
        });

        final ImageView dateendclear = (ImageView) rootView.findViewById(R.id.dateendclear);
        dateendclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEndTitle.setText("");
                dateEnd = null;
            }
        });


        iv = (ImageView) rootView.findViewById(R.id.choiseColor);

        iv.setColorFilter(Utils.parseColor(taskBgColor, DEFAULT_TASKBG_COLOR));

        iv.setEnabled(true);
        //ImageViewCompat.setImageTintList(iv, );

        final ImageView bgcolorchoise = (ImageView) rootView.findViewById(R.id.bgcolorchoise);
        bgcolorchoise.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                /*Dialogs.showDateEndDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });*/

                showColorChoisedialog(getActivity(), new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

        //bgcolorchoise

        if (taskUpdate == null) {
            addComment.setVisibility(View.INVISIBLE);
        } else {
            addComment.setVisibility(View.VISIBLE);
        }

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Dialogs.addCommentDialog(getContext(), null, taskUpdate, null, null, false);
                }
            });



        Button imgbtnsavetask = (Button) rootView.findViewById(R.id.btnsavetask);
        imgbtnsavetask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if (arguments != null && arguments.containsKey("task")) {
                    taskUpdate.setTitle(txtTaskTitle.getText().toString());
                    taskUpdate.setSearchtitle(taskUpdate.getTitle().toUpperCase());
                    taskUpdate.setDescription(txtTaskInfoTitle.getText().toString());
                    taskUpdate.setStatus(Status.from(taskStatus.getId()));
                    //taskUpdate.setStatus(taskStatus.getId());
                    //taskUpdate.setStatus(taskStatus.getId());
                    taskUpdate.setPriority_id(priorityId);
                    taskUpdate.setProject_id(projectId);
                    taskUpdate.setParenttask_id(parentTaskId);
                    taskUpdate.setParenttaskguid(parentTaskGuid);
                    taskUpdate.setTypeoftask(TypeOfTask.from(taskTypes.getId()));

                    //taskUpdate.

                    //taskUpdate.setStatus(Status.NEW);
                    taskUpdate.setDateEnd(dateEnd);
                    taskUpdate.setDateEndStr(dateEndTitle.getText().toString());
                    taskUpdate.setTarget_id(targetId);
                    //taskUpdate.setDateCreate(new Date());
                    //taskUpdate.setDateCreateStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), taskUpdate.getDateCreate()));
                    taskUpdate.setDateBegin(dateBegin);
                    taskUpdate.setDateBeginStr(dateBeginTitle.getText().toString());
                    taskUpdate.setBgColor(taskBgColor);
                    taskUpdate.setGuid(guid);
                    taskUpdate.setDeviceguid(deviceID);
                    taskUpdate.setCategory(taskCategoryId);
                    //Date date = new Date();
                    Date date = new Date(Utils.getCurrentApplicationDateAndTime());
                    taskUpdate.setDateEdit(date);
                    taskUpdate.setDateEditStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));
                    TaskDaoAbs.updateTask(taskUpdate, lsttags, lstConteksts);
                    Toasty.success(getContext(), getString(R.string.taskupdated), Toast.LENGTH_SHORT, true).show();

                } else {
                    Task task = new Task();
                    Task task2;
                    Information information;
                    task.setId(Utils.getLastTaskId());
                    task.setTitle(txtTaskTitle.getText().toString());
                    task.setSearchtitle(task.getTitle().toUpperCase());
                    task.setDescription(txtTaskInfoTitle.getText().toString());
                    task.setStatus(Status.from(taskStatus.getId()));
                    //task.setStatus(taskStatus.getId());
                    task.setTypeoftask(TypeOfTask.from(taskTypes.getId()));
                    task.setParenttask_id(parentTaskId);
                    task.setParenttaskguid(parentTaskGuid);
                    task.setProject_id(projectId);
                    task.setPriority_id(priorityId);
                    //task.setStatus(Status.NEW);
                    //task.setParenttask_id(parentTaskId);
                    task.setDateEnd(dateEnd);
                    task.setDateEndStr(dateEndTitle.getText().toString());
                    task.setTarget_id(targetId);
                    //task.setDateCreate(new Date());
                    //task.setDateCreate(new Date(Utils.getCurrentApplicationDate()));
                    //task.setDateCreateStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), task.getDateCreate()));
                    task.setDateCreate(new Date(Utils.getCurrentApplicationDateAndTime()));
                    task.setDateCreateStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, task.getDateCreate()));

                    task.setDateBegin(dateBegin);
                    task.setDateBeginStr(dateBeginTitle.getText().toString());
                    task.setBgColor(taskBgColor);
                    deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();
                    guid = UUID.randomUUID().toString();
                    task.setDeviceguid(deviceID);
                    task.setGuid(guid);
                    task.setCategory(taskCategoryId);

                    //Date date = new Date();
                    Date date = new Date(Utils.getCurrentApplicationDate());
                    task.setDateEdit(date);
                    task.setDateEditStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));
                    //task.setBgColor(iv.getSolidColor().);

                    Bundle bundle = getArguments();
                    if (bundle != null) {
                        task2 = (Task) bundle.getSerializable("task");
                        if (task2 == null) {

                        } else {
                            task.setParenttask_id(task2.getId());
                            task.setParenttaskguid(task2.getParenttaskguid());
                        }
                        information = (Information) bundle.getSerializable("information");
                        if (information == null) {

                        } else {
                            task.setInfo_id(information.getId());
                        }

                    }
                    //task.setProject_id(projectId);
                    if (task.getTitle().trim().equals("")) {
                        task.setTitle(null);
                    }
                    //MyApplication.getDatabase().beginTransaction();

                    try {

                        long task_id = MyApplication.getDatabase().taskDao().insert(task);


                        List<TaskTagJoin> taskTagJoinList = new ArrayList<TaskTagJoin>();
                        if (lsttags != null){
                            for (Tag tag : lsttags) {

                                //TaskTagJoin taskTagJoin = new TaskTagJoin(task_id, tag.getId());
                                TaskTagJoin taskTagJoin = new TaskTagJoin(task_id, tag.getId(), guid);
                                taskTagJoinList.add(taskTagJoin);
                            }
                            MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoinList);
                        }



                        List<TaskContextJoin> taskContextJoins = new ArrayList<TaskContextJoin>();
                        if (lstConteksts != null) {
                            for (Contekst contekst : lstConteksts) {

                                TaskContextJoin taskContextJoin = new TaskContextJoin(task_id, contekst.getId(), guid);
                                taskContextJoins.add(taskContextJoin);
                            }

                            MyApplication.getDatabase().taskContextJoinDao().insert(taskContextJoins);
                        }



                        Toasty.success(getContext(), getString(R.string.taskcreated), Toast.LENGTH_SHORT, true).show();

                    } catch (Exception e) {
                        Toasty.error(getContext(), getString(R.string.taskcreatederror), Toast.LENGTH_SHORT, true).show();

                    }
                }

                closeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        Button btnsavetasktemplate = (Button) rootView.findViewById(R.id.btnsavetasktemplate);
        btnsavetasktemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taskTemplate = new TaskTemplate();

                guid = UUID.randomUUID().toString();

                taskTemplate.setTitle(txtTaskTitle.getText().toString());
                taskTemplate.setSearchtitle(taskTemplate.getTitle().toUpperCase());
                taskTemplate.setDescription(txtTaskInfoTitle.getText().toString());
                taskTemplate.setStatus(Status.from(taskStatus.getId()));
                //taskUpdate.setStatus(taskStatus.getId());
                //taskUpdate.setStatus(taskStatus.getId());
                taskTemplate.setPriority_id(priorityId);
                taskTemplate.setProject_id(projectId);

                taskTemplate.setTypeOfTask(TypeOfTask.from(taskTypes.getId()));

                //taskUpdate.setStatus(Status.NEW);

                taskTemplate.setTarget_id(targetId);
                //taskUpdate.setDateCreate(new Date());
                //taskUpdate.setDateCreateStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), taskUpdate.getDateCreate()));

                taskTemplate.setBgColor(taskBgColor);
                taskTemplate.setTemplateguid(guid);
                deviceID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();
                taskTemplate.setDeviceguid(deviceID);
                taskTemplate.setCategory(taskCategoryId);

                try {

                    MyApplication.getDatabase().taskTemplateDao().insert(taskTemplate);


                    List<TaskTemplateTagJoin> taskTemplateTagJoinList = new ArrayList<TaskTemplateTagJoin>();
                    if (lsttags != null){
                        for (Tag tag : lsttags) {

                            //TaskTagJoin taskTagJoin = new TaskTagJoin(task_id, tag.getId());
                            TaskTemplateTagJoin taskTemplateTagJoin = new TaskTemplateTagJoin(tag.getId(), guid);
                            taskTemplateTagJoinList.add(taskTemplateTagJoin);
                        }
                        MyApplication.getDatabase().taskTemplateTagJoinDao().insert(taskTemplateTagJoinList);
                    }



                    List<TaskTemplateContextJoin> taskTemplateContextJoinList = new ArrayList<TaskTemplateContextJoin>();
                    if (lstConteksts != null) {
                        for (Contekst contekst : lstConteksts) {

                            TaskTemplateContextJoin taskTemplateContextJoin = new TaskTemplateContextJoin(contekst.getId(), guid);
                            taskTemplateContextJoinList.add(taskTemplateContextJoin);
                        }

                        MyApplication.getDatabase().taskTemplateContextJoinDao().insert(taskTemplateContextJoinList);
                    }

                    Toasty.success(getContext(), getString(R.string.templatecreated), Toast.LENGTH_SHORT, true).show();

                } catch (Exception e) {
                    Toasty.error(getContext(), getString(R.string.templatecreatederror), Toast.LENGTH_SHORT, true).show();

                }

                //closeFragment();
                //FragmentManager fm = getActivity().getSupportFragmentManager();
                //fm.popBackStack();
            }
        });

        Button imgbtncanceltask = (Button) rootView.findViewById(R.id.btncanceltask);
        imgbtncanceltask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });



        return rootView;
    }

    private void closeFragment(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }




    /*@SuppressLint("ResourceAsColor")
    @Override
    public void getProject(Node node) {
        ///projectTitle.setText(node.getName());
        projectId = ((Long)node.getId()).longValue();
        ltaskproject.removeAllViews();
        if (projectId > 0) {
            Project project = MyApplication.getDatabase().projectDao().getProjectById(projectId);
            //ltaskproject.removeAllViews();
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            ltaskproject.setLayoutParams(lParams);
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.folder);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            lParams = new LinearLayout.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
            iv.setLayoutParams(lParams);

            ltaskproject.addView(iv);

            *//*lParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*//*

            RoundTextView rtv1 = new RoundTextView(getActivity());
            //rtv1.setText(lstTask.get(0).getTitle());

            rtv1.setCorner(20);
            rtv1.setPadding(10, 0, 10, 0);
            //rtv1.setCorner(5, 5, 5, 5);
            //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
            rtv1.setTextColor(R.color.black);
            rtv1.setTextSize(16);
            rtv1.setTypeface(Typeface.DEFAULT_BOLD);


            try {
                rtv1.setBgColor(Color.parseColor(project.getColor()));
            } catch (Exception ex) {
                rtv1.setBgColor(Color.parseColor(DEFAULT_PROJECT_COLOR));
            }
            //rtv1.setCorner(2, 2, 2, 2);
            //rtv1.setCorner(5);

            rtv1.setText(project.getTitle());


            lParams = new LinearLayout.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            rtv1.setLayoutParams(lParams);

            ltaskproject.addView(rtv1);
        }


        //Log.e("444444444", node.getName());
    }*/

    @SuppressLint("ResourceAsColor")
    @Override
    public void getTags(List<Tag> tags) {

        ltasktags.removeAllViews();
        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        ltasktags.setLayoutParams(lParams);

        if (tags != null) {
            lsttags = new ArrayList<Tag>();
            lsttags.addAll(tags);
            if (tags.size() > 0) {


                for (int j = 0; j < tags.size(); j++) {

                    LinearLayoutCompat.LayoutParams lParams2 = new LinearLayoutCompat.LayoutParams(Const.LAYOUT_DEFAULT_WIDTH, Const.LAYOUT_DEFAULT_HEIGHT);

                    /*iv = new ImageView(getActivity());
                    iv.setImageResource(R.drawable.context2);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
                    iv.setLayoutParams(lParams);
                    lParams.setMargins(0, DEFAULT_ICON_MARGIN, 0, DEFAULT_ICON_MARGIN);
                    iv.setLayoutParams(lParams);
                    ltaskcontext.addView(iv);

                    RoundTextView rtv1 = new RoundTextView(getActivity());
                    rtv1.setTextAppearance(R.style.rtvTextView);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
                    rtv1.setLayoutParams(lParams);
                    rtv1.setCorner(DEFAULT_RADIUS2);
                    rtv1.setPadding(DEFAULT_RTVPAGGING, 0, DEFAULT_RTVPAGGING, 0);
                    rtv1.setBgColor(Color.parseColor(conteksts.get(j).getColor()));
                    rtv1.setText(conteksts.get(j).getTitle());
                    ltaskcontext.addView(rtv1, lParams);*/

                    ImageView iv = new ImageView(getActivity());
                    iv.setImageResource(R.drawable.merchandising);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
                    lParams.setMargins(0, DEFAULT_ICON_MARGIN, 0, DEFAULT_ICON_MARGIN);
                    iv.setLayoutParams(lParams);
                    try {
                        iv.setColorFilter(Color.parseColor(tags.get(j).getColor()));
                    } catch (Exception e){
                        iv.setColorFilter(Color.parseColor("#000000"));
                    }

                    ltasktags.addView(iv);
                    RoundTextView rtv1 = new RoundTextView(getActivity());
                    rtv1.setTextAppearance(R.style.rtvTextView);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
                    rtv1.setLayoutParams(lParams);
                    rtv1.setCorner(DEFAULT_RADIUS2);
                    rtv1.setPadding(DEFAULT_RTVPAGGING, 0, DEFAULT_RTVPAGGING, 0);
                    rtv1.setBgColor(Utils.parseColor(tags.get(j).getColor()));
                    rtv1.setTextSize(16);

                    rtv1.setText(tags.get(j).getTitle());
                    rtv1.setTypeface(Typeface.DEFAULT_BOLD);
                    rtv1.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
                    /*lParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
                    ltasktags.addView(rtv1);
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void getTarget(Target target) {

        if (target != null) {
            targetId = target.getId();
            //Project project = MyApplication.getDatabase().projectDao().getProjectById(projectId);
            ltasktarget.removeAllViews();
            LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            ltasktarget.setLayoutParams(lParams);

            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.target4);
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
            iv.setLayoutParams(lParams);

            ltasktarget.addView(iv);

            /*lParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/

            RoundTextView rtv1 = new RoundTextView(getActivity());
            //rtv1.setText(lstTask.get(0).getTitle());

            rtv1.setCorner(20);
            rtv1.setPadding(10, 0, 10, 0);
            //rtv1.setCorner(5, 5, 5, 5);
            //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
            //rtv1.setTextColor(R.color.black);


            try {
                rtv1.setBgColor(Color.parseColor(target.getColor()));
            } catch (Exception ex) {
                rtv1.setBgColor(Color.parseColor(DEFAULT_PROJECT_COLOR));
            }
            //rtv1.setCorner(2, 2, 2, 2);
            //rtv1.setCorner(5);

            rtv1.setText(target.getTitle());

            rtv1.setTextSize(16);
            rtv1.setTypeface(Typeface.DEFAULT_BOLD);
            rtv1.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            rtv1.setLayoutParams(lParams);

            ltasktarget.addView(rtv1);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void getTaskCategory(TaskCategory taskCategory2) {

        if (taskCategory2 != null) {
            taskCategoryId = taskCategory2.getId();
            ltaskcategory.removeAllViews();

            /*iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.context2);
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
            iv.setLayoutParams(lParams);
            lParams.setMargins(0, DEFAULT_ICON_MARGIN, 0, DEFAULT_ICON_MARGIN);
            iv.setLayoutParams(lParams);
            ltaskcontext.addView(iv);

            RoundTextView rtv1 = new RoundTextView(getActivity());
            rtv1.setTextAppearance(R.style.rtvTextView);
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            rtv1.setLayoutParams(lParams);
            rtv1.setCorner(DEFAULT_RADIUS2);
            rtv1.setPadding(DEFAULT_RTVPAGGING, 0, DEFAULT_RTVPAGGING, 0);
            rtv1.setBgColor(Color.parseColor(conteksts.get(j).getColor()));
            rtv1.setText(conteksts.get(j).getTitle());
            ltaskcontext.addView(rtv1, lParams);*/


            LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            ltaskcategory.setLayoutParams(lParams);
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.bookmark);
            iv.setColorFilter(Color.parseColor(taskCategory2.getColor()));
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
            lParams.setMargins(0, DEFAULT_ICON_MARGIN, 0, DEFAULT_ICON_MARGIN);
            iv.setLayoutParams(lParams);
            ltaskcategory.addView(iv);

            RoundTextView rtv1 = new RoundTextView(getActivity());
            rtv1.setTextAppearance(R.style.rtvTextView);
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            rtv1.setLayoutParams(lParams);
            rtv1.setCorner(DEFAULT_RADIUS2);
            rtv1.setPadding(DEFAULT_RTVPAGGING, 0, DEFAULT_RTVPAGGING, 0);



            try {
                rtv1.setBgColor(Color.parseColor(taskCategory2.getColor()));
            } catch (Exception ex) {
                rtv1.setBgColor(Color.parseColor(DEFAULT_TASKCATEGORY_COLOR));
            }


            rtv1.setText(taskCategory2.getTitle());

            rtv1.setTextSize(16);
            rtv1.setTypeface(Typeface.DEFAULT_BOLD);
            rtv1.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            rtv1.setLayoutParams(lParams);

            ltaskcategory.addView(rtv1);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void getPriority(Priority priority) {

        priorityId = priority.getId();

        ltaskpriority.removeAllViews();

        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        ltaskpriority.setLayoutParams(lParams);
        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(Utils.getIconForPriority(priority));
        //iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        lParams.setMargins(0, 2, 0, 2);
        iv.setLayoutParams(lParams);

        ltaskpriority.addView(iv);

        /*LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.LAYOUT_DEFAULT_WIDTH, Const.LAYOUT_DEFAULT_HEIGHT);
        ltaskpriority.setLayoutParams(lParams);
        iv = new ImageView(getActivity());
        iv.setImageResource(Utils.getIconForPriority(priority));
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        iv.setLayoutParams(lParams);
        ltaskpriority.addView(iv);*/

        priorityTitle.setText(priority.getTitle());

        priorityTitle.setTypeface(Typeface.DEFAULT_BOLD);
        priorityTitle.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void getContexts(List<Contekst> conteksts) {
        ImageView iv;
        //ltaskcontext
        ltaskcontext.removeAllViews();

        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        ltaskcontext.setLayoutParams(lParams);

        if (conteksts != null) {
            lstConteksts = new ArrayList<Contekst>();
            lstConteksts.addAll(conteksts);
            if (conteksts.size() > 0) {

                //LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);

                //ltaskcontext.setLayoutParams(lParams);





                for (int j = 0; j < conteksts.size(); j++) {

                    iv = new ImageView(getActivity());
                    iv.setImageResource(R.drawable.context2);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
                    iv.setLayoutParams(lParams);
                    lParams.setMargins(0, DEFAULT_ICON_MARGIN, 0, DEFAULT_ICON_MARGIN);
                    iv.setLayoutParams(lParams);
                    ltaskcontext.addView(iv);

                    RoundTextView rtv1 = new RoundTextView(getActivity());
                    rtv1.setTextAppearance(R.style.rtvTextView);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
                    rtv1.setLayoutParams(lParams);
                    rtv1.setCorner(DEFAULT_RADIUS2);
                    rtv1.setPadding(DEFAULT_RTVPAGGING, 0, DEFAULT_RTVPAGGING, 0);
                    try {
                        rtv1.setBgColor(Color.parseColor(conteksts.get(j).getColor()));
                    } catch (Exception e) {
                        rtv1.setBgColor(Color.parseColor(DEFAULT_CONTEXT_COLOR));
                        rtv1.setTextColor(Color.parseColor(DEFAULT_INVERTTEXT_COLOR));
                    }
                    rtv1.setText(conteksts.get(j).getTitle());
                    ltaskcontext.addView(rtv1, lParams);
                }
            }
        }


    }

    @Override
    public void getDateEnd(String date, long datemls) {
        dateEnd = new Date(datemls);
        dateEndTitle.setText(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, dateEnd));
        dateEndTitle.setTypeface(Typeface.DEFAULT_BOLD);
        dateEndTitle.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
    }

    @Override
    public void getDateBegin(String date, long datemls) {
        dateBegin = new Date(datemls);
        dateBeginTitle.setText(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, dateBegin));
        dateBeginTitle.setTypeface(Typeface.DEFAULT_BOLD);
        dateBeginTitle.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void getTypeOfTask(TaskTypes tasktype) {

        //taskTypes = tasktype;



        ltasktype.removeAllViews();

        /*LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        ltasktype.setLayoutParams(lParams);
        ImageView iv = new ImageView(getActivity());
        //iv.setImageResource(R.drawable.folder);
        iv.setImageResource(getImageResourceTaskType(TypeOfTask.from(tasktype.getId())));
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        iv.setLayoutParams(lParams);

        ltaskproject.addView(iv);*/

        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        ltasktype.setLayoutParams(lParams);
        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(getImageResourceTaskType(TypeOfTask.from(tasktype.getId())));
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        iv.setLayoutParams(lParams);
        ltasktype.addView(iv);

        taskTypeTitle.setTextSize(16);
        taskTypeTitle.setText(tasktype.getTitle());
        taskTypeTitle.setTypeface(Typeface.DEFAULT_BOLD);
        taskTypeTitle.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));


        //taskTypes.setId(tasktype.getId());
        taskTypes = MyApplication.getDatabase().taskTypesDao().getById(tasktype.getId());

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void getProject(Project project) {
        ///projectTitle.setText(node.getName());
        //projectId = project.getId();
        //ltaskproject.removeAllViews();
        if (project != null) {
            //Project project = MyApplication.getDatabase().projectDao().getProjectById(projectId);
            ltaskproject.removeAllViews();
            LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            ltaskproject.setLayoutParams(lParams);
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.folder);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
            iv.setLayoutParams(lParams);

            ltaskproject.addView(iv);

            /*lParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/

            RoundTextView rtv1 = new RoundTextView(getActivity());
            //rtv1.setText(lstTask.get(0).getTitle());

            rtv1.setCorner(20);
            rtv1.setPadding(10, 0, 10, 0);
            //rtv1.setCorner(5, 5, 5, 5);
            //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
            //rtv1.setTextColor(R.color.black);
            rtv1.setTextSize(16);
            //rtv1.setTypeface(Typeface.DEFAULT_BOLD);


            try {
                rtv1.setBgColor(Color.parseColor(project.getColor()));
            } catch (Exception ex) {
                rtv1.setBgColor(Color.parseColor(DEFAULT_PROJECT_COLOR));
            }
            //rtv1.setCorner(2, 2, 2, 2);
            //rtv1.setCorner(5);

            rtv1.setText(project.getTitle());
            rtv1.setTypeface(Typeface.DEFAULT_BOLD);
            rtv1.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));


            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            rtv1.setLayoutParams(lParams);

            ltaskproject.addView(rtv1);
        }

        //projectTitle.setText(project.getTitle());
        projectId = (project == null) ? 0 : project.getId();

        //Log.e("444444444", node.getName());
    }

    private static String colorChoice = "";

    @SuppressLint("ResourceAsColor")
    public static void showColorChoisedialog(final Context a, final Runnable refresh) {

        ColorPickerDialog.Builder builder =
                new ColorPickerDialog.Builder(a, R.style.Theme_AppCompat_Dialog)
                        //new ColorPickerDialog.Builder(a, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("ColorPicker Dialog")
                        .setPreferenceName("Test")

                        .setPositiveButton(
                                a.getResources().getString(R.string.apply),
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        //setLayoutColor(a, envelope);

                                        colorChoice = envelope.getHexCode();
                                        taskBgColor = "#" + colorChoice;
                                        if (colorChoice.isEmpty() || colorChoice.equals("")){

                                        } else {
                                            //iv.setColorFilter(Color.parseColor(taskBgColor));
                                            iv.setColorFilter(Utils.parseColor(taskBgColor, DEFAULT_TASKBG_COLOR));
                                        }
                                    }
                                })
                        .setNegativeButton(
                                a.getResources().getString(R.string.close),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
        ColorPickerView colorPickerView = builder.getColorPickerView();
        colorPickerView.setPureColor(R.color.black);
        colorPickerView.setBackgroundColor(R.color.white);
        //colorPickerView.setColo
        colorPickerView.setFlagView(new BubbleFlag(a, R.layout.layout_flag));
        builder.show();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void getParentTask(Task task) {

        if (task != null) {

            TaskTypes tasktype = MyApplication.getDatabase().taskTypesDao().getById(task.getTypeoftask().Value);

            lparenttasktype.removeAllViews();


            LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);

            lparenttasktype.setLayoutParams(lParams);
            //ltasktype.setGravity(Gravity.CENTER | Gravity.BOTTOM);

            ImageView iv = new ImageView(getActivity());
            //iv.setImageResource(R.drawable.pririty);
            iv.setImageResource(getImageResourceTaskType(TypeOfTask.from(tasktype.getId())));
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            //iv.setMinimumWidth(25);
            //iv.setMinimumHeight(25);
            //iv.getLayoutParams().width = 25;
            //iv.getLayoutParams().height = 25;
            //iv.setMaxWidth(25);
            //iv.setMaxHeight(25);
            //iv.setla


            //int color = Color.parseColor("#fafafa");
            //iv.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
            //iv.setColorFilter(color);
            //iv.set

            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
            iv.setLayoutParams(lParams);
            //iv.setForegroundGravity(Gravity.FILL);
            //iv.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            lparenttasktype.addView(iv);

            TextView tv1 = new TextView(getActivity());
            tv1.setText(task.getTypeoftask().name() + " - " + task.getId());
            TxtUtils.underlineTextView(tv1);
            tv1.setTextColor(Color.parseColor(tasktype.getColor()));
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setTypeface(Typeface.DEFAULT_BOLD);
            tv1.setPadding(15, 0, 0, 0);
            tv1.setTextSize(18.5f);
            lparenttasktype.addView(tv1);

            TextView tv2 = new TextView(getActivity());
            tv2.setText(task.getTitle());
            tv2.setGravity(Gravity.CENTER_HORIZONTAL);
            tv2.setTypeface(Typeface.DEFAULT_BOLD);
            tv2.setPadding(15, 0, 0, 0);
            tv2.setTextSize(18.5f);
            tv2.setTextColor(R.color.text_black_100);
            //tv2.setTypeface(Typeface.DEFAULT_BOLD);


            lparenttasktype.addView(tv2);

            parentTaskId = task.getId();
            parentTaskGuid = task.getGuid();
        }

        //taskTypeTitle.setText(tasktype.getTitle());

        //taskTypes.setId(tasktype.getId());
        //taskTypes = MyApplication.getDatabase().taskTypesDao().getById(tasktype.getId());
    }

    @Override
    public void getStatusTask(TaskStatus taskStatus2) {
        taskStatus = MyApplication.getDatabase().taskStatusDao().getById(taskStatus2.getId());
        //Log.e(taskStatus.getTitle().toUpperCase(), taskStatus.getTitle());
        /*rtvstatusTaskTitle.setCorner(20);
        rtvstatusTaskTitle.setPadding(20, 0, 20, 0);
        rtvstatusTaskTitle.setText(taskStatus.getTitle());
        rtvstatusTaskTitle.setTypeface(Typeface.DEFAULT_BOLD);
        rtvstatusTaskTitle.setTextSize(16);
        rtvstatusTaskTitle.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));
        rtvstatusTaskTitle.setBgColor(Color.parseColor(taskStatus.getColor()));*/

        //RoundTextView rtv1 = new RoundTextView(getActivity());
        rtvstatusTaskTitle.setTextAppearance(R.style.rtvTextView);

        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT2);
        rtvstatusTaskTitle.setLayoutParams(lParams);
        rtvstatusTaskTitle.setCorner(DEFAULT_RADIUS2);
        rtvstatusTaskTitle.setPadding(DEFAULT_RTVPAGGING, 0, DEFAULT_RTVPAGGING, 0);
        rtvstatusTaskTitle.setBgColor(Color.parseColor(taskStatus.getColor()));
        rtvstatusTaskTitle.setText(taskStatus.getTitle());
        //ltaskcontext.addView(rtv1, lParams);




    }
}
