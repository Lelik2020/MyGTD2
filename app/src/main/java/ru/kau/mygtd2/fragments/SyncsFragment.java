package ru.kau.mygtd2.fragments;

import static ru.kau.mygtd2.utils.Const.DEFAULT_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMILSECONDS;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TASKOVERDUE_COLOR;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.SyncAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.ProjectStatus;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskContextJoin;
import ru.kau.mygtd2.objects.TaskTagJoin;
import ru.kau.mygtd2.objects.TaskTemplate;
import ru.kau.mygtd2.objects.TaskTemplateContextJoin;
import ru.kau.mygtd2.objects.TaskTemplateTagJoin;
import ru.kau.mygtd2.restapi.SyncApi;
import ru.kau.mygtd2.utils.Synchronisation;
import ru.kau.mygtd2.utils.Utils;
import stream.custombutton.CustomButton;

public class SyncsFragment extends Fragment {

    private static SyncApi calApi;
    private static SyncApi calApi2;
    //private static TasksApi2 calApi2;

    private RecyclerView recyclerView;

    //private BackupsAdapter mainAdapter;

    private List<Sync> lstSync = new ArrayList<Sync>();

    private Long l;

    private int syncStatus = 0; // -1 - ошибка при синхронизации
                                // 1 - успешно
    boolean isError = false;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sync_fragment, null);
        //Toast.makeText(getContext(), "22222222", Toast.LENGTH_SHORT).show();

        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        CustomButton btncreate = rootView.findViewById(R.id.btnsync);
        CustomButton btntest = rootView.findViewById(R.id.btntest);

        TextView txtLastSync = rootView.findViewById(R.id.txtlastsync);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.lstsyncs);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*try {
            l = Synchronisation.getLastSyncDevice2();
            if (l != null) {
                txtLastSync.setText(Utils.dateToString(new Date(l)));
            } else {
                l = 0L;
                txtLastSync.setText("Синхронизаций пока не было");
            }

            lstSync = Synchronisation.getListSyncsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());


        } catch (HttpException e) {

            txtLastSync.setText("Сервер синхронизации недоступен");

        }*/

        // Получаем список синхронизаций данного устройства

        // RxJava

        /*
        Observable.create((ObservableOnSubscribe<Long>) e -> {
            Long data = Synchronisation.getLastSyncDevice2();
            e.onNext(data);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        */

        txtLastSync.setText("Сервер синхронизации недоступен");
        txtLastSync.setTextColor(Color.parseColor(DEFAULT_TASKOVERDUE_COLOR));

        Observable.create((ObservableOnSubscribe<Long>) e -> {
                    try {
                        Long data = Synchronisation.getLastSyncDevice();
                        e.onNext(data);
                    } catch (Exception ex) {
                        e.onError(ex);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(match -> {
                                        Log.e("rest api, success  ", String.valueOf(match));
                                        txtLastSync.setText(Utils.dateToString(new Date(match)));
                                        txtLastSync.setTextColor(Color.parseColor(DEFAULT_COLOR));
                                    },
                            throwable ->    {
                                            txtLastSync.setText("Сервер синхронизации недоступен");
                                            txtLastSync.setTextColor(Color.parseColor(DEFAULT_TASKOVERDUE_COLOR));
                                            Log.e("rest api, error: ", throwable.getMessage());

                                        });

        Observable.create((ObservableOnSubscribe<List<Sync>>) lstSyncs -> {
                    try {
                        List<Sync> data = Synchronisation.getListSyncsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                        //Log.e("SIZE: ", String.valueOf(data.size()));
                        SyncAdapter syncsAdapter = new SyncAdapter(getActivity(), data);
                        recyclerView.setAdapter(syncsAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        //e.onNext(data);
                    } catch (Exception ex) {
                        lstSyncs.onError(ex);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(match2 -> {
                            //Log.e("rest api 33333, success 44444 ", String.valueOf(match2.size()));
                            //SyncAdapter syncsAdapter = new SyncAdapter(getActivity(), match2);
                            //recyclerView.setAdapter(syncsAdapter);
                            //recyclerView.setVisibility(View.VISIBLE);
                        },
                        throwable ->    {

                                            Log.e("rest api  123123, error: 890890", throwable.getMessage());
                                        });

        // RxJava

        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



            btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ERROR", Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации");
                Sync sync = new Sync();

                sync.setGuid(UUID.randomUUID().toString());
                sync.setDeviceguid(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                sync.setDatebegin((new Date()).getTime());
                sync.setDatebeginstr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()));

                Log.e("ERROR", Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации устройств");


                Call deviceCall = calApi.createDevice(MyApplication.getDatabase().deviceDao().getCurrentDevice());
                deviceCall.enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response) {
                        //System.out.println("CONTEXT");
                        if (response.isSuccessful()) {
                            System.out.println("STATUS: " + response.code());

                        } else {
                            System.out.println("ERROR: " + response.code() + "   " + response.errorBody());
                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                        System.out.println("STATUS: " + t.getMessage());
                        isError = true;
                    }
                });


                Log.e("ERROR", Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации устройств");

                //Call<Sync> call3 = calApi.create(sync);
                Log.e("ERROR", Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации контекстов");
                // Обновляем справочники
                // Контексты
                List<Contekst> lstConteksts = MyApplication.getDatabase().contextDao().getAll();
                for(int i = 0; i < lstConteksts.size(); i++){


                    Call<Contekst> contekstCall = calApi.createContekst(lstConteksts.get(i));
                    contekstCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //System.out.println("CONTEXT");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации контекстов");

                List<Tag> lstTag = MyApplication.getDatabase().tagDao().getAll();
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации тэгов");
                for(int i = 0; i < lstTag.size(); i++){
                    Call<Tag> tagCall = calApi.createTag(lstTag.get(i));

                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //System.out.println("TAG");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации тэгов");
                List<Target> lstTarget = MyApplication.getDatabase().targetDao().getAll();
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации целей");
                for(int i = 0; i < lstTarget.size(); i++){

                    Call<Target> tagCall = calApi.createTarget(lstTarget.get(i));
                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //System.out.println("TAG");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации целей");
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации проектов");
                calApi2 = Controller.getSyncApi2();
                List<Project> lstProjects = MyApplication.getDatabase().projectDao().getAll();
                for(int i = 0; i < lstProjects.size(); i++){
                    Call<Project> tagCall = calApi2.createProject(lstProjects.get(i));

                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //System.out.println("TaskTemplate");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //System.out.println("1111111");
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации проектов");

                List<ProjectStatus> lstProjectStatus = MyApplication.getDatabase().projectStatusDao().getAll();
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации статусов проектов");
                for(int i = 0; i < lstProjectStatus.size(); i++){

                    Call<ProjectStatus> tagCall = calApi.createProjectStatus(lstProjectStatus.get(i));

                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //System.out.println("TaskTemplate");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //System.out.println("1111111");
                            isError = true;
                        }
                    });
                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации статусов проектов");

                Log.e("ERROR: ", String.valueOf(isError));

                List<TaskTemplate> lstTaskTemplate = MyApplication.getDatabase().taskTemplateDao().getAll();
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации шаблонов");
                for(int i = 0; i < lstTaskTemplate.size(); i++){

                    Call<TaskTemplate> tagCall = calApi.createTaskTemplate(lstTaskTemplate.get(i));

                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //System.out.println("TaskTemplate");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //System.out.println("1111111");
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации шаблонов");

                Log.e("ERROR: ", String.valueOf(isError));
                List<TaskTemplateContextJoin> lstTaskTemplateContextJoin = MyApplication.getDatabase().taskTemplateContextJoinDao().getAll();
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации связки шаблонов и контекстов");
                for(int i = 0; i < lstTaskTemplateContextJoin.size(); i++){

                    Call<TaskTemplateContextJoin> tagCall = calApi.createTaskTemplateContextJoin(lstTaskTemplateContextJoin.get(i));

                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            System.out.println("TaskTemplateContextJoin");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //System.out.println("1111111");
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации связки шаблонов и контекстов");

                Log.e("ERROR: ", String.valueOf(isError));

                List<TaskTemplateTagJoin> lstTaskTemplateTagJoin = MyApplication.getDatabase().taskTemplateTagJoinDao().getAll();
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации связки тэгов и шаблонов");
                for(int i = 0; i < lstTaskTemplateTagJoin.size(); i++){

                    Call<TaskTemplateTagJoin> tagCall = calApi.createTaskTemplateTagJoin(lstTaskTemplateTagJoin.get(i));

                    tagCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            System.out.println("TaskTemplateTagJoin");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //System.out.println("1111111");
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации связки тэгов и шаблонов");
                // Задачи
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации задач");

                Log.e("ERROR: ", String.valueOf(isError));
                Call<List<Task>> getTasks = calApi.gettasksforupdate(l);
                List<Task> lstTasksNew = new ArrayList<>();
                getTasks.enqueue(new Callback<List<Task>>() {

                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        lstTasksNew.addAll(response.body());
                        for(int i = 0; i < lstTasksNew.size(); i++){
                            Task t = MyApplication.getDatabase().taskDao().getByGuid(lstTasksNew.get(i).getGuid());
                            try {
                                if (t == null) {
                                    MyApplication.getDatabase().taskDao().insert(lstTasksNew.get(i));
                                } else {
                                    if (lstTasksNew.get(i).getDateEdit().getTime() > t.getDateEdit().getTime()){
                                        MyApplication.getDatabase().taskDao().update(lstTasksNew.get(i));
                                    }
                                }
                            } catch (Exception e){

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {
                        //System.out.println("1111111");
                        isError = true;
                    }
                });

                List<Task> lstTasks = MyApplication.getDatabase().taskDao().getAllTasks();
                for(int i = 0; i < lstTasks.size(); i++){

                    Call<Task> taskCall = calApi.settasksforupdate(lstTasks.get(i));
                    String taskGuid = lstTasks.get(i).getGuid();
                    List<TaskTagJoin> lstTaskTagJoin = MyApplication.getDatabase().taskTagJoinDao().getTagsForTask(taskGuid);
                    List<TaskContextJoin> lstTaskContextJoin = MyApplication.getDatabase().taskContextJoinDao().getCotextsForTask(taskGuid);
                    taskCall.enqueue(new Callback() {

                        @Override
                        public void onResponse(Call call, Response response) {

                            Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации связки задач и тегов");
                            Call<TaskTagJoin> call2 = calApi.settasktagjoin(taskGuid, lstTaskTagJoin);

                            call2.enqueue(new Callback() {

                                @Override
                                public void onResponse(Call call, Response response) {
                                    //System.out.println("TaskTagJoin");
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    //System.out.println("1111111");
                                    isError = true;
                                }
                            });
                            Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации задач и тегов");
                            Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало синхронизации связки задач и контекстов");
                            Call<TaskContextJoin> call3 = calApi.settaskscontextjoin(taskGuid, lstTaskContextJoin);

                            call3.enqueue(new Callback() {

                                @Override
                                public void onResponse(Call call, Response response) {
                                    //System.out.println("TaskContextJoin");
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    //System.out.println("1111111");
                                    isError = true;
                                }
                            });
                            Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации задач и контекстов");
                            // ----------------------------------------------------------------------
                        }


                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //System.out.println("1111111");
                            isError = true;
                        }
                    });

                }
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец синхронизации задач");

                // До этого мы передавали данные для синхронизации
                // Теперь будеи получать их

                // Сначала получаем список устройств
                //
                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало получения девайсов");


                    Call<List<Device>> lstdeviceCall = calApi.getAllDevices();
                    lstdeviceCall.enqueue(new Callback<List<Device>>() {

                        @Override
                        public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                            List<Device> lstDevices = new ArrayList<>();
                            lstDevices.addAll(response.body());
                            Log.e("ERROR: ", String.valueOf(lstDevices.size()));
                            for (int i = 0; i < lstDevices.size(); i++) {
                                Device d = MyApplication.getDatabase().deviceDao().getByGuid(lstDevices.get(i).getGuid());
                                if (d == null) {
                                    // Сохраняем новое устройство
                                    lstDevices.get(i).setIscurrent(0);
                                    try {
                                        MyApplication.getDatabase().deviceDao().insert(lstDevices.get(i));
                                    } catch (Exception e) {
                                        Log.e("ERROR: ", e.getMessage());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            isError = true;
                        }
                    });


                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец получения девайсов");

                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало получения справочников");

                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало получения контекстов");

                Call<List<Contekst>> lstContekstCall = calApi.getAllContext();
                lstContekstCall.enqueue(new Callback<List<Contekst>>() {

                    @Override
                    public void onResponse(Call<List<Contekst>> call, Response<List<Contekst>> response) {
                        //System.out.println("CONTEXT");
                        List<Contekst> lstContexts = response.body();
                        if (lstContexts != null) {
                            for (int i = 0; i < lstContexts.size(); i++) {
                                Contekst contekst = MyApplication.getDatabase().contextDao().getContextById(lstContexts.get(i).getId());
                                if (contekst == null) {
                                    MyApplication.getDatabase().contextDao().insert(lstContexts.get(i));
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        isError = true;
                    }
                });

                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец получения контекстов");


                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Начало получения проектов");

                Call<List<Project>> lstProjectCall = calApi.getAllProjects();
                lstProjectCall.enqueue(new Callback<List<Project>>() {

                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        //System.out.println("CONTEXT");
                        List<Project> lstProjects = response.body();
                        if (lstProjects != null) {
                            for (int i = 0; i < lstProjects.size(); i++) {
                                Project project = MyApplication.getDatabase().projectDao().getProjectById(lstProjects.get(i).getId());
                                if (project == null) {
                                    MyApplication.getDatabase().projectDao().insert(lstProjects.get(i));
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        isError = true;
                    }
                });

                Log.e("ERROR",Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()) + ": Конец получения проектов");







                // ----------------------------------------------------------------------------------------------------

                sync.setDateend((new Date()).getTime());
                sync.setDateendstr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMILSECONDS, new Date()));
                sync.setStatus((isError == false) ? 1 : -1);

                Call<Sync> call3 = calApi.create(sync);

                call3.enqueue(new Callback() {

                    @Override
                    public void onResponse(Call call, Response response) {

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        isError = true;
                    }
                });

            }
        });




        return rootView;
    }



}
