package ru.kau.mygtd2.restapi;

//import androidx.room.Query;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
//import retrofit2.http.Headers;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.kau.mygtd2.objects.Sync;

public interface SyncApi {

    @POST("sync/new")
    Call<Sync> create(@Body Sync sync);

    @GET("sync/getlastsync")
    Call<Long> getlastsync();

    //@Headers("Content-Type: text/plain")
    //@HTTP(method = "GET", path = "sync/getlastsyncdevice", hasBody = true)
    @POST("sync/getlastsyncdevice")
    Call<Long> getlastsyncdevice(@Body String deviceGuid);

    @GET("sync/getlastsyncdevice2")
    Call<Long> getlastsyncdevice2(@Query("deviceGuid") String deviceGuid);


    @GET("sync/gettst")
    Call<Long> gettst(String deviceGuid);
}
