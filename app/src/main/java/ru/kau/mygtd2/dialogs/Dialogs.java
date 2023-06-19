package ru.kau.mygtd2.dialogs;

import static ru.kau.mygtd2.utils.Const.DEFAULT_TAG_COLOR;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeListViewAdapter;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import es.dmoral.toasty.Toasty;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.BaseItemLayoutAdapter;
import ru.kau.mygtd2.adapters.ProjectTreeAdapter;
import ru.kau.mygtd2.adapters.TasksAdapter4;
import ru.kau.mygtd2.adapters.dialog.ProjectStatusAdapter;
import ru.kau.mygtd2.adapters.dialog.TagAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.DialogType;
import ru.kau.mygtd2.common.enums.TypeOfInfo;
import ru.kau.mygtd2.common.enums.TypeOfTask;
import ru.kau.mygtd2.db.dao.TagDaoAbs;
import ru.kau.mygtd2.interfaces.DialogCategoryOfTaskChoice;
import ru.kau.mygtd2.interfaces.DialogContextsChoice;
import ru.kau.mygtd2.interfaces.DialogDateBeginChoice;
import ru.kau.mygtd2.interfaces.DialogDateEndChoice;
import ru.kau.mygtd2.interfaces.DialogParentTaskChoice;
import ru.kau.mygtd2.interfaces.DialogPriorityChoice;
import ru.kau.mygtd2.interfaces.DialogProjectChoice;
import ru.kau.mygtd2.interfaces.DialogProjectStatusChoice;
import ru.kau.mygtd2.interfaces.DialogStatusOfInfoChoice;
import ru.kau.mygtd2.interfaces.DialogStatusTaskChoice;
import ru.kau.mygtd2.interfaces.DialogTagsChoice;
import ru.kau.mygtd2.interfaces.DialogTargetChoice;
import ru.kau.mygtd2.interfaces.DialogTypeOfInfoChoice;
import ru.kau.mygtd2.interfaces.DialogTypeOfTaskChoice;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.objects.Comment;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.InfoTypes;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Meeting;
import ru.kau.mygtd2.objects.Priority;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.ProjectStatus;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskCategory;
import ru.kau.mygtd2.objects.TaskStatus;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.BubbleFlag;
import ru.kau.mygtd2.utils.Keyboards;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;

//import android.widget.LinearLayout;

//import android.app.AlertDialog;

//import androidx.appcompat.app.AlertDialog;



//import static android.provider.Settings.System.getString;



//import android.app.Activity;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import ru.kau.mygtd.utils.AppState;

public class Dialogs {

    //private static ProjectListAdapter adapter;

    private static List<Node> projectsList = new ArrayList<Node>();
    private static TreeListViewAdapter projectsAdapter;
    //private static Node node;
    private static DialogProjectChoice callback;
    private static DialogTagsChoice callback2;
    private static DialogTargetChoice callback3;
    private static DialogPriorityChoice callback4;
    private static DialogContextsChoice callback5;
    private static DialogDateEndChoice callback6;
    private static DialogDateBeginChoice callback7;
    private static DialogTypeOfTaskChoice callback8;
    private static DialogProjectStatusChoice callback9;
    private static DialogParentTaskChoice callback10;
    private static DialogStatusTaskChoice callback11;
    private static DialogTypeOfInfoChoice callback12;
    private static DialogStatusOfInfoChoice callback13;
    private static DialogCategoryOfTaskChoice callback14;
    private static String colorChoice = "";
    BlockingQueue<Boolean> blockingQueue;

    static ListView list;
    static RecyclerView rvTopTags;
    static RecyclerView rvAllTags;

    static ListView lvTopTags;

    static List<Tag> tags;

    static List<Tag> lstTopTags;
    static List<Long> lslIdTop = new ArrayList<>();

    static TagAdapter tagsAdapter;
    static TagAdapter tagsAdapter2;

    static Set<Integer> checked;

    static Set<Integer> ch;
    static Set<Integer> checkedTopTags;

    static Iterator<Tag> iterator;

    static Iterator<Tag> iterLstTopTags;

    static ImageView iv;

    static SwitchMaterial cbIsArchive;

    static SwitchMaterial cbWhowArchive;
    static SwitchMaterial cbWhowTop;

    private static List<Project> projectList;

    //private ProjectListAdapter adapter;

