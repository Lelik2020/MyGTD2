package ru.kau.mygtd2.utils.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.dialogs.MyProgressDialog;
import ru.kau.mygtd2.utils.LOG;

public abstract class AsyncProgressTask<T> extends AsyncTask<Object, Object, T> {

    ProgressDialog dialog;

    public abstract Context getContext();


    @Override
    protected void onPreExecute() {
        dialog = MyProgressDialog.show(getContext(),  getContext().getString(R.string.please_wait));
    }

    @Override
    protected void onPostExecute(T result) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            LOG.d(e);
        }

    }

}
