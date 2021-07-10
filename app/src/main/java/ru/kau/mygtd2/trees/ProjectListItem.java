package ru.kau.mygtd2.trees;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.trees.interfaces.Item;

public class ProjectListItem implements Item {

    //private String title;
    private Project project;
    //private ArrayList<Item> childs;
    private List<Project> childs;

    public ProjectListItem (Project project){
        this.project = project;
    }

    @Override
    public String getTitle() { // 2
        return project.getTitle();
    }


    public List<Project> getChildProjects() { // 3
        if (childs != null)
            return childs;
        childs = MyApplication.getDatabase().projectDao().getChild(project.getId());
        return childs;
    }

    @Override
    public int getIconResource() { // 4
        if (childs.size() > 0)
            return R.drawable.glyphicons_433_plus;

        return R.drawable.ic_list_group_empty;
    }

    @Override
    public List<Project> getChilds() {
        return null;
    }

    /*
    public void addChild (Item item) { // 5
        childs.add(item);
    }
    */

}
