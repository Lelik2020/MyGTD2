package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.utils.RemoteBackup;
import ru.kau.mygtd2.utils.Utils;
import stream.custombutton.CustomButton;


public class BackupAdapter extends RecyclerView.Adapter<BackupAdapter.ViewHolder>{

    private Context c;
    private List<Backup> lstBackups;
    public BackupAdapter(Context context, List<Backup> lstBackup) {
        this.c = context;
        this.lstBackups = lstBackup;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.backupitem_card, parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(lstBackups.get(position).getGuid());
        holder.backupbegin.setText(Utils.dateToString(new Date(lstBackups.get(position).getDateBegin())));
        holder.backupend.setText(Utils.dateToString(new Date(lstBackups.get(position).getDateEnd())));
        //Log.e("ERROR: ", String.valueOf(holder.countTasks.getText()));
        holder.countTasks.setText(String.valueOf(lstBackups.get(position).getCountOfTask()));
        //holder.countTasks.setText("ТЕСТ");
        Log.e("ERROR: ", lstBackups.get(position).getGuid() + "  " + lstBackups.get(position).getDateBegin());

        holder.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  --------------------------------------------------------
                // Восстанавливаем информацию об устройстве
                // Меняем идентификатор устройства
                Device device = MyApplication.getDatabase().deviceDao().getCurrentDevice();

                // Пока не понимаю, нужно ли восстанавливать полностью, включая устройство

                //device.setGuid(lstBackups.get(position).getDeviceguid());
                //MyApplication.getDatabase().deviceDao().update(device);

                // ---------------------------------------------------------------------------

                // Получаем справочники
                // -------- Получаем справочник контекстов ------------------------

                Observable.create((ObservableOnSubscribe<List<Contekst>>) lstConteksts -> {
                            try {
                                //List<Backup> data = RemoteBackup.getListBackupsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                                List<Contekst> data = RemoteBackup.getListContekstDevice();
                                Log.e("SIZE: ", String.valueOf(data.size()));
                                //BackupAdapter backupsAdapter = new BackupAdapter(getActivity(), data);
                                for(int i = 0; i < data.size(); i++){
                                    MyApplication.getDatabase().contextDao().insert(data.get(i));
                                }


                                /*Handler handler = new Handler(c.getMainLooper());
                                handler.post( new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                } );*/

                                //e.onNext(data);
                            } catch (Exception ex) {
                                //Log.e("ERROR: ", ex.getMessage());
                                ex.printStackTrace();
                                lstConteksts.onError(ex);
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
                                    Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace()));
                            /*for(int i = 0; i < throwable.getStackTrace().length; i++) {
                                Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace().));
                            }*/
                                });

                // Получаем список тэгов из справочника

                Observable.create((ObservableOnSubscribe<List<Tag>>) lstTags -> {
                            try {
                                //List<Backup> data = RemoteBackup.getListBackupsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                                List<Tag> data = RemoteBackup.getListTag();
                                Log.e("SIZE: ", String.valueOf(data.size()));
                                //BackupAdapter backupsAdapter = new BackupAdapter(getActivity(), data);
                                for(int i = 0; i < data.size(); i++){
                                    MyApplication.getDatabase().tagDao().insert(data.get(i));
                                }


                                /*Handler handler = new Handler(c.getMainLooper());
                                handler.post( new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                } );*/

                                //e.onNext(data);
                            } catch (Exception ex) {
                                //Log.e("ERROR: ", ex.getMessage());
                                ex.printStackTrace();
                                lstTags.onError(ex);
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
                                    Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace()));
                            /*for(int i = 0; i < throwable.getStackTrace().length; i++) {
                                Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace().));
                            }*/
                                });

                // ----------------------------------------------------------------

                // Получаем список целей из справочника

                Observable.create((ObservableOnSubscribe<List<Target>>) lstTargets -> {
                            try {
                                //List<Backup> data = RemoteBackup.getListBackupsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                                List<Target> data = RemoteBackup.getListTarget();
                                Log.e("SIZE: ", String.valueOf(data.size()));
                                //BackupAdapter backupsAdapter = new BackupAdapter(getActivity(), data);
                                for(int i = 0; i < data.size(); i++){
                                    MyApplication.getDatabase().targetDao().insert(data.get(i));
                                }


                                /*Handler handler = new Handler(c.getMainLooper());
                                handler.post( new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                } );*/

                                //e.onNext(data);
                            } catch (Exception ex) {
                                //Log.e("ERROR: ", ex.getMessage());
                                ex.printStackTrace();
                                lstTargets.onError(ex);
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
                                    Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace()));
                            /*for(int i = 0; i < throwable.getStackTrace().length; i++) {
                                Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace().));
                            }*/
                                });



                // ----------------------------------------------------------------

                // Обрабатываем задачи

                Observable.create((ObservableOnSubscribe<List<Task>>) lstTasks -> {
                            try {
                                //List<Backup> data = RemoteBackup.getListBackupsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                                List<Task> data = RemoteBackup.getListTask(lstBackups.get(position).getGuid());
                                //Log.e("TASK SIZE: ", String.valueOf(data.size()));
                                //BackupAdapter backupsAdapter = new BackupAdapter(getActivity(), data);

                                // Удаляем таблицу tasks (удаляем все задачи)

                                MyApplication.getDatabase().taskDao().deleteAll();

                                // --------------------------------------------------------------------------------------


                                for(int i = 0; i < data.size(); i++){
                                    //MyApplication.getDatabase().taskDao().insert(data.get(i));
                                    Task task = data.get(i);
                                    task.setDeviceguid(device.getGuid());
                                    if (task.getDateBegin().getTime() == 0){
                                        task.setDateBegin(null);
                                        task.setDateBeginStr(null);
                                    }
                                    if (task.getDateEnd().getTime() == 0){
                                        task.setDateEnd(null);
                                        task.setDateEndStr(null);
                                    }
                                    if (task.getDateClose().getTime() == 0){
                                        task.setDateClose(null);
                                        task.setDateCloseStr(null);
                                    }
                                    MyApplication.getDatabase().taskDao().insert(task);
                                    //Log.e("TASKGUID", data.get(i).getGuid());
                                }


                                /*Handler handler = new Handler(c.getMainLooper());
                                handler.post( new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                } );*/

                                //e.onNext(data);
                            } catch (Exception ex) {
                                //Log.e("ERROR: ", ex.getMessage());
                                ex.printStackTrace();
                                lstTasks.onError(ex);
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
                                    Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace()));
                            /*for(int i = 0; i < throwable.getStackTrace().length; i++) {
                                Log.e("rest api  345345, error: 890890", String.valueOf(throwable.getStackTrace().));
                            }*/
                                });

                // ----------------------------------------------------------------

                // ----------------------------------------------------------------

                //
            }
        });



    }

    @Override
    public int getItemCount() {
        return lstBackups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        TextView backupbegin;
        TextView backupend;

        TextView countTasks;

        CustomButton btnRestore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.backuptitle);
            btnRestore = itemView.findViewById(R.id.btnrestore);
            backupbegin = (TextView) itemView.findViewById(R.id.backupbegin);
            backupend = (TextView) itemView.findViewById(R.id.backupend);
            countTasks = (TextView) itemView.findViewById(R.id.countTasks);

        }
    }



}
