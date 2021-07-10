package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.multilevel.treelist.Node;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.CommonAdapter;
import ru.kau.mygtd2.adapters.SprProjectTreeAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.CommonType;
import ru.kau.mygtd2.listeners.DefaultListeners;
import ru.kau.mygtd2.objects.Project;

public class SprProjectFragment extends Fragment {

    private List<Node> projectsList = new ArrayList<Node>();
    private SprProjectTreeAdapter projectsAdapter;
    private CommonAdapter commonAdapter;



    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sprproject_fragment, null);

        RecyclerView lvCommon = (RecyclerView) rootView.findViewById(R.id.lvCommon);

        final List<String> lstCommon = new ArrayList<String>();
        lstCommon.add("Всего задач");
        lstCommon.add("Задач с проектами");
        lstCommon.add("Задач без проекта");


        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        lvCommon.setBackground(drawable);

        lvCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter = new CommonAdapter( getActivity(), lstCommon, CommonType.PROJECT);

        lvCommon.setAdapter(commonAdapter);


        ListView list = (ListView) rootView.findViewById(R.id.lvSprProjects);

        final List<Project> projectList = MyApplication.getDatabase().projectDao().getAll();
        projectsList.clear();
        for(Project p: projectList){
            projectsList.add(new Node(p.getId(), p.getParentid(), p.getTitle()));
        }

        list.setBackground(drawable);

        //list.setLayoutManager(new LinearLayoutManager(getActivity()));

        projectsAdapter = new SprProjectTreeAdapter(list, getActivity(), projectsList, 0, R.drawable.minus, R.drawable.plus);

        bindAdapter(projectsAdapter);
        list.setAdapter(projectsAdapter);


        return rootView;
    }

    public void bindAdapter(SprProjectTreeAdapter projectAdapter) {
        DefaultListeners.bindAdapter3(getActivity(), projectAdapter);
    }

}
