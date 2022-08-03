package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.fragments.TasksFragment;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.lstALLFAVOURITE;
import static ru.kau.mygtd2.utils.Const.lstALLPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstALLPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstALLTARGETSID;
import static ru.kau.mygtd2.utils.Const.lstStatus;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder>{

    private Context c;
    private List<Target> lstTargets;

    public TargetAdapter(Context c, List<Target> lstTargets) {
        this.c = c;
        this.lstTargets = lstTargets;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.target_cardview, parent,false);

        return new TargetAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Target target = getItem(position);
        holder.title.setText(target.getTitle());

        long count = 0L;
        long count2 = 0L;
        long count3 = 0L;
        long count4 = 0L;

        count = MyApplication.getDatabase().taskDao().getCountAllTasksByTarget(target.getId());
        count2 = MyApplication.getDatabase().taskDao().getCountAllActiveTasksByTag(target.getId(), lstStatus);
        //count3 = MyApplication.getDatabase().taskDao().getCountByDateByTarget(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), target.getId());
        count3 = MyApplication.getDatabase().taskDao().getCountByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, new ArrayList<Integer>() {
            {
                add((int)target.getId());
            }
        });
        //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingByTarget(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), target.getId());

        count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, new ArrayList<Integer>() {
            {
                add((int)target.getId());
            }
        });

        /*count4 = MyApplication.getDatabase().taskDao().getCountOutstanding(Utils.getStartOfDay(new Date()).getTime(), lstStatus, lstALLPRIORITY, new ArrayList<Integer>() {
            {
                add(((Long) node.getId()).intValue());

            }
        });*/



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


        gradientDrawable.setStroke(2, R.color.black_1);
        gradientDrawable.setCornerRadius(15f);
        holder.alltargetinfo.setBackground(gradientDrawable);

        holder.lnlmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("target", target);
                TasksFragment tasksFragment = new TasksFragment();
                tasksFragment.setArguments(bundle);
                ((MainActivity)c).getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, tasksFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstTargets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        RoundTextView roundTextView;
        RoundTextView roundTextView2;
        RoundTextView roundTextView3;
        RoundTextView roundTextView4;
        ImageView tagImage;
        CardView alltargetinfo;

        LinearLayoutCompat lnlmain;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alltargetinfo = itemView.findViewById(R.id.alltargetinfo);
            tagImage = (ImageView) itemView.findViewById(R.id.target_icon);
            title = (TextView) itemView.findViewById(R.id.targettitle);
            roundTextView = (RoundTextView) itemView.findViewById(R.id.targetcount1);
            roundTextView2 = (RoundTextView) itemView.findViewById(R.id.targetcount2);
            roundTextView3 = (RoundTextView) itemView.findViewById(R.id.targetcount3);
            roundTextView4 = (RoundTextView) itemView.findViewById(R.id.targetcount4);

            lnlmain = itemView.findViewById(R.id.lltitle);

        }
    }

    public Target getItem(int pos) {
        if (lstTargets == null || lstTargets.isEmpty()) {
            return null;
        }
        if (lstTargets.size() - 1 >= pos) {
            return lstTargets.get(pos);
        } else {
            return lstTargets.get(0);
        }
    }

}
