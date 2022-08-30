package ru.kau.mygtd2.utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.exceptions.codec.HttpException;
import ru.kau.mygtd2.restapi.SyncApi;

public class Synchronisation {

    private static SyncApi calApi;
    private static Long l;

    public static Long getLastSyncDevice() throws HttpException {
        calApi = Controller.getSyncApi();
        //Call<Long> call = calApi.getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        Call<Long> call = calApi.getlastsyncdevice(MyApplication.getDatabase().deviceDao().getGuidCurrentDevice());
        //Call<Long> call = calApi.getlastsyncdevice("678678");
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call call, Response response) {
                l = (Long) response.body();
                System.out.println("Long: " + l);

            }
            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage());
                //isError = true;
            }
        });
        return l;
    }


}
