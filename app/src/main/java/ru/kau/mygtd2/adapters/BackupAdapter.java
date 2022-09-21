package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.Backup;


public class BackupAdapter extends RecyclerView.Adapter<BackupAdapter.ViewHolder>{

    private Context c;
    private List<Backup> lstBackups;
    public BackupAdapter(Context context, List<Backup> lstBackup) {
        this.c = context;
        this.lstBackups = lstBackup;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.backupitem_card, parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(lstBackups.get(position).getGuid());
        //holder.syncbegin.setText(Utils.dateToString(new Date(lstBackups.get(position).getDateBegin())));
        //holder.syncend.setText(Utils.dateToString(new Date(lstBackups.get(position).getDateEnd())));
    }

    @Override
    public int getItemCount() {
        return lstBackups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        TextView syncbegin;
        TextView syncend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.commontitle);
            //syncbegin = (TextView) itemView.findViewById(R.id.backupbegin);
            //syncend = (TextView) itemView.findViewById(R.id.backupend);
        }
    }



}
