package ru.kau.mygtd2.fragments;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.MainAdapter;
import ru.kau.mygtd2.adapters.MainAdapter2;
import ru.kau.mygtd2.adapters.MainAdapter3;
import ru.kau.mygtd2.adapters.MainAdapter4;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.interfaces.ClickListener;
import ru.kau.mygtd2.interfaces.DialogDateBeginChoice;
import ru.kau.mygtd2.utils.Utils;

// https://caster.io/lessons/adding-a-new-fragment-with-a-recyclerview

public class MainFragment extends Fragment implements ClickListener, DialogDateBeginChoice {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private MainAdapter mainAdapter;
    private MainAdapter2 mainAdapter2;
    private MainAdapter3 mainAdapter3;
    private MainAdapter4 mainAdapter4;

    private TextInputEditText txtcurrdate;

    private TextInputLayout outlinedTextField;
    //private Toolbar toolbar;

    private ImageView btnSetCurrDate;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_fragment, null);

        //LOG.d("BuildConfig.BUILD_TYPE", BuildConfig.BUILD_TYPE);

        btnSetCurrDate = rootView.findViewById(R.id.btnSetCurrDate);

        btnSetCurrDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        outlinedTextField = rootView.findViewById(R.id.outlinedTextField);

        //int color = Color.parseColor("#FF11AA");
        int color = getResources().getColor(R.color.colorPrimary);
