package ru.kau.mygtd2.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.exceptions.codec.HttpException;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.restapi.BackupApi;


public class RemoteBackup {

    private static BackupApi calApi;

    public static BackupApi getBackupAPI(){
        if (calApi == null){
            calApi = Controller.getCalApi();
        }
        return calApi;
    }

    public static List<Backup> getListBackupsDevice(String deviceGuid) throws HttpException {

        List<Backup> lstBackups = new ArrayList<Backup>();
        Call<List<Backup>> call2 = getBackupAPI().getlstbackupsdevice(deviceGuid);



        try {
            Response<List<Backup>> response = call2.execute();
            lstBackups = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return lstBackups;
    }

    public static List<Contekst> getListContekstDevice() throws HttpException {

        List<Contekst> lstConteksts = new ArrayList<Contekst>();
        Call<List<Contekst>> call2 = getBackupAPI().getLstConteksts();



        try {
            Response<List<Contekst>> response = call2.execute();
            lstConteksts = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return lstConteksts;
    }

    public static List<Tag> getListTag() throws HttpException {

        List<Tag> lstTags = new ArrayList<Tag>();
        Call<List<Tag>> call2 = getBackupAPI().getLstTags();



        try {
            Response<List<Tag>> response = call2.execute();
            lstTags = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return lstTags;
    }

    public static List<Target> getListTarget() throws HttpException {

        List<Target> lstTargets = new ArrayList<Target>();
        Call<List<Target>> call2 = getBackupAPI().getLstTargets();



        try {
            Response<List<Target>> response = call2.execute();
            lstTargets = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return lstTargets;
    }

    public static List<Task> getListTask(String backupGuid) throws HttpException {

        List<Task> lstTasks = new ArrayList<Task>();
        Call<List<Task>> call2 = getBackupAPI().getLstTasks(backupGuid);



        try {
            Response<List<Task>> response = call2.execute();
            lstTasks = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return lstTasks;
    }

}
