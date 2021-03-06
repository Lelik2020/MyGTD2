package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.CommonAdapter;
import ru.kau.mygtd2.adapters.TargetAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.CommonType;
import ru.kau.mygtd2.objects.Target;

public class TargetFragment extends Fragment {

    private CommonAdapter commonAdapter;
    private TargetAdapter targetAdapter;
    private ActionBar toolbar;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.target_fragment, null);

        RecyclerView lvCommon = (RecyclerView) rootView.findViewById(R.id.lvCommon);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Информация");
        setHasOptionsMenu(true);

        final List<String> lstCommon = new ArrayList<String>();
        lstCommon.add("Все задачи");
        lstCommon.add("Задачи с целями");
        lstCommon.add("Задачи без целей");

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        lvCommon.setBackground(drawable);

        lvCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter = new CommonAdapter( getActivity(), lstCommon, CommonType.TARGET);

        lvCommon.setAdapter(commonAdapter);

        RecyclerView lvTarget = (RecyclerView) rootView.findViewById(R.id.lvTargets);
        final List<Target> lstTargets = MyApplication.getDatabase().targetDao().getAll();


        drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        lvTarget.setBackground(drawable);

        lvTarget.setLayoutManager(new LinearLayoutManager(getActivity()));
        targetAdapter = new TargetAdapter( getActivity(), lstTargets);

        lvTarget.setAdapter(targetAdapter);

        return rootView;

    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_target, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }*/

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_target) {
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("AddTargetFragment").replace(R.id.frame_container,new AddTargetFragment()).commit();
            //Intent intent = new Intent(this, SearchUsersActivity.class);
            //startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }*/


}
