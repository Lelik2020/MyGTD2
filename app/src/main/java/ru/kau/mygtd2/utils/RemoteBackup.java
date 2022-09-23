package ru.kau.mygtd2.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.kau.mygtd2.controllers.Controller;
import ru.kau.mygtd2.exceptions.codec.HttpException;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.restapi.BackupApi;
import ru.kau.mygtd2.restapi.SyncApi;


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

        List<Contekst> lstBackups = new ArrayList<Contekst>();
        Call<List<Contekst>> call2 = getBackupAPI().getlstbackupsdevice(deviceGuid);



        try {
            Response<List<Backup>> response = call2.execute();
            lstBackups = response.body();
        }
        catch (Exception e){

            //throw new HttpException();
        }

        return lstBackups;
    }

}
