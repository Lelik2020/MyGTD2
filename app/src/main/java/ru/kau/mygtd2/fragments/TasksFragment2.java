package ru.kau.mygtd2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.TasksAdapter2;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.listeners.DefaultListeners;

public class TasksFragment2 extends Fragment {

    private ImageView onSort;
    private ActionBar toolbar;

    private TextView tv1;
    private RecyclerView recyclerView;

    LinearLayoutCompat linearLayoutCompat;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tasks_fragment, null);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Задачи");
        setHasOptionsMenu(true);
        //toolbar.menu  onCreateOptionsMenu

        ImageView onSorting = (ImageView) rootView.findViewById(R.id.onSorting);

        onSort = (ImageView) rootView.findViewById(R.id.onSort);

        TasksAdapter2 tasksAdapterall;

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey("menunumber")) {
            int menuitem = arguments.getInt("menunumber");
            TasksAdapter2 tasksAdapter;

            switch (menuitem) {

                case 8:

                    tv1 = (TextView) rootView.findViewById(R.id.tv_overdue_task);
                    tv1.setText(getResources().getString(R.string.alltasks3));
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_overdue);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //Log.e("TIME: ", String.valueOf(new Date().getTime()));
                    tasksAdapterall = new TasksAdapter2(getActivity(), MyApplication.getDatabase().taskDao().getAllTasks());
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getOverdueTasksWithoutSubtask(new Date().getTime()));
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksWithoutSubtask());
                    bindAdapter(tasksAdapterall);
                    recyclerView.setAdapter(tasksAdapterall);
                    break;

                case 11:

                    linearLayoutCompat = rootView.findViewById(R.id.containerOut);
                    linearLayoutCompat.setVisibility(View.GONE);
                    linearLayoutCompat = rootView.findViewById(R.id.containerToday);
                    linearLayoutCompat.setVisibility(View.GONE);

                    linearLayoutCompat = rootView.findViewById(R.id.containerTomorrow);
                    linearLayoutCompat.setVisibility(View.GONE);
                    linearLayoutCompat = rootView.findViewById(R.id.containerNextSevenDays);
                    linearLayoutCompat.setVisibility(View.GONE);

                    linearLayoutCompat = rootView.findViewById(R.id.containerAfterWeek);
                    linearLayoutCompat.setVisibility(View.GONE);
                    linearLayoutCompat = rootView.findViewById(R.id.containerAfterTwoWeek);
                    linearLayoutCompat.setVisibility(View.GONE);
                    linearLayoutCompat = rootView.findViewById(R.id.containerInFuture);
                    linearLayoutCompat.setVisibility(View.GONE);
                    linearLayoutCompat = rootView.findViewById(R.id.containerClosed);
                    linearLayoutCompat.setVisibility(View.GONE);

                    tv1 = (TextView) rootView.findViewById(R.id.tv_noenddate_tasks);
                    tv1.setText(getResources().getString(R.string.alltasks3));
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.tasks_recyclerview_noenddate);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //Log.e("TIME: ", String.valueOf(new Date().getTime()));
                    tasksAdapterall = new TasksAdapter2(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksOrderById("%А%"));
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getOverdueTasksWithoutSubtask(new Date().getTime()));
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksWithoutSubtask());
                    bindAdapter(tasksAdapterall);
                    recyclerView.setAdapter(tasksAdapterall);
                    break;

            }

        }


        return rootView;
    }

    public void bindAdapter(TasksAdapter2 tasksAdapter) {
        DefaultListeners.bindAdapter5(getActivity(), tasksAdapter);
    }



}