//Color from hex string
        int color2 = Color.parseColor("#3F51B5");

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_focused}, // focused
                new int[] { android.R.attr.state_hovered}, // hovered
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] { android.R.attr.hint}, // enabled
                new int[] { }  //
        };

        int[] colors = new int[] {
                color,
                color,
                color,
                color2,
                color2
        };

        ColorStateList myColorList = new ColorStateList(states, colors);

        outlinedTextField.setBoxStrokeColorStateList(myColorList);
        outlinedTextField.setDefaultHintTextColor(myColorList);

        txtcurrdate = rootView.findViewById(R.id.txtcurrdate);

        getDateBegin(Utils.dateToString(DEFAULT_DATEFORMAT, new Date(Utils.getCurrentApplicationDate())), Utils.getCurrentApplicationDate());

        /*txtcurrdate.setText(Utils.dateToString(DEFAULT_DATEFORMAT, new Date(Utils.getCurrentApplicationDate())));
        txtcurrdate.setTextColor(color2);

        if (Utils.getStartOfDay(new Date(Utils.getCurrentApplicationDate())).getTime() < Utils.getStartOfDay(new Date()).getTime()){
            //outlinedTextField.setBoxStrokeColor(Color.parseColor("#FF0000"));
            color2 = Color.parseColor("#FF0000");
            color = getResources().getColor(R.color.green);
            color2 = getResources().getColor(R.color.green);
            colors = new int[] {
                    color,
                    color,
                    color,
                    color2,
                    color2
            };

            myColorList = new ColorStateList(states, colors);


            outlinedTextField.setBoxStrokeColorStateList(myColorList);
            outlinedTextField.setDefaultHintTextColor(myColorList);
            txtcurrdate.setTextColor(color2);
        }
        if (Utils.getStartOfDay(new Date(Utils.getCurrentApplicationDate())).getTime() > Utils.getStartOfDay(new Date()).getTime()){
            //outlinedTextField.setBoxStrokeColor(Color.parseColor("#008000"));
            color2 = Color.parseColor("#008000");
            color = getResources().getColor(R.color.red);
            color2 = getResources().getColor(R.color.red);

            colors = new int[] {
                    color,
                    color,
                    color,
                    color2,
                    color2
            };

            myColorList = new ColorStateList(states, colors);


            outlinedTextField.setBoxStrokeColorStateList(myColorList);
            outlinedTextField.setDefaultHintTextColor(myColorList);
            txtcurrdate.setTextColor(color2);
        }*/

        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_recyclerview);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainAdapter = new MainAdapter(getActivity(), MyApplication.getDatabase().categoryDao().getAllByGroup(1));
        mainAdapter.setClickListener(this);

        recyclerView.setAdapter(mainAdapter);

        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.main_recyclerview2);



        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainAdapter2 = new MainAdapter2(getActivity(), MyApplication.getDatabase().categoryDao().getAllByGroup(2));
        mainAdapter2.setClickListener(this);

        recyclerView2.setAdapter(mainAdapter2);

        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.main_recyclerview3);


        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainAdapter3 = new MainAdapter3(getActivity(), MyApplication.getDatabase().categoryDao().getAllByGroup(3));
        mainAdapter3.setClickListener(this);

        recyclerView3.setAdapter(mainAdapter3);

        recyclerView4 = (RecyclerView) rootView.findViewById(R.id.main_recyclerview4);


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
                fragment = new TasksFragmentN();
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
            //Log.e("ПОЗИЦИЯ: ", String.valueOf(position));
            if (position == 11) {
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

            if (position == 6){
                Fragment fragment;
                fragment = new TypeOfTaskFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TypeOfTaskFragment").replace(R.id.frame_container, fragment, "TypeOfTaskFragment").commit();
            }

            if (position == 7){
                Fragment fragment;
                fragment = new CategoryOfTaskFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("CategoryOfTaskFragment").replace(R.id.frame_container, fragment, "CategoryOfTaskFragment").commit();
            }

        }

        if (grp == 3) {
            if (position == 0){
                Fragment fragment;
                fragment = new TemplatesFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("TemplatesFragment").replace(R.id.frame_container, fragment, "TemplatesFragment").commit();
            }

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

    @Override
    public void getDateBegin(String date, long datemls) {
        int color = getResources().getColor(R.color.colorPrimary);
        int color2 = Color.parseColor("#3F51B5");
        txtcurrdate.setText(Utils.dateToString(DEFAULT_DATEFORMAT, new Date(Utils.getCurrentApplicationDate())));
        txtcurrdate.setTextColor(color2);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_focused}, // focused
                new int[] { android.R.attr.state_hovered}, // hovered
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] { android.R.attr.hint}, // enabled
                new int[] { }  //
        };

        int[] colors = new int[] {
                color,
                color,
                color,
                color2,
                color2
        };

        ColorStateList myColorList = new ColorStateList(states, colors);

        if (Utils.getStartOfDay(new Date(Utils.getCurrentApplicationDate())).getTime() < Utils.getStartOfDay(new Date()).getTime()) {
            //outlinedTextField.setBoxStrokeColor(Color.parseColor("#FF0000"));
            color2 = Color.parseColor("#FF0000");
            color = getResources().getColor(R.color.green);
            color2 = getResources().getColor(R.color.green);
            colors = new int[]{
                    color,
                    color,
                    color,
                    color2,
                    color2
            };

            myColorList = new ColorStateList(states, colors);


            outlinedTextField.setBoxStrokeColorStateList(myColorList);
            outlinedTextField.setDefaultHintTextColor(myColorList);
            txtcurrdate.setTextColor(color2);
        }
        if (Utils.getStartOfDay(new Date(Utils.getCurrentApplicationDate())).getTime() > Utils.getStartOfDay(new Date()).getTime()) {
            //outlinedTextField.setBoxStrokeColor(Color.parseColor("#008000"));
            color2 = Color.parseColor("#008000");
            color = getResources().getColor(R.color.red);
            color2 = getResources().getColor(R.color.red);

            colors = new int[]{
                    color,
                    color,
                    color,
                    color2,
                    color2
            };

            myColorList = new ColorStateList(states, colors);


            outlinedTextField.setBoxStrokeColorStateList(myColorList);
            outlinedTextField.setDefaultHintTextColor(myColorList);
            txtcurrdate.setTextColor(color2);
        }
    }
}
