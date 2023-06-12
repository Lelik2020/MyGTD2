package ru.kau.mygtd2.adapters.dialog;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.utils.Utils;


public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{

    private Context c;
    private List<Tag> lstTag;

    static Set<Integer> checked;

    public TagAdapter(Context c, List<Tag> lstTag) {
        this.c = c;
        this.lstTag = lstTag;
    }

    public static Set<Integer> getChecked() {
        return checked;
    }

    @NonNull
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.tag_item, parent,false);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(15f);
        v.setBackground(drawable);

        return new TagAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tag tag = getItem(position);
        holder.title.setText(tag.getTitle());
        //holder.tagImage.setColorFilter(Color.parseColor(tag.getColor()));
        holder.tagImage.setColorFilter(Utils.parseColor(tag.getColor()));

        checked = new HashSet<>();
        holder.title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked.add(position);
                } else {
                    checked.remove(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstTag.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CheckBox title;
        ImageView tagImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tagImage = (ImageView) itemView.findViewById(R.id.iconTag);
            title = (CheckBox) itemView.findViewById(R.id.tagName);



        }
    }

    public Tag getItem(int pos) {
        if (lstTag == null || lstTag.isEmpty()) {
            return null;
        }
        if (lstTag.size() - 1 >= pos) {
            return lstTag.get(pos);
        } else {
            return lstTag.get(0);
        }
    }


}
