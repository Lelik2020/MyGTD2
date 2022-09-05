package ru.kau.mygtd2.utils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
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

    public static Long createDevice(Device device) throws HttpException{

        /*calApi.createDevice(device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Device>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Device device) {

                    }
                });*/

        Call<Device> deviceCall = calApi.createDevice(device);

        try {
            Response response = deviceCall.execute();
            if (response.isSuccessful()) {
                System.out.println("STATUS111: " + response.code());
                System.out.println("ERROR111: " + response.code() + "   " + response.errorBody());

            } else {
                System.out.println("ERROR222: " + response.code() + "   " + response.errorBody());
            }
        }
        catch (Exception e){
            System.out.println("ERROR333: " + e.getMessage());
            throw new HttpException();
        }

        /*deviceCall.enqueue(new Callback() {

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

                System.out.println("ERROR333: " + t.getMessage());
                try {
                    throw new HttpException();
                } catch (HttpException e) {
                    throw new RuntimeException(e);
                }
                //isError = true;
            }
        });*/

        return 0L;

    }


}
