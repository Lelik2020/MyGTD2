package ru.kau.mygtd2.adapters;

import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.HashSet;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.trees.abstr.AllTree;

public class ProjectAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {

    private final HashSet<AllTree<?>> myOpenItems = new HashSet<AllTree<?>>();



    protected final void setIcon(ImageView imageView, AllTree<?> tree) {
        if (tree.hasChildren()) {
            if (isOpen(tree)) {
                imageView.setImageResource(R.drawable.glyphicons_434_minus);
            } else {
                imageView.setImageResource(R.drawable.glyphicons_433_plus);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_list_group_empty);
        }
        imageView.setPadding(25 * (tree.Level - 1), imageView.getPaddingTop(), 0, imageView.getPaddingBottom());
    }

    public final boolean isOpen(AllTree<?> tree) {
        return myOpenItems.contains(tree);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
