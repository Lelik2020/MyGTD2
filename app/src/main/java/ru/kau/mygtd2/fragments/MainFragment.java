package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.kau.mygtd2.BuildConfig;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.MainAdapter;
import ru.kau.mygtd2.adapters.MainAdapter2;
import ru.kau.mygtd2.adapters.MainAdapter3;
import ru.kau.mygtd2.adapters.MainAdapter4;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.interfaces.ClickListener;
import ru.kau.mygtd2.utils.LOG;

// https://caster.io/lessons/adding-a-new-fragment-with-a-recyclerview

public class MainFragment extends Fragment implements ClickListener {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private MainAdapter mainAdapter;
    private MainAdapter2 mainAdapter2;
    private MainAdapter3 mainAdapter3;
    private MainAdapter4 mainAdapter4;
    //private Toolbar toolbar;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_fragment, null);


        //ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        LOG.d("BuildConfig.BUILD_TYPE", BuildConfig.BUILD_TYPE);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_recyclerview);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(7);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        recyclerView.setBackground(drawable);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainAdapter = new MainAdapter(getActivity(), MyApplication.getDatabase().categoryDao().getAllByGroup(1));
        mainAdapter.setClickListener(this);

        recyclerView.setAdapter(mainAdapter);

        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.main_recyclerview2);

        drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);
        recyclerView2.setBackground(drawable);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainAdapter2 = new MainAdapter2(getActivity(), MyApplication.getDatabase().categoryDao().getAllByGroup(2));
        mainAdapter2.setClickListener(this);

        recyclerView2.setAdapter(mainAdapter2);

        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.main_recyclerview3);

        drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);
        recyclerView3.setBackground(drawable);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainAdapter3 = new MainAdapter3(getActivity(), MyApplication.getDatabase().categoryDao().getAllByGroup(3));
        mainAdapter3.setClickListener(this);

        recyclerView3.setAdapter(mainAdapter3);

        recyclerView4 = (RecyclerView) rootView.findViewById(R.id.main_recyclerview4);

        drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);
        recyclerView4.setBackground(drawable);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainAdapter4 = new MainAdapter4(getActivity(), MyApplication.getDatabase().categoryDao().getAllByGroup(4));
        mainAdapter4.setClickListener(this);

        recyclerView4.setAdapter(mainAdapter4);

        ((MainActivity)getActivity()).setVisibleFloatingMenuControls(View.VISIBLE);

        setHasOptionsMenu(true);



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.toolbar_menu2, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    //onOptionsItemSelected

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.add_info) {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("AddInformationFragment").replace(R.id.frame_container,new AddInformationFragment()).commit();
            //Intent intent = new Intent(this, SearchUsersActivity.class);
            //startActivity(intent);
        }

        if(item.getItemId() == R.id.add_task) {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,new AddTaskFragment()).commit();
            //Intent intent = new Intent(this, SearchUsersActivity.class);
            //startActivity(intent);
        }

        if(item.getItemId() == R.id.add_project){
            AddProjectFragment addProjectFragment = new AddProjectFragment();
            //addProjectFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("AddProjectFragment").replace(R.id.frame_container,addProjectFragment).commit();
        }

        if(item.getItemId() == R.id.settings){
            SettingsFragment settingsFragment = new SettingsFragment();
            //addProjectFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("SettingsFragment").replace(R.id.frame_container,settingsFragment).commit();
        }
        //return true;
        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
    */

    @Override
    public void itemClicked(View view, int position, int grp) {

        Bundle bundle;

        if (grp == 1) {
            if (position == 0) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
                Fragment fragment;
                fragment = new InformationsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("InformationsFragment").replace(R.id.frame_container, fragment, "InformationsFragment").commit();
            }

            if (position == 1) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, fragment, "TasksFragment").commit();
            }

            if (position == 2) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, fragment, "TasksFragment").commit();
            }

            if (position == 3) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
                /*Fragment fragment;
                fragment = new SprProjectFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("SprProjectFragment").replace(R.id.frame_container, fragment, "SprProjectFragment").commit();*/

                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, fragment, "TasksFragment").commit();

            }

            if (position == 4) {
                //Intent intent = new Intent(getActivity(), CalendarActivity.class);
                //startActivity(intent);
                /*((MainActivity) getActivity()).setVisibleFloatingMenuControls(View.GONE);
                Fragment fragment;
                fragment = new CalendarFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("CalendarFragment").replace(R.id.frame_container, fragment, "CalendarFragment").commit();*/

                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, fragment, "TasksFragment").commit();

            }

            if (position == 5) {
                //Intent intent = new Intent(getActivity(), CalendarActivity.class);
                //startActivity(intent);
                /*((MainActivity) getActivity()).setVisibleFloatingMenuControls(View.GONE);
                Fragment fragment;
                fragment = new CalendarFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("CalendarFragment").replace(R.id.frame_container, fragment, "CalendarFragment").commit();*/

                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, fragment, "TasksFragment").commit();

            }

            if (position == 6) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");

                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, fragment, "TasksFragment").commit();
            }

            if (position == 7) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, fragment, "TasksFragment").commit();
            }

            if (position == 8) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
                bundle = new Bundle();
                bundle.putInt("menunumber", position);

                Fragment fragment;
                fragment = new TasksFragment2();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment2").replace(R.id.frame_container, fragment, "TasksFragment2").commit();
            }
        }

        if (grp == 2) {
            if (position == 0) {
                //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
                Fragment fragment;
                fragment = new SprProjectFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("SprProjectFragment").replace(R.id.frame_container, fragment, "SprProjectFragment").commit();
            }

            if (position == 1){
                Fragment fragment;
                fragment = new TargetFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TargetFragment").replace(R.id.frame_container, fragment, "TargetFragment").commit();
            }

            if (position == 2){
                Fragment fragment;
                fragment = new TagsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TagsFragment").replace(R.id.frame_container, fragment, "TagsFragment").commit();
            }

            if (position == 3){
                Fragment fragment;
                fragment = new ContextsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("ContextsFragment").replace(R.id.frame_container, fragment, "ContextsFragment").commit();
            }

        }

        if (grp == 3) {
            if (position == 1){
                Fragment fragment;
                fragment = new MainBackupsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("BackupsFragment").replace(R.id.frame_container, fragment, "BackupsFragment").commit();
            }

            if (position == 2){
                Fragment fragment;
                fragment = new SyncsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("SyncsFragment").replace(R.id.frame_container, fragment, "SyncsFragment").commit();
            }

            /*if (position == 3){
                Fragment fragment;
                fragment = new StatisticFragment2();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("StatisticFragment").replace(R.id.frame_container, fragment, "StatisticFragment").commit();
            }*/
        }

        if (grp == 4) {
            if (position == 1){
                Fragment fragment;
                fragment = new AboutDevicesFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("AboutDevicesFragment").replace(R.id.frame_container, fragment, "AboutDevicesFragment").commit();
            }
        }

    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
