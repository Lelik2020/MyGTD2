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
import ru.kau.mygtd2.objects.TaskTemplate;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.ViewHolder>{

    private Context c;
    private List<TaskTemplate> lstTaskTemplate;

    public TemplatesAdapter(Context c, List<TaskTemplate> lstTaskTemplate) {
        this.c = c;
        this.lstTaskTemplate = lstTaskTemplate;
    }

    @NonNull
    @Override
    public TemplatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.template_cardview,viewGroup,false);

        return new TemplatesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lstTaskTemplate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tasktitle);
        }
    }

}
