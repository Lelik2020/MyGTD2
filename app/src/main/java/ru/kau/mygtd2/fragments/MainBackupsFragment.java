package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.BackupAdapter;
import ru.kau.mygtd2.adapters.BackupsAdapter;

import ru.kau.mygtd2.adapters.SyncAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.interfaces.ClickListener;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.Category;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.utils.RemoteBackup;
import ru.kau.mygtd2.utils.Synchronisation;

public class MainBackupsFragment extends Fragment implements ClickListener {

    private RecyclerView recyclerView;

    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private BackupsAdapter mainAdapter;

    List<Category> lstCategories;

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.mainbackups_fragment, null);


        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();

        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview1);

        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.lstbackups);

        recyclerView3 = (RecyclerView) rootView.findViewById(R.id.lstsyncs);

        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        recyclerView.setBackground(drawable);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lstCategories = new ArrayList<Category>();
        Category category = new Category();
        category.setId(1);
        category.setTitle("Локальные бэкапы");
        category.setDescription("Локальные бэкапы");
        category.setGrp(0);
        lstCategories.add(category);

        category = new Category();
        category.setId(2);
        category.setTitle("Удаленные бэкапы");
        category.setDescription("Удаленные бэкапы");
        category.setGrp(0);
        lstCategories.add(category);

        category = new Category();
        category.setId(3);
        category.setTitle("Восстановление из локального бэкапа");
        category.setDescription("Восстановление из локального бэкапа");
        category.setGrp(0);
        lstCategories.add(category);

        category = new Category();
        category.setId(4);
        category.setTitle("Восстановление из удаленного бэкапа");
        category.setDescription("Восстановление из удаленного бэкапа");
        category.setGrp(0);
        lstCategories.add(category);


        mainAdapter = new BackupsAdapter(getActivity(), lstCategories);
        mainAdapter.setClickListener(this);

        recyclerView.setAdapter(mainAdapter);

        /*Observable.create((ObservableOnSubscribe<List<Sync>>) lstSyncs -> {
                    try {
                        List<Sync> data = Synchronisation.getListSyncsDevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
                        //Log.e("SIZE: ", String.valueOf(data.size()));
                        Handler handler = new Handler(getContext().getMainLooper());
                        handler.post( new Runnable() {
                            @Override
                            public void run() {
                                SyncAdapter syncsAdapter = new SyncAdapter(getActivity(), data);
                                recyclerView3.setAdapter(syncsAdapter);
                                recyclerView3.setVisibility(View.VISIBLE);
                            }
                        } );

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
                            Log.e("rest api  345345, error: 890890", throwable.getStackTrace().toString());
                        });*/


        return rootView;
    }


    @Override
    public void itemClicked(View view, int position, int grp) {

    }

    @Override
    public void itemClicked(View view, int position) {
        //Toast.makeText(getContext(), "1111111", Toast.LENGTH_SHORT).show();
        Fragment fragment;
        switch (lstCategories.get(position).getId()) {
            case 2:

                fragment = new RemoteBackupsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("RemoteBackupsFragment").replace(R.id.frame_container, fragment, "RemoteBackupsFragment").commit();
                break;

            case 3:



                break;
            case 4:
                fragment = new RestoreRemoteBackupsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("RestoreRemoteBackupsFragment").replace(R.id.frame_container, fragment, "RestoreRemoteBackupsFragment").commit();



                break;

        }

    }
}
