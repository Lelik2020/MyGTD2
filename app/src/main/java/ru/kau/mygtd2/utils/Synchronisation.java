package ru.kau.mygtd2.utils;

import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kau.mygtd2.adapters.SyncAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.exceptions.codec.HttpException;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.restapi.SyncApi;

public class Synchronisation {

    private static SyncApi calApi;
    private static Long l;

    public static SyncApi getSyncApi(){
        if (calApi == null){
            calApi = Controller.getSyncApi();
        }
        return calApi;
    }

    public static Long getLastSyncDevice() throws HttpException {
        //calApi = Controller.getSyncApi();
        //Call<Long> call = calApi.getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        Call<Long> call = getSyncApi().getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        //Call<Long> call = calApi.getlastsyncdevice("678678");
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call call, Response response) {
                l = (Long) response.body();
                System.out.println("Long: " + l);
                //return l;
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage());
                try {
                    throw new HttpException();
                } catch (HttpException e) {
                    //throw new RuntimeException(e);
                }
                //isError = true;
            }
        });
        return l;
    }

    public static List<Sync> getListSyncsDevice(String deviceGuid) throws HttpException {

        List<Sync> lstSync = new ArrayList<Sync>();
        Call<List<Sync>> call2 = getSyncApi().getlstsyncsdevice(deviceGuid);

        call2.enqueue(new Callback<List<Sync>>() {

            @Override
            public void onResponse(Call<List<Sync>> call, Response<List<Sync>> response) {
                if (response.isSuccessful()) {
                    //System.out.println(response.body().size());
                    lstSync.addAll(response.body());

                }

            }



            @Override
            public void onFailure(Call<List<Sync>> call, Throwable t) {
                try {
                    throw new HttpException();
                } catch (HttpException e) {
                    //throw new RuntimeException(e);
                }
            }
        });
        return lstSync;
    }


}
