package ru.kau.mygtd2.utils;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.appcompat.widget.LinearLayoutCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.Status;

public class Const {

    public final static int LAYOUT_DEFAULT_WIDTH = 35;
    public final static int LAYOUT_DEFAULT_HEIGHT = 35;

    static final public int DEFAULT_LAYOUT_WIDTH = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
    static final public int DEFAULT_LAYOUT_HEIGHT = LinearLayoutCompat.LayoutParams.FILL_PARENT;

    static final public int DEFAULT_LAYOUT_WIDTH2 = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
    static final public int DEFAULT_LAYOUT_HEIGHT2 = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;

    static final public int DEFAULT_LAYOUT_WIDTH3 = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
    static final public int DEFAULT_LAYOUT_HEIGHT3 = LinearLayoutCompat.LayoutParams.MATCH_PARENT;

    static final public int DEFAULT_ICON_WIDTH = 45;; //ViewGroup.LayoutParams.WRAP_CONTENT;//50;
    static final public int DEFAULT_ICON_HEIGHT = LinearLayoutCompat.LayoutParams.FILL_PARENT; //ViewGroup.LayoutParams.WRAP_CONTENT;//50;
    static final public int DEFAULT_ICON_HEIGHT2 = 45;

    static final public int DEFAULT_CB_WIDTH = 25;; //ViewGroup.LayoutParams.WRAP_CONTENT;//50;
    static final public int DEFAULT_CB_HEIGHT = LinearLayoutCompat.LayoutParams.FILL_PARENT; //ViewGroup.LayoutParams.WRAP_CONTENT;//50;
    static final public int DEFAULT_CB_HEIGHT2 = 25;

    static final public int DEFAULT_ICON_WIDTHSMALL = 35;; //ViewGroup.LayoutParams.WRAP_CONTENT;//50;
    //static final public int DEFAULT_ICON_HEIGHT = LinearLayoutCompat.LayoutParams.FILL_PARENT; //ViewGroup.LayoutParams.WRAP_CONTENT;//50;
    static final public int DEFAULT_ICON_HEIGHTSMALL = 35;

    static final public int DEFAULT_ICON_WIDTH3 = ViewGroup.LayoutParams.MATCH_PARENT;//50;
    static final public int DEFAULT_ICON_HEIGHT3 = ViewGroup.LayoutParams.MATCH_PARENT; //LinearLayoutCompat.LayoutParams.WRAP_CONTENT; //ViewGroup.LayoutParams.WRAP_CONTENT;//50;

    static final public int DEFAULT_RTV_WIDTH = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
    static final public int DEFAULT_RTV_HEIGHT = LinearLayoutCompat.LayoutParams.FILL_PARENT;
    static final public int DEFAULT_TV_WIDTH = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;
    static final public int DEFAULT_TV_HEIGHT = LinearLayoutCompat.LayoutParams.WRAP_CONTENT;

    static final public int TASK_LEVEL_OFFSET = 50;
    static final public int TASK_LEVEL_OFFSET2 = 20;

    //static private String DEFAULT_PROJECT_COLOR = "#fafafa";
    //private String DEFAULT_TASKBG_COLOR = "#FFFFFFFF";

    static final public String DEFAULT_RTV_COLOR = "#aa03A9F4";
    static final public String DEFAULT_TASKBG_COLOR = "#FFFFFFFF";
    static final public String DEFAULT_TEXT_COLOR = "#000000";

    static final public int DEFAULT_EXPANDED_ICON = R.drawable.press_down;
    static final public int DEFAULT_COLLAPSE_ICON = R.drawable.not_press;

    public static final String DEFAULT_PROJECT_COLOR = "#fafafa";
    //public static final String DEFAULT_TASKCATEGORY_COLOR = "#fafafa";

    public static final String MYDEVICEID = MyApplication.getDatabase().deviceDao().getGuidCurrentDevice();
    //public static final int MYDEVICETYPE = MyApplication.getDatabase().deviceDao().getCurrentDeviceType();

