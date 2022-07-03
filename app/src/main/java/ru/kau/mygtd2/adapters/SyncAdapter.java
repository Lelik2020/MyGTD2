package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.Sync;
import ru.kau.mygtd2.utils.Utils;

public class SyncAdapter extends RecyclerView.Adapter<SyncAdapter.ViewHolder>{

    private Context c;
    private List<Sync> lstSync;
    public SyncAdapter(Context c, List<Sync> lstSync) {
        this.c = c;
        this.lstSync = lstSync;
    }


    @NonNull
    @Override
    public SyncAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.sync_cardview, parent,false);

        return new SyncAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(lstSync.get(position).getGuid());
        holder.syncbegin.setText(Utils.dateToString(new Date(lstSync.get(position).getDatebegin())));
        holder.syncend.setText(Utils.dateToString(new Date(lstSync.get(position).getDateend())));
    }

    @Override
    public int getItemCount() {
        return lstSync.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        TextView syncbegin;
        TextView syncend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.synctitle);
            syncbegin = (TextView) itemView.findViewById(R.id.syncbegin);
            syncend = (TextView) itemView.findViewById(R.id.syncend);
        }
    }


}
