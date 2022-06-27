package ru.kau.mygtd2.db;

//import android.arch.persistence.room.Database;

//import android.arch.persistence.room.RoomDatabase;

//import android.arch.persistence.room.TypeConverters;

//import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

import ru.kau.mygtd2.BuildConfig;
import ru.kau.mygtd2.db.dao.CategoryDao;
import ru.kau.mygtd2.db.dao.CommentDao;
import ru.kau.mygtd2.db.dao.ContextDao;
import ru.kau.mygtd2.db.dao.DeviceDao;
import ru.kau.mygtd2.db.dao.DeviceInfoDao;
import ru.kau.mygtd2.db.dao.InfoStatusDao;
import ru.kau.mygtd2.db.dao.InfoTypesDao;
import ru.kau.mygtd2.db.dao.InformationDao;
import ru.kau.mygtd2.db.dao.MeetingDao;
import ru.kau.mygtd2.db.dao.PriorityDao;
import ru.kau.mygtd2.db.dao.ProjectDao;
import ru.kau.mygtd2.db.dao.ProjectStatusDao;
import ru.kau.mygtd2.db.dao.TagDao;
import ru.kau.mygtd2.db.dao.TargetDao;
import ru.kau.mygtd2.db.dao.TaskCategoryDao;
import ru.kau.mygtd2.db.dao.TaskContextJoinDao;
import ru.kau.mygtd2.db.dao.TaskDao;
import ru.kau.mygtd2.db.dao.TaskStatusDao;
import ru.kau.mygtd2.db.dao.TaskTagJoinDao;
import ru.kau.mygtd2.db.dao.TaskTypesDao;
import ru.kau.mygtd2.objects.Category;
import ru.kau.mygtd2.objects.Comment;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.objects.DeviceInfo;
import ru.kau.mygtd2.objects.InfoProjectJoin;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.InfoTypes;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Meeting;
import ru.kau.mygtd2.objects.Priority;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.ProjectStatus;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskCategory;
import ru.kau.mygtd2.objects.TaskContextJoin;
import ru.kau.mygtd2.objects.TaskStatus;
import ru.kau.mygtd2.objects.TaskTagJoin;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.Converters;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHSECONDS;

