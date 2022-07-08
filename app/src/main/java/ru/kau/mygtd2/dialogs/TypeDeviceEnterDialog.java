package ru.kau.mygtd2.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.BaseItemLayoutAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.interfaces.IPAddressEnterDialogListener;
import ru.kau.mygtd2.objects.Target;

public class TypeDeviceEnterDialog extends DialogFragment {

    Context a;

    public TypeDeviceEnterDialog(Context a, String type){
        this.a = a;

        //callback4 = (IPAddressEnterDialogListener) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(a);
        final AlertDialog.Builder builder = new AlertDialog.Builder(a, R.style.YDialog);
        builder.setTitle("Выбрать тип устройства");
        View inflate = LayoutInflater.from(a).inflate(R.layout.dialog_typedevice, null, false);
        final ListView list = (ListView) inflate.findViewById(R.id.listView1);

        final List<String> typesdevices = new ArrayList<>();
        typesdevices.add("Телефон");
        typesdevices.add("Планшет");



        final Set<Integer> checked = new HashSet<>();

        final BaseItemLayoutAdapter<String> adapter = new BaseItemLayoutAdapter<String>(a, R.layout.typedevice_item, typesdevices) {

            @Override
            public void populateView(View layout, int position, String item) {
                CheckBox text = (CheckBox) layout.findViewById(R.id.typeDeviceName);
                text.setText(item);

                text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        for (int i = 0; i < getCount(); i++) {
                            // https://www.thaicreate.com/mobile/android-listview-checkbox.html
                            LinearLayoutCompat itemLayout = (LinearLayoutCompat)list.getChildAt(i);
                            CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.typeDeviceName);
                            checkbox.setChecked(false);
                            if (i == position){
                                checkbox.setChecked(isChecked);
                            }
                            //list.getAdapter().;
                        }
                        if (isChecked) {
                            checked.add(position);
                        } else {
                            checked.remove(position);
                        }
                        buttonView.setChecked(isChecked);
                        /*
                        if (buttonView.isChecked() == isChecked){
                            buttonView.setChecked(false);
                        } else {
                            buttonView.setChecked(true);
                        }
                        */
                    }
                });



            }
        };

        list.setAdapter(adapter);
        builder.setView(inflate).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

}
