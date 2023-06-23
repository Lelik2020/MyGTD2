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

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.TaskCategory;
import ru.kau.mygtd2.objects.TaskTypes;

public class TaskCategoryAdapter extends RecyclerView.Adapter<TaskCategoryAdapter.ViewHolder>{

    private Context c;
    private List<TaskCategory> lstTaskCategory;

    public TaskCategoryAdapter(Context c, List<TaskCategory> lstTaskCategory) {
        this.c = c;
        this.lstTaskCategory = lstTaskCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.taskcategory_cardview, parent,false);

        return new TaskCategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TaskCategory taskCategory = getItem(position);
        holder.title.setText(taskCategory.getTitle());
        holder.tagImage.setColorFilter(Color.parseColor(taskCategory.getColor()));

    }

    @Override
    public int getItemCount() {
        return lstTaskCategory.size();
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
            tagImage = itemView.findViewById(R.id.context_icon);
            title = itemView.findViewById(R.id.contexttitle);
            roundTextView = itemView.findViewById(R.id.contextcount1);
            roundTextView2 = itemView.findViewById(R.id.contextcount2);
            roundTextView3 = itemView.findViewById(R.id.contextcount3);
            roundTextView4 = itemView.findViewById(R.id.contextcount4);

        }
    }

    public TaskCategory getItem(int pos) {
        if (lstTaskCategory == null || lstTaskCategory.isEmpty()) {
            return null;
        }
        if (lstTaskCategory.size() - 1 >= pos) {
            return lstTaskCategory.get(pos);
        } else {
            return lstTaskCategory.get(0);
        }
    }


}
