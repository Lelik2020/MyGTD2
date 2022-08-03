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
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.lstALLFAVOURITE;
import static ru.kau.mygtd2.utils.Const.lstALLPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstALLPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstALLTARGETSID;
import static ru.kau.mygtd2.utils.Const.lstStatus;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private Context c;
    private List<Tag> lstTag;

    public TagsAdapter(Context c, List<Tag> lstTag) {
        this.c = c;
        this.lstTag = lstTag;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.tag_cardview, parent,false);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(15f);
        v.setBackground(drawable);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tag tag = getItem(position);
        holder.title.setText(tag.getTitle());
        //holder.title.setTextColor(Color.parseColor(tag.getColor()));
        holder.tagImage.setColorFilter(Color.parseColor(tag.getColor()));
        //holder.tagImage.set

        long count = 0L;
        long count2 = 0L;
        long count3 = 0L;
        long count4 = 0L;

        count = MyApplication.getDatabase().taskDao().getCountAllTasksByTag(tag.getId());
        count2 = MyApplication.getDatabase().taskDao().getCountAllActiveTasksByTag(tag.getId(), lstStatus);
        //count3 = MyApplication.getDatabase().taskDao().getCountByDateByTag(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), tag.getId());
        //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingByTag(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), tag.getId());

        count3 = MyApplication.getDatabase().taskDao().getCountByDateWithTags(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())),
                lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstALLTARGETSID, new ArrayList<Integer>() {
                    {
                        tag.getId();
                    }
                });
        //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingByTarget(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), target.getId());

        count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithTags(Utils.getEndOfDay(new Date()).getTime(),
                lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstALLTARGETSID, new ArrayList<Integer>() {
                    {
                        tag.getId();
                    }
                });
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


        holder.lnlmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("tag", tag);
                TasksFragment tasksFragment = new TasksFragment();
                tasksFragment.setArguments(bundle);
                ((MainActivity)c).getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, tasksFragment).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return lstTag.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        RoundTextView roundTextView;
        RoundTextView roundTextView2;
        RoundTextView roundTextView3;
        RoundTextView roundTextView4;
        ImageView tagImage;

        LinearLayoutCompat lnlmain;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagImage = (ImageView) itemView.findViewById(R.id.tag_tree_icon);
            title = (TextView) itemView.findViewById(R.id.tagtitle);
            roundTextView = (RoundTextView) itemView.findViewById(R.id.tagcount1);
            roundTextView2 = (RoundTextView) itemView.findViewById(R.id.tagcount2);
            roundTextView3 = (RoundTextView) itemView.findViewById(R.id.tagcount3);
            roundTextView4 = (RoundTextView) itemView.findViewById(R.id.tagcount4);
            lnlmain = itemView.findViewById(R.id.lltitle);

        }
    }

    public Tag getItem(int pos) {
        if (lstTag == null || lstTag.isEmpty()) {
            return null;
        }
        if (lstTag.size() - 1 >= pos) {
            return lstTag.get(pos);
        } else {
            return lstTag.get(0);
        }
    }

}
