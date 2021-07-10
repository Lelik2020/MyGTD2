package ru.kau.mygtd2.restapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.kau.mygtd2.objects.Information;

public interface InformationsApi {


    @POST("informations/new")
    Call<Information> create(@Body Information information);

}
