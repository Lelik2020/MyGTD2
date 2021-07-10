package ru.kau.mygtd2.adapters;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.enums.Status;
import ru.kau.mygtd2.fragments.AddTaskFragment;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Priority;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskStatus;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.TASK_LEVEL_OFFSET;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>{

    private Context c;
    private List<Task> lstTask;
    private List<Tag> lstTag;
    private List<Contekst> contekstList;
    private RelativeLayout layout;
    private CardView card;
    private String projectTitle = null;
    private String targetTitle = null;
    private LinearLayoutCompat ltasktarget;
    private LinearLayoutCompat ltasktags;
    private LinearLayoutCompat ltaskproject;
    private LinearLayoutCompat ltaskcontexts;
    private LinearLayoutCompat llcoountsubtask;

    //private LinearLayout lcommon;

    LinearLayoutCompat.LayoutParams lParamsiv;
    LinearLayoutCompat.LayoutParams lParamsrtv;
    LinearLayoutCompat.LayoutParams lParamscv;



    private ResultResponse<Task> onMenuClickListener;


    public TasksAdapter(Context c, List<Task> lstTask) {
        this.c = c;
        this.lstTask = lstTask;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        TextView datecreate;
        //View divider;
        ImageView expandedIcon;
        View layout2;
        ImageView taskPriority;
        TextView taskTitleShort;
        TextView tv_datedone;
        //ImageView statusTask;
        SmoothCheckBox statusTask_n;
        ImageView typeTask;
        ImageView editTask;
        ImageView starIcon;
        ImageView menu;
        ImageView multitask;
        RoundTextView rtvstatus;



        @SuppressLint("ResourceAsColor")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (RelativeLayout) itemView.findViewById(R.id.taskinfo);
            ltasktags = (LinearLayoutCompat) itemView.findViewById(R.id.ltasktags);
            //lcommon = (LinearLayout) itemView.findViewById(R.id.commonLayout);
            ltasktarget = (LinearLayoutCompat) itemView.findViewById(R.id.ltasktarget);
            ltaskproject = (LinearLayoutCompat) itemView.findViewById(R.id.ltaskproject);
            ltaskcontexts = (LinearLayoutCompat) itemView.findViewById(R.id.ltaskcontexts);
            llcoountsubtask = (LinearLayoutCompat) itemView.findViewById(R.id.llcoountsubtask);
            title = (TextView) itemView.findViewById(R.id.tasktitle);
            tv_datedone = (TextView) itemView.findViewById(R.id.tv_datedone);
            description = (TextView) itemView.findViewById(R.id.taskinfotitle);
            datecreate = (TextView) itemView.findViewById(R.id.datecreatevalue);
            taskPriority = (ImageView) itemView.findViewById(R.id.taskPriority);
            taskTitleShort = (TextView) itemView.findViewById(R.id.taskTitleShort);
            //statusTask = (ImageView) itemView.findViewById(R.id.statusTask);
            statusTask_n = itemView.findViewById(R.id.statusTask_n);
            typeTask = (ImageView) itemView.findViewById(R.id.tasktype);
            editTask = (ImageView) itemView.findViewById(R.id.editTask);
            starIcon = (ImageView) itemView.findViewById(R.id.starIcon);

            expandedIcon = (ImageView) itemView.findViewById(R.id.expandedIcon);
            multitask = (ImageView) itemView.findViewById(R.id.multitaskIcon);
            card = itemView.findViewById(R.id.alltaskinfo);

            rtvstatus = (RoundTextView) itemView.findViewById(R.id.status);

            lParamsiv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
            lParamsrtv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_RTV_WIDTH, Const.DEFAULT_RTV_HEIGHT);

            rtvstatus.setLayoutParams(lParamsrtv);



            layout2 = itemView.findViewById(R.id.taskdetailinfo);
            layout2.setVisibility(View.GONE);
            //divider = itemView.findViewById(R.id.divider);
            //divider.setVisibility(description.getVisibility());
            card.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            //layout = (LinearLayout) itemView.findViewById(R.id.taskdetail);
            //layout2 = (LinearLayout) itemView.findViewById(R.id.taskinfo);
            menu = (ImageView) itemView.findViewById(R.id.itemTaskMenu);

            expandedIcon.setLayoutParams(lParamsiv);

            if (layout2.getVisibility() != View.GONE) {
                //TransitionManager.beginDelayedTransition(layout2);
                expandedIcon.setImageResource(R.drawable.press_down);
                //layout2.setVisibility(View.VISIBLE);
                //return;
                //layout.refreshDrawableState();
                //layout2.refreshDrawableState();
                //layout2.sethe
            } else {
                //TransitionManager.beginDelayedTransition(layout2);
                expandedIcon.setImageResource(R.drawable.not_press);
                //layout2.setVisibility(View.GONE);
                //return;
                //layout.refreshDrawableState();
                //layout2.refreshDrawableState();
            }



            expandedIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (layout2.getVisibility() != View.GONE) {
                        layout2.setVisibility(View.GONE);
                    } else {
                        layout2.setVisibility(View.VISIBLE);
                    }

                    if (layout2.getVisibility() != View.GONE) {
                        //TransitionManager.beginDelayedTransition(layout2);
                        expandedIcon.setImageResource(R.drawable.press_down);
                        //layout2.setVisibility(View.GONE);
                        return;
                        //layout.refreshDrawableState();
                        //layout2.refreshDrawableState();
                        //layout2.sethe
                    } else {
                        //TransitionManager.beginDelayedTransition(layout2);
                        expandedIcon.setImageResource(R.drawable.not_press);
                        //layout2.setVisibility(View.VISIBLE);
                        return;
                        //layout.refreshDrawableState();
                        //layout2.refreshDrawableState();
                    }
                    //divider.setVisibility(description.getVisibility());
                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(c).inflate(R.layout.task_cardview_nn,viewGroup,false);

        return new ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //viewHolder.title.setText(lstTask.get(i).getTitle());

        lParamsiv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
        lParamsrtv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_RTV_WIDTH, Const.DEFAULT_RTV_HEIGHT);
        lParamscv = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        lParamscv.setMargins(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET, 0, 0, 0);
        card.setLayoutParams(lParamscv);
        //card.setContentPadding(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET, 0, 0, 0);
        //card.setPadding(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET, 0, 0, 0);

        viewHolder.title.setText(lstTask.get(i).getTypeOfTask().name() + "-" + lstTask.get(i).getId());
        TaskTypes taskTypes = MyApplication.getDatabase().taskTypesDao().getById(lstTask.get(i).getTypeOfTask().Value);
        viewHolder.title.setTextColor(Color.parseColor(taskTypes.getColor()));
        viewHolder.tv_datedone.setText((lstTask.get(i).getDateEndStr() == null || "".equals(lstTask.get(i).getDateEndStr())) ? "" : lstTask.get(i).getDateEndStr());
        viewHolder.tv_datedone.setTextColor(Color.parseColor(Utils.getColorByEndDate(lstTask.get(i).getDateEnd())));

        //TxtUtils.underlineTextView(((Activity)c).findViewById(R.id.tasktitle)).setOnClickListener(new View.OnClickListener() {
        TxtUtils.underlineTextView(viewHolder.title).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                Dialogs.showTagsDialog(a, new File(fileMeta.getPath()), false, new Runnable() {

                    @Override
                    public void run() {
                        tagsRunnable.run();
                        EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
                */
            }
        });

        //Log.e("УРОВЕНЬ = ", String.valueOf(Utils.getLevelTask(lstTask.get(i))));
        viewHolder.description.setText(lstTask.get(i).getDescription());
        viewHolder.datecreate.setText(Utils.dateToString(lstTask.get(i).getDateCreate()));

        //Log.e("СТАТУС2222 ", "7777  " + String.valueOf(lstTask.get(i).getStatus().Value));

        TaskStatus taskStatus = MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value);
        //TaskStatus taskStatus = MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus());


        viewHolder.rtvstatus.setBgColor(Color.parseColor(taskStatus.getColor()));
        viewHolder.rtvstatus.setText(taskStatus.getTitle());
        viewHolder.rtvstatus.setVisibility(View.VISIBLE);
        viewHolder.rtvstatus.setLayoutParams(lParamsrtv);
        viewHolder.typeTask.setImageResource(Utils.getImageResourceTaskType(lstTask.get(i).getTypeOfTask()));
        viewHolder.typeTask.setLayoutParams(lParamsiv);

        bindTaskView(viewHolder, i);
        long cc = MyApplication.getDatabase().taskDao().getCountSubTasksByParent(lstTask.get(i).getId());
        if (cc > 0) {
            viewHolder.multitask.setImageResource(R.drawable.multitask);

            viewHolder.multitask.setLayoutParams(lParamsiv);

            llcoountsubtask.removeAllViews();
            LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);

            RoundTextView rtv1 = new RoundTextView(c);
            //rtv1.setText(lstTask.get(0).getTitle());

            rtv1.setCorner(10);
            //rtv1.setLeft(50);
            rtv1.setPadding(10, 0, 10, 0);
            //rtv1.setCorner(5, 5, 5, 5);
            rtv1.setBgColor(Color.parseColor("#33FF99"));


            rtv1.setTextColor(R.color.black);

            //rtv1.setBgColor(Color.parseColor(lstTag.get(i).getColor()));
            //rtv1.setCorner(2, 2, 2, 2);
            //rtv1.setCorner(5);
            rtv1.setText(Long.toString(cc));
            rtv1.setLayoutParams(lParamsrtv);

            llcoountsubtask.addView(rtv1, lParamsrtv);

            viewHolder.multitask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });

        } else {
            viewHolder.multitask.setImageResource(R.drawable.onetask);
            viewHolder.multitask.setLayoutParams(lParamsiv);
        }



        contekstList = MyApplication.getDatabase().taskContextJoinDao().getCotextsForTask(lstTask.get(i).getId());

        if (contekstList != null) {
            if (contekstList.size() > 0){
                ltaskcontexts.removeAllViews();
                //LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
                //ltaskcontexts.setLayoutParams(lParams);

                //LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);

                for (int j = 0; j < contekstList.size(); j++) {

                    ImageView iv = new ImageView(c);
                    iv.setImageResource(R.drawable.context6);
                    iv.setColorFilter(Color.parseColor(contekstList.get(j).getColor()));

                    //lParams = new LinearLayout.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
                    iv.setLayoutParams(lParamsiv);

                    ltaskcontexts.addView(iv);

                    RoundTextView rtv1 = new RoundTextView(c);
                    //rtv1.setText(lstTask.get(0).getTitle());

                    rtv1.setCorner(20);
                    rtv1.setPadding(10, 0, 10, 0);
                    //rtv1.setCorner(5, 5, 5, 5);
                    rtv1.setBgColor(Color.parseColor(contekstList.get(j).getColor()));
                    rtv1.setTextColor(R.color.black);
                    rtv1.setLayoutParams(lParamsrtv);

                    //rtv1.setBgColor(Color.parseColor(lstTag.get(i).getColor()));
                    //rtv1.setCorner(2, 2, 2, 2);
                    //rtv1.setCorner(5);
                    rtv1.setText(contekstList.get(j).getTitle());
                    //lParams = new LinearLayout.LayoutParams(Const.DEFAULT_RTV_WIDTH, Const.DEFAULT_RTV_HEIGHT);
                    ltaskcontexts.addView(rtv1, lParamsrtv);
                }

            }
        }

        //ltaskcontexts

        lstTag = MyApplication.getDatabase().taskTagJoinDao().getTagsForTask(lstTask.get(i).getId());
        targetTitle = null;
        if (lstTask.get(i).getTarget_id() > 0) {
            targetTitle = MyApplication.getDatabase().targetDao().getById(lstTask.get(i).getTarget_id()).getTitle();
        }
        projectTitle = null;
        if (lstTask.get(i).getProject_id() > 0) {
            projectTitle = MyApplication.getDatabase().projectDao().getProjectById(lstTask.get(i).getProject_id()).getTitle();
        }

        if (lstTag != null) {
            //ltasktags = new LinearLayout(c);
            if (lstTag.size() > 0) {
                ltasktags.removeAllViews();
                //LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
                //ltasktags.setLayoutParams(lParams);
                //LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(35, 35);

                //ImageView iv = new ImageView(c);
                //iv.setImageResource(R.drawable.merchandising);
                //iv.setMinimumWidth(25);
                //iv.setMinimumHeight(25);
                //iv.getLayoutParams().width = 25;
                //iv.getLayoutParams().height = 25;
                //iv.setMaxWidth(25);
                //iv.setMaxHeight(25);
                //iv.setla



                //lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                for (int j = 0; j < lstTag.size(); j++) {

                    ImageView iv = new ImageView(c);
                    iv.setImageResource(R.drawable.merchandising);
                    iv.setColorFilter(Color.parseColor(lstTag.get(j).getColor()));

                    //lParams = new LinearLayout.LayoutParams(50, 50);
                    iv.setLayoutParams(lParamsiv);

                    ltasktags.addView(iv);

                    RoundTextView rtv1 = new RoundTextView(c);
                    //rtv1.setText(lstTask.get(0).getTitle());

                    rtv1.setCorner(20);
                    rtv1.setPadding(10, 0, 10, 0);
                    //rtv1.setCorner(5, 5, 5, 5);
                    rtv1.setBgColor(Color.parseColor(lstTag.get(j).getColor()));
                    rtv1.setTextColor(R.color.black);
                    rtv1.setLayoutParams(lParamsrtv);

                    //rtv1.setBgColor(Color.parseColor(lstTag.get(i).getColor()));
                    //rtv1.setCorner(2, 2, 2, 2);
                    //rtv1.setCorner(5);
                    rtv1.setText(lstTag.get(j).getTitle());
                    //lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ltasktags.addView(rtv1, lParamsrtv);
                }
            }
        }

        if (((targetTitle != null) && (!targetTitle.equals(""))) || ((projectTitle != null) && (!projectTitle.equals("")))){

            LinearLayoutCompat.LayoutParams lParams;
            /*lcommon.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT2);
            lcommon.setLayoutParams(lParams);*/

            ltasktarget.removeAllViews();
            lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
            ltasktarget.setLayoutParams(lParams);

            if ((projectTitle != null) && (!projectTitle.equals(""))) {
                ImageView iv = new ImageView(c);
                iv.setImageResource(R.drawable.folder);
                iv.setLayoutParams(lParamsiv);

                ltasktarget.addView(iv);

                TextView tv1 = new TextView(c);
                tv1.setPadding(10, 0, 0, 0);
                tv1.setTextColor(R.color.black);
                tv1.setHintTextColor(R.color.black);
                tv1.setText(projectTitle);
                tv1.setLayoutParams(lParamsrtv);
                ltasktarget.addView(tv1, lParamsrtv);
            }

            //LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(35, 35);
            if ((targetTitle != null) && (!targetTitle.equals(""))) {

                ImageView iv = new ImageView(c);
                iv.setImageResource(R.drawable.target4);

                //ViewGroup.LayoutParams lParamsiv2 = new LinearLayout.LayoutParams(50, 50);
                iv.setLayoutParams(lParamsiv);
                iv.setPadding(5, 0, 0, 0);

                ltasktarget.addView(iv);

                //lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tv1 = new TextView(c);
                tv1.setPadding(10, 0, 0, 0);
                tv1.setTextColor(R.color.black);
                tv1.setHintTextColor(R.color.black);
                tv1.setText(targetTitle);
                tv1.setLayoutParams(lParamsrtv);
                ltasktarget.addView(tv1, lParamsrtv);
            }
        }

        Priority priority = MyApplication.getDatabase().priorityDao().getById(lstTask.get(i).getPriority_id());
        viewHolder.taskPriority.setColorFilter(Color.parseColor((priority == null) ? Const.DEFAULT_TASKPRIORITY_COLOR : priority.getColor()));
        viewHolder.taskTitleShort.setText(lstTask.get(i).getTitle());

        //viewHolder.statusTask.setImageResource((lstTask.get(i).getStatus() != Status.COMPLETED) ? R.drawable.ok_lightgray: R.drawable.ok_lightgreen);
        //viewHolder.statusTask.setLayoutParams(lParamsiv);

        viewHolder.statusTask_n.setChecked((lstTask.get(i).getStatus() != Status.COMPLETED) ? false : true);
        //viewHolder.statusTask_n.setChecked((Status.from(lstTask.get(i).getStatus()) != Status.COMPLETED) ? false : true);
        viewHolder.statusTask_n.setLayoutParams(lParamsiv);
        viewHolder.statusTask_n.setOnClickListener(new View.OnClickListener() {
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
                viewHolder.statusTask_n.setChecked((lstTask.get(i).getStatus() != Status.COMPLETED) ? false : true);
                //viewHolder.statusTask_n.setChecked((Status.from(lstTask.get(i).getStatus()) != Status.COMPLETED) ? false : true);
                viewHolder.statusTask_n.setLayoutParams(lParamsiv);
                MyApplication.getDatabase().taskDao().update(lstTask.get(i));
                //notifyDataSetChanged();
                notifyItemChanged(i);
            }
        });

        /*
        viewHolder.statusTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lstTask.get(i).getStatus() != Status.COMPLETED) {
                    lstTask.get(i).setPreviousStatus(lstTask.get(i).getStatus());
                    lstTask.get(i).setStatus(Status.COMPLETED);
                    lstTask.get(i).setDateClose(new Date());
                    lstTask.get(i).setDateCloseStr(Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), lstTask.get(i).getDateClose()));
                } else {
                    lstTask.get(i).setStatus(lstTask.get(i).getPreviousStatus());
                    lstTask.get(i).setPreviousStatus(Status.NOTHING);
                    lstTask.get(i).setDateClose(null);
                    lstTask.get(i).setDateCloseStr(null);
                }
                viewHolder.statusTask.setImageResource((lstTask.get(i).getStatusId() != 5) ? R.drawable.ok_lightgray: R.drawable.ok_lightgreen);
                viewHolder.statusTask.setLayoutParams(lParamsiv);
                MyApplication.getDatabase().taskDao().update(lstTask.get(i));
                //notifyDataSetChanged();
                notifyItemChanged(i);
            }
        });
        */


        viewHolder.editTask.setLayoutParams(lParamsiv);
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


        /*
        if (lstTask.get(i).getIsFavourite() > 0) {

            viewHolder.starIcon.setImageResource(R.drawable.star_1);
        } else {

            viewHolder.starIcon.setImageResource(R.drawable.star_2);
        }

        */
        viewHolder.starIcon.setImageResource((lstTask.get(i).getIsFavourite() > 0) ? R.drawable.star_1: R.drawable.star_2);
        viewHolder.starIcon.setColorFilter(R.color.footerbackground);
        viewHolder.starIcon.setLayoutParams(lParamsiv);


        viewHolder.starIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lstTask.get(i).getIsFavourite() <= 0) {
                    lstTask.get(i).setIsFavourite(1);
                    viewHolder.starIcon.setImageResource(R.drawable.star_1);
                } else {
                    lstTask.get(i).setIsFavourite(0);
                    viewHolder.starIcon.setImageResource(R.drawable.star_2);
                }
                viewHolder.starIcon.setColorFilter(R.color.colorPrimary);
                viewHolder.starIcon.setLayoutParams(lParamsiv);
                MyApplication.getDatabase().taskDao().update(lstTask.get(i));
            }
        });
        //viewHolder.
        //String status = MyApplication.getDatabase().taskStatusDao().getById(lstTask.get(i).getStatus().Value).getTitle();
        //Log.e("СТАТУС ", String.valueOf(lstTask.get(i).getStatus().Value));

    }
    @Override
    public int getItemCount() {
        return lstTask.size();
    }

    public void setOnMenuClickListener(ResultResponse<Task> onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    private Task bindTaskView(final TasksAdapter.ViewHolder holder, final int position) {

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
}
