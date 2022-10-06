package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.dialogs.ShareDialog;
import ru.kau.mygtd2.fragments.AddTaskFragment;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Target;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHSECONDS;
import static ru.kau.mygtd2.utils.Const.DEFAULT_TEXT_COLOR;
import static ru.kau.mygtd2.utils.Const.TASK_LEVEL_OFFSET;

public class TasksAdapter2 extends RecyclerView.Adapter<TasksAdapter2.ViewHolder>{


    private Context c;
    private List<Task> lstTask;



    LinearLayoutCompat.LayoutParams lParamstv;

    LinearLayoutCompat.LayoutParams lParamsll;
    LinearLayoutCompat.LayoutParams lParamsiv;
    LinearLayoutCompat.LayoutParams lParamsrtv;

    CardView.LayoutParams lParamscv;
    ViewGroup.MarginLayoutParams lParamscv2;
    RecyclerView.LayoutParams lParamscv3;
    ViewGroup.LayoutParams lParamscv4;

    private ResultResponse<Task> onMenuClickListener;

    public TasksAdapter2(Context c, List<Task> lstTask) {
        this.c = c;
        this.lstTask = lstTask;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView title2;
        TextView taskdetail;
        ImageView typeTask;

        TextView parenttitle;
        TextView parenttitle2;
        ImageView parenttypeTask;

        LinearLayoutCompat statusTaskll;
        LinearLayoutCompat parenttaskll;

        LinearLayoutCompat taskInfo1;
        LinearLayoutCompat taskInfo2;
        LinearLayoutCompat taskInfo3;
        LinearLayoutCompat taskTags;

        ImageView targeticon;
        TextView targetTitle;

        ImageView projecticon;
        TextView projectName;

        TextView details;

        SmoothCheckBox statusTask;

        CardView card;

        ImageView editTask;
        TextView datedoneTask;

        RoundTextView statusTask2;

        ImageView starIcon;
        ImageView priorityicon;
        TextView priorityName;

        ImageView menu;

        ImageView category;
        ImageView itemdevice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tasktitle);
            typeTask = itemView.findViewById(R.id.tasktype);
            statusTask= itemView.findViewById(R.id.statusTask);
            title2 = itemView.findViewById(R.id.tasktitle2);
            taskdetail = itemView.findViewById(R.id.taskdetail);

            parenttitle = itemView.findViewById(R.id.parenttasktitle);
            parenttypeTask = itemView.findViewById(R.id.parenttasktype);
            parenttitle2 = itemView.findViewById(R.id.parenttasktitle2);

            taskInfo1 = itemView.findViewById(R.id.taskInfo1);
            taskInfo2 = itemView.findViewById(R.id.taskInfo2);
            taskInfo3 = itemView.findViewById(R.id.taskInfo3);
            taskTags = itemView.findViewById(R.id.taskTags);
            statusTaskll = itemView.findViewById(R.id.statusTaskll);
            parenttaskll = itemView.findViewById(R.id.lparenttasktitle);