    @SuppressLint("SimpleDateFormat")
    static final public SimpleDateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat("dd.MM.yyyy");
    @SuppressLint("SimpleDateFormat")
    static final public SimpleDateFormat DEFAULT_DATEFORMAT_WITHSECONDS = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    static final public SimpleDateFormat DEFAULT_DATEFORMAT_WITHMINUTES = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    @SuppressLint("SimpleDateFormat")
    static final public SimpleDateFormat DEFAULT_DATEFORMAT_WITHMILSECONDS = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");



    static final public List<Integer> lstALLFAVOURITE = new ArrayList<Integer>(){
        {
            add(0);
            add(1);
        }
    };

    static final public List<Integer> lstONLYFAVOURITE = new ArrayList<Integer>(){
        {
            //add(0);
            add(1);
        }
    };

    static final public List<Integer> lstWITHOUTTARGET = new ArrayList<Integer>() {
        {
            add(0);
        }

    };

    static final public List<Integer> lstALLTARGETSID = new ArrayList<Integer>() {
        {
            add(0);
            addAll(MyApplication.getDatabase().targetDao().getAllId());
        }

    };

    static final public List<Integer> lstTARGETSSID = new ArrayList<Integer>() {
        {
            //add(0);
            addAll(MyApplication.getDatabase().targetDao().getAllId());
        }

    };

    static final public List<Integer> lstALLTAGSID = new ArrayList<Integer>() {
        {
            add(0);
            addAll(MyApplication.getDatabase().tagDao().getAllId());
        }

    };


    static final public List<Integer> lstWITHOUTPROJECT = new ArrayList<Integer>() {
        {
            add(0);
        }

    };

    static final public List<Integer> lstALLPROJECTSID = new ArrayList<Integer>() {
        {
            add(0);
            addAll(MyApplication.getDatabase().projectDao().getAllId());
        }

    };

    static final public List<Integer> lstPROJECTSID = new ArrayList<Integer>() {
        {
            //add(0);
            addAll(MyApplication.getDatabase().projectDao().getAllId());
        }

    };


    static final public List<Integer> lstALLPRIORITY = new ArrayList<Integer>(){
        {
            //add(0);
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }
    };



    static final public List<Integer> lstHIPRIORITY = new ArrayList<Integer>(){
        {
            //add(0);
            add(1);
            add(2);

        }
    };

    static final public List<Integer> lstALLSTATUS = new ArrayList<Integer>(){
        {
            /*add(Status.NEXTACTION.Value);
            add(Status.SOMETIME.Value);
            add(Status.NOTHING.Value);
            add(Status.NEW.Value);
            add(Status.INPROGRESS.Value);
            add(Status.PAUSE.Value);
            add(Status.INHOLD.Value);
            add(Status.COMPLETED.Value);*/

            addAll(MyApplication.getDatabase().taskStatusDao().getAllId());

        }
    };


    static final public List<Integer> lstStatus = new ArrayList<Integer>(){
        {

            add(Status.NOTHING.Value);
            add(Status.NEW.Value);
            add(Status.INPROGRESS.Value);

        }
    };

