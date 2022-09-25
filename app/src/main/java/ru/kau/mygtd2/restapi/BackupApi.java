package ru.kau.mygtd2.restapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.kau.mygtd2.objects.Backup;


public interface BackupApi {


    @POST("backup/create")
    Call<Backup> create(@Body Backup backup);

    @POST("backup/new")
    Call<Backup> create2(@Body Backup backup);

    @GET("backup/getlstbackups")
    Call<List<Backup>> getlstbackupsdevice(@Query("deviceGuid") String deviceGuid);

    //@GET("backup/getlstbackups")
    //Call<List<Backup>> getlstbackupsdevice(@Query("deviceGuid") String deviceGuid);
}
