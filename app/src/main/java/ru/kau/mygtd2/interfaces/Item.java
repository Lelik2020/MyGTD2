package ru.kau.mygtd2.interfaces;

import java.util.ArrayList;

public interface Item {

    public String getTitle();
    public int getIconResource();
    public ArrayList<Item> getChilds();

}
