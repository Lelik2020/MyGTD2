package ru.kau.mygtd2.dialogs;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.utils.AppState;

//import android.support.v7.app.AlertDialog;

public class CloseAppDialog {

    public static void show(final Context c, final Runnable action) {
        final Runnable closeApp = new Runnable() {

            @Override
            public void run() {
                action.run();

            }
        };
        if (!AppState.get().isShowCloseAppDialog) {
            closeApp.run();
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(c, R.style.DialogStyle);
        dialog.setTitle(R.string.close_application_);

        dialog.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                closeApp.run();

            }
        });
        dialog.show();

    }

}
