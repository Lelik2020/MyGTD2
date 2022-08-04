package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.lstALLFAVOURITE;
import static ru.kau.mygtd2.utils.Const.lstALLPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstALLPROJECTSID;
import static ru.kau.mygtd2.utils.Const.lstALLTARGETSID;
import static ru.kau.mygtd2.utils.Const.lstStatus;

public class ContextsAdapter extends RecyclerView.Adapter<ContextsAdapter.ViewHolder> {

    private Context c;
    private List<Contekst> lstContekst;

    public ContextsAdapter(Context c, List<Contekst> lstContekst) {
        this.c = c;
        this.lstContekst = lstContekst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.context_cardview, parent,false);

        return new ViewHolder(v);
    }

    public Contekst getItem(int pos) {
        if (lstContekst == null || lstContekst.isEmpty()) {
            return null;
        }
        if (lstContekst.size() - 1 >= pos) {
            return lstContekst.get(pos);
        } else {
            return lstContekst.get(0);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Contekst contekst = getItem(position);
        holder.title.setText(contekst.getTitle());
        holder.tagImage.setColorFilter(Color.parseColor(contekst.getColor()));

        long count = 0L;
        long count2 = 0L;
        long count3 = 0L;
        long count4 = 0L;

        count = MyApplication.getDatabase().taskDao().getCountAllTasksByContekst(contekst.getId());
        count2 = MyApplication.getDatabase().taskDao().getCountAllActiveTasksByContekst(contekst.getId(), lstStatus);
        //count3 = MyApplication.getDatabase().taskDao().getCountByDateByContekst(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), contekst.getId());
        //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingByContekst(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), contekst.getId());

        count3 = MyApplication.getDatabase().taskDao().getCountByDateWithContext(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())),
                lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstALLTARGETSID, new ArrayList<Integer>() {
                    {
                        add((int) contekst.getId());
                    }
                });
        //count4 = MyApplication.getDatabase().taskDao().getCountOutstandingByTarget(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), target.getId());

        count4 = MyApplication.getDatabase().taskDao().getCountOutstandingWithContext(new Date().getTime(),
                lstStatus, lstALLFAVOURITE,  lstALLPRIORITY, lstALLPROJECTSID, lstALLTARGETSID, new ArrayList<Integer>() {
                    {
                        add((int) contekst.getId());
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
    }

    @Override
    public int getItemCount() {
        return lstContekst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        RoundTextView roundTextView;
        RoundTextView roundTextView2;
        RoundTextView roundTextView3;
        RoundTextView roundTextView4;
        ImageView tagImage;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagImage = (ImageView) itemView.findViewById(R.id.context_icon);
            title = (TextView) itemView.findViewById(R.id.contexttitle);
            roundTextView = (RoundTextView) itemView.findViewById(R.id.contextcount1);
            roundTextView2 = (RoundTextView) itemView.findViewById(R.id.contextcount2);
            roundTextView3 = (RoundTextView) itemView.findViewById(R.id.contextcount3);
            roundTextView4 = (RoundTextView) itemView.findViewById(R.id.contextcount4);

        }
    }

}
