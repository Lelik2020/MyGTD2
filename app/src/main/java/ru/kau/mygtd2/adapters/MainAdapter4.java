package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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
import ru.kau.mygtd2.common.interfaces.ClickListener;
import ru.kau.mygtd2.objects.Category;

import static ru.kau.mygtd2.utils.Const.DEFAULT_COLLAPSE_ICON;
import static ru.kau.mygtd2.utils.Const.DEFAULT_EXPANDED_ICON;

public class MainAdapter4 extends RecyclerView.Adapter<MainAdapter4.ViewHolder>{

    Context c;
    List<Category> lstCategories;


    private ClickListener clicklistener = null;

    public MainAdapter4(Context c, List<Category> lstCategories){
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
    public MainAdapter4.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(c).inflate(R.layout.main_cardview2,viewGroup,false);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(4, R.color.black);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);
        v.setBackground(drawable);

        return new MainAdapter4.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MainAdapter4.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(lstCategories.get(i).getTitle());
        setAllUnvisible(viewHolder);
        int count;

        //setAllUnvisible(viewHolder);
        switch (i){
            case 0:


                viewHolder.expandedIcon.setVisibility(View.INVISIBLE);
                return;
            case 1:







                return;
            case 2:




                return;

            case 3:



                //viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

                return;

            case 4:

                viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

                return;

            case 5:

                viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

                return;

            case 6:

                viewHolder.expandedIcon.setVisibility(View.INVISIBLE);

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
                                                clicklistener.itemClicked(v,getAdapterPosition(), 4);
                                            }
                                        }
                                    }
            );
        }


    }

    public void setClickListener(ClickListener clickListener){
        this.clicklistener = clickListener;
    }

    public void setAllUnvisible(@NonNull MainAdapter4.ViewHolder viewHolder){
        viewHolder.rtv1.setVisibility(View.INVISIBLE);
        viewHolder.rtv2.setVisibility(View.INVISIBLE);
        viewHolder.rtv3.setVisibility(View.INVISIBLE);
        viewHolder.rtv4.setVisibility(View.INVISIBLE);
        viewHolder.expandedIcon.setVisibility(View.INVISIBLE);
    }

}
