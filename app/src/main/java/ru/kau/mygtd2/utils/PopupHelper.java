package ru.kau.mygtd2.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.info.view.MyPopupMenu;
import ru.kau.mygtd2.utils.info.AppsConfig;

public class PopupHelper {

    public static void updateGridOrListIcon(ImageView gridList, int libraryMode) {
        if (gridList == null) {
            return;
        }
        if (libraryMode == AppState.MODE_LIST) {
            gridList.setImageResource(R.drawable.glyphicons_114_justify);
        } else if (libraryMode == AppState.MODE_LIST_COMPACT) {
            gridList.setImageResource(R.drawable.glyphicons_114_justify_compact);
        } else if (libraryMode == AppState.MODE_GRID) {
            gridList.setImageResource(R.drawable.glyphicons_156_show_big_thumbnails);
        } else if (libraryMode == AppState.MODE_COVERS) {
            gridList.setImageResource(R.drawable.glyphicons_157_show_thumbnails);
        } else if (libraryMode == AppState.MODE_AUTHORS) {
            gridList.setImageResource(R.drawable.glyphicons_4_user);
        } else if (libraryMode == AppState.MODE_SERIES) {
            gridList.setImageResource(R.drawable.glyphicons_710_list_numbered);
        } else if (libraryMode == AppState.MODE_GENRE) {
            gridList.setImageResource(R.drawable.glyphicons_66_tag);
        } else if (libraryMode == AppState.MODE_USER_TAGS) {
            gridList.setImageResource(R.drawable.glyphicons_66_tag);
        } else if (libraryMode == AppState.MODE_KEYWORDS) {
            gridList.setImageResource(R.drawable.glyphicons_66_tag);
        }
    }

    public static void addPROIcon(final PopupMenu menu, final Context c) {
        if (!AppsConfig.checkIsProInstalled(c)) {
            menu.getMenu().add("Librera PRO").setIcon(R.mipmap.app_icon11).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Urls.openPdfPro(c);
                    return false;
                }
            });
        }
    }

    public static void addPROIcon(final MyPopupMenu menu, final Context c) {
        if (true) {
            // templorary hide this;
            return;

        }
        if (!AppsConfig.checkIsProInstalled(c)) {
            menu.getMenu().add(R.string.app_name_pro).setIcon(R.mipmap.app_icon11).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Urls.openPdfPro(c);
                    return false;
                }
            });
        }
    }

    public static void initIcons(final PopupMenu menu, int color) {

        try {
            Field[] fields = menu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(menu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }

            for (int i = 0; i < menu.getMenu().size(); i++) {
                MenuItem item = menu.getMenu().getItem(i);
                if (item.getTitle().toString().contains("Librera")) {
                    continue;
                }
                Drawable icon = item.getIcon().getConstantState().newDrawable();
                icon = icon.mutate();
                icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                item.setIcon(icon);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
