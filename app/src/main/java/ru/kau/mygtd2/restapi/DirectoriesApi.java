package ru.kau.mygtd2.restapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;

public interface DirectoriesApi {


    @DELETE("contekst/deleteall")
    Call<ResponseBody> contekstDeleteAll();

    @POST("contekst/new")
    Call<ResponseBody> createContekst(@Body Contekst contekst);

    @DELETE("infostatus/deleteall")
    Call<ResponseBody> infostatusDeleteAll();

    @POST("infostatus/new")
    Call<ResponseBody> createInfoStatus(@Body InfoStatus infoStatus);

    @DELETE("tag/deleteall")
    Call<ResponseBody> tagDeleteAll();

    @POST("tag/new")
    Call<ResponseBody> createTag(@Body Tag tag);

    @DELETE("target/deleteall")
    Call<ResponseBody> targetDeleteAll();

    @POST("target/new")
    Call<ResponseBody> createTarget(@Body Target target);

}
