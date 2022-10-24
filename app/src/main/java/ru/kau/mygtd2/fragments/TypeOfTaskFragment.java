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

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.CommonAdapter;
import ru.kau.mygtd2.adapters.ContextsAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.CommonType;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.TaskTypes;

public class TypeOfTaskFragment extends Fragment {

    private ContextsAdapter contextsAdapter;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.typeoftask_fragment, null);



        RecyclerView lvTaskTypes = (RecyclerView) rootView.findViewById(R.id.lvTypeOfTask);
        final List<TaskTypes> lstTaskTypes = MyApplication.getDatabase().taskTypesDao().getAll();


        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        lvTaskTypes.setBackground(drawable);

        lvTaskTypes.setLayoutManager(new LinearLayoutManager(getActivity()));
        contextsAdapter = new ContextsAdapter( getActivity(), lstContekst);

        lvContexts.setAdapter(contextsAdapter);

        return rootView;
    }



}
