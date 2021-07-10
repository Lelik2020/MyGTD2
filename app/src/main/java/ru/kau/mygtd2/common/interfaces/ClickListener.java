package ru.kau.mygtd2.common.interfaces;

import android.view.View;

public interface ClickListener {
    public void itemClicked(View view , int position, int grp);

    public void itemClicked(View view , int position);
}
