package ru.kau.mygtd2.utils;

import static java.time.temporal.ChronoUnit.DAYS;
import static ru.kau.mygtd2.common.MyApplication.getContext;
import static ru.kau.mygtd2.enums.TypeSetting.CURRENTDATE;
import static ru.kau.mygtd2.enums.TypeSetting.USECURRENTSYSTEMDATE;
import static ru.kau.mygtd2.utils.Const.DEFAULT_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHSECONDS;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TASKAFTERTOMORROW_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TASKOVERDUE_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TASKTODAY_COLOR;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TASKTOMORROW_COLOR;
import static ru.kau.mygtd2.utils.Const.HIERARCHY_TASKS;
import static ru.kau.mygtd2.utils.LOG.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.TypeOfInfo;
import ru.kau.mygtd2.common.enums.TypeOfTask;
import ru.kau.mygtd2.enums.TypeDateTasks;
import ru.kau.mygtd2.objects.Priority;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.SQLCondition;
import ru.kau.mygtd2.objects.Statistic;
import ru.kau.mygtd2.objects.Task;

public class Utils {

    static Date currDate = new Date(Utils.getCurrentApplicationDateAndTime());

    public static String dateToString(Date date) {

        return dateToString(null, date);
    }

    // Метод будет вызываться при сохранении задач и пр. для определения  реальной текущей даты и времени
    public static long getCurrentApplicationDateAndTime(){
        long ret = 0;

        // Пока делаю заглушку, возвращающую текущую дату и текущее время
        //ret = new Date().getTime();
        if (ru.kau.mygtd2.utils.Settings.getBooleanSetting(USECURRENTSYSTEMDATE)){
            return new Date().getTime();
        }

        // Определяем реальное текущее время
        long curtime = new Date().getTime() - getStartOfDay(new Date()).getTime();

        ret = ru.kau.mygtd2.utils.Settings.getLongSetting(CURRENTDATE) + curtime;

        return ret;
    }

    public static long getCurrentApplicationDate(){
        long ret = 0;

        // Пока делаю заглушку, возвращающую текущую дату и текущее время
        //ret = new Date().getTime();
        if (ru.kau.mygtd2.utils.Settings.getBooleanSetting(USECURRENTSYSTEMDATE)){
            return new Date().getTime();
        }

        ret = ru.kau.mygtd2.utils.Settings.getLongSetting(CURRENTDATE);

        return ret;
    }


