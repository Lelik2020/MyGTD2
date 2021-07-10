package ru.kau.mygtd2.trees.interfaces;

import java.util.List;

import ru.kau.mygtd2.objects.Project;

public interface Item {
    public String getTitle();
    public int getIconResource();
    public List<Project> getChilds();
}
