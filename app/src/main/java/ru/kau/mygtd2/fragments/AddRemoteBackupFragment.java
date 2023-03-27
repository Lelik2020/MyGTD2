package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;
//import com.moos.library.CircleProgressView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.BackupItemAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.BackupItem;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.restapi.BackupApi;
import ru.kau.mygtd2.restapi.DirectoriesApi;
import ru.kau.mygtd2.restapi.InformationsApi;
import ru.kau.mygtd2.restapi.TasksApi;
import ru.kau.mygtd2.utils.Utils;

public class AddRemoteBackupFragment extends Fragment {

    private static BackupApi calApi;
    private static TasksApi calApi2;
    private InformationsApi calApi3;
    private DirectoriesApi calApi4;
    int i;
    int j;
    int k = 0;
    long all = 0;

    TextView tv;
    TextView txtDateBegin;
    TextView txtDateEnd;

    RecyclerView rv;
    ProgressBar cpv;
    List<BackupItem> lstBakupItem= new ArrayList<>();
    private String backupGuid;
    RoundTextView rtv2;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.addremotebackup_fragment, null);
        tv = rootView.findViewById(R.id.txt);
        txtDateBegin = rootView.findViewById(R.id.txtDateStart);
        txtDateEnd = rootView.findViewById(R.id.txtDateEnd);
        rv = rootView.findViewById(R.id.main_recyclerview);
        cpv = rootView.findViewById(R.id.progressView_circle);
        rtv2 = rootView.findViewById(R.id.rtv2);
        rtv2.setText("В процессе");
        rtv2.setCorner(16, 16, 16, 16);
        //rtv2.setBgColor(R.color.green);
        rtv2.setBgColor(getResources().getColor(R.color.lt_grey_alpha));

        txtDateBegin.setText(Utils.dateToString(new Date()));

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.colorPrimaryDark);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        rv.setBackground(drawable);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        BackupItem backupItem = new BackupItem();
        backupItem = new BackupItem();
        backupItem.setId(0);
        backupItem.setTitle("Справочники");
        backupItem.setCount1(0);
        backupItem.setCount2(0);
        backupItem.setPercent((float) (100.0 * backupItem.getCount1() / backupItem.getCount2()));
        lstBakupItem.add(backupItem);

        //BackupItem
        backupItem = new BackupItem();
        backupItem.setId(1);
        backupItem.setTitle("Задачи");
        backupItem.setCount1(0);
        backupItem.setCount2(MyApplication.getDatabase().taskDao().getCountAllTasks());
        backupItem.setPercent((float) (100.0 * backupItem.getCount1() / backupItem.getCount2()));

        lstBakupItem.add(backupItem);

        backupItem = new BackupItem();
        backupItem.setId(2);
        backupItem.setTitle("Информация");
        backupItem.setCount1(0);
        backupItem.setCount2(MyApplication.getDatabase().informationDao().getCountAll());
        backupItem.setPercent((float) (100.0 * backupItem.getCount1() / backupItem.getCount2()));

        lstBakupItem.add(backupItem);




        all = lstBakupItem.get(0).getCount2() + lstBakupItem.get(1).getCount2() + lstBakupItem.get(2).getCount2();

        BackupItemAdapter backupsAdapter = new BackupItemAdapter(getActivity(), lstBakupItem);

        rv.setAdapter(backupsAdapter);


        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();
        Backup backup = new Backup();
        backup.setDeviceguid(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());

        calApi = Controller.getCalApi();
        Call<Backup> call = calApi.create2(backup);

        call.enqueue(new Callback<Backup>() {

            @Override
            public void onResponse(Call<Backup> call, Response<Backup> response) {
                //edittxt2.setText(response.body().getId().toString() + ":  " + response.body().getText());
                //tv.setText(response.body().getGuid());
                backupGuid = response.body().getGuid();
                //Log.e("JSONBackup", response.message());
                //Log.e("JSONBackup", String.valueOf(response.code()));
                //Log.e("JSONBackup", backupGuid);
                //Log.e("JSONBackup", String.valueOf(response.body().get));
                //Toast.makeText(getContext(), "??????????????", Toast.LENGTH_SHORT).show();

                List<Task> lstTask = MyApplication.getDatabase().taskDao().getAllTasks();
                for(i = 0; i < lstTask.size();i++){
                //for(int i = 0; i < 1;i++){
                    Task task = lstTask.get(i);
                    task.setBackupguid(backupGuid);
                    calApi2 = Controller.getCalApi2();
                    Call<Task> call2 = calApi2.create2(task);

                    call2.enqueue(new Callback<Task>() {

                        @Override
                        public void onResponse(Call call, Response response) {
                            //edittxt2.setText(response.body().getId().toString() + ":  " + response.body().getText());
                            //tv.setText(response.body().getGuid());
                            //backupGuid = response.body().getGuid();
                            //Log.e("onResponse", response.body());
                            //Log.e("222222", backupGuid);
                            //Log.e("JSONTASK", String.valueOf(response.code()));
                            lstBakupItem.get(1).setCount1(i);
                            lstBakupItem.get(1).setPercent(100f * lstBakupItem.get(1).getCount1() / lstBakupItem.get(1).getCount2());
                            backupsAdapter.notifyItemChanged(2);
                            backupsAdapter.notifyDataSetChanged();
                            k++;
                            cpv.setProgress((int)(100 * k / all));
                            //JsonObject post = new JsonObject().get(response.body().toString()).getAsJsonObject();
                            //Log.e("JSONTASK", post.getAsString());
                            //Toast.makeText(getContext(), "??????????????", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Task> call, Throwable t) {
                            Log.e("ERROR999", t.getMessage());
                            rtv2.setText(t.getMessage());
                            rtv2.setBgColor(getResources().getColor(R.color.red_start));
                            //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //delay(100);
                }
                List<Information> lstInformation = MyApplication.getDatabase().informationDao().getAll();
                for(j = 0; j < lstInformation.size();j++){
                    //for(int i = 0; i < 1;i++){
                    Information information = lstInformation.get(j);
                    information.setBackupguid(backupGuid);
                    calApi3 = Controller.getCalApi3();
                    Call<Information> call3 = calApi3.create(information);

                    call3.enqueue(new Callback<Information>() {

                        @Override
                        public void onResponse(Call<Information> call, Response<Information> response) {
                            //edittxt2.setText(response.body().getId().toString() + ":  " + response.body().getText());
                            //tv.setText(response.body().getGuid());
                            //backupGuid = response.body().getGuid();
                            //Log.e("onResponse", response.body());
                            //Log.e("222222", backupGuid);
                            //Log.e("JSONINFO", response.message());
                            //response.message()
                            //Toast.makeText(getContext(), "??????????????", Toast.LENGTH_SHORT).show();
                            lstBakupItem.get(2).setCount1(j);
                            lstBakupItem.get(2).setPercent(100f * lstBakupItem.get(2).getCount1() / lstBakupItem.get(2).getCount2());
                            backupsAdapter.notifyItemChanged(3);
                            k++;
                            cpv.setProgress((int) (100 * k / all));
                        }

                        @Override
                        public void onFailure(Call<Information> call, Throwable t) {
                            Log.e("ERROR777888", t.getMessage());
                            rtv2.setText(t.getMessage());
                            rtv2.setBgColor(getResources().getColor(R.color.red_start));
                            //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //delay(100);
                }

                calApi4 = Controller.getCalApi4();
                Call<ResponseBody> a = calApi4.contekstDeleteAll();
                a.enqueue(new Callback<ResponseBody>() {


                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //Log.e("ResponseBodyDELETE", response.toString());
                        saveContekst();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        rtv2.setText(t.getMessage());
                        rtv2.setBgColor(getResources().getColor(R.color.red_start));
                    }
                });



                Call<ResponseBody> b = calApi4.infostatusDeleteAll();
                b.enqueue(new Callback<ResponseBody>() {


                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //Log.e("ResponseBodyDELETE", response.toString());
                        saveInfostatus();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        rtv2.setText(t.getMessage());
                        rtv2.setBgColor(getResources().getColor(R.color.red_start));
                    }
                });

                Call<ResponseBody> c = calApi4.tagDeleteAll();
                c.enqueue(new Callback<ResponseBody>() {


                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //Log.e("ResponseBodyDELETE", response.toString());
                        saveTag();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        rtv2.setText(t.getMessage());
                        rtv2.setBgColor(getResources().getColor(R.color.red_start));
                    }
                });

                Call<ResponseBody> d = calApi4.targetDeleteAll();
                d.enqueue(new Callback<ResponseBody>() {


                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //Log.e("ResponseBodyDELETE", response.toString());
                        saveTarget();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        rtv2.setText(t.getMessage());
                        rtv2.setBgColor(getResources().getColor(R.color.red_start));
                    }
                });

                txtDateEnd.setText(Utils.dateToString(new Date()));
                rtv2.setBgColor(getResources().getColor(R.color.green));
                rtv2.setText("Завершено");


            }

            @Override
            public void onFailure(Call<Backup> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                rtv2.setText(t.getMessage());
                rtv2.setBgColor(getResources().getColor(R.color.red_start));
                //Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), "!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        Call call2 = calApi.finish(backupGuid);
        call2.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });




        return rootView;
    }

    void saveContekst(){
        List<Contekst> lstContekst = MyApplication.getDatabase().contextDao().getAll();
        for(int k = 0; k < lstContekst.size(); k++){
            final Contekst c = lstContekst.get(k);
            Call<ResponseBody> b = calApi4.createContekst(lstContekst.get(k));
            b.enqueue(new Callback<ResponseBody>() {


                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //Log.e("CONTEXT", c.getTitle());
                    //Log.e("ResponseBody", response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    void saveTag(){
        List<Tag> lstTag = MyApplication.getDatabase().tagDao().getAll();
        for(int k = 0; k < lstTag.size(); k++){
            final Tag t = lstTag.get(k);
            Call<ResponseBody> b = calApi4.createTag(lstTag.get(k));
            b.enqueue(new Callback<ResponseBody>() {


                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //Log.e("TAG", t.getTitle());
                    //Log.e("ResponseBody", response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    void saveTarget(){
        List<Target> lstTarget = MyApplication.getDatabase().targetDao().getAll();
        for(int k = 0; k < lstTarget.size(); k++){
            final Target t = lstTarget.get(k);
            Call<ResponseBody> b = calApi4.createTarget(lstTarget.get(k));
            b.enqueue(new Callback<ResponseBody>() {


                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //Log.e("TAG", t.getTitle());
                    //Log.e("ResponseBody", response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }




    void saveInfostatus(){
        List<InfoStatus> lstInfoStatus = MyApplication.getDatabase().infoStatusDao().getAll();
        for(int k = 0; k < lstInfoStatus.size(); k++){
            final InfoStatus c = lstInfoStatus.get(k);
            Call<ResponseBody> q = calApi4.createInfoStatus(lstInfoStatus.get(k));
            q.enqueue(new Callback<ResponseBody>() {


                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //Log.e("INFOSTATUS", c.getTitle());
                    //Log.e("ResponseBody", response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    /*public static void delay(int secs){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }*/

}
