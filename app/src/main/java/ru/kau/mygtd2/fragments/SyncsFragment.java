package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.SyncAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.TaskTemplate;
import ru.kau.mygtd2.restapi.SyncApi;
import ru.kau.mygtd2.utils.Utils;
import stream.custombutton.CustomButton;

public class SyncsFragment extends Fragment {

    private static SyncApi calApi;
    //private static TasksApi2 calApi2;

    private RecyclerView recyclerView;

    //private BackupsAdapter mainAdapter;

    List<Sync> lstSync = new ArrayList<Sync>();

    private Long l;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sync_fragment, null);
        //Toast.makeText(getContext(), "22222222", Toast.LENGTH_SHORT).show();

        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        CustomButton btncreate = rootView.findViewById(R.id.btnsync);

        TextView txtLastSync = rootView.findViewById(R.id.txtlastsync);;

        calApi = Controller.getSyncApi();
        //Call<Long> call = calApi.getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        Call<Long> call = calApi.getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        //Call<Long> call = calApi.getlastsyncdevice("678678");
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call call, Response response) {
                l = (Long) response.body();
                System.out.println("Long: " + l);
                if (l != null) {
                    txtLastSync.setText(Utils.dateToString(new Date(l)));
                } else {
                    l = 0L;
                    txtLastSync.setText("Синхронизаций пока не было");
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage());
            }
        });


        recyclerView = (RecyclerView) rootView.findViewById(R.id.lstsyncs);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // Получаем список синхронизаций данного устройства

        Call<List<Sync>> call2 = calApi.getlstsyncsdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());

        call2.enqueue(new Callback<List<Sync>>() {

            @Override
            public void onResponse(Call<List<Sync>> call, Response<List<Sync>> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().size());
                    lstSync.addAll(response.body());
                    SyncAdapter syncsAdapter = new SyncAdapter(getActivity(), lstSync);
                    recyclerView.setAdapter(syncsAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                if (l != null && l != 0L) {
                    txtLastSync.setText(Utils.dateToString(new Date(l)));
                } else {
                    txtLastSync.setText("Синхронизаций не было");
                }

            }



            @Override
            public void onFailure(Call<List<Sync>> call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage());
            }
        });




        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sync sync = new Sync();
                sync.setDeviceguid(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());

                Call<Sync> call3 = calApi.create(sync);

                // Обновляем справочники
                // Контексты
                List<Contekst> lstConteksts = MyApplication.getDatabase().contextDao().getAll();
                for(int i = 0; i < lstConteksts.size(); i++){
                    Call<Contekst> contekstCall = calApi.createContekst(lstConteksts.get(i));
                    contekstCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            System.out.println("CONTEXT");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                }


                List<Tag> lstTag = MyApplication.getDatabase().tagDao().getAll();
                for(int i = 0; i < lstTag.size(); i++){
                    Call<Tag> tagCall = calApi.createTag(lstTag.get(i));
                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            System.out.println("TAG");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                }

                List<Target> lstTarget = MyApplication.getDatabase().targetDao().getAll();
                for(int i = 0; i < lstTarget.size(); i++){
                    Call<Target> tagCall = calApi.createTarget(lstTarget.get(i));
                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //System.out.println("TAG");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                }

                List<TaskTemplate> lstTaskTemplate = MyApplication.getDatabase().taskTemplateDao().getAll();
                for(int i = 0; i < lstTaskTemplate.size(); i++){
                    Call<TaskTemplate> tagCall = calApi.createTaskTemplate(lstTaskTemplate.get(i));

                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            System.out.println("TaskTemplate");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            System.out.println("1111111");
                        }
                    });



                }



                /*List<Task> lstTask = MyApplication.getDatabase().taskDao().getTasksForUpdate(l);
                calApi2 = Controller.getTasksApi();
                Call call2 = calApi2.settasksforupdate(lstTask);   //settst(lstTask.get(0));
                call2.enqueue(new Callback() {

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
*/
            }
        });




        return rootView;
    }



}
