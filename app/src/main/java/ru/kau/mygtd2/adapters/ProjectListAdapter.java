package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.objects.Project;

public class ProjectListAdapter extends BaseAdapter {

    private class Pair {
        Project item;
        int level;

        Pair (Project item, int level) {
            this.item = item;
            this.level = level;
        }
    }

    private LayoutInflater mLayoutInflater;
    private List<Pair> hierarchyArray;

    private List<Project> originalItems;
    private LinkedList<Project> openItems;

    public ProjectListAdapter (Context ctx, List<Project> items) {
        mLayoutInflater = LayoutInflater.from(ctx);
        originalItems = items;

        hierarchyArray = new ArrayList<Pair>();
        openItems = new LinkedList<Project>();

        //generateHierarchy();
    }

    @Override
    public int getCount() {
        return hierarchyArray.size();
    }

    @Override
    public Object getItem(int position) {
        return hierarchyArray.get(position).item;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.project_choice_item, null);
        TextView title = (TextView)convertView.findViewById(R.id.project_tree_item_text);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.project_tree_icon);

        Pair pair = hierarchyArray.get(position);

        title.setText(pair.item.getTitle());

        //imageView.setImageResource(pair.item.getIconResource());

        //title.setCompoundDrawablesWithIntrinsicBounds(pair.item.getIconResource(), 0, 0, 0);
        title.setPadding(pair.level * 15, 0, 0, 0);
        return convertView;
    }






}
