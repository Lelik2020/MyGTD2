package ru.kau.mygtd2.restapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.kau.mygtd2.objects.Task;

public interface TasksApi {

    @POST("tasks/create")
    Call<Task> create(@Body Task backup);

    @POST("tasks/new")
    Call<Task> create2(@Body Task task);

}
