package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.TaskCategoryAdapter;
import ru.kau.mygtd2.adapters.TaskTypesAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.TaskCategory;

public class CategoryOfTaskFragment  extends Fragment {

    TaskCategoryAdapter taskCategoryAdapter;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.categoryoftask_fragment, null);

        RecyclerView lvCategoryTypes = (RecyclerView) rootView.findViewById(R.id.lvCategoryOfTask);
        final List<TaskCategory> lstTaskCategory = MyApplication.getDatabase().taskCategoryDao().getAll();


        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        lvTaskTypes.setBackground(drawable);

        lvTaskTypes.setLayoutManager(new LinearLayoutManager(getActivity()));
        tskTypesAdapter = new TaskTypesAdapter( getActivity(), lstTaskTypes);

        lvTaskTypes.setAdapter(tskTypesAdapter);

        return rootView;


    }

}
