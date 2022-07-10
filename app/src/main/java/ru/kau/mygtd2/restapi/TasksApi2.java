package ru.kau.mygtd2.restapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.kau.mygtd2.objects.Task;

public interface TasksApi2 {

    @POST("tasks/settasksforupdate")
    Call<String> settasksforupdate(@Body List<Task> lstTasks);


}
