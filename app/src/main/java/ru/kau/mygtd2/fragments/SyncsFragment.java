package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.BackupsAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.restapi.SyncApi;
import stream.custombutton.CustomButton;

public class SyncsFragment extends Fragment {

    private static SyncApi calApi;

    private RecyclerView recyclerView;

    private BackupsAdapter mainAdapter;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sync_fragment, null);
        //Toast.makeText(getContext(), "22222222", Toast.LENGTH_SHORT).show();

        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        CustomButton btncreate = rootView.findViewById(R.id.btncreate);

        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


        /*recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview1);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        recyclerView.setBackground(drawable);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        //mainAdapter = new BackupsAdapter(getActivity(), lstCategories);
        //mainAdapter.setClickListener(this);

        //recyclerView.setAdapter(mainAdapter);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sync sync = new Sync();
                sync.setDeviceguid(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());

                calApi = Controller.getSyncApi();
                Call<Sync> call = calApi.create(sync);
            }
        });




        return rootView;
    }



}
