package ru.kau.mygtd2.fragments;

import static android.view.View.GONE;
import static ru.kau.mygtd2.enums.TypeDateTasks.CLOSED;
import static ru.kau.mygtd2.enums.TypeDateTasks.OVERDUE;
import static ru.kau.mygtd2.enums.TypeDateTasks.TODOAFTERTWOWEEK;
import static ru.kau.mygtd2.enums.TypeDateTasks.TODOAFTERWEEK;
import static ru.kau.mygtd2.enums.TypeDateTasks.TODOINFUTURE;
import static ru.kau.mygtd2.enums.TypeDateTasks.TODONEXTSEVENDAYS;
import static ru.kau.mygtd2.enums.TypeDateTasks.TODONOENDDATE;
import static ru.kau.mygtd2.enums.TypeDateTasks.TODOTODAY;
import static ru.kau.mygtd2.enums.TypeDateTasks.TODOTOMORROW;
import static ru.kau.mygtd2.utils.Const.DEFAULT_COLLAPSE_ICON2;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMILSECONDS;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.DEFAULT_EXPANDED_ICON2;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSCOMPLETED;
import static ru.kau.mygtd2.utils.Const.lstALLFAVOURITE;
import static ru.kau.mygtd2.utils.Const.lstALLPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstALLPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstALLSTATUS;
import static ru.kau.mygtd2.utils.Const.lstALLTARGETSID;
import static ru.kau.mygtd2.utils.Const.lstALLTASKCATEGORIESID;
import static ru.kau.mygtd2.utils.Const.lstALLTASKTYPESID;
import static ru.kau.mygtd2.utils.Const.lstHIPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstONLYFAVOURITE;
import static ru.kau.mygtd2.utils.Const.lstPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstStatus;
import static ru.kau.mygtd2.utils.Const.lstTARGETSSID;
import static ru.kau.mygtd2.utils.Const.lstWITHOUTPROJECT;
import static ru.kau.mygtd2.utils.Const.lstWITHOUTTARGET;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.TasksAdapter;
import ru.kau.mygtd2.adapters.TasksAdapter2;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.listeners.DefaultListeners;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.SQLCondition;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskCategory;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.Utils;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;
    private RecyclerView recyclerView7;
    private RecyclerView recyclerView8;
    private RecyclerView recyclerView9;

    private Date currDate = new Date(Utils.getCurrentApplicationDateAndTime());

    private ImageView expandedIcon1;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;


    private ImageView onSort;
    private ActionBar toolbar;
    //private List<Task> lstTask;

    private SearchView searchView;

    private TextInputEditText txtcurrdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tasks_fragment, null);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Задачи");
        setHasOptionsMenu(true);
        //toolbar.menu  onCreateOptionsMenu

        txtcurrdate = rootView.findViewById(R.id.txtcurrdate);

        txtcurrdate.setText(Utils.dateToString(DEFAULT_DATEFORMAT, new Date(Utils.getCurrentApplicationDate())));

        ImageView onSorting = (ImageView) rootView.findViewById(R.id.onSorting);

        onSort = (ImageView) rootView.findViewById(R.id.onSort);


        //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
        //TasksAdapter tasksAdapter = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasks());

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey("all")) {



        }

        if (arguments != null && arguments.containsKey("menunumber")) {
            int menuitem = arguments.getInt("menunumber");
            TasksAdapter tasksAdapter;
            switch (menuitem) {

                case 1:

                    fullView(rootView);

                    break;

                case 2:

                    fullView(rootView);

                    tv3.setVisibility(View.GONE);
                    recyclerView3.setVisibility(View.GONE);

                    tv4.setVisibility(View.GONE);
                    recyclerView4.setVisibility(View.GONE);

                    tv5.setVisibility(View.GONE);
                    recyclerView5.setVisibility(View.GONE);

                    tv6.setVisibility(View.GONE);
                    recyclerView6.setVisibility(View.GONE);

                    tv7.setVisibility(View.GONE);
                    recyclerView7.setVisibility(View.GONE);

                    tv9.setVisibility(View.GONE);
                    recyclerView9.setVisibility(View.GONE);

                    break;

                case 3:

                    fullView(rootView, lstALLFAVOURITE, lstHIPRIORITY);

                    break;

                case 4:
                    fullView(rootView, lstONLYFAVOURITE, lstALLPRIORITY);
                    break;

                case 5:
                    fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY);

                    tv1.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);

                    tv2.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.GONE);

                    tv3.setVisibility(View.GONE);
                    recyclerView3.setVisibility(View.GONE);

                    tv4.setVisibility(View.GONE);
                    recyclerView4.setVisibility(View.GONE);

                    tv5.setVisibility(View.GONE);
                    recyclerView5.setVisibility(View.GONE);

                    tv6.setVisibility(View.GONE);
                    recyclerView6.setVisibility(View.GONE);

                    tv7.setVisibility(View.GONE);
                    recyclerView7.setVisibility(View.GONE);

                    tv8.setVisibility(View.GONE);
                    recyclerView8.setVisibility(View.GONE);

                    //tv9.setVisibility(View.GONE);
                    //recyclerView9.setVisibility(View.GONE);

                case 6:

                    fullView(rootView);

                    break;

                case 7:

                    tv1 = (TextView) rootView.findViewById(R.id.tv_overdue_task);
                    tv1.setText(getResources().getString(R.string.alltasks3));
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_overdue);
                    expandedIcon1 = rootView.findViewById(R.id.expandedIcon1);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //Log.e("TIME: ", String.valueOf(new Date().getTime()));
                    TasksAdapter2 tasksAdapterall = new TasksAdapter2(getActivity(), MyApplication.getDatabase().taskDao().getAllTasks());
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getOverdueTasksWithoutSubtask(new Date().getTime()));
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksWithoutSubtask());
                    bindAdapter(tasksAdapterall);
                    recyclerView.setAdapter(tasksAdapterall);
                    break;

                case 1000:

                    fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstPROJECTSID, lstALLSTATUS);

                    break;

                case 1001:

                    fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstWITHOUTPROJECT, lstALLSTATUS);

                    break;

                case 2000:

                    //fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstALLPROJECTSID, lstALLSTATUS, lstTARGETSSID, null);

                    fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstALLPROJECTSID, lstALLSTATUS, lstTARGETSSID, null, lstALLTASKCATEGORIESID, lstALLTASKTYPESID);
                    break;

                case 2001:

                    fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstALLPROJECTSID, lstALLSTATUS, lstWITHOUTTARGET, null, lstALLTASKCATEGORIESID, lstALLTASKTYPESID);

                    break;

            }

            //tasksAdapter = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksWithoutSubtask());
        }

        if (arguments != null && arguments.containsKey("project")) {
            TasksAdapter tasksAdapter;
            Project project = (Project) arguments.getSerializable("project");

            tasksAdapter = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksOfProject(project.getId()));
            fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, new ArrayList<Integer>(){
                {
                    //add(0);
                    add((int) project.getId());
                }
            });


        }

        if (arguments != null && arguments.containsKey("taskcategory")) {
            //TasksAdapter tasksAdapter;
            TaskCategory taskCategory = (TaskCategory) arguments.getSerializable("taskcategory");

            //tasksAdapter = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksOfProject(project.getId()));
            fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstALLPROJECTSID, lstALLSTATUS, lstALLTARGETSID,
                    new ArrayList<Integer>(){
                {
                    //add(0);
                    add((int) taskCategory.getId());
                }
            }, null
                    , lstALLTASKTYPESID
            );


        }

        if (arguments != null && arguments.containsKey("target")) {
            //TasksAdapter tasksAdapter;
            Target target = (Target) arguments.getSerializable("target");

            //tasksAdapter = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksOfProject(project.getId()));
            fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstALLPROJECTSID, lstALLSTATUS, new ArrayList<Integer>(){
                {
                    //add(0);
                    add((int) target.getId());
                }
            }, null
                    , lstALLTASKCATEGORIESID, lstALLTASKTYPESID);


        }

        if (arguments != null && arguments.containsKey("tag")) {
            //TasksAdapter tasksAdapter;
            Tag tag = (Tag) arguments.getSerializable("tag");

            //tasksAdapter = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksOfProject(project.getId()));
            fullView(rootView, lstALLFAVOURITE, lstALLPRIORITY, lstALLPROJECTSID, lstALLSTATUS, lstALLTARGETSID, new ArrayList<Integer>(){
                {
                    //add(0);
                    add((int) tag.getId());
                }
            }
                    , lstALLTASKCATEGORIESID, lstALLTASKTYPESID);


        }


        return rootView;
    }


    public void bindAdapter(TasksAdapter2 tasksAdapter) {
        DefaultListeners.bindAdapter2(getActivity(), tasksAdapter);
    }

    public void fullView(View rootView) {

        fullView(rootView, lstALLFAVOURITE);
    }

    public void fullView(View rootView, List<Integer> favour) {
        fullView(rootView, favour, lstALLPRIORITY);
    }

    public void fullView(View rootView, List<Integer> favour, List<Integer> lstPriority) {
        fullView(rootView, favour, lstPriority, lstALLPROJECTSID);
    }

    public void fullView(View rootView, List<Integer> favour, List<Integer> lstPriority, List<Integer> lstProjects) {
        fullView(rootView, favour, lstPriority, lstProjects, lstALLSTATUS);
    }

    public void fullView(View rootView, List<Integer> favour, List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> listStatus) {
        fullView(rootView, favour, lstPriority, lstProjects, listStatus, lstALLTARGETSID, null, lstALLTASKCATEGORIESID, lstALLTASKTYPESID);
    }

    public void fullView(View rootView, List<Integer> favour, List<Integer> lstPriority, List<Integer> lstProjects, List<Integer> listStatus
                        , List<Integer> lstTaskCategories) {
        fullView(rootView, favour, lstPriority, lstProjects, listStatus, lstALLTARGETSID, null, lstTaskCategories, lstALLTASKTYPESID);
    }

    public void fullView(View rootView, List<Integer> favour, List<Integer> lstPriority,
                         List<Integer> lstProjects, List<Integer> listStatus,
                         List<Integer> lstTargets, List<Integer> lstTags
                         , List<Integer> lstTaskCategories
                         , List<Integer> lstTaskTypes
                         ) {

        tv1 = (TextView) rootView.findViewById(R.id.tv_overdue_task);
        //tv1.setText(getResources().getString(R.string.overduetask));


        recyclerView = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_overdue);
        expandedIcon1 = rootView.findViewById(R.id.expandedIcon1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Log.e("TIME: ", String.valueOf(new Date().getTime()));
        expandedIcon1.setImageResource(DEFAULT_EXPANDED_ICON2);
        expandedIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getVisibility() != GONE) {
                    recyclerView.setVisibility(GONE);
                    expandedIcon1.setImageResource(DEFAULT_COLLAPSE_ICON2);
                    return;
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    expandedIcon1.setImageResource(DEFAULT_EXPANDED_ICON2);
                    return;
                }

                /*if (recyclerView.getVisibility() != GONE) {
                    expandedIcon1.setImageResource(DEFAULT_COLLAPSE_ICON);
                    return;
                } else {
                    expandedIcon1.setImageResource(DEFAULT_EXPANDED_ICON);
                    return;
                }*/
            }
        });

        List<Task> lstTask;
        if (lstTags == null) {
            //lstTask = MyApplication.getDatabase().taskDao().getOverdueTasks(Utils.atStartOfDay(new Date()).getTime(), lstStatus, favour, lstPriority, lstProjects, lstTargets);
            lstTask = MyApplication.getDatabase().taskDao().getOverdueTasks(Utils.atStartOfDay(currDate).getTime(), lstStatus, favour, lstPriority, lstProjects, lstTargets
                                                                            , lstTaskCategories, lstTaskTypes);
        } else {
            /*String sqltext = HIERARCHY_TASKS +
                    " AND (dateEnd < ?) " +
                    " AND isFavourite IN (" + Utils.getStringByArrayInteger(favour) + ") " +
                    " AND status IN (" + Utils.getStringByArrayInteger(lstStatus) + ") " +
                    " AND priority_id IN (" + Utils.getStringByArrayInteger(lstPriority) + ") " +
                    " AND project_id IN (" + Utils.getStringByArrayInteger(lstProjects) + ") " +
                    " AND target_id IN (" + Utils.getStringByArrayInteger(lstTargets) + ") " +
                    " AND id IN (SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + "))";*/
            //Object[] args = new Object[]{Utils.atStartOfDay(new Date()).getTime()};
            Object[] args = new Object[]{Utils.atStartOfDay(currDate).getTime()};
            //args.add(Utils.atStartOfDay(new Date()).getTime());
            //args.add(Utils.getStringByArrayInteger(lstStatus));
            //args.add(Utils.getStringByArrayInteger(favour));
            //args.add(Utils.getStringByArrayInteger(lstPriority));
            //args.add(Utils.getStringByArrayInteger(lstProjects));
            //args.add(Utils.getStringByArrayInteger(lstTargets));
            //args.add(Utils.getStringByArrayInteger(lstTags));

            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    add(new SQLCondition("dateEnd", "<", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "IN", Utils.getStringByArrayInteger(lstStatus)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("category", "IN", Utils.getStringByArrayInteger(lstTaskCategories)));
                    add(new SQLCondition("typeoftask", "IN", Utils.getStringByArrayInteger(lstTaskTypes)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, null, args);
        }
        tv1.setText(Utils.getTextHeading(this, OVERDUE, lstTask.size()));
        TasksAdapter2 tasksAdapter1 = new TasksAdapter2(getActivity(), lstTask);
        //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getOverdueTasksWithoutSubtask(new Date().getTime()));
        //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksWithoutSubtask());
        bindAdapter(tasksAdapter1);
        recyclerView.setAdapter(tasksAdapter1);

        if (tasksAdapter1.getItemCount() == 0){
            tv1.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            //expandedIcon1
        }


        tv2 = (TextView) rootView.findViewById(R.id.tv_today_tasks);
        //tv2.setText(getResources().getString(R.string.maketoday) + " (" + Utils.dateToString(DEFAULT_DATEFORMAT, new Date()) + ")");
        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_today);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);

        //List<Task> lstTask;
        if (lstTags == null) {
            //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
            //lstTask = MyApplication.getDatabase().taskDao().getTasksBetweenDates(Utils.getStartOfDay(new Date()).getTime(), Utils.getEndOfDay(new Date()).getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
            lstTask = MyApplication.getDatabase().taskDao().getTasksBetweenDates(Utils.getStartOfDay(currDate).getTime(), Utils.getEndOfDay(currDate).getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);

        } else {
            /*String sqltext = HIERARCHY_TASKS +
                    " AND (dateEnd = ? OR dateEndStr = ?) " +
                    " AND isFavourite IN (" + Utils.getStringByArrayInteger(favour) + ") " +
                    " AND status NOT IN (" + Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED) + ") " +
                    " AND priority_id IN (" + Utils.getStringByArrayInteger(lstPriority) + ") " +
                    " AND project_id IN (" + Utils.getStringByArrayInteger(lstProjects) + ") " +
                    " AND target_id IN (" + Utils.getStringByArrayInteger(lstTargets) + ") " +
                    " AND id IN (SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + "))";*/
            //Object[] args = new Object[]{Utils.atEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.atEndOfDay(new Date()))};
            Object[] args = new Object[]{Utils.atEndOfDay(currDate).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.atEndOfDay(currDate))};
            //args.add(Utils.getEndOfDay(new Date()).getTime());
            //args.add(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())));
            //args.add(Utils.getStringByArrayInteger(lstStatus));
            //args.add(Utils.getStringByArrayInteger(favour));
            //args.add(Utils.getStringByArrayInteger(lstPriority));
            //args.add(Utils.getStringByArrayInteger(lstProjects));
            //args.add(Utils.getStringByArrayInteger(lstTargets));
            //args.add(Utils.getStringByArrayInteger(lstTags));
            /*Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                }
            });*/

            //@Query(HIERARCHY_TASKS + " AND (dateEnd = :dateEnd OR dateEndStr = :dateEndStr) AND status NOT IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            //        "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")

            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    //add(new SQLCondition("dateEnd", "<", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, "(dateEnd = ? OR dateEndStr = ?)", args);

            //lstTask = MyApplication.getDatabase().taskDao().getTasks(new SimpleSQLiteQuery(sqltext, args.toArray()));
        }


        tv2.setText(Utils.getTextHeading(this, TODOTODAY, lstTask.size()));
        TasksAdapter2 tasksAdapter2 = new TasksAdapter2(getActivity(), lstTask);
        bindAdapter(tasksAdapter2);
        recyclerView2.setAdapter(tasksAdapter2);

        if (tasksAdapter2.getItemCount() == 0){
            tv2.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.GONE);
        }

        // ------------------------------------------------------------------------------------

        //Calendar calendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();//   currDate;
        calendar.setTime(currDate);
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        // ------------------------------------------------------------------------------------

        tv3 = (TextView) rootView.findViewById(R.id.tv_tomorrow_tasks);
        //tv3.setText(getResources().getString(R.string.maketomorrow) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), tomorrow) + ")");
        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_tomorrow);

        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(Utils.getEndOfDay(tomorrow).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(tomorrow)), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        if (lstTags == null) {
            //Log.d("TIME1", String.valueOf(Utils.getStartOfDay(tomorrow).getTime()));
            //Log.d("TIME2", String.valueOf(Utils.getEndOfDay(tomorrow).getTime()));
            //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(Utils.getEndOfDay(tomorrow).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(tomorrow)), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
            lstTask = MyApplication.getDatabase().taskDao().getTasksBetweenDates(Utils.getStartOfDay(tomorrow).getTime(), Utils.getEndOfDay(tomorrow).getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        } else {
            /*String sqltext = HIERARCHY_TASKS +
                    " AND (dateEnd = ? OR dateEndStr = ?) " +
                    " AND isFavourite IN (" + Utils.getStringByArrayInteger(favour) + ") " +
                    " AND status NOT IN (" + Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED) + ") " +
                    " AND priority_id IN (" + Utils.getStringByArrayInteger(lstPriority) + ") " +
                    " AND project_id IN (" + Utils.getStringByArrayInteger(lstProjects) + ") " +
                    " AND target_id IN (" + Utils.getStringByArrayInteger(lstTargets) + ") " +
                    " AND id IN (SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + "))";*/
            Object[] args = new Object[]{Utils.atEndOfDay(tomorrow).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.atEndOfDay(tomorrow))};
            //args.add(Utils.getEndOfDay(tomorrow).getTime());
            //args.add(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(tomorrow)));
            //args.add(Utils.getStringByArrayInteger(lstStatus));
            //args.add(Utils.getStringByArrayInteger(favour));
            //args.add(Utils.getStringByArrayInteger(lstPriority));
            //args.add(Utils.getStringByArrayInteger(lstProjects));
            //args.add(Utils.getStringByArrayInteger(lstTargets));
            //args.add(Utils.getStringByArrayInteger(lstTags));
            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, "(dateEnd = ? OR dateEndStr = ?)", args);

            //lstTask = MyApplication.getDatabase().taskDao().getTasks(new SimpleSQLiteQuery(sqltext, args.toArray()));
        }


        TasksAdapter2 tasksAdapter3 = new TasksAdapter2(getActivity(), lstTask);
        tv3.setText(Utils.getTextHeading(this, TODOTOMORROW, lstTask.size()));
        bindAdapter(tasksAdapter3);
        recyclerView3.setAdapter(tasksAdapter3);

        if (tasksAdapter3.getItemCount() == 0){
            tv3.setVisibility(View.GONE);
            recyclerView3.setVisibility(View.GONE);
        }

        // ------------------------------------------------------------------------------------

        calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date d1 = Utils.atStartOfDay(calendar.getTime());

        //calendar = Calendar.getInstance();
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 5);
        Date d2 = Utils.atEndOfDay(calendar.getTime());

        tv4 = (TextView) rootView.findViewById(R.id.tv_nextsevendays_tasks);
        //tv4.setText(getResources().getString(R.string.makenextweek) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + "-" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d2) + ")");

        recyclerView4 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_nextsevendays);

        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));
        //TasksAdapter tasksAdapter4 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), d2.getTime()));

        //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), d2.getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        if (lstTags == null) {
            //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), d2.getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
            lstTask = MyApplication.getDatabase().taskDao().getTasksBetweenDates(Utils.getStartOfDay(d1).getTime(), Utils.getEndOfDay(d2).getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        } else {

            Object[] args = new Object[]{d1.getTime(), d2.getTime()};
            //args.add(d1.getTime());
            //args.add(d2.getTime());

            Log.e("d1.getTime()", String.valueOf(d1.getTime()) + "   " + Utils.dateToString(d1));
            Log.e("d2.getTime()", String.valueOf(d2.getTime()) + "   " + Utils.dateToString(d2));

            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    add(new SQLCondition("dateEnd", ">=", "?"));
                    add(new SQLCondition("dateEnd", "<=", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, null, args);
        }


        TasksAdapter2 tasksAdapter4 = new TasksAdapter2(getActivity(), lstTask);
        tv4.setText(Utils.getTextHeading(this, TODONEXTSEVENDAYS, lstTask.size()));
        bindAdapter(tasksAdapter4);
        recyclerView4.setAdapter(tasksAdapter4);

        if (tasksAdapter4.getItemCount() == 0){
            tv4.setVisibility(View.GONE);
            recyclerView4.setVisibility(View.GONE);
        }

        // ------------------------------------------------------------------------------------

        calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 8);
        d1 = Utils.atStartOfDay(calendar.getTime());

        calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 14);
        d2 = Utils.atEndOfDay(calendar.getTime());

        tv5 = (TextView) rootView.findViewById(R.id.tv_afterweek_tasks);
        //tv5.setText(getResources().getString(R.string.makeafterweek) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + "-" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d2) + ")");

        recyclerView5 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_afterweek);

        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));

        //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), d2.getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);

        if (lstTags == null) {
            //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), d2.getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
            Log.d("TIME1", String.valueOf(Utils.getStartOfDay(d1).getTime()) + "   " + Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, d1));
            Log.d("TIME2", String.valueOf(Utils.getEndOfDay(d1).getTime()) + "   " + Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, d2));
            lstTask = MyApplication.getDatabase().taskDao().getTasksBetweenDates(Utils.getStartOfDay(d1).getTime(), Utils.getEndOfDay(d2).getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        } else {

            Object[] args = new Object[]{d1.getTime(), d2.getTime()};
            //args.add(String.valueOf(d1.getTime()));
            //args.add(String.valueOf(d2.getTime()));
            //args.add(d1.getTime());
            //args.add(d2.getTime());

            Log.e("d1.getTime()", String.valueOf(d1.getTime()) + "   " + Utils.dateToString(d1));
            Log.e("d2.getTime()", String.valueOf(d2.getTime()) + "   " + Utils.dateToString(d2));

            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    add(new SQLCondition("dateEnd", ">", "?"));
                    add(new SQLCondition("dateEnd", "<", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, null, args);
        }

        TasksAdapter2 tasksAdapter5 = new TasksAdapter2(getActivity(), lstTask);
        tv5.setText(Utils.getTextHeading(this, TODOAFTERWEEK, lstTask.size()));

        bindAdapter(tasksAdapter5);
        recyclerView5.setAdapter(tasksAdapter5);

        if (tasksAdapter5.getItemCount() == 0){
            tv5.setVisibility(View.GONE);
            recyclerView5.setVisibility(View.GONE);
        }

        // ------------------------------------------------------------------------------------

        calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 15);
        d1 = Utils.atStartOfDay(calendar.getTime());

        calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 30);
        d2 = Utils.atEndOfDay(calendar.getTime());

        tv6 = (TextView) rootView.findViewById(R.id.tv_aftertwoweek_tasks);
        //tv6.setText(getResources().getString(R.string.makeaftertwoweek) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + "-" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d2) + ")");

        recyclerView6 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_aftertwoweek);

        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));

        //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), d2.getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);

        if (lstTags == null) {
            //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), d2.getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
            Log.d("TIME1", String.valueOf(Utils.getStartOfDay(d1).getTime()) + "   " + Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, d1));
            Log.d("TIME2", String.valueOf(Utils.getEndOfDay(d2).getTime()) + "   " + Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, d2));
            lstTask = MyApplication.getDatabase().taskDao().getTasksBetweenDates(Utils.getStartOfDay(d1).getTime(), Utils.getEndOfDay(d2).getTime(), LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        } else {

            Object[] args = new Object[]{d1.getTime(), d2.getTime()};
            //args.add(d1.getTime());
            //args.add(d2.getTime());



            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    add(new SQLCondition("dateEnd", ">", "?"));
                    add(new SQLCondition("dateEnd", "<", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, null, args);
        }


        TasksAdapter2 tasksAdapter6 = new TasksAdapter2(getActivity(), lstTask);
        tv6.setText(Utils.getTextHeading(this, TODOAFTERTWOWEEK, lstTask.size()));

        bindAdapter(tasksAdapter6);
        recyclerView6.setAdapter(tasksAdapter6);

        if (tasksAdapter6.getItemCount() == 0){
            tv6.setVisibility(View.GONE);
            recyclerView6.setVisibility(View.GONE);
        }

        // ------------------------------------------------------------------------------------

        calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        //Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 31);
        d1 = Utils.atStartOfDay(calendar.getTime());



        tv7 = (TextView) rootView.findViewById(R.id.tv_infuture_tasks);
        //tv7.setText(getResources().getString(R.string.makeinfuture) + " (после " + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + ")");

        recyclerView7 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_infuture);

        recyclerView7.setLayoutManager(new LinearLayoutManager(getActivity()));

        //lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), LSTSTATUSCOMPLETED, lstPriority, lstProjects, lstTargets);

        if (lstTags == null) {
            lstTask = MyApplication.getDatabase().taskDao().getTasksByDate(d1.getTime(), LSTSTATUSCOMPLETED, lstPriority, lstProjects, lstTargets);
        } else {

            Object[] args= new Object[]{d1.getTime()};
            //args.add(d1.getTime());
            //args.add(d2.getTime());

            //@Query(HIERARCHY_TASKS + " /*and (parenttask_id == null or parenttask_id = 0)*/ AND (dateEnd >= :dateEnd1) AND status NOT IN (:lstStatus) " +
            //       "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")

            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    //add(new SQLCondition("dateEnd", "<", "?"));
                    add(new SQLCondition("dateEnd", ">=", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, null, args);
        }


        TasksAdapter2 tasksAdapter7 = new TasksAdapter2(getActivity(), lstTask);
        tv7.setText(Utils.getTextHeading(this, TODOINFUTURE, lstTask.size()));
        bindAdapter(tasksAdapter7);
        recyclerView7.setAdapter(tasksAdapter7);

        if (tasksAdapter7.getItemCount() == 0){
            tv7.setVisibility(View.GONE);
            recyclerView7.setVisibility(View.GONE);
        }

        // -------------------------------------------------------------------------------------------


        tv8 = (TextView) rootView.findViewById(R.id.tv_noenddate_tasks);
        //tv8.setText(getResources().getString(R.string.makenoenddate));

        recyclerView8 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_noenddate);

        recyclerView8.setLayoutManager(new LinearLayoutManager(getActivity()));

        //lstTask = MyApplication.getDatabase().taskDao().getTasksNoDateEnd(LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);

        if (lstTags == null) {
            lstTask = MyApplication.getDatabase().taskDao().getTasksNoDateEnd(LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        } else {

            Object[] args = new Object[]{};
            //args.add(d1.getTime());
            //args.add(d2.getTime());

            //@Query(HIERARCHY_TASKS + " and (dateEnd > :dateEnd1) and (dateEnd < :dateEnd2) " +
            //            "AND status NOT IN (:lstStatus) AND isFavourite IN (:lstFavour) AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) " +
            //            "AND target_id IN (:lstTargets)")

            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    //add(new SQLCondition("dateEnd", ">", "?"));
                    //add(new SQLCondition("dateEnd", "<", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "NOT IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, "(dateEnd IS NULL) and ((dateEndStr is null) or (dateEndStr = ''))", args);
        }


        TasksAdapter2 tasksAdapter8 = new TasksAdapter2(getActivity(), lstTask);
        //TasksAdapter tasksAdapter8 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getTasksWithoutSubtaskNoDateEnd());
        tv8.setText(Utils.getTextHeading(this, TODONOENDDATE, lstTask.size()));
        bindAdapter(tasksAdapter8);
        recyclerView8.setAdapter(tasksAdapter8);

        if (tasksAdapter8.getItemCount() == 0){
            tv8.setVisibility(View.GONE);
            recyclerView8.setVisibility(View.GONE);
        }

        // -------------------------------------------------------------------------------------------


        tv9 = (TextView) rootView.findViewById(R.id.tv_closed_tasks);
        //tv9.setText(getResources().getString(R.string.closedtasks));

        recyclerView9 = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_closed);

        recyclerView9.setLayoutManager(new LinearLayoutManager(getActivity()));
        //lstTask = MyApplication.getDatabase().taskDao().getAllClosedTasks(LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);

        if (lstTags == null) {
            lstTask = MyApplication.getDatabase().taskDao().getAllClosedTasks(LSTSTATUSCOMPLETED, favour, lstPriority, lstProjects, lstTargets);
        } else {

            Object[] args = new Object[]{};
            //args.add(d1.getTime());
            //args.add(d2.getTime());

            //@Query(HIERARCHY_TASKS + " and status IN (:lstStatus) AND isFavourite IN (:lstFavour) " +
            //            "AND priority_id IN (:lstPriority) AND project_id IN (:lstProjects) AND target_id IN (:lstTargets)")

            lstTask = Utils.getListTasksBySQL(new ArrayList<SQLCondition>(){
                {
                    //add(new SQLCondition("dateEnd", ">", "?"));
                    //add(new SQLCondition("dateEnd", "<", "?"));
                    add(new SQLCondition("isFavourite", "IN", Utils.getStringByArrayInteger(favour)));
                    add(new SQLCondition("status", "IN", Utils.getStringByArrayInteger(LSTSTATUSCOMPLETED)));
                    add(new SQLCondition("priority_id", "IN", Utils.getStringByArrayInteger(lstPriority)));
                    add(new SQLCondition("project_id", "IN", Utils.getStringByArrayInteger(lstProjects)));
                    add(new SQLCondition("target_id", "IN", Utils.getStringByArrayInteger(lstTargets)));
                    add(new SQLCondition("id", "IN", "SELECT idtask FROM tasktags WHERE idtag IN (" + Utils.getStringByArrayInteger(lstTags) + ")"));
                }
            }, null, args);
        }


        TasksAdapter2 tasksAdapter9 = new TasksAdapter2(getActivity(), lstTask);
        //TasksAdapter tasksAdapter8 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getTasksWithoutSubtaskNoDateEnd());
        tv9.setText(Utils.getTextHeading(this, CLOSED, lstTask.size()));
        bindAdapter(tasksAdapter9);
        recyclerView9.setAdapter(tasksAdapter9);

        if (tasksAdapter9.getItemCount() == 0){
            tv9.setVisibility(View.GONE);
            recyclerView9.setVisibility(View.GONE);
        }

    }

    public void fullView2(View rootView, List<Integer> favour, List<Integer> lstPriority, List<Integer> lstProjects,
                          List<Integer> listStatus, List<Integer> lstTargets) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.toolbar_menu4, menu);


        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //mAdapter.getFilter().filter(query);
                return false;
            }
        });
        //return true;

        //super.onCreateOptionsMenu(menu,inflater);
    }


    /*
    @Override
    public void onBackPressed() {

        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }

    }

     */
}
