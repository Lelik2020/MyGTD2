package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.fragments.AddTaskFragment;
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
        holder.title.setText(lstTaskTemplate.get(position).getTitle());
        holder.itemAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("template", lstTaskTemplate.get(position));

                //if
                AddTaskFragment addTaskFragment = new AddTaskFragment();
                addTaskFragment.setArguments(bundle);
                ((MainActivity)c).getSupportFragmentManager().beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,addTaskFragment).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lstTaskTemplate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView itemAddTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tasktitle2);
            itemAddTask= itemView.findViewById(R.id.itemAddTask);
        }
    }

}
