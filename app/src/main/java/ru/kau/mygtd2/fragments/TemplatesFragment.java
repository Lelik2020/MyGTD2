package ru.kau.mygtd2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.TemplatesAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskTemplate;

public class TemplatesFragment extends Fragment {

    private RecyclerView recyclerView;

    private ActionBar toolbar;

    private TemplatesAdapter templatesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.template_fragment, null);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Задачи");
        setHasOptionsMenu(true);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvtemplates);

        List<TaskTemplate> lstTaskTemplate;

        lstTaskTemplate = MyApplication.getDatabase().taskTemplateDao().getAll();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        templatesAdapter = new TemplatesAdapter(getActivity(), lstTaskTemplate);

        recyclerView.setAdapter(templatesAdapter);

        return rootView;

    }
}
