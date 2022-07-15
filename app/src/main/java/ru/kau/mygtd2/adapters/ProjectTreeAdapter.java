package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.multilevel.treelist.Node;
import com.multilevel.treelist.OnTreeNodeClickListener;
import com.multilevel.treelist.TreeListViewAdapter;

import java.util.List;

import ru.kau.mygtd2.R;

public class ProjectTreeAdapter extends TreeListViewAdapter {

    public Context context;


    private OnTreeNodeClickListener onTreeNodeClickListener;

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public ProjectTreeAdapter(ListView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
        this.context = context;

        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                expandOrCollapse(position);

                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position),
                            position);
                }
            }

        });



    }

    public ProjectTreeAdapter(ListView mTree, Context context, List<Node> datas,
                             int defaultExpandLevel) {

        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(final Node node , int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.project_choice_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cb = (CheckBox) convertView
                    .findViewById(R.id.checkBoxProject);
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.project_tree_item_text);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.project_tree_icon);
            viewHolder.editProject = (ImageView) convertView.findViewById(R.id.editProject);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = viewHolder.cb.isChecked();
                allUnCheck();
                setChecked(node,checked);
            }
        });

        if (node.isChecked()){
            viewHolder.cb.setChecked(true);
        }else {
            viewHolder.cb.setChecked(false);
        }

        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }

        viewHolder.label.setText(node.getName());

        viewHolder.editProject.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                /*AddProjectFragment addProjectFragment = new AddProjectFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("project", MyApplication.getDatabase().projectDao().getProjectById((Long)node.getId()));
                addProjectFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("AddProjectFragment").replace(R.id.frame_container, addProjectFragment).commit();*/

            }
        });

        return convertView;
    }

    private final class ViewHolder
    {
        ImageView icon;
        ImageView editProject;
        CheckBox cb;
        TextView label;
    }

}
