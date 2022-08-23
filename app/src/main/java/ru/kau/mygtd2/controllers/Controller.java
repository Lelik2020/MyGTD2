package ru.kau.mygtd2.controllers;

import static ru.kau.mygtd2.enums.TypeSetting.IPSERVERBACKUP;
import static ru.kau.mygtd2.enums.TypeSetting.IPSERVERSYNC;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kau.mygtd2.common.enums.PrStatus;
import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.common.enums.TypeOfInfo;
import ru.kau.mygtd2.common.enums.TypeOfTask;
import ru.kau.mygtd2.jsonconvert.DateConverter;
import ru.kau.mygtd2.jsonconvert.ProjectStatusConverter;
import ru.kau.mygtd2.jsonconvert.StatusConverter;
import ru.kau.mygtd2.jsonconvert.TypeOfInfoConverter;
import ru.kau.mygtd2.jsonconvert.TypeOfTaskConverter;
import ru.kau.mygtd2.restapi.BackupApi;
import ru.kau.mygtd2.restapi.DirectoriesApi;
import ru.kau.mygtd2.restapi.InformationsApi;
import ru.kau.mygtd2.restapi.SyncApi;
import ru.kau.mygtd2.restapi.TasksApi;
import ru.kau.mygtd2.restapi.TasksApi2;
import ru.kau.mygtd2.utils.Settings;

public class Controller {

    public static SyncApi getSyncApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Date.class, new DateConverter())
                .registerTypeAdapter(Status.class, new StatusConverter())
                .registerTypeAdapter(TypeOfTask.class, new TypeOfTaskConverter())
                //.registerTypeAdapter(PrStatus.class, new ProjectStatusConverter())
                .create();
        System.out.println("URL: " + Settings.getStringSetting(IPSERVERSYNC));
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Settings.getStringSetting(IPSERVERSYNC))
                .build();

        SyncApi calApi = retrofit.create(SyncApi.class);
        return calApi;
    }

    public static SyncApi getSyncApi2(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(PrStatus.class, new ProjectStatusConverter())
                .create();
        //System.out.println("URL: " + Settings.getStringSetting(IPSERVERSYNC));
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Settings.getStringSetting(IPSERVERSYNC))
                .build();

        SyncApi calApi = retrofit.create(SyncApi.class);
        return calApi;
    }

    public static BackupApi getCalApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.getStringSetting(IPSERVERBACKUP))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BackupApi calApi = retrofit.create(BackupApi.class);
        return calApi;
    }

    public static InformationsApi getCalApi3(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Date.class, new DateConverter())
                .registerTypeAdapter(TypeOfInfo.class, new TypeOfInfoConverter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.getStringSetting(IPSERVERBACKUP))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        InformationsApi calApi = retrofit.create(InformationsApi.class);
        return calApi;
    }

    public static DirectoriesApi getCalApi4(){
        Gson gson = new GsonBuilder()
                .setLenient()
                //.registerTypeAdapter(Date.class, new DateConverter())
                //.registerTypeAdapter(TypeOfInfo.class, new TypeOfInfoConverter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.getStringSetting(IPSERVERBACKUP))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DirectoriesApi calApi = retrofit.create(DirectoriesApi.class);
        return calApi;
    }

    public static TasksApi getCalApi2(){
        Gson gson = new GsonBuilder()
                .setLenient()
                //.setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Date.class, new DateConverter())
                .registerTypeAdapter(Status.class, new StatusConverter())
                .registerTypeAdapter(TypeOfTask.class, new TypeOfTaskConverter())
                .create();

        /*JsonDeserializer<Long> deserializer = new JsonDeserializer<Long>() {
            @Override
            public Long deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                try{
                    if(json==null){
                        return new Long(0);
                    }
                    else{
                        String dateString = json.getAsString();
                        long dateLong = DateFormatUtil.getLongServerTime(dateString);
                        return new Long(dateLong);
                    }
                }
                catch(ParseException e){
                    return new Long(0);
                }
            }
        };*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.getStringSetting(IPSERVERBACKUP))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TasksApi calApi = retrofit.create(TasksApi.class);
        return calApi;
    }

    public static TasksApi2 getTasksApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                //.setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Date.class, new DateConverter())
                .registerTypeAdapter(Status.class, new StatusConverter())
                .registerTypeAdapter(TypeOfTask.class, new TypeOfTaskConverter())
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.getStringSetting(IPSERVERSYNC))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TasksApi2 calApi = retrofit.create(TasksApi2.class);
        return calApi;
    }



}
