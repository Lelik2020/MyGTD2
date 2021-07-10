package ru.kau.mygtd2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.moos.library.HorizontalProgressView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.BackupItem;


public class BackupItemAdapter extends RecyclerView.Adapter<BackupItemAdapter.ViewHolder>{

    private Context c;
    private List<BackupItem> lstBackupItem;

    public BackupItemAdapter(Context c, List<BackupItem> lstBackupItem) {
        this.c = c;
        this.lstBackupItem = lstBackupItem;
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull BackupItemAdapter.ViewHolder viewHolder, int i) {
        BackupItem backupItem = lstBackupItem.get(i);
        viewHolder.title.setText(backupItem.getTitle());
        viewHolder.title2.setText(backupItem.getCount1() + "/" + backupItem.getCount2());
        viewHolder.title3.setText(backupItem.getPercent() + "%");
        //Log.
        viewHolder.progressBar.setProgress((int)(backupItem.getCount1() * 100/ backupItem.getCount2()));

    }

    @Override
    public int getItemCount() {
        return lstBackupItem.size();
    }

    @NonNull
    @Override
    public BackupItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.backupitem_cardview,viewGroup,false);

        return new BackupItemAdapter.ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView title2;
        TextView title3;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            title2 = (TextView) itemView.findViewById(R.id.title2);
            title3 = (TextView) itemView.findViewById(R.id.title3);
            progressBar = itemView.findViewById(R.id.progressView_horizontal);





        }
    }


}
