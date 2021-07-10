package ru.kau.mygtd2.trees;

import java.util.ArrayList;
import java.util.List;

public class Node<T,B> {


    public B bean;

    public int iconExpand=-1, iconNoExpand = -1;

    private T id;

    private T pId ;

    private String name;


    private int level;


    private boolean isExpand = false;

    private int icon = -1;


    private List<Node> children = new ArrayList<>();


    private Node parent;

    private boolean isChecked;

    public boolean isNewAdd = true;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Node() {}

    public Node(T id, T pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
    public Node(T id, T pId, String name,B bean) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.bean = bean;
    }


    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public T getId()
    {
        return id;
    }

    public void setId(T id)
    {
        this.id = id;
    }

    public T getpId()
    {
        return pId;
    }

    public void setpId(T pId)
    {
        this.pId = pId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    public boolean isRoot() {
        return parent == null;
    }


    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }


    public boolean isLeaf()
    {
        return children.size() == 0;
    }


    public int getLevel() {

        return parent == null ? 0 : parent.getLevel() + 1;
    }


    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {

            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }

}
