package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.interfaces.ClickListener;
import ru.kau.mygtd2.objects.Meeting;

public class ListMeetingsAdapter extends RecyclerView.Adapter<ListMeetingsAdapter.ViewHolder>{

    Context c;
    List<Meeting> lstMeetings;
    //long currentTime;
    Date currentdate;

    private ClickListener clicklistener = null;

    public ListMeetingsAdapter(Context c, List<Meeting> lstMeetings){
        this.c = c;
        this.lstMeetings = lstMeetings;
        //this.currentTime = System.currentTimeMillis() / 1000;
        this.currentdate = new Date();
    }

    @Override
    public ListMeetingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(c).inflate(R.layout.listmeeting_cardview,viewGroup,false);

        return new ListMeetingsAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(lstMeetings.get(position).getTitle());
        holder.datemeeting.setText((new SimpleDateFormat("dd.MM.yyyy")).format(lstMeetings.get(position).getDateBegin()));
        holder.timebegin.setText((new SimpleDateFormat("HH:mm")).format(lstMeetings.get(position).getDateBegin()));
        holder.timeend.setText((new SimpleDateFormat("HH:mm")).format(lstMeetings.get(position).getDateEnd()));
        if (currentdate.getTime() > lstMeetings.get(position).getDateBegin().getTime() && currentdate.getTime() < lstMeetings.get(position).getDateEnd().getTime()){
            holder.cw.setCardBackgroundColor(R.color.colorAccent);
        }
        if (currentdate.getTime() < lstMeetings.get(position).getDateBegin().getTime()){
            holder.cw.setCardBackgroundColor(R.color.past_meeting);
        }
        if (currentdate.getTime() > lstMeetings.get(position).getDateEnd().getTime()){
            holder.cw.setCardBackgroundColor(R.color.colorFAB);
        }
    }

    @Override
    public int getItemCount() {
        return lstMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView datemeeting;
        TextView timebegin;
        TextView timeend;
        CardView cw;
        ImageView imageView;

        private RelativeLayout main;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titlemeeting);
            datemeeting = (TextView) itemView.findViewById(R.id.date_meeting);
            timebegin = (TextView) itemView.findViewById(R.id.timebeginmeeting);
            timeend = (TextView) itemView.findViewById(R.id.timeendmeeting);
            cw = (CardView) itemView.findViewById(R.id.cwlstmeeting);
            main = (RelativeLayout) itemView.findViewById(R.id.listmeeting);
            main.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            //Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                                            if(clicklistener !=null){
                                                clicklistener.itemClicked(v,getAdapterPosition(), 0);
                                            }
                                        }
                                    }
            );
            imageView = (ImageView) itemView.findViewById(R.id.itemMenu);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


    }

    public void setClickListener(ClickListener clickListener){
        this.clicklistener = clickListener;
    }


}
