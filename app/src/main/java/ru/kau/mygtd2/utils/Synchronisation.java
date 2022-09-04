package ru.kau.mygtd2.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.exceptions.codec.HttpException;
import ru.kau.mygtd2.objects.Device;
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

    public static Long getLastSyncDevice2() throws HttpException {
        Call<Long> call = getSyncApi().getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call call, Response response) {
                l = (Long) response.body();
                System.out.println("Long: " + l);

            }
            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage());
                try {
                    throw new HttpException();
                } catch (HttpException e) {

                }

            }
        });
        return l;
    }

    public static Long getLastSyncDevice() { //throws HttpException, IOException {
        l = null;
        Call<Long> call = getSyncApi().getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        try {
            Response<Long> response = call.execute();
            l = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return l;
    }

    public static List<Sync> getListSyncsDevice2(String deviceGuid) throws HttpException {

        List<Sync> lstSync = new ArrayList<Sync>();
        Call<List<Sync>> call2 = getSyncApi().getlstsyncsdevice(deviceGuid);

        call2.enqueue(new Callback<List<Sync>>() {

            @Override
            public void onResponse(Call<List<Sync>> call, Response<List<Sync>> response) {
                if (response.isSuccessful()) {
                    lstSync.addAll(response.body());

                }

            }



            @Override
            public void onFailure(Call<List<Sync>> call, Throwable t) {
                try {
                    throw new HttpException();
                } catch (HttpException e) {

                }
            }
        });
        return lstSync;
    }

    public static List<Sync> getListSyncsDevice(String deviceGuid) throws HttpException {

        List<Sync> lstSync = new ArrayList<Sync>();
        Call<List<Sync>> call2 = getSyncApi().getlstsyncsdevice(deviceGuid);



        try {
            Response<List<Sync>> response = call2.execute();
            lstSync = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return lstSync;
    }

    public static void createDevice(String deviceGuid) throws HttpException{

        Call<Device> deviceCall = calApi.createDevice(MyApplication.getDatabase().deviceDao().getCurrentDevice());
        deviceCall.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                //System.out.println("CONTEXT");
                if (response.isSuccessful()) {
                    System.out.println("STATUS111: " + response.code());
                    System.out.println("ERROR111: " + response.code() + "   " + response.errorBody());

                } else {
                    System.out.println("ERROR222: " + response.code() + "   " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                System.out.println("STATUS222: " + t.getMessage());
                //isError = true;
            }
        });



    }


}