@Database(entities = {  Task.class, Information.class, Project.class, Category.class, TaskStatus.class, Contekst.class,
                        Tag.class, InfoStatus.class,
                        TaskContextJoin.class, InfoProjectJoin.class, TaskTagJoin.class,
                        Priority.class, Meeting.class, Comment.class, ProjectStatus.class, Target.class,
                        TaskTypes.class
                        , InfoTypes.class
                        , Device.class
                        , DeviceInfo.class
                        , TaskCategory.class
                        },
                        //version = 3
                        version = BuildConfig.DB
                        , exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final Migration MIGRATION_2_3n = new Migration(2, 3) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN guid TEXT NOT NULL DEFAULT ''");
            database.execSQL("ALTER TABLE tasks ADD COLUMN deviceguid TEXT NOT NULL DEFAULT ''");
            //DbCreator.tasksUpdate();
        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {

            //database.execSQL("CREATE UNIQUE INDEX index_tasks_guid ON [tasks]([guid]);");

            //database.execSQL("ALTER TABLE comments ADD COLUMN infoguid TEXT NOT NULL DEFAULT ''");
            //database.execSQL("ALTER TABLE comments ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");

            database.execSQL("ALTER TABLE tasktags ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");
        }
    };

    public static final Migration MIGRATION_12_13 = new Migration(12, 13) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {

            //database.execSQL("CREATE UNIQUE INDEX index_tasks_guid ON [tasks]([guid]);");

            //database.execSQL("ALTER TABLE comments ADD COLUMN infoguid TEXT NOT NULL DEFAULT ''");
            //database.execSQL("ALTER TABLE comments ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");

            database.execSQL("ALTER TABLE devices ADD COLUMN devicetype INTEGER NOT NULL DEFAULT 1");
        }
    };

    public static final Migration MIGRATION_11_12 = new Migration(11, 12) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE [main].[tasks] RENAME TO [_sqliteexpert_temp_table_1];");
            String sqlText;
            sqlText = "CREATE TABLE [main].[tasks](\n" +
                    "  [id] INTEGER NOT NULL, \n" +
                    "  [title] TEXT NOT NULL, \n" +
                    "  [searchtitle] TEXT, \n" +
                    "  [description] TEXT, \n" +
                    "  [info_id] INTEGER NOT NULL, \n" +
                    "  [meeting_id] INTEGER NOT NULL, \n" +
                    "  [project_id] INTEGER NOT NULL, \n" +
                    "  [target_id] INTEGER NOT NULL, \n" +
                    "  [priority_id] INTEGER NOT NULL, \n" +
                    "  [parenttask_id] INTEGER NOT NULL, \n" +
                    "  [isFavourite] INTEGER NOT NULL, \n" +
                    "  [bgColor] TEXT, \n" +
                    "  [previousStatus] INTEGER, \n" +
                    "  [status] INTEGER, \n" +
                    "  [typeOfTask] INTEGER, \n" +
                    "  [dateCreate] INTEGER, \n" +
                    "  [dateBegin] INTEGER, \n" +
                    "  [dateEnd] INTEGER, \n" +
                    "  [dateClose] INTEGER, \n" +
                    "  [dateCreateStr] TEXT, \n" +
                    "  [dateBeginStr] TEXT, \n" +
                    "  [dateEndStr] TEXT, \n" +
                    "  [dateCloseStr] TEXT, \n" +
                    "  [guid] TEXT PRIMARY KEY NOT NULL DEFAULT '', \n" +
                    "  [deviceguid] TEXT NOT NULL DEFAULT '', \n" +
                    "  [category] INTEGER NOT NULL DEFAULT 3, \n" +
                    "  [dateEdit] INTEGER, \n" +
                    "  [dateEditStr] TEXT \n" +
                    "  );";
            database.execSQL(sqlText);
            sqlText = "INSERT INTO [main].[tasks]([rowid], [id], [title], [searchtitle], [description], [info_id], [meeting_id], [project_id], [target_id], [priority_id], [parenttask_id], [isFavourite], [bgColor], [previousStatus], [status], [typeOfTask], [dateCreate], [dateBegin], [dateEnd], [dateClose], [dateCreateStr], [dateBeginStr], [dateEndStr], [dateCloseStr], [guid], [deviceguid], [category])\n" +
                    "SELECT [rowid], [id], [title], [searchtitle], [description], [info_id], [meeting_id], [project_id], [target_id], [priority_id], [parenttask_id], [isFavourite], [bgColor], [previousStatus], [status], [typeOfTask], [dateCreate], [dateBegin], [dateEnd], [dateClose], [dateCreateStr], [dateBeginStr], [dateEndStr], [dateCloseStr], [guid], [deviceguid], [category]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];";
            database.execSQL(sqlText);
            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];");
            database.execSQL("CREATE UNIQUE INDEX [main].[index_tasks_guid] ON [tasks]([guid]);");
            /*Date date = new Date();
            database.execSQL("UPDATE [main].[tasks] " +
                              "SET dateEdit = " + date.getTime() + ", " +
                              " dateEditStr = '" + Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));*/
        }
    };

    public static final Migration MIGRATION_10_11 = new Migration(10, 11) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String sqlText = "CREATE TABLE [main].[taskcategory] ([id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "[title] TEXT NOT NULL, [description] TEXT, [color] TEXT);";
            database.execSQL(sqlText);

            sqlText = "CREATE UNIQUE INDEX [index_taskcategory_title] ON [taskcategory] ([title]);";
            database.execSQL(sqlText);

            database.execSQL("ALTER TABLE [main].[tasks] RENAME TO [_sqliteexpert_temp_table_1];");
            sqlText = "CREATE TABLE [main].[tasks](\n" +
                    "  [id] INTEGER NOT NULL, \n" +
                    "  [title] TEXT NOT NULL, \n" +
                    "  [searchtitle] TEXT, \n" +
                    "  [description] TEXT, \n" +
                    "  [info_id] INTEGER NOT NULL, \n" +
                    "  [meeting_id] INTEGER NOT NULL, \n" +
                    "  [project_id] INTEGER NOT NULL, \n" +
                    "  [target_id] INTEGER NOT NULL, \n" +
                    "  [priority_id] INTEGER NOT NULL, \n" +
                    "  [parenttask_id] INTEGER NOT NULL, \n" +
                    "  [isFavourite] INTEGER NOT NULL, \n" +
                    "  [bgColor] TEXT, \n" +
                    "  [previousStatus] INTEGER, \n" +
                    "  [status] INTEGER, \n" +
                    "  [typeOfTask] INTEGER, \n" +
                    "  [dateCreate] INTEGER, \n" +
                    "  [dateBegin] INTEGER, \n" +
                    "  [dateEnd] INTEGER, \n" +
                    "  [dateClose] INTEGER, \n" +
                    "  [dateCreateStr] TEXT, \n" +
                    "  [dateBeginStr] TEXT, \n" +
                    "  [dateEndStr] TEXT, \n" +
                    "  [dateCloseStr] TEXT, \n" +
                    "  [guid] TEXT PRIMARY KEY NOT NULL DEFAULT '', \n" +
                    "  [deviceguid] TEXT NOT NULL DEFAULT '', \n" +
                    "  [category] INTEGER NOT NULL DEFAULT 3);";
            database.execSQL(sqlText);
            sqlText = "INSERT INTO [main].[tasks]([rowid], [id], [title], [searchtitle], [description], [info_id], [meeting_id], [project_id], [target_id], [priority_id], [parenttask_id], [isFavourite], [bgColor], [previousStatus], [status], [typeOfTask], [dateCreate], [dateBegin], [dateEnd], [dateClose], [dateCreateStr], [dateBeginStr], [dateEndStr], [dateCloseStr], [guid], [deviceguid])\n" +
                    "SELECT [rowid], [id], [title], [searchtitle], [description], [info_id], [meeting_id], [project_id], [target_id], [priority_id], [parenttask_id], [isFavourite], [bgColor], [previousStatus], [status], [typeOfTask], [dateCreate], [dateBegin], [dateEnd], [dateClose], [dateCreateStr], [dateBeginStr], [dateEndStr], [dateCloseStr], [guid], [deviceguid]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];";
            database.execSQL(sqlText);
            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];");
            database.execSQL("CREATE UNIQUE INDEX [main].[index_tasks_guid] ON [tasks]([guid]);");

        }
    };

    public static final Migration MIGRATION_9_10 = new Migration(9, 10) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE [main].[tasks] RENAME TO [_sqliteexpert_temp_table_1];");
            String sqlText = "CREATE TABLE [main].[tasks](\n" +
                    "  [id] INTEGER NOT NULL, \n" +
                    "  [title] TEXT NOT NULL, \n" +
                    "  [searchtitle] TEXT, \n" +
                    "  [description] TEXT, \n" +
                    "  [info_id] INTEGER NOT NULL, \n" +
                    "  [meeting_id] INTEGER NOT NULL, \n" +
                    "  [project_id] INTEGER NOT NULL, \n" +
                    "  [target_id] INTEGER NOT NULL, \n" +
                    "  [priority_id] INTEGER NOT NULL, \n" +
                    "  [parenttask_id] INTEGER NOT NULL, \n" +
                    "  [isFavourite] INTEGER NOT NULL, \n" +
                    "  [bgColor] TEXT, \n" +
                    "  [previousStatus] INTEGER, \n" +
                    "  [status] INTEGER, \n" +
                    "  [typeOfTask] INTEGER, \n" +
                    "  [dateCreate] INTEGER, \n" +
                    "  [dateBegin] INTEGER, \n" +
                    "  [dateEnd] INTEGER, \n" +
                    "  [dateClose] INTEGER, \n" +
                    "  [dateCreateStr] TEXT, \n" +
                    "  [dateBeginStr] TEXT, \n" +
                    "  [dateEndStr] TEXT, \n" +
                    "  [dateCloseStr] TEXT, \n" +
                    "  [guid] TEXT PRIMARY KEY NOT NULL DEFAULT '', \n" +
                    "  [deviceguid] TEXT NOT NULL DEFAULT '');";
            database.execSQL(sqlText);
            sqlText = "INSERT INTO [main].[tasks]([rowid], [id], [title], [searchtitle], [description], [info_id], [meeting_id], [project_id], [target_id], [priority_id], [parenttask_id], [isFavourite], [bgColor], [previousStatus], [status], [typeOfTask], [dateCreate], [dateBegin], [dateEnd], [dateClose], [dateCreateStr], [dateBeginStr], [dateEndStr], [dateCloseStr], [guid], [deviceguid])\n" +
                    "SELECT [rowid], [id], [title], [searchtitle], [description], [info_id], [meeting_id], [project_id], [target_id], [priority_id], [parenttask_id], [isFavourite], [bgColor], [previousStatus], [status], [typeOfTask], [dateCreate], [dateBegin], [dateEnd], [dateClose], [dateCreateStr], [dateBeginStr], [dateEndStr], [dateCloseStr], [guid], [deviceguid]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];";
            database.execSQL(sqlText);
            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];");
            database.execSQL("CREATE UNIQUE INDEX [main].[index_tasks_guid] ON [tasks]([guid]);");


            database.execSQL("ALTER TABLE [main].[informations] RENAME TO [_sqliteexpert_temp_table_1];");
            sqlText = "CREATE TABLE [main].[informations](\n" +
                    "  [id] INTEGER NOT NULL, \n" +
                    "  [title] TEXT NOT NULL, \n" +
                    "  [searchtitle] TEXT, \n" +
                    "  [description] TEXT, \n" +
                    "  [typeOfInfo] INTEGER, \n" +
                    "  [dateCreate] INTEGER, \n" +
                    "  [dateCreateStr] TEXT, \n" +
                    "  [idstatus] INTEGER NOT NULL REFERENCES [infostatus]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  [guid] TEXT PRIMARY KEY NOT NULL DEFAULT '', \n" +
                    "  [deviceguid] TEXT NOT NULL DEFAULT '');";
            database.execSQL(sqlText);
            sqlText = "INSERT INTO [main].[informations]([rowid], [id], [title], [searchtitle], [description], [typeOfInfo], [dateCreate], [dateCreateStr], [idstatus], [guid])\n" +
                    "SELECT [rowid], [id], [title], [searchtitle], [description], [typeOfInfo], [dateCreate], [dateCreateStr], [idstatus], [guid]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];";

            database.execSQL(sqlText);

            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];");
            database.execSQL("CREATE INDEX [main].[index_informations_idstatus] ON [informations]([idstatus]);");

            database.execSQL("ALTER TABLE [main].[infoproject] RENAME TO [_sqliteexpert_temp_table_1];");
            sqlText = "CREATE TABLE [main].[infoproject](\n" +
                    "  [idinformation] INTEGER NOT NULL, \n" +
                    "  [idproject] INTEGER NOT NULL REFERENCES [projects]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  [infoguid] TEXT NOT NULL DEFAULT '' REFERENCES [informations]([guid]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  PRIMARY KEY([infoguid], [idproject]));";
            database.execSQL(sqlText);
            sqlText = "INSERT INTO [main].[infoproject]([rowid], [idproject], [idinformation])\n" +
                    "SELECT [rowid], [idproject], [idinformation]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];";
            database.execSQL(sqlText);
            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];");
            database.execSQL("CREATE INDEX [main].[index_infoproject_idproject] ON [infoproject]([idproject]);");
            database.execSQL("CREATE INDEX [main].[index_infoproject_infoguid] ON [infoproject]([infoguid]);");


        }
    };


    public static final Migration MIGRATION_8_9 = new Migration(8, 9) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {


            database.execSQL("ALTER TABLE [main].[taskcontexts] RENAME TO [_sqliteexpert_temp_table_1]");

            database.execSQL("CREATE TABLE [main].[taskcontexts](\n" +
                    "  [idtask] INTEGER NOT NULL, \n" +
                    "  [idcontext] INTEGER NOT NULL REFERENCES [contexts]([id]), \n" +
                    "  [taskguid] TEXT NOT NULL DEFAULT '' REFERENCES [tasks]([guid])," +
                    " PRIMARY KEY([taskguid], [idcontext])) ");

            database.execSQL("INSERT INTO [main].[taskcontexts]([rowid], [idtask], [idcontext], [taskguid]) \n" +
                    "                    SELECT [rowid], [idtask], [idcontext], [taskguid] \n" +
                    "                    FROM [main].[_sqliteexpert_temp_table_1]");

            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1]");

            database.execSQL("CREATE UNIQUE INDEX [main].[index_taskcontexts_taskguid]\n" +
                    "ON [taskcontexts]( \n" +
                    "  [taskguid], \n" +
                    "  [idcontext])");

            database.execSQL("ALTER TABLE [main].[tasktags] RENAME TO [_sqliteexpert_temp_table_1]");
            database.execSQL("CREATE TABLE [main].[tasktags](\n" +
                    "  [idtask] INTEGER NOT NULL, \n" +
                    "  [idtag] INTEGER NOT NULL REFERENCES [tags]([id]), \n" +
                    "  [taskguid] TEXT NOT NULL DEFAULT '' REFERENCES [tasks]([guid]), \n" +
                    "  PRIMARY KEY([taskguid], [idtag]))");
            database.execSQL("INSERT INTO [main].[tasktags]([rowid], [idtask], [idtag], [taskguid])\n" +
                    "SELECT [rowid], [idtask], [idtag], [taskguid]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1]");
            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1]");

            database.execSQL("CREATE UNIQUE INDEX [main].[index_tasktags_taskguid_idtag]\n" +
                    "ON [tasktags]( \n" +
                    "  [taskguid], \n" +
                    "  [idtag])");

            //Log.e("1231231", "890890890");

        }
    };

    public static final Migration MIGRATION_7_8 = new Migration(7, 8) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            

            database.execSQL("ALTER TABLE [main].[tasktags] RENAME TO [_sqliteexpert_temp_table_1]");

            database.execSQL("CREATE TABLE [main].[tasktags](\n" +
                    "  [idtask] INTEGER NOT NULL, \n" +
                    "  [idtag] INTEGER NOT NULL, \n" +
                    "  [taskguid] TEXT NOT NULL DEFAULT ''," +
                    " PRIMARY KEY([taskguid], [idtag]))");

            database.execSQL("INSERT INTO [main].[tasktags]([rowid], [idtask], [idtag], [taskguid]) \n" +
                    "                    SELECT [rowid], [idtask], [idtag], [taskguid] \n" +
                    "                    FROM [main].[_sqliteexpert_temp_table_1]");

            database.execSQL("DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1]");

            //Log.e("1231231", "890890890");

        }
    };

    public static final Migration MIGRATION_6_7 = new Migration(6, 7) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String sqlText = "PRAGMA [main].legacy_alter_table = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'off';\n" +
                    "\n" +
                    "SAVEPOINT [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_tasktags_idtask];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_tasktags_idtag];\n" +
                    "\n" +
                    "ALTER TABLE [main].[tasktags] RENAME TO [_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE TABLE [main].[tasktags](\n" +
                    "  [idtask] INTEGER NOT NULL REFERENCES [tasks]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  [idtag] INTEGER NOT NULL REFERENCES [tags]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  [taskguid] TEXT NOT NULL DEFAULT '', \n" +
                    "  PRIMARY KEY([taskguid], [idtag]));\n" +
                    "\n" +
                    "INSERT INTO [main].[tasktags]([rowid], [idtask], [idtag], [taskguid])\n" +
                    "SELECT [rowid], [idtask], [idtag], [taskguid]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_tasktags_idtask] ON [tasktags]([idtask]);\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_tasktags_idtag] ON [tasktags]([idtag]);\n" +
                    "\n" +
                    "RELEASE [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].legacy_alter_table = 'off';";

            database.execSQL(sqlText);
        }
    };

    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {

            //database.execSQL("CREATE UNIQUE INDEX index_tasks_guid ON [tasks]([guid]);");

            //database.execSQL("ALTER TABLE comments ADD COLUMN infoguid TEXT NOT NULL DEFAULT ''");
            //database.execSQL("ALTER TABLE comments ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");

            String sqlText = "PRAGMA legacy_alter_table = on;\n" +
                    "\n" +
                    "PRAGMA foreign_keys = off;\n" +
                    "\n" +
                    "SAVEPOINT sqlite_expert_apply_design_transaction;\n" +
                    "\n" +
                    "DROP INDEX index_tasktags_idtask;\n" +
                    "\n" +
                    "DROP INDEX index_tasktags_idtag;\n" +
                    "\n" +
                    "ALTER TABLE tasktags RENAME TO _sqliteexpert_temp_table_1;\n" +
                    "\n" +
                    "CREATE TABLE tasktags(\n" +
                    "  idtask INTEGER NOT NULL , \n" +
                    "  idtag INTEGER NOT NULL REFERENCES tags(id) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  taskguid TEXT NOT NULL DEFAULT '' REFERENCES tasks(guid) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  PRIMARY KEY(taskguid, idtag));\n" +
                    "\n" +
                    "INSERT INTO tasktags(rowid, idtask, idtag, taskguid)\n" +
                    "SELECT rowid, idtask, idtag, taskguid\n" +
                    "FROM _sqliteexpert_temp_table_1;\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS _sqliteexpert_temp_table_1;\n" +
                    "\n" +
                    "CREATE INDEX index_tasktags_idtask ON tasktags(idtask);\n" +
                    "\n" +
                    "CREATE INDEX index_tasktags_idtag ON tasktags(idtag);\n" +
                    "\n" +
                    "RELEASE sqlite_expert_apply_design_transaction;\n" +
                    "\n" +
                    "PRAGMA foreign_keys = on;\n" +
                    "\n" +
                    "PRAGMA legacy_alter_table = off;\n";
            database.execSQL(sqlText);

            database.execSQL("ALTER TABLE taskcontexts ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");

            



        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {

            database.execSQL("CREATE UNIQUE INDEX index_tasks_guid ON [tasks]([guid]);");

            database.execSQL("ALTER TABLE comments ADD COLUMN infoguid TEXT NOT NULL DEFAULT ''");
            database.execSQL("ALTER TABLE comments ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");

            database.execSQL("ALTER TABLE informations ADD COLUMN guid TEXT NOT NULL DEFAULT ''");

            /*String sqlText = "PRAGMA [main].legacy_alter_table = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'off';\n" +
                    "\n" +
                    "SAVEPOINT [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_taskcontexts_idcontext];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_taskcontexts_idtask];\n" +
                    "\n" +
                    "ALTER TABLE [main].[taskcontexts] RENAME TO [_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE TABLE [main].[taskcontexts](\n" +
                    "  [idtask] INTEGER NOT NULL, \n" +
                    "  [idcontext] INTEGER NOT NULL REFERENCES [contexts]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  [taskguid] TEXT NOT NULL REFERENCES [tasks]([guid]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  PRIMARY KEY([taskguid], [idcontext]));\n" +
                    "\n" +
                    "INSERT INTO [main].[taskcontexts]([rowid], [idtask], [idcontext])\n" +
                    "SELECT [rowid], [idtask], [idcontext]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_taskcontexts_idcontext]\n" +
                    "ON [taskcontexts]([idcontext]);\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_taskcontexts_taskguid] ON [taskcontexts]([taskguid]);\n" +
                    "\n" +
                    "RELEASE [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].legacy_alter_table = 'off';";

            database.execSQL(sqlText);*/


            /*String SQL = "PRAGMA [main].legacy_alter_table = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'off';\n" +
                    "\n" +
                    "SAVEPOINT [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_taskcontexts_idcontext];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_taskcontexts_idtask];\n" +
                    "\n" +
                    "ALTER TABLE [main].[taskcontexts] RENAME TO [_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE TABLE [main].[taskcontexts](\n" +
                    "  [idtask] INTEGER NOT NULL, \n" +
                    "  [idcontext] INTEGER NOT NULL REFERENCES [contexts]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  [taskguid] TEXT NOT NULL DEFAULT '' REFERENCES [tasks]([guid]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  PRIMARY KEY([taskguid], [idcontext]));\n" +
                    "\n" +
                    "INSERT INTO [main].[taskcontexts]([rowid], [idtask], [idcontext], [taskguid])\n" +
                    "SELECT [rowid], [idtask], [idcontext], [taskguid]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_taskcontexts_idcontext]\n" +
                    "ON [taskcontexts]([idcontext]);\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_taskcontexts_taskguid] ON [taskcontexts]([taskguid]);\n" +
                    "\n" +
                    "RELEASE [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].legacy_alter_table = 'off';";

            database.execSQL(SQL);*/



            /*SQL = "PRAGMA [main].legacy_alter_table = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'off';\n" +
                    "\n" +
                    "SAVEPOINT [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_tasktags_idtag];\n" +
                    "\n" +
                    "DROP INDEX [main].[index_tasktags_idtask];\n" +
                    "\n" +
                    "ALTER TABLE [main].[tasktags] RENAME TO [_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE TABLE [main].[tasktags](\n" +
                    "  [idtask] INTEGER NOT NULL, \n" +
                    "  [idtag] INTEGER NOT NULL REFERENCES [tags]([id]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  [taskguid] TEXT NOT NULL DEFAULT '' REFERENCES [tasks]([guid]) ON DELETE NO ACTION ON UPDATE NO ACTION, \n" +
                    "  PRIMARY KEY([taskguid], [idtag]));\n" +
                    "\n" +
                    "INSERT INTO [main].[tasktags]([rowid], [idtask], [idtag], [taskguid])\n" +
                    "SELECT [rowid], [idtask], [idtag], [taskguid]\n" +
                    "FROM [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS [main].[_sqliteexpert_temp_table_1];\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_tasktags_idtag] ON [tasktags]([idtag]);\n" +
                    "\n" +
                    "CREATE INDEX [main].[index_tasktags_taskguid] ON [tasktags]([taskguid]);\n" +
                    "\n" +
                    "RELEASE [sqlite_expert_apply_design_transaction];\n" +
                    "\n" +
                    "PRAGMA [main].foreign_keys = 'on';\n" +
                    "\n" +
                    "PRAGMA [main].legacy_alter_table = 'off';";

            database.execSQL(SQL);*/

            //database.execSQL("ALTER TABLE tasktags ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");
            //database.execSQL("ALTER TABLE taskcontexts ADD COLUMN taskguid TEXT NOT NULL DEFAULT ''");

            //DbCreator.tasksUpdate();
        }
    };

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            String SQL1 = "CREATE TABLE [devices](\n" +
                    "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, \n" +
                    "  [title] TEXT NOT NULL UNIQUE, \n" +
                    "  [guid] TEXT NOT NULL UNIQUE, \n" +
                    "  [iscurrent] INTEGER NOT NULL DEFAULT 0)";

            String SQL2 = "CREATE UNIQUE INDEX [index_devices_guid] ON [devices]([guid])";

            String SQL3 = "CREATE TABLE [devicesinfo](\n" +
                    "  [deviceid] INTEGER NOT NULL, \n" +
                    "  [key] TEXT NOT NULL, \n" +
                    "  [value] TEXT NOT NULL, \n" +
                    "  PRIMARY KEY([deviceid], [key]));\n";

            database.execSQL(SQL1);
            database.execSQL(SQL2);
            database.execSQL(SQL3);
        }

    };

    public static final Migration MIGRATION_13_14 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            String SQL1 = "CREATE TABLE [devices](\n" +
                    "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, \n" +
                    "  [title] TEXT NOT NULL UNIQUE, \n" +
                    "  [guid] TEXT NOT NULL UNIQUE, \n" +
                    "  [iscurrent] INTEGER NOT NULL DEFAULT 0)";

            String SQL2 = "CREATE UNIQUE INDEX [index_devices_guid] ON [devices]([guid])";

            String SQL3 = "CREATE TABLE [devicesinfo](\n" +
                    "  [deviceid] INTEGER NOT NULL, \n" +
                    "  [key] TEXT NOT NULL, \n" +
                    "  [value] TEXT NOT NULL, \n" +
                    "  PRIMARY KEY([deviceid], [key]));\n";

            database.execSQL(SQL1);
            database.execSQL(SQL2);
            database.execSQL(SQL3);
        }

    };

    /*public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN bgColor TEXT DEFAULT null");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE informations ADD COLUMN typeOfInfo INTEGER DEFAULT 3");
            database.execSQL("create table infotypes (id INTEGER not null primary key autoincrement, title TEXT NOT NULL DEFAULT '', visualTitle TEXT NOT NULL DEFAULT '', color TEXT)");
            database.execSQL("CREATE UNIQUE INDEX index_infotypes_title ON  infotypes(title)");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN title TEXT DEFAULT ''");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN visualTitle TEXT");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN color TEXT");
            //database.execSQL("create table infotypes (id INTEGER not null DEFAULT 0 primary key autoincrement, title TEXT, visualTitle TEXT, color TEXT)");
        }
    };*/

    /*public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE informations ADD COLUMN typeOfInfo INTEGER DEFAULT 3");
            database.execSQL("create table infotypes (id INTEGER not null primary key autoincrement, title TEXT NOT NULL DEFAULT '', visualTitle TEXT NOT NULL DEFAULT '', color TEXT)");
            database.execSQL("CREATE UNIQUE INDEX index_infotypes_title ON  infotypes(title)");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN title TEXT DEFAULT ''");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN visualTitle TEXT");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN color TEXT");
            //database.execSQL("create table infotypes (id INTEGER not null DEFAULT 0 primary key autoincrement, title TEXT, visualTitle TEXT, color TEXT)");
        }
    };*/

    /*public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            //database.execSQL("ALTER TABLE informations ADD COLUMN typeOfInfo INTEGER DEFAULT 3");
            //database.execSQL("create table infotypes (id INTEGER not null primary key autoincrement, title TEXT, visualTitle TEXT, color TEXT)");
            //database.execSQL("create table infotypes (id INTEGER not null primary key autoincrement)");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN title TEXT DEFAULT ''");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN visualTitle TEXT");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN color TEXT");
            //database.execSQL("ALTER TABLE tasks ALTER COLUMN status INTEGER NOT NULL");
        }
    };*/

    /*public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            //database.execSQL("ALTER TABLE informations ADD COLUMN typeOfInfo INTEGER DEFAULT 3");
            //database.execSQL("create table infotypes (id INTEGER not null primary key autoincrement, title TEXT, visualTitle TEXT, color TEXT)");
            //database.execSQL("create table infotypes (id INTEGER not null primary key autoincrement)");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN title TEXT");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN visualTitle TEXT");
            //database.execSQL("ALTER TABLE infotypes ADD COLUMN color TEXT");
        }
    };*/

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();
    public abstract CategoryDao categoryDao();
    public abstract TaskStatusDao taskStatusDao();
    public abstract ContextDao contextDao();
    public abstract InformationDao informationDao();
    public abstract InfoStatusDao infoStatusDao();
    public abstract MeetingDao meetingDao();
    public abstract ProjectStatusDao projectStatusDao();
    public abstract TagDao tagDao();
    public abstract TaskTagJoinDao taskTagJoinDao();
    public abstract TaskContextJoinDao taskContextJoinDao();

    public abstract TargetDao targetDao();

    public abstract PriorityDao priorityDao();

    public abstract CommentDao commentDao();

    public abstract TaskTypesDao taskTypesDao();

    public abstract InfoTypesDao infoTypesDao();

    public abstract DeviceDao deviceDao();

    public abstract DeviceInfoDao deviceInfoDao();

    public abstract TaskCategoryDao taskCategoryDao();

}