    public static void choiseParentTaskDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choiseparenttask);
        View inflate = LayoutInflater.from(a).inflate(R.layout.parenttask_choice, null, false);

        callback10 = (DialogParentTaskChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);


        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.parentTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(a));

        List<Task> lstParentTask = MyApplication.getDatabase().taskDao().getAllTasks();
        TasksAdapter4 tasksAdapterall = new TasksAdapter4(a, lstParentTask);
        recyclerView.setAdapter(tasksAdapterall);
        /*
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                projectsAdapter.clickOnItem(position);
            }
        });
        */

        builder.setView(inflate);

        /*builder.setNeutralButton(R.string.add_project, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/

        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.choice, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*for (Node n: projectsList) {
                    if (n.isChecked()) {
                        Project project = MyApplication.getDatabase().projectDao().getProjectById(((Long)n.getId()).longValue());
                        callback.getProject(project);
                    }
                }*/
                callback10.getParentTask(MyApplication.getDatabase().taskDao().getById(TasksAdapter4.checkedID));
            }

        });

        //alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)


        AlertDialog create = builder.create();



        create.show();


    }

    public static void choiseProjectDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choiseproject).setIcon(R.drawable.folder);
        View inflate = LayoutInflater.from(a).inflate(R.layout.project_choice, null, false);

        callback = (DialogProjectChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);


        ListView list = (ListView) inflate.findViewById(R.id.listView1);


        projectList = MyApplication.getDatabase().projectDao().getAllProjectsByStatuses(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
        projectsList.clear();
        for(Project p: projectList){
            projectsList.add(new Node(p.getId(), p.getParentid(), p.getTitle()));
        }

        SwitchMaterial cbWhowActiveProjects = inflate.findViewById(R.id.cbWhowActiveProjects);
        cbWhowActiveProjects.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    projectList = MyApplication.getDatabase().projectDao().getAllProjectsByStatuses(new ArrayList<>(Arrays.asList(1)));
                    projectsList.clear();
                    for(Project p: projectList){
                        projectsList.add(new Node(p.getId(), p.getParentid(), p.getTitle()));
                    }
                } else {
                    projectList = MyApplication.getDatabase().projectDao().getAllProjectsByStatuses(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
                    projectsList.clear();
                    for(Project p: projectList){
                        projectsList.add(new Node(p.getId(), p.getParentid(), p.getTitle()));
                    }
                }
                projectsAdapter = new ProjectTreeAdapter(list, a, projectsList, 3, R.drawable.minus, R.drawable.plus);

                list.setAdapter(projectsAdapter);
            }
        });

        //String[] projectStatus = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};
        //Spinner spinner = inflate.findViewById(R.id.spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter(a, android.R.layout.simple_spinner_item, projectStatus);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);

        /*List<ProjectStatus> projectStatus = MyApplication.getDatabase().projectStatusDao().getAll();
        Spinner spinner = inflate.findViewById(R.id.spinner);
        ArrayAdapter<ProjectStatus> adapter = new ArrayAdapter(a, R.layout.target_item, projectStatus);
        adapter.setDropDownViewResource(R.layout.target_item);
        spinner.setAdapter(adapter);*/

        /*
        MultiSelectionSpinner.OnMultipleItemsSelectedListener selectedListener = new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices, MultiSelectionSpinner spinner) {

            }

            @Override
            public void selectedStrings(List<String> strings, MultiSelectionSpinner spinner) {

            }
        };
        */

        /*List<String> projectStatus = MyApplication.getDatabase().projectStatusDao().getAlllstString();
        //int[] projectStatusId = MyApplication.getDatabase().projectStatusDao().getAlllstInt();
        int[] projectStatusId = new int[projectStatus.size()];
        for (int i = 0; i < projectStatus.size(); i++){
            projectStatusId[i] = i;
        }*/
        /*MultiSelectionSpinner spinner = inflate.findViewById(R.id.spinner);
        spinner.setItems(projectStatus);
        spinner.setSelection(projectStatusId);

        spinner.setListener(selectedListener);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                //selection.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };*/
        //spinner.setOnItemSelectedListener(itemSelectedListener);

        Spinner spinner = inflate.findViewById(R.id.spinner);
        List<ProjectStatus> projectStatus = MyApplication.getDatabase().projectStatusDao().getAll();

        ProjectStatusAdapter psAdapter = new ProjectStatusAdapter(a, 0, projectStatus);
        spinner.setAdapter(psAdapter);



        projectsAdapter = new ProjectTreeAdapter(list, a, projectsList, 3, R.drawable.minus, R.drawable.plus);
        //adapter = new ProjectListAdapter(a, projectList);

        //projectsAdapter.set

        list.setAdapter(projectsAdapter);
        /*
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                projectsAdapter.clickOnItem(position);
            }
        });
        */

        builder.setView(inflate);

        builder.setNeutralButton(R.string.add_project, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.choice, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (Node n: projectsList) {
                    if (n.isChecked()) {
                        Project project = MyApplication.getDatabase().projectDao().getProjectById(((Long)n.getId()).longValue());
                        callback.getProject(project);
                    }
                }
            }

        });

        //alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)


        AlertDialog create = builder.create();



        create.show();

    }

    public static void addContextDialog(final Context a, final Runnable onRefresh, Contekst contekst) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.add_context);


        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_add_context, null, false);

        //RelativeLayout view1 = new RelativeLayout(a);

        EditText edit = (EditText) inflate.findViewById(R.id.editContextName);

        iv = (ImageView) inflate.findViewById(R.id.choiseColor);


        builder.setView(inflate);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorChoisedialog(a, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });



        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);
            }
        });

        builder.setPositiveButton(R.string.add, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);

                //notifyDataSetChanged();
                //notifyItemChanged(i);
            }
        });

        final AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        create.show();



        create.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //String text = edit.getText().toString().trim();

                Contekst contekst1 = new Contekst();
                contekst1.setTitle(edit.getText().toString());
                contekst1.setColor("#" + colorChoice);
                contekst1.setDescription(edit.getText().toString());
                MyApplication.getDatabase().contextDao().insert(contekst1);

                create.dismiss();
                //AppState.get().save(a);

            }
        });
    }

    public static void addCommentDialog(final Context a, Information information, Task task, Meeting meeting){
        addCommentDialog(a, information, task, meeting, null, false);
    }

    public static void addCommentDialog(final Context a, Information information, Task task, Meeting meeting, Comment editComment, boolean readOnly){
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);

        /*
        Bundle arguments = ((MainActivity)a).getSupportFragmentManager().getFragment()   getArguments();
        Project parentProject = null;

        if (arguments != null && arguments.containsKey("project")) {

            parentProject = (Project) arguments.getSerializable("project");

            parentProjectTitle = (TextView) rootView.findViewById(R.id.parentProject);
            parentProjectTitle.setText(parentProject.getTitle());
        }
        */


        //builder.setTitle((editComment == null) ? R.string.add_comment : R.string.edit_comment);
        builder.setTitle((readOnly) ? R.string.read_comment : (editComment == null) ? R.string.add_comment : R.string.edit_comment);

        Comment comment = new Comment();
        comment.setInfo_id((information == null)? 0L: information.getId());
        comment.setMeeting_id((meeting == null)? 0L: meeting.getId());
        comment.setTask_id((task == null)? 0L: task.getId());

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_add_comment, null, false);

        EditText edit = (EditText) inflate.findViewById(R.id.editComment);
        edit.setText((editComment == null) ? "" : editComment.getTitle());
        //edit.set
        if (readOnly){
            edit.setEnabled(false);
            edit.setClickable(false);
        }

        builder.setView(inflate);

        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);
            }
        });

        builder.setPositiveButton((readOnly) ? R.string.close : R.string.save, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);

                //notifyDataSetChanged();
                //notifyItemChanged(i);




            }
        });

        final AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        create.show();

        if (readOnly == false) {
            create.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //String text = edit.getText().toString().trim();
                    comment.setTitle(edit.getText().toString());
                    if (editComment == null) {
                        comment.setDateCreate(new Date());
                        if (information != null) {
                            comment.setInfo_id(information.getId());
                            comment.setInfoguid(information.getGuid());
                        }
                        if (meeting != null) {
                            comment.setMeeting_id(meeting.getId());
                        }
                        if (task != null) {
                            comment.setTask_id(task.getId());
                            comment.setTaskguid(task.getGuid());
                            comment.setInfoguid("");
                        }
                        comment.setDateCreateStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), comment.getDateCreate()));
                        try {
                            MyApplication.getDatabase().commentDao().insert(comment);
                            Toasty.success(a, a.getString(R.string.commentcreated), Toast.LENGTH_SHORT, true).show();
                        } catch (Exception e) {
                            Toasty.error(a, a.getString(R.string.commentcreatedexception), Toast.LENGTH_LONG, true).show();
                        }

                    } else {
                        comment.setId(editComment.getId());
                        comment.setInfo_id(editComment.getInfo_id());
                        comment.setMeeting_id(editComment.getMeeting_id());
                        comment.setTask_id(editComment.getTask_id());
                        comment.setDateCreate(editComment.getDateCreate());
                        comment.setDateCreateStr(editComment.getDateCreateStr());
                        MyApplication.getDatabase().commentDao().update(comment);
                        Toasty.success(a, a.getString(R.string.commentupdated), Toast.LENGTH_SHORT, true).show();
                    }
                    create.dismiss();


                }
            });
        }
    }

    public static void addTagsDialog(final Context a, final Runnable onRefresh, Tag tag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_add_tag, null, false);

        //RelativeLayout view1 = new RelativeLayout(a);

        //EditText edit = (EditText) inflate.findViewById(R.id.editTagName);
        TextInputEditText editTagName2 = inflate.findViewById(R.id.editTagName2);

        iv = (ImageView) inflate.findViewById(R.id.choiseColor);
        cbIsArchive = inflate.findViewById(R.id.cbIsArchive);


        if (tag != null){
            //edit.setText(tag.getTitle());
            editTagName2.setText(tag.getTitle());
            iv.setColorFilter(Utils.parseColor(tag.getColor(), DEFAULT_TAG_COLOR));
            cbIsArchive.setChecked(tag.getIsarchive() == 0 ? false : true);
            builder.setTitle(R.string.edit_tag);
        } else {
            builder.setTitle(R.string.add_tag);
        }

        builder.setView(inflate);

        //iv.setColorFilter(R.color.superblack);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorChoisedialog(a, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });






        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);
            }
        });

        builder.setPositiveButton(R.string.save, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);

                //notifyDataSetChanged();
                //notifyItemChanged(i);
                /*if (tag != null){
                    tag.setTitle(edit.getText().toString());
                    tag.setColor(iv.getSolidColor());
                }*/



            }
        });

        final AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        create.show();



        create.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //String text = edit.getText().toString().trim();

                if (tag == null){
                    Tag tag1 = new Tag();
                    //tag1.setTitle(edit.getText().toString());
                    tag1.setTitle(editTagName2.getText().toString());
                    if (colorChoice == null || colorChoice.equals("")){
                        try {
                            colorChoice = iv.getColorFilter().toString();
                        } catch (Exception e) {
                            colorChoice = "3F51B5";
                        }
                    }
                    tag1.setColor("#" + colorChoice);
                    //tag1.setDescription(edit.getText().toString());
                    tag1.setDescription(editTagName2.getText().toString());
                    tag1.setIsarchive(cbIsArchive.isChecked() ? 1 : 0);
                    MyApplication.getDatabase().tagDao().insert(tag1);
                } else {
                    //tag.setTitle(edit.getText().toString());
                    tag.setTitle(editTagName2.getText().toString());
                    if (colorChoice == null || colorChoice.equals("")){
                        colorChoice = tag.getColor();
                    }
                    tag.setColor(colorChoice);
                    //tag.setDescription(edit.getText().toString());
                    tag.setDescription(editTagName2.getText().toString());
                    tag.setIsarchive(cbIsArchive.isChecked() ? 1 : 0);
                    MyApplication.getDatabase().tagDao().update(tag);
                }

                //tags.clear();
                //tags.addAll(MyApplication.getDatabase().tagDao().getAll());
                //notifyDataSetChanged();

                /*

                */
                create.dismiss();


            }
        });
    }

    public static void addPriorDialog(final Context a, final Runnable onRefresh, Priority priority) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.add_priority);


        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_add_priority, null, false);

        //RelativeLayout view1 = new RelativeLayout(a);

        EditText edit = (EditText) inflate.findViewById(R.id.editPriorityName);

        iv = (ImageView) inflate.findViewById(R.id.choiseColor);


        builder.setView(inflate);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorChoisedialog(a, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });



        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);
            }
        });

        builder.setPositiveButton(R.string.add, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);

                //notifyDataSetChanged();
                //notifyItemChanged(i);
            }
        });

        final AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        create.show();



        create.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //String text = edit.getText().toString().trim();

                if (priority == null) {

                    Priority priority1 = new Priority();
                    priority1.setTitle(edit.getText().toString());
                    priority1.setColor("#" + colorChoice);
                    priority1.setDescription(edit.getText().toString());
                    MyApplication.getDatabase().priorityDao().insert(priority1);
                    /*
                    Tag333 tag = new Tag();
                    tag.setTitle(edit.getText().toString());
                    tag.setColor("#" + colorChoice);
                    tag.setDescription(edit.getText().toString());
                    MyApplication.getDatabase().tagDao().insert(tag);
                    */
                } else {
                    priority.setTitle(edit.getText().toString());
                    priority.setColor("#" + colorChoice);
                    priority.setDescription(edit.getText().toString());
                    MyApplication.getDatabase().priorityDao().update(priority);
                }

                create.dismiss();


            }
        });
    }

    public static void addTargetDialog(final Context a, final Runnable onRefresh, Target target) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.add_target);


        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_add_target, null, false);

        //RelativeLayout view1 = new RelativeLayout(a);

        EditText edit = (EditText) inflate.findViewById(R.id.editTargetName);

        iv = (ImageView) inflate.findViewById(R.id.choiseColor);


        builder.setView(inflate);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorChoisedialog(a, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });



        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);
            }
        });

        builder.setPositiveButton(R.string.add, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Keyboards.close(edit);

                //notifyDataSetChanged();
                //notifyItemChanged(i);
            }
        });

        final AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        create.show();



        create.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //String text = edit.getText().toString().trim();

                if (target == null) {

                    Target target2 = new Target();
                    target2.setTitle(edit.getText().toString());
                    target2.setColor("#" + colorChoice);
                    target2.setDescription(edit.getText().toString());
                    MyApplication.getDatabase().targetDao().insert(target2);
                } else {
                    target.setTitle(edit.getText().toString());
                    target.setColor("#" + colorChoice);
                    target.setDescription(edit.getText().toString());
                    MyApplication.getDatabase().targetDao().update(target);
                }
                create.dismiss();
                //AppState.get().save(a);

            }
        });
    }

    public static void showTagsDialog_123(final Context a, final Runnable refresh) {

        int height = a.getResources().getDimensionPixelSize(R.dimen.alertdialog_button_height);
        int width  = a.getResources().getDimensionPixelSize(R.dimen.alertdialog_button_width);
        new AlertDialog.Builder(a, R.style.YDialog)
                .setTitle("Greetings to you")
                .setMessage("Hello, this is just a message with several lines\nWith the best wishes, just me")
                .setPositiveButton("Positive", null)
                .setNegativeButton("Negative", null)
                .setNeutralButton("Neutral", null)

                .show().getButton(AlertDialog.BUTTON_POSITIVE).setHeight(height);//   setWidth(width);
    }

    @SuppressLint("ResourceAsColor")
    public static void showTagsDialogn(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        //final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(a, R.style.YDialog);
        //setDividerColor("#FF00FF")

        builder.setTitle(R.string.choisetags);

        callback2 = (DialogTagsChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_tags3, null, false);

        rvTopTags = inflate.findViewById(R.id.lstTopTags);
        rvTopTags.setLayoutManager(new LinearLayoutManager(a));


        rvAllTags = inflate.findViewById(R.id.lstAllTags);
        rvAllTags.setLayoutManager(new LinearLayoutManager(a));



        cbWhowArchive = inflate.findViewById(R.id.cbWhowArchive);
        cbWhowTop = inflate.findViewById(R.id.cbWhowTop);

        rvTopTags.setVisibility(cbWhowTop.isChecked() ? View.VISIBLE : View.GONE);

        cbWhowTop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rvTopTags.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                tags = cbWhowArchive.isChecked() ? MyApplication.getDatabase().tagDao().getAllSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>()) : MyApplication.getDatabase().tagDao().getNoArchiveSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>());
                //rvAllTags.notifyAll();
                tagsAdapter2 = new TagAdapter(a, tags);
                rvAllTags.setAdapter(tagsAdapter2);
            }
        });

        cbWhowArchive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags = MyApplication.getDatabase().tagDao().getAllSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>());

                } else {
                    tags = MyApplication.getDatabase().tagDao().getNoArchiveSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>());

                }
                checked = new HashSet<>();
                iterator = tags.iterator();
                while (iterator.hasNext()) {
                    if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                        iterator.remove();
                    }
                }
                //adapter.setItems(tags);
                //list.setAdapter(adapter);
                tagsAdapter = new TagAdapter(a, tags);
                rvAllTags.setAdapter(tagsAdapter);
                //rvAllTags.notify();


            }
        });

        lstTopTags = MyApplication.getDatabase().tagDao().getTopTagsSortByTitle(3);
        for(Tag t: lstTopTags){
            lslIdTop.add(t.getId());
        }

        tagsAdapter = new TagAdapter(a, lstTopTags);
        rvTopTags.setAdapter(tagsAdapter);

        tags = cbWhowArchive.isChecked() ? MyApplication.getDatabase().tagDao().getAllSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>()) : MyApplication.getDatabase().tagDao().getNoArchiveSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>());
        tagsAdapter2 = new TagAdapter(a, tags);
        rvAllTags.setAdapter(tagsAdapter2);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton(R.string.add_tag, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTagsDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        //tags.clear();
                        //tags.addAll(DbItemCreator.getTagDb().getListStringItems());
                        //adapter.notifyDataSetChanged();
                        //adapter.notifyAll();

                        tags.clear();
                        tags.addAll(MyApplication.getDatabase().tagDao().getAll());
                        //adapter.setItems(tags);
                        //adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });

        builder.setPositiveButton(R.string.choice, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                List<Tag> lsttags = new ArrayList<Tag>();
                ch = tagsAdapter.getChecked();

                int i = 0;
                for (Tag tag: tags){
                    //Tag tag1 =
                    if (ch.contains(i)) {
                        lsttags.add(tag);
                    }
                    i++;
                }

                ch = tagsAdapter2.getChecked();
                i = 0;
                for (Tag tag: tags){
                    //Tag tag1 =
                    if (ch.contains(i)) {
                        lsttags.add(tag);
                    }
                    i++;
                }

                callback2.getTags(lsttags);

            }

        });

        builder.create();
        builder.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        builder.show();



    }


    @SuppressLint("ResourceAsColor")
    public static void showTagsDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        //final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(a, R.style.YDialog);
        //setDividerColor("#FF00FF")

        builder.setTitle(R.string.choisetags);

        callback2 = (DialogTagsChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_tags2, null, false);

        lvTopTags = inflate.findViewById(R.id.lstTopTags);

        //list2 = inflate.findViewById(R.id.listView1);

        cbWhowArchive = inflate.findViewById(R.id.cbWhowArchive);

        lstTopTags = MyApplication.getDatabase().tagDao().getTopTagsSortByTitle(3);

        tags = cbWhowArchive.isChecked() ? MyApplication.getDatabase().tagDao().getAllSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>()) : MyApplication.getDatabase().tagDao().getNoArchiveSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>());

        /*iterator = tags.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }*/

        /*iterLstTopTags = lstTopTags.iterator();
        while (iterLstTopTags.hasNext()) {
            if (TxtUtils.isEmpty(iterLstTopTags.next().getTitle().trim())) {
                iterLstTopTags.remove();
            }
        }*/

        checkedTopTags = new HashSet<>();

        final BaseItemLayoutAdapter<Tag> adapterTopTags = new BaseItemLayoutAdapter<Tag>(a, R.layout.tag_item, lstTopTags) {


            @Override
            public void populateView(View layout, final int position, final Tag tagName) {

                ImageView iv = (ImageView) layout.findViewById(R.id.iconTag);
                CheckBox text = (CheckBox) layout.findViewById(R.id.tagName);

                text.setText(tagName.getTitle());
                try {
                    iv.setColorFilter(Color.parseColor(tagName.getColor()));
                } catch (Exception e) {
                    iv.setColorFilter(R.color.black);
                }
                text.setBackgroundColor(tagName.getIsarchive() == 1 ? Color.parseColor("#808080") : Color.parseColor("#FFFFFF"));
                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            checkedTopTags.add(position);
                        } else {
                            checkedTopTags.remove(position);
                        }
                    }
                });

                //text.setChecked(StringDB.contains(fileMeta.getTag(), tagName));
                //text.setChecked(StringDB.contains(book, tagName));
                //if (fileTags.contains(tagName)) {
                //text.setChecked(fileTags.contains(tagName));
                //}

                layout.findViewById(R.id.editTag).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        addTagsDialog(a, new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, tagName);
                    }
                });


                layout.findViewById(R.id.deleteTag).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        showDialog(a, DialogType.DELETE_TAG, new Runnable() {
                            @Override
                            public void run() {
                                TagDaoAbs.deleteTag(tagName);

                                tags.clear();
                                tags.addAll(MyApplication.getDatabase().tagDao().getAll());
                                notifyDataSetChanged();
                            }
                        });


                    }
                });

            }
        };


        checked = new HashSet<>();
        tagsAdapter = new TagAdapter( a, lstTopTags);

        final BaseItemLayoutAdapter<Tag> adapter = new BaseItemLayoutAdapter<Tag>(a, R.layout.tag_item, tags) {


            @Override
            public void populateView(View layout, final int position, final Tag tagName) {

                ImageView iv = (ImageView) layout.findViewById(R.id.iconTag);
                CheckBox text = (CheckBox) layout.findViewById(R.id.tagName);

                text.setText(tagName.getTitle());
                try {
                    iv.setColorFilter(Color.parseColor(tagName.getColor()));
                } catch (Exception e) {
                    iv.setColorFilter(R.color.black);
                }
                text.setBackgroundColor(tagName.getIsarchive() == 1 ? Color.parseColor("#808080") : Color.parseColor("#FFFFFF"));
                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                    }
                });

                //text.setChecked(StringDB.contains(fileMeta.getTag(), tagName));
                //text.setChecked(StringDB.contains(book, tagName));
                //if (fileTags.contains(tagName)) {
                //text.setChecked(fileTags.contains(tagName));
                //}

                layout.findViewById(R.id.editTag).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        addTagsDialog(a, new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, tagName);
                    }
                });


                layout.findViewById(R.id.deleteTag).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        showDialog(a, DialogType.DELETE_TAG, new Runnable() {
                            @Override
                            public void run() {
                                TagDaoAbs.deleteTag(tagName);

                                tags.clear();
                                tags.addAll(MyApplication.getDatabase().tagDao().getAll());
                                notifyDataSetChanged();
                            }
                        });


                    }
                });

            }
        };

        cbWhowArchive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags = MyApplication.getDatabase().tagDao().getAllSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>());

                } else {
                    tags = MyApplication.getDatabase().tagDao().getNoArchiveSortByTitle(cbWhowTop.isChecked() ? lslIdTop : new ArrayList<>());

                }
                checked = new HashSet<>();
                iterator = tags.iterator();
                while (iterator.hasNext()) {
                    if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                        iterator.remove();
                    }
                }
                adapter.setItems(tags);
                list.setAdapter(adapter);

            }
        });

        /*addtag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTagsDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        //tags.clear();
                        //tags.addAll(DbItemCreator.getTagDb().getListStringItems());
                        //adapter.notifyDataSetChanged();
                        //adapter.notifyAll();

                        tags.clear();
                        tags.addAll(MyApplication.getDatabase().tagDao().getAll());
                        adapter.setItems(tags);
                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        lvTopTags.setAdapter(adapterTopTags);
        //list2.setAdapter(tagsAdapter);


        builder.setView(inflate);

        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton(R.string.add_tag, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTagsDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        //tags.clear();
                        //tags.addAll(DbItemCreator.getTagDb().getListStringItems());
                        //adapter.notifyDataSetChanged();
                        //adapter.notifyAll();

                        tags.clear();
                        tags.addAll(MyApplication.getDatabase().tagDao().getAll());
                        adapter.setItems(tags);
                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });

        builder.setPositiveButton(R.string.choice, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                List<Tag> lsttags = new ArrayList<Tag>();

                int i = 0;
                for (Tag tag: tags){
                    //Tag tag1 =
                    if (checked.contains(i)) {
                        lsttags.add(tag);
                    }
                    i++;
                }

                callback2.getTags(lsttags);

                /*
                String res = "";
                for (int i : checked) {
                    res = StringDB.add(res, tags.get(i));

                }
                LOG.d("showTagsDialog", res);
                fileMeta.setTag(res);
                AppDB.get().update(fileMeta);
                if (refresh != null) {
                    refresh.run();
                }
                TempHolder.listHash++;
                */

                /*
                book.setMyTagList(DbItemCreator.getTagDb().getListItemsByBook(book.getId()));
                if (refresh != null) {
                    refresh.run();
                }
                TempHolder.listHash++;
                */
            }

        });

        builder.create();
        builder.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                //Keyboards.close((Activity) a);
                //Keyboards.hideNavigation((Activity) a);

            }
        });
        builder.show();

    }

    public static void showTargetsDialog(final Context a, final Runnable refresh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choisetarget);



        callback3 = (DialogTargetChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_target, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        //final TextView addtarget = (TextView) inflate.findViewById(R.id.addTarget);

        final List<Target> targets = MyApplication.getDatabase().targetDao().getAll();




        Iterator<Target> iterator = targets.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<Target> adapter = new BaseItemLayoutAdapter<Target>(a, R.layout.target_item, targets) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final Target target) {

                CheckBox text = (CheckBox) layout.findViewById(R.id.targetName);
                text.setText(target.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.targetName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });

                layout.findViewById(R.id.deleteTarget).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        /*addtarget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTargetDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        targets.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.cancel, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton(R.string.add_target, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTargetDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        targets.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });

        builder.setPositiveButton(R.string.choice, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (Target trg: targets){

                    if (checked.contains(i)) {
                        callback3.getTarget(trg);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();
    }

    public static void showStatusTaskDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choisestatustask);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_statusoftask, null, false);

        callback11 = (DialogStatusTaskChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        final ListView list = inflate.findViewById(R.id.listView1);
        //final TextView addprior = (TextView) inflate.findViewById(R.id.addPriority);

        final List<TaskStatus> taskStatus = MyApplication.getDatabase().taskStatusDao().getAll();

        Iterator<TaskStatus> iterator = taskStatus.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<TaskStatus> adapter = new BaseItemLayoutAdapter<TaskStatus>(a, R.layout.typeoftask_item, taskStatus) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final TaskStatus taskStatus) {

                ImageView iv = (ImageView) layout.findViewById(R.id.iconTypeOfTask);

                //iv.setImageResource(Utils.getImageResourceTaskType(TypeOfTask.from(taskStatus.getId())));

                CheckBox text = (CheckBox) layout.findViewById(R.id.typeOfTaskName);
                text.setText(taskStatus.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.typeOfTaskName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });


            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (TaskStatus taskStatus1: taskStatus){

                    if (checked.contains(i)) {
                        callback11.getStatusTask(taskStatus1);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();

    }

    public static void showStatusInfoDialog(final Context a, final Runnable refresh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choicestatusinfo);

        callback13 = (DialogStatusOfInfoChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_statusofinfo, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        //final TextView addprior = (TextView) inflate.findViewById(R.id.addPriority);

        final List<InfoStatus> infoStatuses = MyApplication.getDatabase().infoStatusDao().getAll();




        Iterator<InfoStatus> iterator = infoStatuses.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<InfoStatus> adapter = new BaseItemLayoutAdapter<InfoStatus>(a, R.layout.statusofinfo_item, infoStatuses) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final InfoStatus infoStatus) {



                CheckBox text = (CheckBox) layout.findViewById(R.id.typeOfTaskName);
                text.setText(infoStatus.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.typeOfTaskName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });


            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        /*addprior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPriorDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        priorities.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (InfoStatus infoStatus1: infoStatuses){

                    if (checked.contains(i)) {
                        callback13.getStatusOfInfo(infoStatus1);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();
    }

    public static void showTypesInfoDialog(final Context a, final Runnable refresh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choisetypeinfo);

        callback12 = (DialogTypeOfInfoChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_typeoftask, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        //final TextView addprior = (TextView) inflate.findViewById(R.id.addPriority);

        final List<InfoTypes> infoTypes = MyApplication.getDatabase().infoTypesDao().getAll();




        Iterator<InfoTypes> iterator = infoTypes.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<InfoTypes> adapter = new BaseItemLayoutAdapter<InfoTypes>(a, R.layout.typeoftask_item, infoTypes) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final InfoTypes infoTypes) {

                ImageView iv = (ImageView) layout.findViewById(R.id.iconTypeOfTask);

                iv.setImageResource(Utils.getImageResourceInfoType(TypeOfInfo.from(infoTypes.getId())));

                CheckBox text = (CheckBox) layout.findViewById(R.id.typeOfTaskName);
                text.setText(infoTypes.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.typeOfTaskName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });


            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        /*addprior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPriorDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        priorities.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (InfoTypes infoTypes1: infoTypes){

                    if (checked.contains(i)) {
                        callback12.getTypeOfInfo(infoTypes1);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();
    }

    public static void showCategoriesTaskDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choisecategorytask);

        callback14 = (DialogCategoryOfTaskChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_taskcategory, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        //final TextView addprior = (TextView) inflate.findViewById(R.id.addPriority);

        final List<TaskCategory> taskCategories = MyApplication.getDatabase().taskCategoryDao().getAll();




        Iterator<TaskCategory> iterator = taskCategories.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<TaskCategory> adapter = new BaseItemLayoutAdapter<TaskCategory>(a, R.layout.taskcategory_item, taskCategories) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final TaskCategory taskCategory) {

                ImageView iv = (ImageView) layout.findViewById(R.id.iconTypeOfTask);

                iv.setImageResource(R.drawable.bookmark);
                iv.setColorFilter(Color.parseColor(taskCategory.getColor()));

                CheckBox text = (CheckBox) layout.findViewById(R.id.taskCategoryName);
                text.setText(taskCategory.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.taskCategoryName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });


            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        /*addprior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPriorDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        priorities.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (TaskCategory taskCategory: taskCategories){

                    if (checked.contains(i)) {
                        callback14.getTaskCategory(taskCategory);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();


    }

    public static void showTypesTaskDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choisetypetask);

        callback8 = (DialogTypeOfTaskChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_typeoftask, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        //final TextView addprior = (TextView) inflate.findViewById(R.id.addPriority);

        final List<TaskTypes> taskTypes = MyApplication.getDatabase().taskTypesDao().getAll();




        Iterator<TaskTypes> iterator = taskTypes.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<TaskTypes> adapter = new BaseItemLayoutAdapter<TaskTypes>(a, R.layout.typeoftask_item, taskTypes) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final TaskTypes taskTypes) {

                ImageView iv = (ImageView) layout.findViewById(R.id.iconTypeOfTask);

                iv.setImageResource(Utils.getImageResourceTaskType(TypeOfTask.from(taskTypes.getId())));

                CheckBox text = (CheckBox) layout.findViewById(R.id.typeOfTaskName);
                text.setText(taskTypes.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.typeOfTaskName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });


            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        /*addprior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPriorDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        priorities.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (TaskTypes taskTypes1: taskTypes){

                    if (checked.contains(i)) {
                        callback8.getTypeOfTask(taskTypes1);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();


    }

    public static void showProjectStatusDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);

        callback9 = (DialogProjectStatusChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);
        builder.setTitle(R.string.choisepriority);

        //callback4 = (DialogPriorityChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_projectstatus, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        //final TextView addprior = (TextView) inflate.findViewById(R.id.addPriority);

        final List<ProjectStatus> projectStatuses = MyApplication.getDatabase().projectStatusDao().getAll();




        Iterator<ProjectStatus> iterator = projectStatuses.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<ProjectStatus> adapter = new BaseItemLayoutAdapter<ProjectStatus>(a, R.layout.target_item, projectStatuses) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final ProjectStatus projectStatus) {

                CheckBox text = (CheckBox) layout.findViewById(R.id.targetName);
                text.setText(projectStatus.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.targetName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });

                layout.findViewById(R.id.deleteTarget).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        /*addprior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPriorDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        priorities.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (ProjectStatus projectStatus: projectStatuses){

                    if (checked.contains(i)) {
                        callback9.getProjectStatus(projectStatus);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();


    }



    public static void showPriorityDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choisepriority);

        callback4 = (DialogPriorityChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_priority, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        final TextView addprior = (TextView) inflate.findViewById(R.id.addPriority);

        final List<Priority> priorities = MyApplication.getDatabase().priorityDao().getAll();




        Iterator<Priority> iterator = priorities.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<Priority> adapter = new BaseItemLayoutAdapter<Priority>(a, R.layout.target_item, priorities) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final Priority priority) {

                CheckBox text = (CheckBox) layout.findViewById(R.id.targetName);
                text.setText(priority.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.targetName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });

                layout.findViewById(R.id.deleteTarget).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };

        /*addprior.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPriorDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        priorities.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/

        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();

                int i = 0;
                for (Priority prior: priorities){

                    if (checked.contains(i)) {
                        callback4.getPriority(prior);
                    }
                    i++;
                }




            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();


    }

    public static void showContextsDialog(final Context a, final Runnable refresh) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle(R.string.choisecontext);

        callback5 = (DialogContextsChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_contexts, null, false);

        final ListView list = (ListView) inflate.findViewById(R.id.listView1);
        //final TextView addcontext = (TextView) inflate.findViewById(R.id.addContext);

        final List<Contekst> conteksts = MyApplication.getDatabase().contextDao().getAll();




        Iterator<Contekst> iterator = conteksts.iterator();
        while (iterator.hasNext()) {
            if (TxtUtils.isEmpty(iterator.next().getTitle().trim())) {
                iterator.remove();
            }
        }

        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<Contekst> adapter = new BaseItemLayoutAdapter<Contekst>(a, R.layout.context_item, conteksts) {

            private int selectedPosition = -1;

            @Override
            public void populateView(View layout, final int position, final Contekst context) {

                CheckBox text = (CheckBox) layout.findViewById(R.id.contextName);
                text.setText(context.getTitle());
                //text.setTag();

                text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        /*
                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayout itemLayout = (LinearLayout)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.targetName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        */
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });

                layout.findViewById(R.id.deleteContext).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            private void itemCheckChanged(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }



        };


        /*addcontext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addContextDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        conteksts.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });*/


        list.setAdapter(adapter);

        builder.setView(inflate);

        builder.setNegativeButton(R.string.close, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton(R.string.add_tag, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                addContextDialog(a, new Runnable() {

                    @Override
                    public void run() {
                        conteksts.clear();

                        adapter.notifyDataSetChanged();
                    }
                }, null);
            }
        });

        builder.setPositiveButton(R.string.apply, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Target target = new Target();
                List<Contekst> lstcontexts = new ArrayList<Contekst>();

                int i = 0;
                for (Contekst contekst : conteksts){

                    if (checked.contains(i)) {
                        lstcontexts.add(contekst);
                    }
                    i++;
                }

                callback5.getContexts(lstcontexts);


            }

        });

        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        create.show();


    }

    public static void showDateBeginDialog(final Context a, final Runnable refresh) {

        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        callback7 = (DialogDateBeginChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);

        DatePickerDialog datePickerDialog = new DatePickerDialog(a, R.style.CalendarDatePickerDialog,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        callback7.getDateBegin(dayOfMonth + "." + (monthOfYear + 1) + "." + year,
                                Utils.getDateOfParam(dayOfMonth, monthOfYear, year, 0, 0, 0));
                    }



                }, mYear, mMonth, mDay);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, a.getString(R.string.apply), datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, a.getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback7.getDateBegin("", 0L);
            }
        });

        datePickerDialog.show();
    }

    public static void showDateEndDialog(final Context a, final Runnable refresh) {

        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        callback6 = (DialogDateEndChoice) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);

        DatePickerDialog datePickerDialog = new DatePickerDialog(a, R.style.CalendarDatePickerDialog,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        callback6.getDateEnd(dayOfMonth + "." + (monthOfYear + 1) + "." + year,
                                Utils.getDateOfParam(dayOfMonth, monthOfYear, year, 23, 59, 59));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, a.getString(R.string.choice), datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, a.getString(R.string.cancel), datePickerDialog);
        datePickerDialog.show();
    }

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
                                        if (colorChoice.isEmpty() || colorChoice.equals("")){

                                        } else {
                                            iv.setColorFilter(Color.parseColor("#" + colorChoice));
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
        colorPickerView.setFlagView(new BubbleFlag(a, R.layout.layout_flag));
        builder.show();
    }



    private static void setLayoutColor(final Context a, ColorEnvelope envelope) {
        TextView textView = ((MainActivity)a).findViewById(R.id.textView);
        textView.setText("#" + envelope.getHexCode());

        //AlphaTileView alphaTileView = ((MainActivity)a).findViewById(R.id.alphaTileView);
        //alphaTileView.setPaintColor(envelope.getColor());
    }



    public static void showDialog(final Context c, DialogType dialogType, final Runnable action) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.YDialog);
        String title = "";
        String message = "";

        switch (dialogType){
            case DELETE_TAG:
                title = "Удаление тэга";
                message = "Вы деййствительно хотите удалить тэг?";

        }


        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int id) {
                if (action != null) {
                    action.run();

                    //return true;
                }

            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int id) {

            }
        });
        builder.show();
    }

    /*
    public boolean confirm(final Context a, final String title, final String message){

        ((MainActivity)a).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(a)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                blockingQueue.add(true);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                blockingQueue.add(false);
                            }
                        })
                        .show();
            }
        });

        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }
    */

    public static void showEditDialog2(final Activity c, String title, String init,
                                       final ResultResponse<String> onresult) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(c);
        builder.setTitle(title);
        final EditText input = new EditText(c);
        input.setSingleLine();

        if (init != null) {
            if (!init.endsWith("/")) {
                init += "/";
            }
            input.setText(init);
            input.setSelection(init.length());
        }
        builder.setView(input);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onresult.onResultRecive(input.getText().toString());
            }
        });


        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Keyboards.close(input);
                Keyboards.hideNavigation(c);
            }
        });
        alertDialog.show();
    }


}
