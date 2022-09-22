package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Backup;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.utils.Utils;
import stream.custombutton.CustomButton;


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
        holder.backupbegin.setText(Utils.dateToString(new Date(lstBackups.get(position).getDateBegin())));
        holder.backupend.setText(Utils.dateToString(new Date(lstBackups.get(position).getDateEnd())));

        Log.e("ERROR: ", lstBackups.get(position).getGuid() + "  " + lstBackups.get(position).getDateBegin());

        holder.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  --------------------------------------------------------
                // Восстанавливаем информацию об устройстве
                // Меняем идентификатор устройства
                Device device = MyApplication.getDatabase().deviceDao().getCurrentDevice();
                device.setGuid(lstBackups.get(position).getDeviceguid());
                MyApplication.getDatabase().deviceDao().update(device);

                // Получаем справочники
                // -------- Получаем справочник контекстов ------------------------


                // ----------------------------------------------------------------

                //
            }
        });



    }

    @Override
    public int getItemCount() {
        return lstBackups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        TextView backupbegin;
        TextView backupend;

        CustomButton btnRestore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.backuptitle);
            btnRestore = itemView.findViewById(R.id.btnrestore);
            backupbegin = (TextView) itemView.findViewById(R.id.backupbegin);
            backupend = (TextView) itemView.findViewById(R.id.backupend);
        }
    }



}
