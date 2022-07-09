package ru.kau.mygtd2.fragments;

import static ru.kau.mygtd2.enums.TypeSetting.IPSERVERBACKUP;
import static ru.kau.mygtd2.enums.TypeSetting.IPSERVERSYNC;
import static ru.kau.mygtd2.enums.TypeSetting.TYPEDEVICE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.dialogs.IPAddressEnterDialog;
import ru.kau.mygtd2.dialogs.TypeDeviceEnterDialog;
import ru.kau.mygtd2.interfaces.IPAddressEnterDialogListener;
import ru.kau.mygtd2.interfaces.TypeDeviceEnterDialogListener;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.utils.Settings;

public class SettingsFragment extends Fragment implements IPAddressEnterDialogListener, TypeDeviceEnterDialogListener {
    EditText txteditipdaddressbackup;
    EditText txteditipdaddresssync;

    EditText txtedittypeofdevice;
    //SharedPreferences settings;
    //SharedPreferences.Editor prefEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, null);

        //settings = getActivity().getSharedPreferences("MyGTD3", MODE_PRIVATE);
        //prefEditor = settings.edit();

        txteditipdaddresssync = (EditText) rootView.findViewById(R.id.txteditipdaddress);
        txteditipdaddressbackup = (EditText) rootView.findViewById(R.id.txteditipdaddressbackup);
        txtedittypeofdevice = (EditText) rootView.findViewById(R.id.txtedittypeofdevice);

        //String ipAddresssync = Settings.getStringSetting(IPSERVERSYNC);
        txteditipdaddresssync.setText(Settings.getStringSetting(IPSERVERSYNC));
        //String ipAddressbackup= Settings.getStringSetting(IPSERVERBACKUP);
        txteditipdaddressbackup.setText(Settings.getStringSetting(IPSERVERBACKUP));
        txtedittypeofdevice.setText(Settings.getStringSetting(TYPEDEVICE));


        txtedittypeofdevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypeDeviceEnterDialog dialog = new TypeDeviceEnterDialog(getActivity(), "typedevice");
                dialog.show(getActivity().getSupportFragmentManager(), "typedevice");
            }
        });


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

        if (type.equals("ipsync")) {
            Settings.setStringSettings(IPSERVERSYNC, txtIPAddress);
        }
        if (type.equals("ipbackup")) {
            Settings.setStringSettings(IPSERVERBACKUP, txtIPAddress);
        }

        if (type.equals("ipsync")) {
            txteditipdaddresssync.setText(Settings.getStringSetting(IPSERVERSYNC));
        }

        if (type.equals("ipbackup")) {
            txteditipdaddressbackup.setText(Settings.getStringSetting(IPSERVERBACKUP));
        }
    }

    @Override
    public void onDialogPositiveClick(String txtTypeDevice) {
        Settings.setStringSettings(TYPEDEVICE, txtTypeDevice);
        Device device = MyApplication.getDatabase().deviceDao().getCurrentDevice();
        if (txtTypeDevice.equals("Телефон")) {
            device.setDevicetype(2);
        }
        if (txtTypeDevice.equals("Планшет")) {
            device.setDevicetype(1);
        }
        MyApplication.getDatabase().deviceDao().update(device);
        txtedittypeofdevice.setText(Settings.getStringSetting(TYPEDEVICE));

    }
}
