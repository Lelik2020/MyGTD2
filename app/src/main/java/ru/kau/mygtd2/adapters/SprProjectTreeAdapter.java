package ru.kau.mygtd2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentManager;

import com.apg.mobile.roundtextview.RoundTextView;
import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.fragments.AddProjectFragment;
import ru.kau.mygtd2.fragments.TasksFragment;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.utils.Utils;

import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT_WITHMINUTES;
import static ru.kau.mygtd2.utils.Const.lstALLPRIORITY;
import static ru.kau.mygtd2.utils.Const.lstALLSTATUS;
import static ru.kau.mygtd2.utils.Const.lstStatus;

public class SprProjectTreeAdapter extends TreeListViewAdapter {

    private ResultResponse<Project> onMenuClickListener;
    private Context c;

    public SprProjectTreeAdapter(ListView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
        this.c = context;
    }

    public SprProjectTreeAdapter(ListView mTree, Context context, List<Node> datas,
                              int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(final Node node , int position, View convertView, ViewGroup parent) {

        final SprProjectTreeAdapter.ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sprproject_choice_item, parent, false);
            viewHolder = new SprProjectTreeAdapter.ViewHolder();

            viewHolder.lnlmain = (LinearLayoutCompat) convertView.findViewById(R.id.lnlmain);

            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.sprproject_tree_item_text);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.sprproject_tree_icon);
            viewHolder.itemMenu = (ImageView) convertView.findViewById(R.id.itemProjectMenu);

            viewHolder.rtv1 = (RoundTextView) convertView.findViewById(R.id.rtv_1);
            viewHolder.rtv2 = (RoundTextView) convertView.findViewById(R.id.rtv_2);
            viewHolder.rtv3 = (RoundTextView) convertView.findViewById(R.id.rtv_3);
            viewHolder.rtv4 = (RoundTextView) convertView.findViewById(R.id.rtv_4);

            viewHolder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
            viewHolder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
            viewHolder.txt3 = (TextView) convertView.findViewById(R.id.txt3);
            viewHolder.txt4 = (TextView) convertView.findViewById(R.id.txt4);
            //textView = new TextView(c);

            viewHolder.rtv1.setVisibility(View.INVISIBLE);
            viewHolder.rtv2.setVisibility(View.INVISIBLE);
            viewHolder.rtv3.setVisibility(View.INVISIBLE);
            viewHolder.rtv4.setVisibility(View.INVISIBLE);

            viewHolder.editProject = (ImageView) convertView.findViewById(R.id.editProject);

            viewHolder.layout2 = convertView.findViewById(R.id.rltlegend);
            viewHolder.layout2.setVisibility(View.GONE);

            //viewHolder.expandedIcon = (ImageView) convertView.findViewById(R.id.expandedIcon);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (SprProjectTreeAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.lnlmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("project", MyApplication.getDatabase().projectDao().getProjectById((Long)node.getId()));
                TasksFragment tasksFragment = new TasksFragment();
                tasksFragment.setArguments(bundle);
                ((MainActivity)c).getSupportFragmentManager().beginTransaction().addToBackStack("TasksFragment").replace(R.id.frame_container, tasksFragment).commit();
            }
        });

        viewHolder.editProject.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                AddProjectFragment addProjectFragment = new AddProjectFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("editproject", MyApplication.getDatabase().projectDao().getProjectById((Long)node.getId()));
                addProjectFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity) c).getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("AddProjectFragment").replace(R.id.frame_container, addProjectFragment).commit();
            }
        });


        /*viewHolder.expandedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewHolder.layout2.getVisibility() != View.GONE) {
                    viewHolder.layout2.setVisibility(View.GONE);
                } else {
                    viewHolder.layout2.setVisibility(View.VISIBLE);
                }

                if (viewHolder.layout2.getVisibility() != View.GONE) {
                    //TransitionManager.beginDelayedTransition(layout2);
                    viewHolder.expandedIcon.setImageResource(R.drawable.cr3_toc_item_expanded);
                    //layout2.setVisibility(View.GONE);
                    return;
                    //layout.refreshDrawableState();
                    //layout2.refreshDrawableState();
                    //layout2.sethe
                } else {
                    //TransitionManager.beginDelayedTransition(layout2);
                    viewHolder.expandedIcon.setImageResource(R.drawable.cr3_toc_item_collapsed);
                    //layout2.setVisibility(View.VISIBLE);
                    return;
                    //layout.refreshDrawableState();
                    //layout2.refreshDrawableState();
                }
                //divider.setVisibility(description.getVisibility());
            }
        });*/



        viewHolder.itemMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.e("111111111111", "222222222222");
                if (onMenuClickListener != null) {
                    onMenuClickListener.onResultRecive(MyApplication.getDatabase().projectDao().getProjectById((Long)node.getId()));
                }
            }

        });


        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }

        viewHolder.label.setText(node.getName());



        //taskStatus = MyApplication.getDatabase().taskStatusDao().getById(1);

        //long count = MyApplication.getDatabase().taskDao().getCountAllTasksOfProjectOutstanding((Long)node.getId(), new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), lstStatus);
        long count = MyApplication.getDatabase().taskDao().getCountOutstanding(Utils.getStartOfDay(new Date()).getTime(), lstStatus, lstALLPRIORITY, new ArrayList<Integer>() {
            {
                add(((Long) node.getId()).intValue());

            }
        });

        //infoStatus = MyApplication.getDatabase().infoStatusDao().getById(1);
        //count = MyApplication.getDatabase().informationDao().getCountByStatus(infoStatus.getId());



        viewHolder.rtv1.setCorner(16, 0, 0, 16);

        viewHolder.rtv1.setBgColor(Color.parseColor("#FF0000"));

        viewHolder.rtv1.setText(" " + Long.toString(count) + " ");
        viewHolder.rtv1.setVisibility(View.VISIBLE);

        //count = MyApplication.getDatabase().taskDao().getCountOutstanding(new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()));

        //count = MyApplication.getDatabase().taskDao().getCountAllTasksOfProjectToday((Long)node.getId(), new Date().getTime(), Utils.dateToString(new SimpleDateFormat("dd.MM.yyyy"), new Date()), lstStatus);
        count = MyApplication.getDatabase().taskDao().getCountByDate(Utils.getEndOfDay(new Date()).getTime(), Utils.dateToString(DEFAULT_DATEFORMAT_WITHMINUTES, Utils.getEndOfDay(new Date())), lstStatus, new ArrayList<Integer>() {
            {
                add(((Long) node.getId()).intValue());

            }
        });


        viewHolder.rtv2.setCorner(0, 0, 0, 0);

        viewHolder.rtv2.setBgColor(Color.parseColor("#33FF99"));

        viewHolder.rtv2.setText(" " + Long.toString(count) + " ");
        viewHolder.rtv2.setVisibility(View.VISIBLE);

        count = MyApplication.getDatabase().taskDao().getCountAllTasks(lstStatus, new ArrayList<Integer>() {
            {
                add(((Long) node.getId()).intValue());

            }
        });

        viewHolder.rtv3.setCorner(0, 0, 0, 0);

        viewHolder.rtv3.setBgColor(Color.parseColor("#aa03A9F4"));

        viewHolder.rtv3.setText(" " + Long.toString(count) + " ");
        viewHolder.rtv3.setVisibility(View.VISIBLE);

        count = MyApplication.getDatabase().taskDao().getCountAllTasks(lstALLSTATUS, new ArrayList<Integer>() {
            {
                add(((Long) node.getId()).intValue());

            }
        });

        viewHolder.rtv4.setCorner(0, 16, 16, 0);

        viewHolder.rtv4.setBgColor(Color.parseColor("#808080"));

        viewHolder.rtv4.setText(" " + Long.toString(count) + " ");
        viewHolder.rtv4.setVisibility(View.VISIBLE);

        viewHolder.txt1.setText(R.string.todaytask);
        viewHolder.txt2.setText(R.string.expiredtask);
        viewHolder.txt3.setText(R.string.allactivetask);
        viewHolder.txt4.setText(R.string.alltasks);


        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
            }
        });


        return convertView;
    }

    private final class ViewHolder
    {
        ImageView icon;
        TextView label;
        ImageView itemMenu;
        LinearLayoutCompat lnlmain;

        RoundTextView rtv1;
        RoundTextView rtv2;
        RoundTextView rtv3;
        RoundTextView rtv4;

        ImageView editProject;

        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;

        //ImageView expandedIcon;

        View layout2;





        /*
        Bundle bundle = new Bundle();
                bundle.putSerializable("project", new Project());
                AddProjectFragment addProjectFragment = new AddProjectFragment();
                addProjectFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().addToBackStack("AddProjectFragment").replace(R.id.frame_container,addProjectFragment).commit();
        */

    }

    public void setOnMenuClickListener(ResultResponse<Project> onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

}


