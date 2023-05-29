package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executors;

import es.dmoral.toasty.Toasty;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.TasksAdapter2;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.listeners.DefaultListeners;
import ru.kau.mygtd2.utils.Keyboards;
import ru.kau.mygtd2.utils.Settings;

public class TasksFragment2 extends Fragment {

    private ImageView onSort;
    private ActionBar toolbar;

    private TextView tv1;
    private RecyclerView recyclerView;

    LinearLayoutCompat linearLayoutCompat;

    private TextInputEditText searchEditText;

    Handler handler;

    String txt = "%%";
    long tasksCount;

    TextView countTasks;

    TasksAdapter2 tasksAdapterall;

    Runnable hideKeyboard = new Runnable() {

        @Override
        public void run() {
            Keyboards.close(searchEditText);
            //Keyboards.hideNavigation(getActivity());
        }
    };

    Runnable sortAndSeach = new Runnable() {

        @Override
        public void run() {
            recyclerView.scrollToPosition(0);
            searchAndOrderAsync();
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.toolbar_menu4, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public void searchAndOrderAsync() {


        tasksAdapterall = new TasksAdapter2(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksOrderById(txt));
        //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getOverdueTasksWithoutSubtask(new Date().getTime()));
        //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksWithoutSubtask());
        bindAdapter(tasksAdapterall);
        recyclerView.setAdapter(tasksAdapterall);



        handler.post( new Runnable() {
            @Override
            public void run() {

                showTasksCount();
            }
        } );

        //booksCount = dataSource.getBooksCount();
        //showBookCount();

        //populate();

    }

    private final TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(final Editable s) {
            //AppState.get().searchQuery = s.toString();

        }

        @Override
        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
        }

        @Override
        public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

            //handler.removeCallbacks(sortAndSeach);
            txt = "%" + searchEditText.getText().toString().trim() + "%";



            handler.removeCallbacks(sortAndSeach);
            handler.removeCallbacks(hideKeyboard);
            if (s.toString().trim().length() == 0) {
                //handler.postDelayed(sortAndSeach, 750);
                handler.postDelayed(sortAndSeach, 1000);
                handler.postDelayed(hideKeyboard, 1000);
            } else {
                //handler.postDelayed(sortAndSeach, 2000);
                handler.postDelayed(sortAndSeach, 1000);
            }


            //recyclerView.scrollToPosition(0);
            searchAndOrderAsync();

        }

    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tasks_fragment, null);

        handler = new Handler();

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Задачи");
        setHasOptionsMenu(true);
        //toolbar.menu  onCreateOptionsMenu

        ImageView onSorting = (ImageView) rootView.findViewById(R.id.onSorting);

        onSort = (ImageView) rootView.findViewById(R.id.onSort);


        searchEditText = rootView.findViewById(R.id.filterLine);
        searchEditText.addTextChangedListener(filterTextWatcher);

        countTasks = (TextView) rootView.findViewById(R.id.countTasks);



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
                    tasksAdapterall = new TasksAdapter2(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksOrderById(txt));
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getOverdueTasksWithoutSubtask(new Date().getTime()));
                    //TasksAdapter tasksAdapter1 = new TasksAdapter(getActivity(), MyApplication.getDatabase().taskDao().getAllTasksWithoutSubtask());
                    bindAdapter(tasksAdapterall);
                    recyclerView.setAdapter(tasksAdapterall);
                    showTasksCount();
                    break;

            }

        }

        Handler handler = new Handler(getContext().getMainLooper());
        handler.post( new Runnable() {
            @Override
            public void run() {

                /*if (booksCount == 0){
                    outlinedTextField.setError("Книги не найдены");
                } else {
                    //outlinedTextField.setError(null);
                }*/
                showTasksCount();
            }
        } );


        return rootView;
    }

    public void bindAdapter(TasksAdapter2 tasksAdapter) {
        DefaultListeners.bindAdapter5(getActivity(), tasksAdapter);
    }

    public void showTasksCount() {
        //countBooks.setText("" + (recyclerView.getAdapter().getItemCount() - countTitles));
        tasksCount = MyApplication.getDatabase().taskDao().getCountTasks(txt);
        countTasks.setText("" + tasksCount);
    }

}
