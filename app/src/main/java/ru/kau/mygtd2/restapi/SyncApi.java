package ru.kau.mygtd2.restapi;

//import androidx.room.Query;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.ProjectStatus;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskContextJoin;
import ru.kau.mygtd2.objects.TaskTagJoin;
import ru.kau.mygtd2.objects.TaskTemplate;
import ru.kau.mygtd2.objects.TaskTemplateContextJoin;
import ru.kau.mygtd2.objects.TaskTemplateTagJoin;

public interface SyncApi {

    @POST("sync/new")
    Call<Sync> create(@Body Sync sync);

    @POST("sync/update")
    Call<Sync> update(@Body Sync sync);

    @GET("sync/getlastsync")
    Call<Long> getlastsync();

    //@Headers("Content-Type: text/plain")
    //@HTTP(method = "GET", path = "sync/getlastsyncdevice", hasBody = true)
    @GET("sync/getlastsyncdevice")
    Call<Long> getlastsyncdevice(@Query("deviceGuid") String deviceGuid);


    @GET("sync/getlstsyncsdevice")
    Call<List<Sync>> getlstsyncsdevice(@Query("deviceGuid") String deviceGuid);

    // Справочники
    // Контекст

    @POST("dict/contekst/new")
    Call<Contekst> createContekst(@Body Contekst contekst);

    @GET("dict/contekst/getall")
    Call<List<Contekst>> getAllContext();

    @POST("dict/project/new")
    Call<Project> createProject(@Body Project project);

    @POST("dict/projectstatus/new")
    Call<ProjectStatus> createProjectStatus(@Body ProjectStatus projectStatus);

    @POST("dict/tag/new")
    Call<Tag> createTag(@Body Tag tag);

    @POST("dict/target/new")
    Call<Target> createTarget(@Body Target target);

    @POST("dict/tasktemplate/new")
    Call<TaskTemplate> createTaskTemplate(@Body TaskTemplate taskTemplate);

    @POST("dict/tasktemplatecontextjoin/new")
    Call<TaskTemplateContextJoin> createTaskTemplateContextJoin(@Body TaskTemplateContextJoin taskTemplateContextJoin);

    @POST("dict/tasktemplatetagjoin/new")
    Call<TaskTemplateTagJoin> createTaskTemplateTagJoin(@Body TaskTemplateTagJoin taskTemplateTagJoin);

    @POST("tasks/settasksforupdate")
    Call<Task> settasksforupdate(@Body Task task);

    @POST("tasks/settaskstagjoin/{taskGuid}")
    Call<TaskTagJoin> settasktagjoin(@Path("taskGuid") String taskGuid, @Body List<TaskTagJoin> taskTagJoinList);

    @POST("tasks/settaskscontextjoin/{taskGuid}")
    Call<TaskContextJoin> settaskscontextjoin(@Path("taskGuid") String taskGuid, @Body List<TaskContextJoin> taskContextJoinList);

    @GET("tasks/gettasksforupdate/{date}")
    Call<List<Task>> gettasksforupdate(@Path("date") long dateEdit);

    @POST("device/new")
    Call<Device> createDevice(@Body Device device);

    @GET("device/getalldevices")
    Call<List<Device>> getAllDevices();

    @GET("dict/project/getall")
    Call<List<Project>> getAllProjects();

}
