package ru.kau.mygtd2.fragments;




import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.BackupAdapter;

import ru.kau.mygtd2.objects.Backup;

import ru.kau.mygtd2.utils.RemoteBackup;
import ru.kau.mygtd2.utils.Utils;


public class RestoreRemoteBackupsFragment extends Fragment {

    private RecyclerView recyclerView;

    //private List<Backup> lstBackups = new ArrayList<Backup>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restoreremotebackups_fragment, null);

        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.lstbackups);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Log.e("IP1: ", Utils.getLocalIpAddress());
        //Log.e("IP2: ", Utils.getLocalIpAddress2());
        //Log.e("IP3: ", Utils.getLocalIpAddress3());

        Observable.create((ObservableOnSubscribe<List<Backup>>) lstBackups -> {
                    try {
                        //List<Backup> data = RemoteBackup.getListBackupsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                        List<Backup> data = RemoteBackup.getListBackupsDevice("");
                        //Log.e("SIZE: ", String.valueOf(data.size()));
                        //BackupAdapter backupsAdapter = new BackupAdapter(getActivity(), data);

                        Handler handler = new Handler(getContext().getMainLooper());
                        handler.post( new Runnable() {
                            @Override
                            public void run() {
                                BackupAdapter backupsAdapter = new BackupAdapter(getContext(), data);
                                recyclerView.setAdapter(backupsAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        } );

                        //e.onNext(data);
                    } catch (Exception ex) {
                        //Log.e("ERROR: ", ex.getMessage());
                        ex.printStackTrace();
                        lstBackups.onError(ex);
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

        return rootView;
    }

    /*Observable.create((ObservableOnSubscribe<Long>) e -> {
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

    });*/



}
