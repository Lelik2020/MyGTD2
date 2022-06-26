package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.BackupsAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.restapi.SyncApi;
import ru.kau.mygtd2.restapi.TasksApi2;
import stream.custombutton.CustomButton;

public class SyncsFragment extends Fragment {

    private static SyncApi calApi;
    private static TasksApi2 calApi2;

    private RecyclerView recyclerView;

    private BackupsAdapter mainAdapter;

    private Long l;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sync_fragment, null);
        //Toast.makeText(getContext(), "22222222", Toast.LENGTH_SHORT).show();

        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        CustomButton btncreate = rootView.findViewById(R.id.btncreate);

        calApi = Controller.getSyncApi();
        Call<Long> call = calApi.getlastsync();
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                l = (Long) response.body();
                System.out.println("Long: " + l);

            }



            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

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



                List<Task> lstTask = MyApplication.getDatabase().taskDao().getAllTasks();
                calApi2 = Controller.getTasksApi();
                Call<String> call2 = calApi2.settasksforupdate(lstTask);   //settst(lstTask.get(0));
                call2.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call call, Response response) {
                        //System.out.println("Response: " + response.code());
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        //System.out.println("Response: " + response.code());
                        //Log.e("ERROR8888", t.getMessage());
                    }
                });

            }
        });




        return rootView;
    }



}
