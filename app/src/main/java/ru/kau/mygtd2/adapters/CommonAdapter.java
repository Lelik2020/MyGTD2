package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.CommonType;
import ru.kau.mygtd2.fragments.TasksFragment;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.lstALLFAVOURITE;
import static ru.kau.mygtd2.utils.Const.lstALLPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstALLPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstALLSTATUS;
import static ru.kau.mygtd2.utils.Const.lstALLTAGSID;
import static ru.kau.mygtd2.utils.Const.lstALLTARGETSID;
import static ru.kau.mygtd2.utils.Const.lstPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstStatus;
import static ru.kau.mygtd2.utils.Const.lstTARGETSSID;
import static ru.kau.mygtd2.utils.Const.lstWITHOUTPROJECT;
import static ru.kau.mygtd2.utils.Const.lstWITHOUTTARGET;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {

    Context c;
    List<String> lstCommon;
    CommonType commonType;

    public CommonAdapter(Context c, List<String> lstCommon, CommonType commonType) {
        this.c = c;
        this.lstCommon = lstCommon;
        this.commonType = commonType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.common_cardview, parent,false);

        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String strLine = getItem(position);
        //final InfoStatus infoStatus = MyApplication.getDatabase().infoStatusDao().getById(information.getIdstatus());
        holder.title.setText(strLine);
        long count = 0L;
        long count2 = 0L;
        long count3 = 0L;
        long count4 = 0L;

        if (position == 0) {
            /*List<Integer> lstStatus = new ArrayList<>();
            lstStatus.add(1);
            lstStatus.add(2);
            lstStatus.add(3);*/
            count = MyApplication.getDatabase().taskDao().getCountTasks(lstALLSTATUS, lstALLPROJECTSID, lstALLTARGETSID);
            count2 = MyApplication.getDatabase().taskDao().getCountTasks(lstStatus, lstALLPROJECTSID, lstALLTARGETSID);
            //count3 = MyApplication.getDatabase().taskDao().getCountByDate(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()), lstStatus, lstALLPROJECTSID);
            //count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(Utils.getEndOfDay(new Date()).getTime(), lstStatus, lstALLPRIORITY, lstALLPROJECTSID);
            count3 = MyApplication.getDatabase().taskDao().getCountByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstALLTARGETSID);

            count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstALLTARGETSID);
        }

        if (position == 1) {
            switch (commonType) {
                case PROJECT:
                    count = MyApplication.getDatabase().taskDao().getCountTasks(lstALLSTATUS, lstPROJECTSID, lstALLTARGETSID);
                    count2 = MyApplication.getDatabase().taskDao().getCountTasks(lstStatus, lstPROJECTSID, lstALLTARGETSID);
                    count3 = MyApplication.getDatabase().taskDao().getCountByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstPROJECTSID, lstALLTARGETSID);
                    //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithProject(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()));
                    count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstPROJECTSID, lstALLTARGETSID);
                    break;

                case TARGET:

                    count = MyApplication.getDatabase().taskDao().getCountTasks(lstALLSTATUS, lstALLPROJECTSID, lstTARGETSSID);
                    count2 = MyApplication.getDatabase().taskDao().getCountTasks(lstStatus, lstALLPROJECTSID, lstTARGETSSID);
                    count3 = MyApplication.getDatabase().taskDao().getCountByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstTARGETSSID);
                    //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithProject(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()));
                    count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstTARGETSSID);
                    break;

                case TAG:
                    count = MyApplication.getDatabase().taskDao().getCountTasksWithTags(lstALLSTATUS, lstPROJECTSID, lstALLTARGETSID, lstALLTAGSID);
                    count2 = MyApplication.getDatabase().taskDao().getCountTasksWithTags(lstStatus, lstPROJECTSID, lstALLTARGETSID, lstALLTAGSID);
                    count3 = MyApplication.getDatabase().taskDao().getCountByDateWithTags(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstPROJECTSID, lstALLTARGETSID, lstALLTAGSID);
                    //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithProject(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()));
                    count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithTags(Utils.getEndOfDay(new Date()).getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstPROJECTSID, lstALLTARGETSID, lstALLTAGSID);
                    break;
            }
        }

        if (position == 2) {
            switch (commonType) {
                case PROJECT:
                    count = MyApplication.getDatabase().taskDao().getCountTasks(lstALLSTATUS, lstWITHOUTPROJECT, lstALLTARGETSID);
                    count2 = MyApplication.getDatabase().taskDao().getCountTasks(lstStatus, lstWITHOUTPROJECT, lstALLTARGETSID);
                    //count3 = MyApplication.getDatabase().taskDao().getCountByDateWithoutProject(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()));
                    count3 = MyApplication.getDatabase().taskDao().getCountByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstWITHOUTPROJECT, lstALLTARGETSID);
                    //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithoutProject(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()));
                    count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstWITHOUTPROJECT, lstALLTARGETSID);
                    break;
                case TAG:
                    count = MyApplication.getDatabase().taskDao().getCountTasksWithoutTags(lstALLSTATUS, lstPROJECTSID, lstALLTARGETSID);
                    count2 = MyApplication.getDatabase().taskDao().getCountTasksWithoutTags(lstStatus, lstPROJECTSID, lstALLTARGETSID);
                    //count3 = MyApplication.getDatabase().taskDao().getCountByDateWithoutTag(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()), lstStatus);
                    count3 = MyApplication.getDatabase().taskDao().getCountByDateWithoutTags(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstPROJECTSID, lstALLTARGETSID);
                    //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithoutTag(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()));
                    count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithoutTags(Utils.getEndOfDay(new Date()).getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstPROJECTSID, lstALLTARGETSID);
                    break;
                case TARGET:
                    //count = MyApplication.getDatabase().taskDao().getCountAllTasksWithoutTarget();
                    //count2 = MyApplication.getDatabase().taskDao().getCountAllActiveTasksWithoutTarget(lstStatus);
                    //count3 = MyApplication.getDatabase().taskDao().getCountByDateWithoutTarget(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()), lstStatus);
                    //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithoutTarget(new Date().getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, new Date()));
                    count = MyApplication.getDatabase().taskDao().getCountTasks(lstALLSTATUS, lstALLPROJECTSID, lstWITHOUTTARGET);
                    count2 = MyApplication.getDatabase().taskDao().getCountTasks(lstStatus, lstALLPROJECTSID, lstWITHOUTTARGET);
                    count3 = MyApplication.getDatabase().taskDao().getCountByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstWITHOUTTARGET);

                    count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstWITHOUTTARGET);
                    break;
                case CONTEXT:
                    break;

            }


        }

        holder.roundTextView.setCorner(16, 0, 0, 16);

        holder.roundTextView.setBgColor(Color.parseColor("#FF0000"));
        holder.roundTextView.setText(" " + Long.toString(count4) + " ");
        holder.roundTextView.setVisibility(View.VISIBLE);

        holder.roundTextView2.setCorner(0, 0, 0, 0);

        holder.roundTextView2.setBgColor(Color.parseColor("#33FF99"));
        holder.roundTextView2.setText(" " + Long.toString(count3) + " ");
        holder.roundTextView2.setVisibility(View.VISIBLE);

        holder.roundTextView3.setCorner(0, 0, 0, 0);

        holder.roundTextView3.setBgColor(Color.parseColor("#aa03A9F4"));
        holder.roundTextView3.setText(" " + Long.toString(count2) + " ");
        holder.roundTextView3.setVisibility(View.VISIBLE);

        holder.roundTextView4.setCorner(0, 16, 16, 0);

        holder.roundTextView4.setBgColor(Color.parseColor("#808080"));
        holder.roundTextView4.setText(" " + Long.toString(count) + " ");
        holder.roundTextView4.setVisibility(View.VISIBLE);

        GradientDrawable gradientDrawable = new GradientDrawable();
        //gradientDrawable.setColor(Color.GREEN);
        //gradientDrawable.setShape();


        gradientDrawable.setColor(Color.parseColor(Const.DEFAULT_TASKBG_COLOR));


        gradientDrawable.setStroke(2, R.color.colorPrimaryDark);
        gradientDrawable.setCornerRadius(15f);
        holder.common.setBackground(gradientDrawable);

        holder.lcommontitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                int number = 1;
                switch (commonType) {
                    case PROJECT:
                        number = (position == 0) ? 1 : (position == 1) ? 1000 : 1001;
                        break;
                    case TARGET:
                        number = (position == 0) ? 1 : (position == 1) ? 2000 : 2001;
                        break;
                }
                bundle.putInt("menunumber", number);
                TasksFragment tasksFragment = new TasksFragment();
                //position;
                tasksFragment.setArguments(bundle);
                ((MainActivity)c).getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, tasksFragment).commit();
            }
        });



        bindStringView(holder, position);
    }

    private String bindStringView(final ViewHolder holder, final int position) {

        final String strLine = (String) lstCommon.get(position);

        /*
        holder.menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onMenuClickListener != null) {
                    onMenuClickListener.onResultRecive(information);
                }
            }

        });
        */
        return null;
    }


    @Override
    public int getItemCount() {
        return lstCommon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        RoundTextView roundTextView;
        RoundTextView roundTextView2;
        RoundTextView roundTextView3;
        RoundTextView roundTextView4;
        LinearLayoutCompat lcommontitle;
        CardView common;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.commontitle);
            roundTextView = (RoundTextView) itemView.findViewById(R.id.count1);
            roundTextView2 = (RoundTextView) itemView.findViewById(R.id.count2);
            roundTextView3 = (RoundTextView) itemView.findViewById(R.id.count3);
            roundTextView4 = (RoundTextView) itemView.findViewById(R.id.count4);
            common = itemView.findViewById(R.id.common);
            lcommontitle = itemView.findViewById(R.id.lcommontitle);

        }
    }

    public String getItem(int pos) {
        if (lstCommon == null || lstCommon.isEmpty()) {
            return null;
        }
        if (lstCommon.size() - 1 >= pos) {
            return lstCommon.get(pos);
        } else {
            return lstCommon.get(0);
        }
    }
}
