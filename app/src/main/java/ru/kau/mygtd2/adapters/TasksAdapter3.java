package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;

public class TasksAdapter3 extends RecyclerView.Adapter<TasksAdapter3.ViewHolder>{

    private Context c;
    private List<Task> lstTask;

    LinearLayoutCompat.LayoutParams lParamscv;
    LinearLayoutCompat.LayoutParams lParamsiv;


    private CardView card;

    public TasksAdapter3(Context c, List<Task> lstTask) {
        this.c = c;
        this.lstTask = lstTask;
    }

    @NonNull
    @Override
    public TasksAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.task_cardview_3,viewGroup,false);

        return new TasksAdapter3.ViewHolder(v);
    }



    @Override
    public int getItemCount() {
        return ((lstTask != null) ? lstTask.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView title2;
        ImageView typeTask;
        TextView taskdetail;

        ImageView priorityicon;
        TextView priorityName;


        RoundTextView statusTask2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tasktitle);
            typeTask = (ImageView) itemView.findViewById(R.id.tasktype);
            title2 = (TextView) itemView.findViewById(R.id.tasktitle2);
            card = itemView.findViewById(R.id.alltaskinfo);
            taskdetail = itemView.findViewById(R.id.taskdetail);
            statusTask2 = itemView.findViewById(R.id.statusTask2);
            priorityicon = itemView.findViewById(R.id.priorityicon);
            priorityName = itemView.findViewById(R.id.priorityName);


        }


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TasksAdapter3.ViewHolder viewHolder, int i) {

        lParamsiv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
        viewHolder.title.setText(lstTask.get(i).getTypeoftask().name() + " - " + lstTask.get(i).getId());
        TaskTypes taskTypes = MyApplication.getDatabase().taskTypesDao().getById(lstTask.get(i).getTypeoftask().Value);
        viewHolder.typeTask.setImageResource(Utils.getImageResourceTaskType(lstTask.get(i).getTypeoftask()));
        viewHolder.typeTask.setLayoutParams(lParamsiv);
        viewHolder.title.setTextColor(Color.parseColor(taskTypes.getColor()));
        viewHolder.title2.setText(lstTask.get(i).getTitle());
        TxtUtils.underlineTextView(viewHolder.title);
        lParamscv = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        lParamscv.setMargins(3, 3, 3, 3);
        card.setLayoutParams(lParamscv);
        viewHolder.statusTask2.setCorner(20);
        viewHolder.statusTask2.setPadding(10, 0, 10, 0);
        //viewHolder.statusTask2.setText(taskStatus.getTitle());
        //viewHolder.statusTask2.setBgColor(Color.parseColor(taskStatus.getColor()));

        viewHolder.priorityicon.setImageResource(Utils.getIconForPriority(MyApplication.getDatabase().priorityDao().getById(lstTask.get(i).getPriority_id())));
        viewHolder.priorityName.setText(MyApplication.getDatabase().priorityDao().getById(lstTask.get(i).getPriority_id()).getTitle());


        viewHolder.statusTask2.setText(MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value).getTitle());
        viewHolder.statusTask2.setBgColor(Color.parseColor(MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value).getColor()));



        //viewHolder.taskdetail.setText(lstTask.get(i).getDateEndStr() + " WWW " + Utils.dateToString(lstTask.get(i).getDateEnd()));

        GradientDrawable gradientDrawable = new GradientDrawable();
        //gradientDrawable.setColor(Color.GREEN);
        //gradientDrawable.setShape();

        try {
            gradientDrawable.setColor(Color.parseColor(lstTask.get(i).getBgColor()));
        } catch (Exception e) {
            //viewHolder.card.setBackgroundResource(R.drawable.selector_2);
            gradientDrawable.setColor(Color.parseColor(Const.DEFAULT_TASKBG_COLOR));
        }
        gradientDrawable.setStroke(3, R.color.colorPrimaryDark);
        gradientDrawable.setCornerRadius(25f);
        card.setBackground(gradientDrawable);
    }
}
