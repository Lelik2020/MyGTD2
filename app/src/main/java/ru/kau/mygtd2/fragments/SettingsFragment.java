package ru.kau.mygtd2.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.dialogs.IPAddressEnterDialog;
import ru.kau.mygtd2.interfaces.IPAddressEnterDialogListener;

public class SettingsFragment extends Fragment implements IPAddressEnterDialogListener {
    EditText txteditipdaddressbackup;
    EditText txteditipdaddresssync;
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, null);

        settings = getActivity().getSharedPreferences("MyGTD3", MODE_PRIVATE);
        //prefEditor = settings.edit();

        txteditipdaddresssync = (EditText) rootView.findViewById(R.id.txteditipdaddress);
        txteditipdaddressbackup = (EditText) rootView.findViewById(R.id.txteditipdaddressbackup);

        String ipAddresssync = settings.getString("ipaddresssync", "Не установлен");
        txteditipdaddresssync.setText(ipAddresssync);
        String ipAddressbackup= settings.getString("ipaddressbackup", "Не установлен");
        txteditipdaddressbackup.setText(ipAddressbackup);

        txteditipdaddresssync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPAddressEnterDialog dialog = new IPAddressEnterDialog(getActivity(), "ipsync");
                dialog.show(getActivity().getSupportFragmentManager(), "ipsync");
            }
        });

        txteditipdaddressbackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPAddressEnterDialog dialog = new IPAddressEnterDialog(getActivity(), "ipbackup");
                dialog.show(getActivity().getSupportFragmentManager(), "ipbackup");
            }
        });


        return rootView;
    }


    @Override
    public void onDialogPositiveClick(String txtIPAddress, String type) {
        prefEditor = settings.edit();
        if (type.equals("ipsync")) {
            prefEditor.putString("ipaddresssync", txtIPAddress);
        }
        if (type.equals("ipbackup")) {
            prefEditor.putString("ipaddressbackup", txtIPAddress);
        }

        prefEditor.apply();
        //txteditipdaddress.setText(txtIPAddress);
        if (type.equals("ipsync")) {
            txteditipdaddresssync.setText(settings.getString("ipaddresssync", "Не установлен"));
        }

        if (type.equals("ipbackup")) {
            txteditipdaddressbackup.setText(settings.getString("ipaddressbackup", "Не установлен"));
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