    public static String dateToString(SimpleDateFormat format, Date date) {
        if (format == null || format.equals("")) {
            format = DEFAULT_DATEFORMAT_WITHSECONDS;
        }

        return date == null ? "" : format.format(date);
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null) return null;
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        return getEndOfDay(date, 999);
    }

    public static Date getEndOfDay(Date date, int millisec) {
        Calendar calendar = Calendar.getInstance();
        if (date == null) return null;
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        //calendar.setTime(date);
        //calendar.set(Calendar.HOUR_OF_DAY, 23);
        //calendar.set(Calendar.MINUTE, 59);
        //calendar.set(Calendar.SECOND, 59);
        //calendar.set(Calendar.MILLISECOND, 99999999);
        calendar.set(Calendar.MILLISECOND, millisec);
        return calendar.getTime();
    }

    public static Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //calendar.set(Calendar.MILLISECOND, 99999999);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public static Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static long getDateOfParam(int day, int month, int year, int hours, int minutes, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime().getTime();
    }

    public static int parseColor(String colorString) {
        return parseColor(colorString, DEFAULT_COLOR);
    }

    public static int parseColor(String colorString, String defaultColorString) {
        int ret = 0;
        if (colorString != null) {

            try {
                ret = Color.parseColor(colorString);
            } catch (Exception e) {
                ret = parseColor(defaultColorString);
            }
        } else {
            ret = parseColor(defaultColorString);
        }
        return ret;

    }

    @SuppressLint("ResourceType")
    public static ImageView getIconTaskType(Context c, TypeOfTask typeOfTask) {

        switch (typeOfTask.Value) {
            case 1:
                return (ImageView) ((Activity) c).findViewById(R.drawable.epic);
            case 2:
                return (ImageView) ((Activity) c).findViewById(R.drawable.story);
            case 3:
                return (ImageView) ((Activity) c).findViewById(R.drawable.task);
            case 4:
                return (ImageView) ((Activity) c).findViewById(R.drawable.bug);
            case 5:
                return (ImageView) ((Activity) c).findViewById(R.drawable.question);
            case 6:
                return (ImageView) ((Activity) c).findViewById(R.drawable.newfeature);
            case 7:
                return (ImageView) ((Activity) c).findViewById(R.drawable.improvement);
            default:
                return (ImageView) ((Activity) c).findViewById(R.drawable.task);
        }
        //return (ImageView) ((Activity)c).findViewById(R.drawable.task);
    }

    public static int getImageResourceTaskType(TypeOfTask typeOfTask, String parentTask) {

        Log.e("PARENTGUID: ", parentTask);
        if ((parentTask != null) && !(parentTask.equals(""))){
            return R.drawable.subtask2;
        }

        switch (typeOfTask.Value) {
            case 1:
                return R.drawable.epic;
            case 2:
                return R.drawable.story;
            case 3:
                return R.drawable.task;
            case 4:
                return R.drawable.bug;
            case 5:
                return R.drawable.question;
            case 6:
                return R.drawable.newfeature;
            case 7:
                return R.drawable.improvement;
            default:
                return R.drawable.task;
        }
        //return (ImageView) ((Activity)c).findViewById(R.drawable.task);
    }

    public static int getImageResourceTaskType(TypeOfTask typeOfTask) {

        switch (typeOfTask.Value) {
            case 1:
                return R.drawable.epic;
            case 2:
                return R.drawable.story;
            case 3:
                return R.drawable.task;
            case 4:
                return R.drawable.bug;
            case 5:
                return R.drawable.question;
            case 6:
                return R.drawable.newfeature;
            case 7:
                return R.drawable.improvement;
            default:
                return R.drawable.task;
        }
        //return (ImageView) ((Activity)c).findViewById(R.drawable.task);
    }

    public static int getImageResourceDeviceType(int devicetype) {
        switch (devicetype) {
            case 1:
                return R.drawable.tablet;
            case 2:
                return R.drawable.mobilephone;
            case 3:
                return R.drawable.computer1;

            default:
                return R.drawable.computer1;
        }
    }

    public static int getImageResourceInfoType(TypeOfInfo typeOfInfo) {

        int image = R.drawable.info;

        switch (typeOfInfo) {
            case QUESTION:
                image = R.drawable.question2;
                break;
            case IDEA:
                image = R.drawable.idea4;
                break;
            case INFO:
                image = R.drawable.info;
                break;

            default:
                image = R.drawable.info;
        }
        return image;
        //return (ImageView) ((Activity)c).findViewById(R.drawable.task);
    }


    public static String getColorByEndDate(Date date) {
        //int diff = daysBetween(Utils.getStartOfDay(date), Utils.getStartOfDay(new Date()));
        long diff = diffInDays(convertToLocalDateViaInstant(getStartOfDay(date)), convertToLocalDateViaInstant(getStartOfDay(new Date())));
        if (diff < 0) {
            return DEFAULT_TASKOVERDUE_COLOR;
        } else {
            if (diff == 0) {
                return DEFAULT_TASKTODAY_COLOR;
            } else {
                if (diff == 1) {
                    return DEFAULT_TASKTOMORROW_COLOR;
                } else {
                    return DEFAULT_TASKAFTERTOMORROW_COLOR;
                }
            }
        }

        //return DEFAULT_TASKAFTERTOMORROW_COLOR;

    }

    public static int getBackgroundByEndDate(Date date) {
        //long diff = diffInDays(convertToLocalDateViaInstant(getStartOfDay(date)), convertToLocalDateViaInstant(getStartOfDay(new Date())));
        long diff = diffInDays(convertToLocalDateViaInstant(getStartOfDay(date)), convertToLocalDateViaInstant(getStartOfDay(currDate)));
        if (diff < 0) {
            return R.drawable.roundrect_1;
        } else {
            if (diff == 0) {
                return R.drawable.roundrect_2;
            } else {
                if (diff == 1) {
                    return R.drawable.roundrect;
                } else {
                    return R.drawable.roundrect_4;
                }
            }
        }
        //return R.drawable.roundrect_4;
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert == null ? null : dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static int daysBetween(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            return (int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
        } else {
            return 1000000000;
        }
    }

    public static long diffInDays(LocalDate a, LocalDate b) {
        return (a != null && b != null) ? DAYS.between(b, a) : 1000000000;
    }

    public static int getLevelTask(Task task) {
        int level = 0;
        long id = task.getParenttask_id();
        while (id > 0) {
            level++;
            id = MyApplication.getDatabase().taskDao().getById(id).getParenttask_id();
            Task t1 = MyApplication.getDatabase().taskDao().getById(id);
            if (t1 != null) {
                getLevelTask(t1);
            }
        }

        return level;
    }

    public static Project getProjectByRootTask(Task task) {
        Project pr;

        long id = task.getParenttask_id();
        pr = MyApplication.getDatabase().projectDao().getProjectById(MyApplication.getDatabase().taskDao().getById(id).getProject_id());
        while (id > 0) {

            id = MyApplication.getDatabase().taskDao().getById(id).getParenttask_id();
            Task t1 = MyApplication.getDatabase().taskDao().getById(id);

            if (t1 != null) {
                pr = MyApplication.getDatabase().projectDao().getProjectById(t1.getProject_id());
                getLevelTask(t1);
            }
        }

        return pr;
    }

    public static void updateProjectSubTasks() {

    }

    public static int getIconForPriority(Priority priority) {

        switch (priority.getId()) {
            case 1:
                return R.drawable.hi_priority;
            //break;
            case 2:
                return R.drawable.hi_priority2;
            //break;
            case 3:
                return R.drawable.middle_prior;
            //break;
            case 4:
                return R.drawable.low_priority;
            //break;
            case 5:
                return R.drawable.minus;
            //break;
            default:
                return R.drawable.minus;
            //break;
        }

    }

    public static String getTextHeading(Fragment fr, TypeDateTasks typeHeading, int count) {

        String ret = "";

        switch (typeHeading) {
            case OVERDUE:
                ret = ret + fr.getResources().getString(R.string.overduetask);
                break;
            case TODOTODAY:
                //ret = ret + fr.getResources().getString(R.string.maketoday) + " (" + Utils.dateToString(DEFAULT_DATEFORMAT, new Date()) + ")";
                ret = ret + fr.getResources().getString(R.string.maketoday) + " (" + Utils.dateToString(DEFAULT_DATEFORMAT, currDate) + ")";
                break;
            case TODOTOMORROW:
                Calendar calendar = Calendar.getInstance();
                //Date today = calendar.getTime();
                calendar.setTime(currDate);

                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date tomorrow = calendar.getTime();
                ret = ret + fr.getResources().getString(R.string.maketomorrow) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), tomorrow) + ")";
                break;
            case TODONEXTSEVENDAYS:
                calendar = Calendar.getInstance();
                calendar.setTime(currDate);
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 2);
                Date d1 = Utils.getStartOfDay(calendar.getTime());

                //calendar = Calendar.getInstance();
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 5);
                Date d2 = Utils.getEndOfDay(calendar.getTime());
                ret = ret + fr.getResources().getString(R.string.makenextweek) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + "-" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d2) + ")";
                break;
            case TODOAFTERWEEK:
                calendar = Calendar.getInstance();
                calendar.setTime(currDate);
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 8);
                d1 = Utils.getStartOfDay(calendar.getTime());

                calendar = Calendar.getInstance();
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 14);
                d2 = Utils.getEndOfDay(calendar.getTime());
                ret = ret + fr.getResources().getString(R.string.makeafterweek) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + "-" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d2) + ")";
                break;
            case TODOAFTERTWOWEEK:
                calendar = Calendar.getInstance();
                calendar.setTime(currDate);
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 15);
                d1 = Utils.getStartOfDay(calendar.getTime());

                calendar = Calendar.getInstance();
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 30);
                d2 = Utils.getEndOfDay(calendar.getTime());
                ret = ret + fr.getResources().getString(R.string.makeaftertwoweek) + " (" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + "-" + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d2) + ")";
                break;
            case TODOINFUTURE:
                calendar = Calendar.getInstance();
                calendar.setTime(currDate);
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 31);
                d1 = Utils.getStartOfDay(calendar.getTime());
                ret = ret + fr.getResources().getString(R.string.makeinfuture) + " (после " + Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), d1) + ")";
                break;
            case TODONOENDDATE:
                ret = ret + fr.getResources().getString(R.string.makenoenddate);
                break;
            case CLOSED:
                ret = ret + fr.getResources().getString(R.string.closedtasks);
                break;
        }
        ret = ret + ".\nКоличество задач: " + count;
        return ret;

    }

    public static byte[] getArrayByte(List<Integer> intArray) {
        byte[] ret = new byte[intArray.size()];
        for (int i = 0; i < intArray.size(); i++) {
            ret[i] = intArray.get(i).byteValue();
        }
        return ret;
    }

    public static String getStringByArrayInteger(List<Integer> intArray) {
        String ret = "";
        for (int i = 0; i < intArray.size() - 1; i++) {
            ret = ret + intArray.get(i).toString() + ", ";
        }
        ret = ret + intArray.get(intArray.size() - 1).toString();
        return ret;
    }

    public static List<Task> getListTasksBySQL(List<SQLCondition> lstSQLCond, String sqlText2, Object[] args) {
        List<Task> lstTask = null;
        String sqlText = HIERARCHY_TASKS;
        if (sqlText2 != null && !sqlText2.equals("")) {
            sqlText += " AND " + sqlText2;
        }
        for (int i = 0; i < lstSQLCond.size(); i++) {
            if (lstSQLCond.get(i).getCond().trim().equals("=")
                    || lstSQLCond.get(i).getCond().trim().equals("<")
                    || lstSQLCond.get(i).getCond().trim().equals(">")
                    || lstSQLCond.get(i).getCond().trim().equals(">=")
                    || lstSQLCond.get(i).getCond().trim().equals("<=")) {
                sqlText += " AND " + lstSQLCond.get(i).getSource1() + " " + lstSQLCond.get(i).getCond().trim() + " " + lstSQLCond.get(i).getSource2();
            } else {
                sqlText += " AND " + lstSQLCond.get(i).getSource1() + " " + lstSQLCond.get(i).getCond().trim() + " (" + lstSQLCond.get(i).getSource2() + ")";
            }
        }
        //System.out.println(sqltext);
        //Log.e("ERROR", sqlText);
        //Log.e("ERROR2", args.toArray().toString());
        lstTask = MyApplication.getDatabase().taskDao().getTasks(new SimpleSQLiteQuery(sqlText, args));

        return lstTask;
    }

    public static long getLastTaskId() {
        long id = MyApplication.getDatabase().taskDao().getMaxId();
        return id + 1;
    }

    public static long getLastInfoId() {
        long id = MyApplication.getDatabase().informationDao().getMaxId();
        return id + 1;
    }

    public static List<String> getListDateBetweenDates(Date d1, Date d2) throws ParseException {
        List<String> lstDates = new ArrayList<String>();
        String strDate1 = dateToString(DEFAULT_DATEFORMAT, d1);
        String strDate2 = dateToString(DEFAULT_DATEFORMAT, d2);
        lstDates.add(strDate1);
        while (!strDate1.equals(strDate2)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(DEFAULT_DATEFORMAT.parse(strDate1));
            cal.add(Calendar.DATE, 1);
            strDate1 = dateToString(DEFAULT_DATEFORMAT, cal.getTime());
            lstDates.add(strDate1);
            Log.e(strDate1, strDate1);
        }

        return lstDates;
    }

    public static int getMaxFromArray(List<Statistic> lstStat) {
        int max = 0;
        for (int i = 0; i < lstStat.size(); i++) {
            if (lstStat.get(i).getCount1() > max) {
                max = (int) lstStat.get(i).getCount1();
            }
        }

        return max;
    }

    public static int getMinFromArray(List<Statistic> lstStat) {
        int min = 1000000;
        for (int i = 0; i < lstStat.size(); i++) {
            if (lstStat.get(i).getCount1() < min) {
                min = (int) lstStat.get(i).getCount1();
            }
        }

        return min;
    }

    public static double getAvgFromArray(List<Statistic> lstStat) {
        double avg = 0;
        for (int i = 0; i < lstStat.size(); i++) {
            avg += lstStat.get(i).getCount1();
        }

        return avg / (double) lstStat.size();
    }

    public static Task getCloneTask(Task t) {
        Task tt = new Task();

        tt.setTitle(t.getTitle());
        tt.setSearchtitle(t.getSearchtitle());
        tt.setDescription(t.getDescription());
        tt.setInfo_id(t.getInfo_id());
        tt.setMeeting_id(t.getMeeting_id());
        tt.setProject_id(t.getProject_id());
        tt.setTarget_id(t.getTarget_id());
        tt.setPriority_id(t.getPriority_id());
        tt.setParenttask_id(t.getParenttask_id());
        tt.setParenttaskguid(t.getParenttaskguid());
        tt.setIsFavourite(t.getIsFavourite());
        tt.setBgColor(t.getBgColor());
        tt.setPreviousStatus(t.getPreviousStatus());
        tt.setStatus(t.getStatus());
        tt.setTypeoftask(t.getTypeoftask());
        tt.setDateCreate(t.getDateCreate());
        tt.setDateBegin(t.getDateBegin());
        tt.setDateEnd(t.getDateEnd());
        tt.setDateClose(t.getDateClose());
        tt.setDateCreateStr(t.getDateCreateStr());
        tt.setDateBeginStr(t.getDateBeginStr());
        tt.setDateEndStr(t.getDateEndStr());
        tt.setDateCloseStr(t.getDateCloseStr());
        tt.setCategory(t.getCategory());
        tt.setDeviceguid(t.getDeviceguid());

        return tt;

    }

    public static Task getCloneTaskTomorrow(Task t) {
        Task tt = getCloneTask(t);
        Date date = new Date();
        tt.setId(Utils.getLastTaskId());
        tt.setGuid(UUID.randomUUID().toString());
        tt.setDateEdit(date);
        tt.setDateEditStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));
        tt.setDateCreate(date);
        tt.setDateCreateStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        tt.setDateBegin(getStartOfDay(date));
        tt.setDateEnd(getEndOfDay(date));
        tt.setDateBeginStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, tt.getDateBegin()));
        tt.setDateEndStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, tt.getDateEnd()));
        //MyApplication.getDatabase().taskDao().insert(tt);
        //LOG.d(null, tt.getId());
        //LOG.d(null, tt.getDateBegin() + "   " + Utils.dateToString(tt.getDateBegin()));
        //LOG.d(null, tt.getDateEnd() + "   " + Utils.dateToString(tt.getDateEnd()));
        Log.e("TASKTASK", tt.getDateBegin() + "   " + Utils.dateToString(tt.getDateBegin()));
        Log.e("TASKTASK", tt.getDateEnd() + "   " + Utils.dateToString(tt.getDateEnd()));

        try {

            long task_id = MyApplication.getDatabase().taskDao().insert(tt);


            /*List<TaskTagJoin> taskTagJoinList = new ArrayList<TaskTagJoin>();
            if (lsttags != null){
                for (Tag tag : lsttags) {

                    //TaskTagJoin taskTagJoin = new TaskTagJoin(task_id, tag.getId());
                    TaskTagJoin taskTagJoin = new TaskTagJoin(task_id, tag.getId(), guid);
                    taskTagJoinList.add(taskTagJoin);
                }
                MyApplication.getDatabase().taskTagJoinDao().insert(taskTagJoinList);
            }*/



            /*List<TaskContextJoin> taskContextJoins = new ArrayList<TaskContextJoin>();
            if (lstConteksts != null) {
                for (Contekst contekst : lstConteksts) {

                    TaskContextJoin taskContextJoin = new TaskContextJoin(task_id, contekst.getId(), guid);
                    taskContextJoins.add(taskContextJoin);
                }

                MyApplication.getDatabase().taskContextJoinDao().insert(taskContextJoins);
            }*/


            Toasty.success(getContext(), getContext().getString(R.string.taskcreated), Toast.LENGTH_SHORT, true).show();

        } catch (Exception e) {
            LOG.d("ERROR 0099", e.getMessage());
            Toasty.error(getContext(), getContext().getString(R.string.taskcreatederror), Toast.LENGTH_SHORT, true).show();
            //ViewUtils.viewNegativeToast(getContext(), getLayoutInflater(), String.valueOf(R.string.taskcreatederror), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);

                    /*
                    View toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view2, null);

                    // Initiate the Toast instance.
                    Toast toast = new Toast(getContext());
                    // Set custom view in toast.
                    toast.setView(toastView);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                    */
        }


        return tt;
    }

    public static String getIMEIDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    deviceId = mTelephony.getImei();
                } else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        //Log.d("deviceId", deviceId);
        return deviceId;
    }

    public static String getSerialDeviceId(Context context) {

        String deviceId = "";

        //if ( isPermissionGranted(READ_PHONE_STATE) ) {

        String simSerialNo = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            SubscriptionManager subsManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);


            List<SubscriptionInfo> subsList = subsManager.getActiveSubscriptionInfoList();

                if (subsList!=null) {
                    for (SubscriptionInfo subsInfo : subsList) {
                        if (subsInfo != null) {
                            deviceId  = subsInfo.getIccId();
                        }
                    }
                }
            } else {
                TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = tMgr.getSimSerialNumber();
            }
        //}
        return deviceId;
    }

    public static String getSerial() {

        String deviceId = "";

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
        {
            // Todo Don't forget to ask the permission
            deviceId = Build.SERIAL;
        }
        else
        {
            deviceId = Build.getSerial();

        }

        return deviceId;
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i(TAG, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }

    public static String getLocalIpAddress2(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        //return inetAddress.get();
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }

    public static String getLocalIpAddress3() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
