package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.Statistic;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder>{

    private Context c;
    private List<Statistic> lstStatistic;

    public StatisticAdapter(Context c, List<Statistic> lstStatistic) {
        this.c = c;
        this.lstStatistic = lstStatistic;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.statistic_cardview,parent,false);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(4, R.color.black);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);
        v.setBackground(drawable);

        return new StatisticAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title1.setText(lstStatistic.get(i).getTitle1() + "  -  "
                + String.valueOf(lstStatistic.get(i).getCount1()) + "  -  "
                + String.valueOf(lstStatistic.get(i).getCount2()) + "  -  "
                + String.valueOf(lstStatistic.get(i).getCount3()));
    }

    @Override
    public int getItemCount() {
        return lstStatistic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title1;
        TextView title2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.title);
        }
    }

}
