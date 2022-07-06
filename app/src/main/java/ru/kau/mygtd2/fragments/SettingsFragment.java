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

    EditText txteditipdaddress;
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, null);

        settings = getActivity().getSharedPreferences("MyGTD3", MODE_PRIVATE);
        //prefEditor = settings.edit();

        txteditipdaddress = (EditText) rootView.findViewById(R.id.txteditipdaddress);

        String ipAddress = settings.getString("ipaddresssync", "Не установлен");
        if ((ipAddress == null) || (ipAddress.equals(""))) {
            txteditipdaddress.setText("Не установлен");
        } else {
            txteditipdaddress.setText(ipAddress);
        }



        txteditipdaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPAddressEnterDialog dialog = new IPAddressEnterDialog(getActivity());
                dialog.show(getActivity().getSupportFragmentManager(), "ipaddress");
            }
        });


        return rootView;
    }


    @Override
    public void onDialogPositiveClick(String txtIPAddress) {
        prefEditor = settings.edit();
        prefEditor.putString("ipaddresssync", txtIPAddress);
        prefEditor.apply();
        //txteditipdaddress.setText(txtIPAddress);
        txteditipdaddress.setText(settings.getString("ipaddresssync", "Не установлен"));

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
