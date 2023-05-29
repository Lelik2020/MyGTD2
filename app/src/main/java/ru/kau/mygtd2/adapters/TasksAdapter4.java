package ru.kau.mygtd2.adapters;

import static ru.kau.mygtd2.utils.Const.TASK_LEVEL_OFFSET2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.objects.TaskTypes;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;

public class TasksAdapter4 extends RecyclerView.Adapter<TasksAdapter4.ViewHolder>{

    private Context c;
    private List<Task> lstTask;
    //String LOG_TAG = "MyGTD";
    int checkedPosition = -1;
    public static long checkedID = -1;

    CardView.LayoutParams lParamscv;
    //LinearLayoutCompat.LayoutParams lParamscv;
    LinearLayoutCompat.LayoutParams lParamscb;
    LinearLayoutCompat.LayoutParams lParamsiv;

    public TasksAdapter4(Context c, List<Task> lstTask) {
        //Log.d (LOG_TAG, "adapter OnCreate viewHolder");
        this.c = c;
        this.lstTask = lstTask;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //Log.d (LOG_TAG, "adapter OnCreate viewHolder");
        View v= LayoutInflater.from(c).inflate(R.layout.cv_parenttaskchoice,viewGroup,false);

        return new ViewHolder(v);
    }



    @Override
    public int getItemCount() {
        //Log.d (LOG_TAG, String.valueOf(lstTask.size()));
        return ((lstTask != null) ? lstTask.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView title2;
        CheckBox checkBoxTask;
        ImageView typeTask;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Log.d (LOG_TAG, "ViewHolder create");
            title = (TextView) itemView.findViewById(R.id.tasktitle);
            typeTask = (ImageView) itemView.findViewById(R.id.tasktype);
            title2 = (TextView) itemView.findViewById(R.id.tasktitle2);
            checkBoxTask = (CheckBox) itemView.findViewById(R.id.checkBoxTask);
            card = itemView.findViewById(R.id.alltaskinfo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // при нажатии на элемент проверяем отмечен ли чекбокс в данной позиции
            // если нет, то запоминаем позицию для отметки
            // если отмечен, то сбрасываем отметку (ставим -1)
            checkedPosition = checkBoxTask.isChecked() ? -1: getAdapterPosition();
            if (checkedPosition > 0) {
                checkedID = lstTask.get(checkedPosition).getId();
            }
            // обновляем список, чтобы убрать прошлую! отметку и показать новую
            notifyDataSetChanged();

        }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //Log.d (LOG_TAG, "bind viewHolder");

        lParamscv = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
        lParamscv.setMargins(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET2 + 5, 5, 5, 5);
        viewHolder.card.setLayoutParams(lParamscv);


        /*lParamscv = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, 50);
        lParamscv.setMargins(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET + 5, 5, 5, 5);
        card.setLayoutParams(lParamscv);*/

        lParamsiv = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
        //lParamscb = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH3, Const.DEFAULT_LAYOUT_HEIGHT3);
        lParamscb = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH4, Const.DEFAULT_ICON_HEIGHT4);
        viewHolder.title.setText(lstTask.get(i).getTypeoftask().name() + "-" + lstTask.get(i).getId());
        TaskTypes taskTypes = MyApplication.getDatabase().taskTypesDao().getById(lstTask.get(i).getTypeoftask().Value);
        viewHolder.typeTask.setImageResource(Utils.getImageResourceTaskType(lstTask.get(i).getTypeoftask()));
        //viewHolder.typeTask.setLayoutParams(lParamsiv);

        //Drawable d = viewHolder.checkBoxTask.getCompoundDrawables()[0];
        Drawable d = ContextCompat.getDrawable(c, R.drawable.toggle);
        d.setBounds(0, 0, Const.DEFAULT_CB_WIDTH, Const.DEFAULT_CB_HEIGHT2);
        viewHolder.checkBoxTask.setCompoundDrawables(d, null, null, null);
        //viewHolder.checkBoxTask.setLayoutParams(lParamscb);

        //viewHolder.checkBoxTask.setLayoutParams(lParamsiv);
        viewHolder.title.setTextColor(Color.parseColor(taskTypes.getColor()));
        viewHolder.title2.setText(lstTask.get(i).getTitle());
        TxtUtils.underlineTextView(viewHolder.title);
        //ViewGroup.LayoutParams lp = new LinearLayoutCompat.LayoutParams(25, 25);
        //lp.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        //viewHolder.title.setGravity(Gravity.CENTER_VERTICAL);
        //viewHolder.title2.setGravity(Gravity.CENTER_VERTICAL);

        viewHolder.checkBoxTask.setChecked(i == checkedPosition);

        /*viewHolder.checkBoxTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean checked = viewHolder.checkBoxTask.isChecked();
                //allUnCheck();
                //setChecked(node,checked);
                //checkedID = lstTask.get(getAdapterPosition()).getId();
                // при нажатии на элемент проверяем отмечен ли чекбокс в данной позиции
                // если нет, то запоминаем позицию для отметки
                // если отмечен, то сбрасываем отметку (ставим -1)
                //checkedPosition = viewHolder.checkBoxTask.isChecked() ? -1: getAdapterPosition();
                // обновляем список, чтобы убрать прошлую! отметку и показать новую
                //notifyDataSetChanged();

            }
        });*/

        /*lParamscv = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        lParamscv.setMargins(Utils.getLevelTask(lstTask.get(i)) * TASK_LEVEL_OFFSET, 0, 0, 0);
        card.setLayoutParams(lParamscv);*/



        /*GradientDrawable gradientDrawable = new GradientDrawable();
        //gradientDrawable.setColor(Color.GREEN);
        //gradientDrawable.setShape();

        try {
            gradientDrawable.setColor(Color.parseColor(lstTask.get(i).getBgColor()));
        } catch (Exception e) {
            //viewHolder.card.setBackgroundResource(R.drawable.selector_2);
            gradientDrawable.setColor(Color.parseColor(Const.DEFAULT_TASKBG_COLOR));
        }
        gradientDrawable.setStroke(7, R.color.colorPrimaryDark);
        setCornerRadius(gradientDrawable, 25f);
        viewHolder.card.setBackground(gradientDrawable);*/

    }

    protected void allUnCheck(){
        for (int i = 0; i < lstTask.size(); i++) {
            //Node node = mAllNodes.get(i);
            //node.setChecked(false);

            //lstTask.get(i).setChecked(false);
            //notifyDataSetChanged();
        }

        notifyDataSetChanged();

    }

    public void setCheckedPosition (int position){
        checkedPosition = position;
        notifyDataSetChanged();
    }

    public int getChecedPosition (){
        return checkedPosition;
    }
}

