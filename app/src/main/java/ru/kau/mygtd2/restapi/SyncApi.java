package ru.kau.mygtd2.restapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.kau.mygtd2.objects.Sync;

public interface SyncApi {

    @POST("sync/new")
    Call<Sync> create(@Body Sync sync);

    @GET("sync/getlastsync")
    Call<Long> getlastsync();

}
