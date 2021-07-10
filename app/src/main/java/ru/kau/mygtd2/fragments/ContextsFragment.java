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

public class ContextsFragment extends Fragment {

    private CommonAdapter commonAdapter;
    private ContextsAdapter contextsAdapter;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contexts_fragment, null);

        RecyclerView lvCommon = (RecyclerView) rootView.findViewById(R.id.lvCommon);

        final List<String> lstCommon = new ArrayList<String>();
        lstCommon.add("Все задачи");
        lstCommon.add("Задачи без контекстов");

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        lvCommon.setBackground(drawable);

        lvCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter = new CommonAdapter( getActivity(), lstCommon, CommonType.CONTEXT);

        lvCommon.setAdapter(commonAdapter);

        RecyclerView lvContexts = (RecyclerView) rootView.findViewById(R.id.lvContexts);
        final List<Contekst> lstContekst = MyApplication.getDatabase().contextDao().getAll();


        drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        lvContexts.setBackground(drawable);

        lvContexts.setLayoutManager(new LinearLayoutManager(getActivity()));
        contextsAdapter = new ContextsAdapter( getActivity(), lstContekst);

        lvContexts.setAdapter(contextsAdapter);

        return rootView;
    }


}
