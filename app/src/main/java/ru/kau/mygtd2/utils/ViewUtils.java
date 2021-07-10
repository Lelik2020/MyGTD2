package ru.kau.mygtd2.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.kau.mygtd2.R;


public class ViewUtils {

    /*
    public void viewToast(Context context, LayoutInflater layoutInflater, int resource, String txt, int lenght, int gravity, int offsetX, int offsetY){

    }
    */

    public static void viewPositiveToast(Context context, LayoutInflater layoutInflater, String txt, int lenght, int gravity, int offsetX, int offsetY){
        viewToast(context, layoutInflater, R.layout.activity_toast_custom_view, txt, lenght, gravity, offsetX, offsetY);
    }

    public static void viewNegativeToast(Context context, LayoutInflater layoutInflater, String txt, int lenght, int gravity, int offsetX, int offsetY){
        viewToast(context, layoutInflater, R.layout.activity_toast_custom_view2, txt, lenght, gravity, offsetX, offsetY);
    }

    public static void viewToast(Context context, LayoutInflater layoutInflater, int resource, String txt, int lenght, int gravity, int offsetX, int offsetY){


        View toastView = layoutInflater.inflate(resource, null);

        // Initiate the Toast instance.
        Toast toast = new Toast(context);
        // Set custom view in toast.
        toast.setView(toastView);
        toast.setDuration(lenght);
        toast.setGravity(gravity, offsetX, offsetY);
        toast.makeText(context, txt, lenght);
        TextView tv = (TextView) toastView.findViewById(R.id.customToastText);
        tv.setText(txt);
        toast.show();


        //return;
    }

}
