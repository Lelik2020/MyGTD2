package ru.kau.mygtd2.restapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;


public interface BackupApi {


    @POST("backup/create")
    Call<Backup> create(@Body Backup backup);

    @POST("backup/new")
    Call<Backup> create2(@Body Backup backup);

    @POST("backup/finish")
    Call finish(String backupGuid);

    @GET("backup/getlstbackups")
    Call<List<Backup>> getlstbackupsdevice(@Query("deviceGuid") String deviceGuid);

    @GET("contekst/getlstconteksts")
    Call<List<Contekst>> getLstConteksts();

    @GET("tag/getlsttags")
    Call<List<Tag>> getLstTags();

    @GET("target/getlsttargets")
    Call<List<Target>> getLstTargets();

    @GET("tasks/getlsttasks")
    Call<List<Task>> getLstTasks(@Query("backupGuid") String backupGuid);
}
