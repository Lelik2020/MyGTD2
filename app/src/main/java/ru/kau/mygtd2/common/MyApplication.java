package ru.kau.mygtd2.common;

import static ru.kau.mygtd2.db.AppDatabase.MIGRATION_16_17;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import ru.kau.mygtd2.db.AppDatabase;

//import android.arch.persistence.room.Room;

public class MyApplication extends Application {

    public static MyApplication instance;

    private static AppDatabase database;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        database = Room.databaseBuilder(this, AppDatabase.class, "MyGTD")
                //.addMigrations(AppDatabase.MIGRATION_1_2, AppDatabase.MIGRATION_2_3, AppDatabase.MIGRATION_3_4, AppDatabase.MIGRATION_4_5)
                //.addMigrations(AppDatabase.MIGRATION_1_2, AppDatabase.MIGRATION_2_3/*, AppDatabase.MIGRATION_2_3, AppDatabase.MIGRATION_3_4, AppDatabase.MIGRATION_4_5*/)
                //.addMigrations(MIGRATION_1_2)
                //.fallbackToDestructiveMigration().addMigrations(MIGRATION_2_3n)
                //.addMigrations(MIGRATION_3_4)
                //.addMigrations(MIGRATION_4_5)
                //.addMigrations(MIGRATION_5_6)
                //.addMigrations(MIGRATION_6_7)
                //.addMigrations(MIGRATION_7_8)
                //.addMigrations(MIGRATION_8_9)
                //.addMigrations(MIGRATION_9_10)
                //.addMigrations(MIGRATION_10_11)
                //.addMigrations(MIGRATION_11_12)
                //.addMigrations(MIGRATION_12_13)
                //.addMigrations(MIGRATION_13_14)
                //.addMigrations(MIGRATION_14_15)
                //.addMigrations(MIGRATION_15_16)
                .addMigrations(MIGRATION_16_17)

                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        //DbCreator.firstInit();
        //DbCreator.tasksUpdate2();
        //DbCreator.infoUpdate();
        //DbCreator.tasksUpdate3();
        //DbCreator.infoUpdate2();
        //DbCreator.commentUpdate();
        //DbCreator.firstInitSpr6();
        //DbCreator.firstInit();
        //DbCreator.taskUpdate();
        //DbCreator.taskUpdate2();
    }

    //77

    public static MyApplication getInstance() {
        return instance;
    }

    public static AppDatabase getDatabase() {
        return database;
    }

    public static Context getContext() {return context;}
}
