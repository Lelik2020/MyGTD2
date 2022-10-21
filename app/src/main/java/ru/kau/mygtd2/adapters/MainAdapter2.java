package ru.kau.mygtd2.adapters;

import static ru.kau.mygtd2.utils.Const.DEFAULT_COLLAPSE_ICON;
import static ru.kau.mygtd2.utils.Const.DEFAULT_EXPANDED_ICON;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.interfaces.ClickListener;
import ru.kau.mygtd2.objects.Category;
import ru.kau.mygtd2.utils.Const;

public class MainAdapter2 extends RecyclerView.Adapter<MainAdapter2.ViewHolder>{

    Context c;
    List<Category> lstCategories;


    private ClickListener clicklistener = null;

    public MainAdapter2(Context c, List<Category> lstCategories){
        this.c = c;
        this.lstCategories = lstCategories;
    }

    /*
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {




        return convertView;

    }
    */


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(c).inflate(R.layout.main_cardview2,viewGroup,false);

        /*GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(4, R.color.black);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);
        v.setBackground(drawable);*/

        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(lstCategories.get(i).getTitle());

        int count;

        //setAllUnvisible(viewHolder);
        switch (i){
            case 0:
                /*
                InfoStatus infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);
                long count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());

                //setAllUnvisible(viewHolder);
                viewHolder.rtv2.setCorner(16, 0, 0, 16);

                viewHolder.rtv2.setBgColor(Color.parseColor(infoStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(infoStatus.getColor()));
                //viewHolder.rtv1.setBgColor(Color.parseColor("#2196F3"));

                viewHolder.rtv2.setText(" " + Long.toString(count) + " ");

                viewHolder.rtv2.setVisibility(View.VISIBLE);

                //viewHolder.notifyAll();//notifyItemChanged();



                infoStatus = MyApplication.getDatabase().infoStatusDao().getById(2);
                count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());



                viewHolder.rtv3.setCorner(0, 0, 0, 0);

                viewHolder.rtv3.setBgColor(Color.parseColor(infoStatus.getColor()));
                viewHolder.iv2.setColorFilter(Color.parseColor(infoStatus.getColor()));
                viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv3.setVisibility(View.VISIBLE);

                infoStatus = MyApplication.getDatabase().infoStatusDao().getById(3);
                count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());


                viewHolder.rtv4.setCorner(0, 16, 16, 0);

                viewHolder.rtv4.setBgColor(Color.parseColor(infoStatus.getColor()));
                viewHolder.iv3.setColorFilter(Color.parseColor(infoStatus.getColor()));
                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);


                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                viewHolder.txtv1.setText(R.string.newinfo);
                viewHolder.txtv2.setText(R.string.workinfo);
                viewHolder.txtv3.setText(R.string.archiveinfo);
                */

                viewHolder.expandedIcon.setVisibility(View.INVISIBLE);
                return;
            case 1:


                count = MyApplication.getDatabase().targetDao().countOfTargets();

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.iv1.setColorFilter(Color.parseColor(Const.DEFAULT_RTV_COLOR));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);

                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                viewHolder.txtv1.setText(R.string.targets);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);

                //infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);
                //count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());

                /*
                TaskStatus taskStatus = MyApplication.getDatabase().taskStatusDao().getById(1);
                count = MyApplication.getDatabase().taskDao().getCountByStatus(taskStatus.getId());
                //setAllUnvisible(viewHolder);

                viewHolder.rtv1.setCorner(16, 0, 0, 16);

                viewHolder.rtv1.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv1.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv1.setVisibility(View.VISIBLE);


                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(2);
                count = MyApplication.getDatabase().taskDao().getCountByStatus(taskStatus.getId());
                //setAllUnvisible(viewHolder);

                viewHolder.rtv2.setCorner(0, 0, 0, 0);

                viewHolder.rtv2.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv2.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv2.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv2.setVisibility(View.VISIBLE);

                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(3);
                count = MyApplication.getDatabase().taskDao().getCountByStatus(taskStatus.getId());
                //setAllUnvisible(viewHolder);

                viewHolder.rtv3.setCorner(0, 0, 0, 0);

                viewHolder.rtv3.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv3.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv3.setVisibility(View.VISIBLE);

                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(5);
                count = MyApplication.getDatabase().taskDao().getCountByStatus(taskStatus.getId());
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(0, 16, 16, 0);

                viewHolder.rtv4.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv4.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.txtv1.setText(R.string.newtask);
                viewHolder.txtv2.setText(R.string.worktask);
                viewHolder.txtv3.setText(R.string.pausetask);
                viewHolder.txtv4.setText(R.string.completetask);
                */

                //viewHolder.expandedIcon.setVisibility(View.INVISIBLE);
                return;
            case 2:

                count = MyApplication.getDatabase().tagDao().countOfTags();

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.iv1.setColorFilter(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.txtv1.setText(R.string.tags);

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);


                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                //taskStatus = MyApplication.getDatabase().taskStatusDao().getById(1);
                /*
                count = MyApplication.getDatabase().taskDao().getCountByDate(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()));


                //infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);
                //count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());



                viewHolder.rtv2.setCorner(16, 0, 0, 16);

                viewHolder.rtv2.setBgColor(Color.parseColor("#33FF99"));
                viewHolder.iv1.setColorFilter(Color.parseColor("#33FF99"));

                viewHolder.rtv2.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv2.setVisibility(View.VISIBLE);

                count = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()));

                viewHolder.rtv3.setCorner(0, 0, 0, 0);

                viewHolder.rtv3.setBgColor(Color.parseColor("#FF0000"));
                viewHolder.iv2.setColorFilter(Color.parseColor("#FF0000"));

                viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv3.setVisibility(View.VISIBLE);

                count = MyApplication.getDatabase().taskDao().getCountAllActiveTasks();

                viewHolder.rtv4.setCorner(0, 16, 16, 0);

                viewHolder.rtv4.setBgColor(Color.parseColor("#aa03A9F4"));
                viewHolder.iv3.setColorFilter(Color.parseColor("#aa03A9F4"));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.txtv1.setText(R.string.todaytask);
                viewHolder.txtv2.setText(R.string.expiredtask);
                viewHolder.txtv3.setText(R.string.allactivetask);

                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);
                */

                //viewHolder.expandedIcon.setVisibility(View.INVISIBLE);
                return;

            case 3:

                count = MyApplication.getDatabase().contextDao().countOfcontexts();

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.iv4.setColorFilter(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.txtv1.setText(R.string.contexts);

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);


                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                //viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

                return;

            case 4:

                viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

                return;

            case 5:

                viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

                return;

            case 6:

                count = MyApplication.getDatabase().taskTypesDao().countOftypetask();

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.iv4.setColorFilter(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.txtv1.setText(R.string.typetask);

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);


                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                //viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

                //viewHolder.expandedIcon.setVisibility(View.VISIBLE);



                return;
        }

        //viewHolder.notifyAll();
        //this.notifyItemChanged(0);
        //this.

    }

    @Override
    public int getItemCount() {
        return lstCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        private LinearLayoutCompat main;
        LinearLayoutCompat linLayout;
        RoundTextView rtv1;
        RoundTextView rtv2;
        RoundTextView rtv3;
        RoundTextView rtv4;

        ImageView iv1;
        ImageView iv2;
        ImageView iv3;
        ImageView iv4;

        TextView txtv1;
        TextView txtv2;
        TextView txtv3;
        TextView txtv4;

        View layout2;
        //TextView textView;

        ImageView expandedIcon;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title2);
            main = (LinearLayoutCompat) itemView.findViewById(R.id.main2);
            linLayout = (LinearLayoutCompat) itemView.findViewById(R.id.lnrLayout2);

            layout2 = itemView.findViewById(R.id.legend);
            layout2.setVisibility(View.GONE);
            //rtv1 = new RoundTextView(c);
            //rtv1 = new RoundTextView(c);
            rtv1 = (RoundTextView) itemView.findViewById(R.id.rtv1);
            rtv2 = (RoundTextView) itemView.findViewById(R.id.rtv2);
            rtv3 = (RoundTextView) itemView.findViewById(R.id.rtv3);
            rtv4 = (RoundTextView) itemView.findViewById(R.id.rtv4);
            //textView = new TextView(c);

            iv1 = (ImageView) itemView.findViewById(R.id.square1);
            txtv1 = (TextView) itemView.findViewById(R.id.txt1);
            iv2 = (ImageView) itemView.findViewById(R.id.square2);
            txtv2 = (TextView) itemView.findViewById(R.id.txt2);
            iv3 = (ImageView) itemView.findViewById(R.id.square3);
            txtv3 = (TextView) itemView.findViewById(R.id.txt3);
            iv4 = (ImageView) itemView.findViewById(R.id.square4);
            txtv4 = (TextView) itemView.findViewById(R.id.txt4);

            rtv1.setVisibility(View.INVISIBLE);
            rtv2.setVisibility(View.INVISIBLE);
            rtv3.setVisibility(View.INVISIBLE);
            rtv4.setVisibility(View.INVISIBLE);

            expandedIcon = (ImageView) itemView.findViewById(R.id.expandedIcon);
            expandedIcon.setImageResource(DEFAULT_COLLAPSE_ICON);

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
                        expandedIcon.setImageResource(DEFAULT_EXPANDED_ICON);
                        //layout2.setVisibility(View.GONE);
                        return;
                        //layout.refreshDrawableState();
                        //layout2.refreshDrawableState();
                        //layout2.sethe
                    } else {
                        //TransitionManager.beginDelayedTransition(layout2);
                        expandedIcon.setImageResource(DEFAULT_COLLAPSE_ICON);
                        //layout2.setVisibility(View.VISIBLE);
                        return;
                        //layout.refreshDrawableState();
                        //layout2.refreshDrawableState();
                    }
                    //divider.setVisibility(description.getVisibility());
                }
            });

            main.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            //Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                                            if(clicklistener !=null){
                                                clicklistener.itemClicked(v,getAdapterPosition(), 2);
                                            }
                                        }
                                    }
            );
        }


    }

    public void setClickListener(ClickListener clickListener){
        this.clicklistener = clickListener;
    }

    public void setAllUnvisible(@NonNull ViewHolder viewHolder){
        viewHolder.rtv1.setVisibility(View.INVISIBLE);
        viewHolder.rtv2.setVisibility(View.INVISIBLE);
        viewHolder.rtv3.setVisibility(View.INVISIBLE);
        viewHolder.rtv4.setVisibility(View.INVISIBLE);
    }

}
