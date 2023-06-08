package ru.kau.mygtd2.adapters;

import static android.view.View.GONE;
import static ru.kau.mygtd2.utils.Const.DEFAULT_COLLAPSE_ICON;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.DEFAULT_EXPANDED_ICON;
import static ru.kau.mygtd2.utils.Const.DEFAULT_RADIUS2;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSCOMPLETED;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSINHOLD;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSINPROGRESS;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSNEW;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSPAUSE;
import static ru.kau.mygtd2.utils.Const.LSTSTATUSSOMEDAY;
import static ru.kau.mygtd2.utils.Const.lstALLPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstALLPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstHIPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstONLYFAVOURITE;
import static ru.kau.mygtd2.utils.Const.lstStatus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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

import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.interfaces.ClickListener;
import ru.kau.mygtd2.objects.Category;
import ru.kau.mygtd2.objects.InfoStatus;
import ru.kau.mygtd2.objects.TaskStatus;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.Utils;

//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private Context c;
    private List<Category> lstCategories;

    private Date currDate = new Date(Utils.getCurrentApplicationDateAndTime());


    private ClickListener clicklistener = null;

    public MainAdapter(Context c, List<Category> lstCategories){
        this.c = c;
        this.lstCategories = lstCategories;
    }

    /*
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {




        return convertView;

    }
    */


    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(c).inflate(R.layout.main_cardview,viewGroup,false);

        /*GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(5, R.color.black);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(7);
        v.setBackground(drawable);*/

        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(lstCategories.get(i).getTitle());
        //viewHolder.title.setWidth(75);

        if (lstCategories.get(i).getGrp() == 1 && (lstCategories.get(i).getId() == 14 || lstCategories.get(i).getId() == 15)){
            viewHolder.cardView.setVisibility(GONE);
            viewHolder.main.setVisibility(View.GONE);
            viewHolder.rlmain.setVisibility(View.GONE);
        }

        //setAllUnvisible(viewHolder);

        /*switch((int) lstCategories.get(i).getId()){
            case 14:
                TaskStatus taskStatus = MyApplication.getDatabase().taskStatusDao().getById(-1);
                long count = MyApplication.getDatabase().taskDao().getCountByStatus(taskStatus.getId());
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);




                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv1.setText(R.string.paused);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);
                break;
            case 15:
                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(-1);
                count = MyApplication.getDatabase().taskDao().getCountByStatus(taskStatus.getId());
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);




                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv1.setText(R.string.inhold);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);
                break;
        }*/

        switch (i){
            case 0:
                InfoStatus infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);
                long count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());

                //setAllUnvisible(viewHolder);
                viewHolder.rtv2.setCorner(DEFAULT_RADIUS2, 0, 0, DEFAULT_RADIUS2);

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

                return;
            case 1:
                //infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);
                //count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());

                viewHolder.iv5.setVisibility(View.VISIBLE);
                viewHolder.iv6.setVisibility(View.VISIBLE);
                viewHolder.txtv5.setVisibility(View.VISIBLE);
                viewHolder.txtv6.setVisibility(View.VISIBLE);

                TaskStatus taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSNEW.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSNEW);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv1.setCorner(16, 0, 0, 16);

                viewHolder.rtv1.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv1.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv1.setVisibility(View.VISIBLE);


                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSINPROGRESS.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSINPROGRESS);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv2.setCorner(0, 0, 0, 0);

                viewHolder.rtv2.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv2.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv2.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv2.setVisibility(View.VISIBLE);

                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSPAUSE.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSPAUSE);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv3.setCorner(0, 0, 0, 0);

                viewHolder.rtv3.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv3.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv3.setVisibility(View.VISIBLE);

                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSINHOLD.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSINHOLD);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(0, 0, 0, 0);

                viewHolder.rtv4.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv4.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSSOMEDAY.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSSOMEDAY);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv5.setCorner(0, 0, 0, 0);

                viewHolder.rtv5.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv5.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv5.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv5.setVisibility(View.VISIBLE);

                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSCOMPLETED.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSCOMPLETED);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv6.setCorner(0, 16, 16, 0);

                viewHolder.rtv6.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv6.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv6.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv6.setVisibility(View.VISIBLE);

                viewHolder.txtv1.setText(R.string.newtask);
                viewHolder.txtv2.setText(R.string.worktask);
                viewHolder.txtv3.setText(R.string.pausetask);
                viewHolder.txtv4.setText(R.string.posponedtask);
                viewHolder.txtv5.setText(R.string.sometime);

                viewHolder.txtv6.setText(R.string.completetask);

                return;
            case 2:

                //taskStatus = MyApplication.getDatabase().taskStatusDao().getById(1);
                /*List<Integer> lstStatus = new ArrayList<>();
                lstStatus.add(1);
                lstStatus.add(2);
                lstStatus.add(3);*/
                //count = MyApplication.getDatabase().taskDao().getCountOutstanding(Utils.getStartOfDay(new Date()).getTime(), lstStatus, lstALLPRIORITY, lstALLPROJECTSID);
                count = MyApplication.getDatabase().taskDao().getCountOutstanding(Utils.getStartOfDay(currDate).getTime(), lstStatus, lstALLPRIORITY, lstALLPROJECTSID);


                //infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);
                //count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());



                viewHolder.rtv2.setCorner(16, 0, 0, 16);

                viewHolder.rtv2.setBgColor(Color.parseColor("#FF0000"));
                viewHolder.iv1.setColorFilter(Color.parseColor("#FF0000"));

                viewHolder.rtv2.setText(" " + Long.toString(count) + " ");
                //viewHolder.rtv2.setText(" " + Long.toString(99) + " ");
                viewHolder.rtv2.setVisibility(View.VISIBLE);

                //count = MyApplication.getDatabase().taskDao().getCountByDate( Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstALLPROJECTSID);
                count = MyApplication.getDatabase().taskDao().getCountByDate( Utils.getEndOfDay(currDate).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(currDate)), lstStatus, lstALLPROJECTSID);


                viewHolder.rtv3.setCorner(0, 0, 0, 0);

                viewHolder.rtv3.setBgColor(Color.parseColor("#33FF99"));
                viewHolder.iv2.setColorFilter(Color.parseColor("#33FF99"));

                viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv3.setVisibility(View.VISIBLE);

                count = MyApplication.getDatabase().taskDao().getCountAllTasks(lstStatus, lstALLPROJECTSID);

                viewHolder.rtv4.setCorner(0, 16, 16, 0);

                viewHolder.rtv4.setBgColor(Color.parseColor(Const.DEFAULT_RTV_COLOR));
                viewHolder.iv3.setColorFilter(Color.parseColor(Const.DEFAULT_RTV_COLOR));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.txtv1.setText(R.string.expiredtask);
                viewHolder.txtv2.setText(R.string.todaytask);
                viewHolder.txtv3.setText(R.string.allactivetask);

                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                return;

            case 3:


                //count = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), lstStatus, lstHIPRIORITY, lstALLPROJECTSID);
                count = MyApplication.getDatabase().taskDao().getCountOutstanding(currDate.getTime(), lstStatus, lstHIPRIORITY, lstALLPROJECTSID);


                //setAllUnvisible(viewHolder);

                viewHolder.rtv2.setCorner(16, 0, 0, 16);

                viewHolder.rtv2.setBgColor(Color.parseColor("#FF0000"));
                viewHolder.iv1.setColorFilter(Color.parseColor("#FF0000"));

                viewHolder.rtv2.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv2.setVisibility(View.VISIBLE);



                //count = MyApplication.getDatabase().taskDao().getCountAllTasksOfHotToday(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstHIPRIORITY);
                count = MyApplication.getDatabase().taskDao().getCountAllTasksOfHotToday(Utils.getEndOfDay(currDate).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(currDate)), lstStatus, lstHIPRIORITY);

                //setAllUnvisible(viewHolder);

                viewHolder.rtv3.setCorner(0, 0, 0, 0);

                viewHolder.rtv3.setBgColor(Color.parseColor("#33FF99"));
                viewHolder.iv2.setColorFilter(Color.parseColor("#33FF99"));

                viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv3.setVisibility(View.VISIBLE);

                count = MyApplication.getDatabase().taskDao().getCountAllActiveTasksOfHot(lstStatus);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(0, 16, 16, 0);

                viewHolder.rtv4.setBgColor(Color.parseColor("#aa03A9F4"));
                viewHolder.iv3.setColorFilter(Color.parseColor("#aa03A9F4"));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);


                viewHolder.txtv1.setText(R.string.expiredtask);
                viewHolder.txtv2.setText(R.string.todaytask);
                viewHolder.txtv3.setText(R.string.allactivetask);

                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                return;

            case 4:

                //count = MyApplication.getDatabase().taskDao().getCountAllTasksOfFavouriteOutstanding(Utils.getStartOfDay(new Date()).getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), lstStatus, lstONLYFAVOURITE);
                //count = MyApplication.getDatabase().taskDao().getCountOutstandingFavourite(Utils.getStartOfDay(new Date()).getTime(), lstStatus, lstALLPRIORITY, lstALLPROJECTSID);
                count = MyApplication.getDatabase().taskDao().getCountOutstandingFavourite(Utils.getStartOfDay(currDate).getTime(), lstStatus, lstALLPRIORITY, lstALLPROJECTSID);

                //setAllUnvisible(viewHolder);

                viewHolder.rtv2.setCorner(16, 0, 0, 16);

                viewHolder.rtv2.setBgColor(Color.parseColor("#FF0000"));
                viewHolder.iv1.setColorFilter(Color.parseColor("#FF0000"));

                viewHolder.rtv2.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv2.setVisibility(View.VISIBLE);



                //count = MyApplication.getDatabase().taskDao().getCountAllTasksOfFavouriteToday(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, lstONLYFAVOURITE);
                count = MyApplication.getDatabase().taskDao().getCountAllTasksOfFavouriteToday(Utils.getEndOfDay(currDate).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(currDate)), lstStatus, lstONLYFAVOURITE);

                //setAllUnvisible(viewHolder);

                viewHolder.rtv3.setCorner(0, 0, 0, 0);

                viewHolder.rtv3.setBgColor(Color.parseColor("#33FF99"));
                viewHolder.iv2.setColorFilter(Color.parseColor("#33FF99"));

                viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv3.setVisibility(View.VISIBLE);

                count = MyApplication.getDatabase().taskDao().getCountAllActiveTasksOfFavourite(lstStatus, lstONLYFAVOURITE);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(0, 16, 16, 0);

                viewHolder.rtv4.setBgColor(Color.parseColor("#aa03A9F4"));
                viewHolder.iv3.setColorFilter(Color.parseColor("#aa03A9F4"));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);


                viewHolder.txtv1.setText(R.string.expiredtask);
                viewHolder.txtv2.setText(R.string.todaytask);
                viewHolder.txtv3.setText(R.string.allactivetask);

                viewHolder.iv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);

                //viewHolder.iv2.setVisibility(View.INVISIBLE);
                //viewHolder.iv3.setVisibility(View.INVISIBLE);
                //viewHolder.iv4.setVisibility(View.INVISIBLE);

                return;

            case 5:

                count = MyApplication.getDatabase().taskDao().getCountAllClosedTasks(LSTSTATUSCOMPLETED);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor("#aa03A9F4"));
                viewHolder.iv1.setColorFilter(Color.parseColor("#aa03A9F4"));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);count = MyApplication.getDatabase().taskDao().getCountAllClosedTasks(LSTSTATUSCOMPLETED);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(MyApplication.getDatabase().taskStatusDao().getById(6).getColor()));
                //viewHolder.iv3.setColorFilter(Color.parseColor("#aa03A9F4"));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);

                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv1.setText(R.string.completedtasks);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);

                return;

            case 6:

                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSSOMEDAY.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSSOMEDAY);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);




                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv1.setText(R.string.sometime);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);



                return;

            case 9:
                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSPAUSE.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSPAUSE);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);




                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv1.setText(R.string.paused);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);
                break;
            case 10:
                taskStatus = MyApplication.getDatabase().taskStatusDao().getById(LSTSTATUSINHOLD.get(0));
                count = MyApplication.getDatabase().taskDao().getCountByStatus(LSTSTATUSINHOLD);
                //setAllUnvisible(viewHolder);

                viewHolder.rtv4.setCorner(16, 16, 16, 16);

                viewHolder.rtv4.setBgColor(Color.parseColor(taskStatus.getColor()));
                viewHolder.iv1.setColorFilter(Color.parseColor(taskStatus.getColor()));

                viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
                viewHolder.rtv4.setVisibility(View.VISIBLE);




                viewHolder.txtv2.setVisibility(View.INVISIBLE);
                viewHolder.txtv3.setVisibility(View.INVISIBLE);
                viewHolder.txtv4.setVisibility(View.INVISIBLE);
                viewHolder.txtv1.setText(R.string.inhold);
                viewHolder.iv2.setVisibility(View.INVISIBLE);
                viewHolder.iv3.setVisibility(View.INVISIBLE);
                viewHolder.iv4.setVisibility(View.INVISIBLE);
                break;

            /*case 14:
                viewHolder.main.setVisibility(View.INVISIBLE);
                return;

            case 15:
                viewHolder.main.setVisibility(View.GONE);
                return;*/


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
        private CardView cardView;
        LinearLayoutCompat linLayout;
        RoundTextView rtv1;
        RoundTextView rtv2;
        RoundTextView rtv3;
        RoundTextView rtv4;
        RoundTextView rtv5;
        RoundTextView rtv6;

        ImageView iv1;
        ImageView iv2;
        ImageView iv3;
        ImageView iv4;
        ImageView iv5;
        ImageView iv6;

        TextView txtv1;
        TextView txtv2;
        TextView txtv3;
        TextView txtv4;
        TextView txtv5;
        TextView txtv6;

        RelativeLayout rlmain;

        View layout2;
        //TextView textView;

        ImageView expandedIcon;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            main = (LinearLayoutCompat) itemView.findViewById(R.id.main);
            rlmain = (RelativeLayout) itemView.findViewById(R.id.rlmain);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            linLayout = (LinearLayoutCompat) itemView.findViewById(R.id.lnrLayout);

            layout2 = itemView.findViewById(R.id.legend);
            layout2.setVisibility(GONE);
            //rtv1 = new RoundTextView(c);
            //rtv1 = new RoundTextView(c);
            rtv1 = (RoundTextView) itemView.findViewById(R.id.rtv1);
            rtv2 = (RoundTextView) itemView.findViewById(R.id.rtv2);
            rtv3 = (RoundTextView) itemView.findViewById(R.id.rtv3);
            rtv4 = (RoundTextView) itemView.findViewById(R.id.rtv4);
            rtv5 = (RoundTextView) itemView.findViewById(R.id.rtv5);
            rtv6 = (RoundTextView) itemView.findViewById(R.id.rtv6);
            //textView = new TextView(c);

            iv1 = (ImageView) itemView.findViewById(R.id.square1);
            txtv1 = (TextView) itemView.findViewById(R.id.txt1);
            iv2 = (ImageView) itemView.findViewById(R.id.square2);
            txtv2 = (TextView) itemView.findViewById(R.id.txt2);
            iv3 = (ImageView) itemView.findViewById(R.id.square3);
            txtv3 = (TextView) itemView.findViewById(R.id.txt3);
            iv4 = (ImageView) itemView.findViewById(R.id.square4);
            txtv4 = (TextView) itemView.findViewById(R.id.txt4);
            iv5 = (ImageView) itemView.findViewById(R.id.square5);
            txtv5 = (TextView) itemView.findViewById(R.id.txt5);
            iv6 = (ImageView) itemView.findViewById(R.id.square6);
            txtv6 = (TextView) itemView.findViewById(R.id.txt6);

            rtv1.setVisibility(View.INVISIBLE);
            rtv2.setVisibility(View.INVISIBLE);
            rtv3.setVisibility(View.INVISIBLE);
            rtv4.setVisibility(View.INVISIBLE);
            rtv5.setVisibility(View.INVISIBLE);
            rtv6.setVisibility(View.INVISIBLE);
            iv5.setVisibility(View.GONE);
            iv6.setVisibility(View.GONE);
            txtv5.setVisibility(View.GONE);
            txtv6.setVisibility(View.GONE);

            expandedIcon = (ImageView) itemView.findViewById(R.id.expandedIcon);
            expandedIcon.setImageResource(DEFAULT_COLLAPSE_ICON);
            //expandedIcon.setMinimumWidth(35);

            expandedIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (layout2.getVisibility() != GONE) {
                        layout2.setVisibility(GONE);
                    } else {
                        layout2.setVisibility(View.VISIBLE);
                    }

                    if (layout2.getVisibility() != GONE) {
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
                                                clicklistener.itemClicked(v,getAdapterPosition(), 1);
                                            }
                                        }
                                    }
            );

            /*GradientDrawable drawable = new GradientDrawable();
            drawable.setStroke(4, R.color.black);
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(20);
            cardView.setBackground(drawable);*/
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