    static final public List<Integer> LSTSTATUSINHOLD = new ArrayList<Integer>(){
        {
            add(Status.INHOLD.Value);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTSTATUSNEW = new ArrayList<Integer>(){
        {
            add(Status.NEW.Value);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTSTATUSINPROGRESS = new ArrayList<Integer>(){
        {
            add(Status.INPROGRESS.Value);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTSTATUSPAUSE = new ArrayList<Integer>(){
        {
            add(Status.PAUSE.Value);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTSTATUSCOMPLETED = new ArrayList<Integer>(){
        {
            add(Status.COMPLETED.Value);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTSTATUSSOMEDAY = new ArrayList<Integer>(){
        {
            add(Status.SOMETIME.Value);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTCATEGORYGREEN = new ArrayList<Integer>(){
        {
            add(1);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTCATEGORYNONGREEN = new ArrayList<Integer>(){
        {
            add(2);
            add(3);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTCATEGORYRED = new ArrayList<Integer>(){
        {
            add(2);

            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    static final public List<Integer> LSTCATEGORYBROWN = new ArrayList<Integer>(){
        {
            add(3);
            //add(Status.NEW.Value);
            //add(Status.INPROGRESS.Value);
        }
    };

    // --------------------------------------------------------------------------------------

    //public static final String BASE_URL = "http://192.168.31.202:8090/"; // DEV HOME BACKUP
    //public static final String BASE_URL2 = "http://192.168.31.202:8091/"; // DEV HOME SYNC
    //public static final String BASE_URL = "http://192.168.31.202:9999/"; // PROD HOME

    public static final String BASE_URL = "http://192.168.68.2:8090/"; // DEV WORK BACKUP
    public static final String BASE_URL2 = "http://192.168.68.2:8091/"; // DEV WORK SYNC




    // --------------------------------------------------------------------------------------




    static final public String DEFAULT_TASKSTATUS_COLOR = "#FFA500";
    static final public String DEFAULT_TASKPRIORITY_COLOR = "#FFA500";
    static final public String DEFAULT_TASKOVERDUE_COLOR = "#FF0000";
    static final public String DEFAULT_TASKTODAY_COLOR = "#FFA500";
    static final public String DEFAULT_TASKTOMORROW_COLOR = "#0000FF";
    static final public String DEFAULT_TASKAFTERTOMORROW_COLOR = "#686868";
    static final public String DEFAULT_COLOR = "#000000";
    static final public String DEFAULT_TASKCATEGORY_COLOR = "#FF0000";

    /*static final public String HIERARCHY_TASKS = "WITH RECURSIVE tree(id, title, searchtitle, description, info_id, meeting_id, project_id, target_id, priority_id, parenttask_id, isFavourite, bgColor, previousStatus,\n" +
            "        status, typeOfTask, dateCreate, dateBegin, dateEnd, dateClose, dateCreateStr, dateBeginStr, dateEndStr, dateCloseStr, level) AS (\n" +
            "        VALUES (0, '', '', '', 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 0, '', '', '', '', 0)\n" +

            "        UNION ALL\n" +
            "        SELECT t2.id, t2.title, t2.searchtitle, t2.description, t2.info_id, t2.meeting_id, t2.project_id, t2.target_id, t2.priority_id, t2.parenttask_id, t2.isFavourite, t2.bgColor, t2.previousStatus, " +
            "        t2.status, t2.typeOfTask, t2.dateCreate, t2.dateBegin, t2.dateEnd, t2.dateClose, t2.dateCreateStr, t2.dateBeginStr, t2.dateEndStr, t2.dateCloseStr, " +
            "        tree.level + 1 \n" +
            "        FROM tasks t2 \n" +
            "                 JOIN tree ON t2.parenttask_id = tree.id\n" +
            "        order by 23 desc\n" +
            "    )\n" +
            "    SELECT *\n" +
            "    FROM tree\n" +
            "    WHERE level > 0 ";*/

    /*static final public String HIERARCHY_TASKS = "WITH RECURSIVE tree(id, title, searchtitle, description, info_id, meeting_id, project_id, target_id, priority_id, parenttask_id, isFavourite, bgColor, previousStatus,\n" +
            "        status, typeOfTask, dateCreate, dateBegin, dateEnd, dateClose, dateCreateStr, dateBeginStr, dateEndStr, dateCloseStr, guid, deviceguid, level) AS (\n" +
            "        VALUES (0, '', '', '', 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 0, '', '', '', '', '', '', 0)\n" +
            "        UNION ALL\n" +
            "        SELECT t2.id, t2.title, t2.searchtitle, t2.description, t2.info_id, t2.meeting_id, t2.project_id, t2.target_id, t2.priority_id, t2.parenttask_id, t2.isFavourite, t2.bgColor, t2.previousStatus, " +
            "        t2.status, t2.typeOfTask, t2.dateCreate, t2.dateBegin, t2.dateEnd, t2.dateClose, t2.dateCreateStr, t2.dateBeginStr, t2.dateEndStr, t2.dateCloseStr, t2.guid, t2.deviceguid, " +
            "        tree.level + 1 \n" +
            "        FROM tasks t2 \n" +
            "                 JOIN tree ON t2.parenttask_id = tree.id\n" +
            "        order by 25 desc\n" +
            "    )\n" +
            "    SELECT *\n" +
            "    FROM tree\n" +
            "    WHERE level > 0 ";*/

    /*static final public String HIERARCHY_TASKS = "WITH RECURSIVE tree(id, title, searchtitle, description, info_id, meeting_id, project_id, target_id, priority_id, parenttask_id, isFavourite, bgColor, previousStatus,\n" +
            "        status, typeOfTask, dateCreate, dateBegin, dateEnd, dateClose, dateCreateStr, dateBeginStr, dateEndStr, dateCloseStr, guid, deviceguid, category, level) AS (\n" +
            "        VALUES (0, '', '', '', 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 0, '', '', '', '', '', '', 0, 0)\n" +
            "        UNION ALL\n" +
            "        SELECT t2.id, t2.title, t2.searchtitle, t2.description, t2.info_id, t2.meeting_id, t2.project_id, t2.target_id, t2.priority_id, t2.parenttask_id, t2.isFavourite, t2.bgColor, t2.previousStatus, " +
            "        t2.status, t2.typeOfTask, t2.dateCreate, t2.dateBegin, t2.dateEnd, t2.dateClose, t2.dateCreateStr, t2.dateBeginStr, t2.dateEndStr, t2.dateCloseStr, t2.guid, t2.deviceguid, " +
            "        t2.category, tree.level + 1 \n" +
            "        FROM tasks t2 \n" +
            "                 JOIN tree ON t2.parenttask_id = tree.id\n" +
            "        order by 27 desc\n" +
            "    )\n" +
            "    SELECT *\n" +
            "    FROM tree\n" +
            "    WHERE level > 0 ";*/

    static final public String HIERARCHY_TASKS = "WITH RECURSIVE tree(id, title, searchtitle, description, info_id, meeting_id, project_id, target_id, priority_id, parenttask_id, isFavourite, bgColor, previousStatus,\n" +
            "        status, typeOfTask, dateCreate, dateBegin, dateEnd, dateClose, dateCreateStr, dateBeginStr, dateEndStr, dateCloseStr, guid, deviceguid, category, dateEdit, dateEditStr, level) AS (\n" +
            "        VALUES (0, '', '', '', 0, 0, 0, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 0, '', '', '', '', '', '', 0, 0, '', 0)\n" +
            "        UNION ALL\n" +
            "        SELECT t2.id, t2.title, t2.searchtitle, t2.description, t2.info_id, t2.meeting_id, t2.project_id, t2.target_id, t2.priority_id, t2.parenttask_id, t2.isFavourite, t2.bgColor, t2.previousStatus, " +
            "        t2.status, t2.typeOfTask, t2.dateCreate, t2.dateBegin, t2.dateEnd, t2.dateClose, t2.dateCreateStr, t2.dateBeginStr, t2.dateEndStr, t2.dateCloseStr, t2.guid, t2.deviceguid, " +
            "        t2.category, t2.dateEdit, t2.dateEditStr, tree.level + 1 \n" +
            "        FROM tasks t2 \n" +
            "                 JOIN tree ON t2.parenttask_id = tree.id\n" +
            "        order by 27 desc\n" +
            "    )\n" +
            "    SELECT *\n" +
            "    FROM tree\n" +
            "    WHERE level > 0 ";



    /*static final public String TASKS = "select id, title, searchtitle, description, info_id, meeting_id, project_id, target_id, priority_id, parenttask_id, isFavourite, previousStatus, " +
            "        status, typeOfTask, dateCreate, dateBegin, dateEnd, dateClose, dateCreateStr, dateBeginStr, dateEndStr, dateCloseStr, bgColor, 0 from tasks " +
            "        where 1 = 1 ";*/
}
