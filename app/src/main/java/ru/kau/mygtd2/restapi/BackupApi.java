package ru.kau.mygtd2.restapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.kau.mygtd2.objects.Backup;

public interface BackupApi {


    @POST("backup/create")
    Call<Backup> create(@Body Backup backup);

    @POST("backup/new")
    Call<Backup> create2(@Body Backup backup);
}