            projecticon = itemView.findViewById(R.id.projecticon);
            projectName = itemView.findViewById(R.id.projectName);
            targeticon = itemView.findViewById(R.id.targeticon);
            targetTitle = itemView.findViewById(R.id.targettitle);
            details = itemView.findViewById(R.id.details);
            card = itemView.findViewById(R.id.alltaskinfo);
            editTask = itemView.findViewById(R.id.editTask);
            statusTask2 = itemView.findViewById(R.id.statusTask2);
            datedoneTask = itemView.findViewById(R.id.datedoneTask);
            menu = itemView.findViewById(R.id.itemTaskMenu);
            starIcon = itemView.findViewById(R.id.starIcon);
            priorityicon = itemView.findViewById(R.id.priorityicon);
            priorityName = itemView.findViewById(R.id.priorityName);
            category = itemView.findViewById(R.id.category);
            itemdevice = itemView.findViewById(R.id.itemdevice);


        }

    }

    public void setOnMenuClickListener(ResultResponse<Task> onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    @NonNull
    @Override
    public TasksAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.task_cardview_new2,viewGroup,false);

        return new TasksAdapter2.ViewHolder(v);
    }


    /*@SuppressLint("ResourceAsColor")
    //@Override
    public void onBindViewHolder_old(@NonNull TasksAdapter2.ViewHolder viewHolder, int i) {

        *//*lParamscv3 = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        lParamscv3.setMargins(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET, 0, 0, 0);
        card.setLayoutParams(lParamscv3);*//*
        FrameLayout.LayoutParams lParamscv5 = (FrameLayout.LayoutParams) viewHolder.card.getLayoutParams();
        lParamscv5.setMargins(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET, 3, 0, 0);
        viewHolder.card.setLayoutParams(lParamscv5);


        viewHolder.title.setText(lstTask.get(i).getTypeOfTask().name() + "-" + lstTask.get(i).getId());
    }*/



    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TasksAdapter2.ViewHolder viewHolder, int i) {


        lParamscv = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
        lParamscv.setMargins(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET + 10, 9, 5, 5);
        viewHolder.card.setLayoutParams(lParamscv);

        viewHolder.title.setText(lstTask.get(i).getTypeOfTask().name() + "-" + lstTask.get(i).getId());
        TaskTypes taskTypes = MyApplication.getDatabase().taskTypesDao().getById(lstTask.get(i).getTypeOfTask().Value);
        viewHolder.title.setTextColor(Color.parseColor(taskTypes.getColor()));
        viewHolder.title2.setText(lstTask.get(i).getTitle());
        viewHolder.taskdetail.setText(lstTask.get(i).getDescription());
        TxtUtils.underlineTextView(viewHolder.title);
        viewHolder.typeTask.setImageResource(Utils.getImageResourceTaskType(lstTask.get(i).getTypeOfTask()));
        //viewHolder.typeTask.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //lParamsll = new LinearLayoutCompat.LayoutParams((int) c.getResources().getDimension(R.dimen.wh_layout_big2), (int) c.getResources().getDimension(R.dimen.wh_layout_big2));
        lParamsll = new LinearLayoutCompat.LayoutParams((int) c.getResources().getDimension(R.dimen.wh_button_small2), ViewGroup.LayoutParams.WRAP_CONTENT);
        lParamsiv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
        lParamsrtv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_RTV_WIDTH, Const.DEFAULT_RTV_HEIGHT);
        lParamsiv.setMargins(5, 5, 0, 0);
        //viewHolder.typeTask.setLayoutParams(lParamsiv);
        //Log.e("dateEnd", String.valueOf(lstTask.get(i).getDateEnd().getTime()) + "   " + Utils.dateToString(lstTask.get(i).getDateEnd()));

        Task parenttask = MyApplication.getDatabase().taskDao().getById(lstTask.get(i).getParenttask_id());
        if (parenttask != null){
            viewHolder.parenttitle.setText(parenttask.getTypeOfTask().name() + "-" + parenttask.getId());
            taskTypes = MyApplication.getDatabase().taskTypesDao().getById(parenttask.getTypeOfTask().Value);
            viewHolder.parenttitle.setTextColor(Color.parseColor(taskTypes.getColor()));
            viewHolder.parenttitle2.setText(parenttask.getTitle());
            TxtUtils.underlineTextView(viewHolder.parenttitle);
            viewHolder.parenttypeTask.setImageResource(Utils.getImageResourceTaskType(parenttask.getTypeOfTask()));
            //lParamsll = new LinearLayoutCompat.LayoutParams((int) c.getResources().getDimension(R.dimen.wh_layout_big2), (int) c.getResources().getDimension(R.dimen.wh_layout_big2));
            lParamsll = new LinearLayoutCompat.LayoutParams((int) c.getResources().getDimension(R.dimen.wh_button_small2), ViewGroup.LayoutParams.WRAP_CONTENT);
            lParamsiv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTHSMALL, Const.DEFAULT_ICON_HEIGHTSMALL);
            lParamsrtv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_RTV_WIDTH, Const.DEFAULT_RTV_HEIGHT);
            lParamsiv.setMargins(5, 5, 0, 0);
            //viewHolder.parenttypeTask.setLayoutParams(lParamsiv);
        } else {
            viewHolder.parenttaskll.setVisibility(View.GONE);
        }


        /*viewHolder.datedoneTask.setText(Utils.dateToString(lstTask.get(i).getDateEnd()));
        viewHolder.datedoneTask.setTextColor(Color.parseColor(Utils.getColorByEndDate(lstTask.get(i).getDateEnd())));*/

        //viewHolder.datedoneTask.setText("  " + Utils.dateToString(lstTask.get(i).getDateEnd()) + "  ");
        viewHolder.datedoneTask.setText(" " + Utils.dateToString(DEFAULT_DATEFORMAT, lstTask.get(i).getDateEnd()) + " ");



        //viewHolder.datedoneTask.setTextColor(R.color.white);
        if (lstTask.get(i).getDateEnd() != null && lstTask.get(i).getStatus() != Status.COMPLETED) {
        //if (lstTask.get(i).getDateEnd() != null && Status.from(lstTask.get(i).getStatus()) != Status.COMPLETED) {
            viewHolder.datedoneTask.setBackground(c.getResources().getDrawable(Utils.getBackgroundByEndDate(lstTask.get(i).getDateEnd())));
        } else {
            viewHolder.datedoneTask.setTextColor(R.color.black);
            viewHolder.datedoneTask.setTextSize(13);
            //viewHolder.datedoneTask.setTypeface(viewHolder.datedoneTask.getTypeface(), Typeface.BOLD);
            //viewHolder.datedoneTask.setTextAppearance(c, R.style.boldText);
            viewHolder.datedoneTask.setTextAppearance(R.style.boldText);
        }

        if (lstTask.get(i).getStatus()== Status.COMPLETED) {
        //if (Status.from(lstTask.get(i).getStatus()) == Status.COMPLETED) {
        }



        viewHolder.statusTask.setChecked((lstTask.get(i).getStatus() != Status.COMPLETED) ? false : true);
        //viewHolder.statusTask.setChecked((Status.from(lstTask.get(i).getStatus()) != Status.COMPLETED) ? false : true);
        lParamsiv = new LinearLayoutCompat.LayoutParams((int) c.getResources().getDimension(R.dimen.wh_button_big2), (int) c.getResources().getDimension(R.dimen.wh_button_big2));
        viewHolder.statusTask.setLayoutParams(lParamsiv);
        //lParamsiv.setMargins(5, 5, 0, 0);
        lParamsll = new LinearLayoutCompat.LayoutParams((int) c.getResources().getDimension(R.dimen.wh_button_big2), ViewGroup.LayoutParams.MATCH_PARENT);
        lParamsll.setMargins(2, 2, 0, 0);
        viewHolder.statusTaskll.setLayoutParams(lParamsll);
        viewHolder.statusTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lstTask.get(i).getStatus() != Status.COMPLETED) {
                //if (Status.from(lstTask.get(i).getStatus()) != Status.COMPLETED) {
                    lstTask.get(i).setPreviousStatus(lstTask.get(i).getStatus());
                    lstTask.get(i).setStatus(Status.COMPLETED);
                    //lstTask.get(i).setPreviousStatus(Status.from(lstTask.get(i).getStatus()));
                    //lstTask.get(i).setStatus(Status.COMPLETED.Value);
                    lstTask.get(i).setDateClose(new Date());
                    lstTask.get(i).setDateCloseStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), lstTask.get(i).getDateClose()));
                } else {
                    lstTask.get(i).setStatus(lstTask.get(i).getPreviousStatus());
                    //lstTask.get(i).setStatus(lstTask.get(i).getPreviousStatus().Value);
                    lstTask.get(i).setPreviousStatus(Status.NOTHING);
                    lstTask.get(i).setDateClose(null);
                    lstTask.get(i).setDateCloseStr(null);
                }
                viewHolder.statusTask.setChecked((lstTask.get(i).getStatus() != Status.COMPLETED) ? false : true);
                //viewHolder.statusTask.setChecked((Status.from(lstTask.get(i).getStatus()) != Status.COMPLETED) ? false : true);
                lParamsiv = new LinearLayoutCompat.LayoutParams((int) c.getResources().getDimension(R.dimen.wh_button_big2), (int) c.getResources().getDimension(R.dimen.wh_button_big2));
                viewHolder.statusTask.setLayoutParams(lParamsiv);
                Date date = new Date();
                lstTask.get(i).setDateEdit(date);
                lstTask.get(i).setDateEditStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));
                MyApplication.getDatabase().taskDao().update(lstTask.get(i));
                //notifyDataSetChanged();
                notifyItemChanged(i);
            }
        });

        viewHolder.starIcon.setImageResource((lstTask.get(i).getIsFavourite() > 0) ? R.drawable.star55press: R.drawable.star55);
        //viewHolder.starIcon.setColorFilter(R.color.footerbackground);
        viewHolder.starIcon.setColorFilter(R.color.colorPrimary);

        viewHolder.category.setColorFilter(Color.parseColor(MyApplication.getDatabase().taskCategoryDao().getById(lstTask.get(i).getCategory()).getColor()));
        //viewHolder.starIcon.setLayoutParams(lParamsiv);

        viewHolder.starIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lstTask.get(i).getIsFavourite() <= 0) {
                    lstTask.get(i).setIsFavourite(1);
                    viewHolder.starIcon.setImageResource(R.drawable.star55);
                    //viewHolder.starIcon.setColorFilter(R.color.bugcolor);
                } else {
                    lstTask.get(i).setIsFavourite(0);
                    viewHolder.starIcon.setImageResource(R.drawable.star55press);
                }
                viewHolder.starIcon.setColorFilter(R.color.colorPrimary);
                //viewHolder.starIcon.setLayoutParams(lParamsiv);
                Date date = new Date();
                lstTask.get(i).setDateEdit(date);
                lstTask.get(i).setDateEditStr(Utils.dateToString(DEFAULT_DATEFORMAT_WITHSECONDS, date));
                MyApplication.getDatabase().taskDao().update(lstTask.get(i));
            }
        });

        /*viewHolder.taskInfo1.removeAllViews();
        viewHolder.taskInfo1.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tv = new TextView(c);
        lParamstv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_TV_WIDTH, Const.DEFAULT_TV_HEIGHT);
        tv.setLayoutParams(lParamstv);
        tv.setText("Сделать до: ");
        tv.setTextAppearance(R.style.textDialogTextView);
        viewHolder.taskInfo1.addView(tv);

        tv = new TextView(c);
        lParamstv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_TV_WIDTH, Const.DEFAULT_TV_HEIGHT);
        tv.setLayoutParams(lParamstv);
        tv.setText((lstTask.get(i).getDateEndStr() == null || "".equals(lstTask.get(i).getDateEndStr())) ? "" : lstTask.get(i).getDateEndStr());
        tv.setTextColor(Color.parseColor(Utils.getColorByEndDate(lstTask.get(i).getDateEnd())));
        viewHolder.taskInfo1.addView(tv);

        LinearLayoutCompat.LayoutParams rightGravityParams = new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;
        tv = new TextView(c);
        lParamstv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_TV_WIDTH, Const.DEFAULT_TV_HEIGHT);
        rightGravityParams.gravity = Gravity.RIGHT;//(RelativeLayout.ALIGN_PARENT_RIGHT);
        tv.setLayoutParams(lParamstv);
        tv.setText("Статус:");
        tv.setTextAppearance(R.style.textDialogTextView);

        viewHolder.taskInfo1.addView(tv);

        RoundTextView rtv1 = new RoundTextView(c);
        //rtv1.setText(lstTask.get(0).getTitle());

        rtv1.setCorner(20);
        rtv1.setPadding(10, 0, 10, 0);
        //rtv1.setCorner(5, 5, 5, 5);
        rtv1.setBgColor(Color.parseColor(MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value).getColor()));
        rtv1.setText( MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value).getTitle());
        rtv1.setVisibility(View.VISIBLE);
        rtv1.setLayoutParams(lParamsrtv);
        viewHolder.taskInfo1.addView(rtv1, rightGravityParams);*/

        //TaskStatus taskStatus = MyApplication.getDatabase().taskStatusDao().getById(MyApplication.getDatabase().taskDao().getStatusByTask(lstTask.get(i).getId()));

        viewHolder.statusTask2.setCorner(20);
        viewHolder.statusTask2.setPadding(10, 0, 10, 0);
        //viewHolder.statusTask2.setText(taskStatus.getTitle());
        //viewHolder.statusTask2.setBgColor(Color.parseColor(taskStatus.getColor()));

        viewHolder.priorityicon.setImageResource(Utils.getIconForPriority(MyApplication.getDatabase().priorityDao().getById(lstTask.get(i).getPriority_id())));
        viewHolder.priorityName.setText(MyApplication.getDatabase().priorityDao().getById(lstTask.get(i).getPriority_id()).getTitle());


        viewHolder.statusTask2.setText(MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value).getTitle());
        viewHolder.statusTask2.setBgColor(Color.parseColor(MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value).getColor()));



        /*viewHolder.taskInfo2.removeAllViews();
        ImageView iv = new ImageView(c);
        iv.setImageResource(R.drawable.folder);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        lParamsiv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
        iv.setLayoutParams(lParamsiv);

        viewHolder.taskInfo2.addView(iv);

            *//*lParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*//*

        RoundTextView rtv1 = new RoundTextView(c);
        //rtv1.setText(lstTask.get(0).getTitle());

        rtv1.setCorner(20);
        rtv1.setPadding(10, 0, 10, 0);
        //rtv1.setCorner(5, 5, 5, 5);
        //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
        rtv1.setTextColor( c.getColor(R.color.black)  );
        rtv1.setTextSize(14);
        rtv1.setGravity(Gravity.CENTER_HORIZONTAL);
        rtv1.setTypeface(Typeface.DEFAULT_BOLD);


        try {
            rtv1.setBgColor(Color.parseColor(MyApplication.getDatabase().projectDao().getProjectById(lstTask.get(i).getProject_id()).getColor()));
        } catch (Exception ex) {
            rtv1.setBgColor(Color.parseColor(DEFAULT_PROJECT_COLOR));
        }
        //rtv1.setCorner(2, 2, 2, 2);
        //rtv1.setCorner(5);
        Project project = MyApplication.getDatabase().projectDao().getProjectById(lstTask.get(i).getProject_id());

        rtv1.setText((project == null) ? "": project.getTitle());


        lParamsrtv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        rtv1.setLayoutParams(lParamsrtv);

        viewHolder.taskInfo2.addView(rtv1);*/

        Project project = MyApplication.getDatabase().projectDao().getProjectById(lstTask.get(i).getProject_id());
        if (project == null){
            viewHolder.projecticon.setVisibility(View.INVISIBLE);
            viewHolder.projectName.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.projectName.setText(project.getTitle());
            viewHolder.projectName.setVisibility(View.VISIBLE);
            LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
            //viewHolder.targeticon.setLayoutParams(lParams);

            viewHolder.targetTitle.setTextSize(14);
            viewHolder.projectName.setTypeface(Typeface.DEFAULT_BOLD);
            viewHolder.projectName.setGravity(Gravity.CENTER_VERTICAL);
        }

        Target target = MyApplication.getDatabase().targetDao().getById(lstTask.get(i).getTarget_id());
        if (target == null) {

            viewHolder.targeticon.setVisibility(View.INVISIBLE);
            viewHolder.targetTitle.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.targeticon.setVisibility(View.VISIBLE);
            LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
            //viewHolder.targeticon.setLayoutParams(lParams);

            viewHolder.targetTitle.setTextSize(14);
            viewHolder.targetTitle.setText(target.getTitle());
            viewHolder.targetTitle.setVisibility(View.VISIBLE);
            /*lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT2);
            viewHolder.targetTitle.setLayoutParams(lParams);*/
            viewHolder.targetTitle.setTypeface(Typeface.DEFAULT_BOLD);
            viewHolder.targetTitle.setGravity(Gravity.CENTER_VERTICAL);
        }

        viewHolder.taskTags.removeAllViews();

        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT, 1.0f);
        viewHolder.taskTags.setLayoutParams(lParams);
        List<Tag> lsttags;

        List<Tag> tags = MyApplication.getDatabase().tagDao().getAllByTask(lstTask.get(i).getId());
        if (tags != null) {
            lsttags = new ArrayList<Tag>();
            lsttags.addAll(tags);
            if (tags.size() > 0) {

                //LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(Const.LAYOUT_DEFAULT_WIDTH, Const.LAYOUT_DEFAULT_HEIGHT);


                //iv.setMinimumWidth(25);
                //iv.setMinimumHeight(25);
                //iv.getLayoutParams().width = 25;
                //iv.getLayoutParams().height = 25;
                //iv.setMaxWidth(25);
                //iv.setMaxHeight(25);
                //iv.setla



                //ltasktags.addView(iv);



                for (int j = 0; j < tags.size(); j++) {

                    LinearLayoutCompat.LayoutParams lParams2 = new LinearLayoutCompat.LayoutParams(Const.LAYOUT_DEFAULT_WIDTH, Const.LAYOUT_DEFAULT_HEIGHT);

                    ImageView iv = new ImageView(c);
                    iv.setImageResource(R.drawable.merchandising);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
                    iv.setLayoutParams(lParams);
                    //iv.setLayoutParams(lParams2);
                    try {
                        iv.setColorFilter(Color.parseColor(tags.get(j).getColor()));
                    } catch (Exception e){
                        iv.setColorFilter(Color.parseColor("#000000"));
                    }

                    viewHolder.taskTags.addView(iv);

                    /*RoundTextView rtv1 = new RoundTextView(c);


                    rtv1.setCorner(20);
                    rtv1.setPadding(10, 0, 10, 0);

                    rtv1.setBgColor(Utils.parseColor(tags.get(j).getColor()));
                    rtv1.setText(tags.get(j).getTitle());
                    rtv1.setTextSize(16);
                    rtv1.setTextColor(R.color.text_black_100);
                    rtv1.setTypeface(Typeface.DEFAULT_BOLD);*/

                    TextView rtv1 = new TextView(c);


                    //rtv1.setCorner(20);
                    rtv1.setPadding(10, 0, 10, 0);

                    //rtv1.setBgColor(Utils.parseColor(tags.get(j).getColor()));
                    //rtv1.setText(tags.get(j).getTitle() + " 123");
                    //rtv1.setTextSize(16);
                    //rtv1.setTextColor(R.color.text_black_100);
                    //rtv1.setTypeface(Typeface.DEFAULT_BOLD);
                    rtv1.setTextSize(16);
                    rtv1.setText(tags.get(j).getTitle());
                    rtv1.setTypeface(Typeface.DEFAULT_BOLD);
                    rtv1.setTextColor(Color.parseColor(DEFAULT_TEXT_COLOR));

                    //rtv1.setBgColor(Color.parseColor(lstTag.get(i).getColor()));
                    //rtv1.setCorner(2, 2, 2, 2);
                    //rtv1.setCorner(5);

                    /*lParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
                    viewHolder.taskTags.addView(rtv1);
                }
            }
        }

        //viewHolder.taskTags.addView(new TextView().);

        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareDialog().showTaskInfoDialog((AppCompatActivity) c, (Task) lstTask.get(i));
            }
        });

        //viewHolder.editTask.setLayoutParams(lParamsiv);
        viewHolder.editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", lstTask.get(i));

                //if
                AddTaskFragment addTaskFragment = new AddTaskFragment();
                addTaskFragment.setArguments(bundle);
                ((MainActivity)c).getSupportFragmentManager().beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,addTaskFragment).commit();
            }
        });

        viewHolder.itemdevice.setImageResource(Utils.getImageResourceDeviceType(MyApplication.getDatabase().deviceDao().getDeviceType(lstTask.get(i).getDeviceguid())));

        //viewHolder.card.setCardBackgroundColor(Color.RED);


        //viewHolder.card.setBackgroundResource(R.drawable.selector_2);
        //viewHolder.card.setBackgroundResource(R.drawable.selector_2);

        /*Drawable background = ResourcesCompat.getDrawable(c.getResources(), R.drawable.gradient, null);
        background.mutate();
        background.setColorFilter(R.color.blue, PorterDuff.Mode.LIGHTEN);
        viewHolder.card.setBackground(background);*/
        /*try {
            viewHolder.card.getBackground().setTint(Color.parseColor(lstTask.get(i).getBgColor()));
        } catch (Exception e) {
            //viewHolder.card.setBackgroundResource(R.drawable.selector_2);
            viewHolder.card.getBackground().setTint(Color.parseColor(Const.DEFAULT_TASKBG_COLOR));
        }*/

        //RoundRectDrawableWithShadow backgroundDrawable
        //viewHolder.card.setCardBackgroundColor(Color.parseColor("#FF0000"));

        //viewHolder.card.getBackgroundResource();
        //viewHolder.card.getBackground().setTint(Color.RED);

        /*GradientDrawable gradientDrawable = new GradientDrawable();
        //gradientDrawable.setColor(Color.GREEN);
        //gradientDrawable.setShape();

        try {
            gradientDrawable.setColor(Color.parseColor(lstTask.get(i).getBgColor()));
        } catch (Exception e) {
            //viewHolder.card.setBackgroundResource(R.drawable.selector_2);
            gradientDrawable.setColor(Color.parseColor(Const.DEFAULT_TASKBG_COLOR));
        }
        gradientDrawable.setStroke(4, R.color.colorPrimaryDark);
        setCornerRadius(gradientDrawable, 20f);
        viewHolder.card.setBackground(gradientDrawable);*/



        bindTaskView(viewHolder, i);


        /*viewHolder.taskInfo3.removeAllViews();

        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        viewHolder.taskInfo3.setLayoutParams(lParams);

        iv = new ImageView(c);
        iv.setImageResource(R.drawable.target4);
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
        iv.setLayoutParams(lParams);

        viewHolder.taskInfo3.addView(iv);

            *//*lParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*//*

        rtv1 = new RoundTextView(c);
        //rtv1.setText(lstTask.get(0).getTitle());

        rtv1.setCorner(20);
        rtv1.setPadding(10, 0, 10, 0);
        //rtv1.setCorner(5, 5, 5, 5);
        //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
        rtv1.setTextColor(R.color.black);

        Target target = MyApplication.getDatabase().targetDao().getById(lstTask.get(i).getTarget_id());

        try {
            rtv1.setBgColor(Color.parseColor(target.getColor()));
        } catch (Exception ex) {
            rtv1.setBgColor(Color.parseColor(DEFAULT_PROJECT_COLOR));
        }
        //rtv1.setCorner(2, 2, 2, 2);
        //rtv1.setCorner(5);

        rtv1.setText((target == null) ? "" : target.getTitle());
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        rtv1.setLayoutParams(lParams);
        rtv1.setTypeface(Typeface.DEFAULT_BOLD);

        viewHolder.taskInfo3.addView(rtv1);


        lParamsrtv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_RTV_WIDTH, Const.DEFAULT_RTV_HEIGHT);
        rtv1 = new RoundTextView(c);

        lParamsrtv.gravity = Gravity.RIGHT;
        rtv1.setLayoutParams(lParamsrtv);

        //rtv1.setGravity(Gravity.RIGHT);
        rtv1.setText(R.string.details);

        rtv1.setTextAppearance(R.style.textLink);

        TxtUtils.underlineTextView(rtv1);

        viewHolder.taskInfo3.addView(rtv1);*/

        //viewHolder.taskInfo3.addView(iv);

    }

    @Override
    public int getItemCount() {
        return lstTask.size();
    }

    private Task bindTaskView(final TasksAdapter2.ViewHolder holder, final int position) {

        final Task task = (Task) lstTask.get(position);

        holder.menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.e("111111111111", "222222222222");
                if (onMenuClickListener != null) {
                    onMenuClickListener.onResultRecive(task);
                }
            }

        });
        return null;
    }

    static void setCornerRadius(GradientDrawable drawable, float topLeft,
                                float topRight, float bottomRight, float bottomLeft) {
        drawable.setCornerRadii(new float[] { topLeft, topLeft, topRight, topRight,
                bottomRight, bottomRight, bottomLeft, bottomLeft });
    }

    static void setCornerRadius(GradientDrawable drawable, float radius) {
        drawable.setCornerRadius(radius);
    }
}
