package ru.kau.mygtd2.fragments;

import static ru.kau.mygtd2.enums.TypeSetting.CURRENTDATE;
import static ru.kau.mygtd2.enums.TypeSetting.IPSERVERBACKUP;
import static ru.kau.mygtd2.enums.TypeSetting.IPSERVERSYNC;
import static ru.kau.mygtd2.enums.TypeSetting.TYPEDEVICE;
import static ru.kau.mygtd2.enums.TypeSetting.USECURRENTSYSTEMDATE;
import static ru.kau.mygtd2.utils.Const.DEFAULT_DATEFORMAT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Date;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.dialogs.Dialogs;
import ru.kau.mygtd2.dialogs.IPAddressEnterDialog;
import ru.kau.mygtd2.dialogs.TypeDeviceEnterDialog;
import ru.kau.mygtd2.interfaces.DialogDateBeginChoice;
import ru.kau.mygtd2.interfaces.IPAddressEnterDialogListener;
import ru.kau.mygtd2.interfaces.TypeDeviceEnterDialogListener;
import ru.kau.mygtd2.objects.Device;
import ru.kau.mygtd2.utils.Settings;
import ru.kau.mygtd2.utils.Utils;

public class SettingsFragment extends Fragment implements IPAddressEnterDialogListener, TypeDeviceEnterDialogListener, DialogDateBeginChoice {
    EditText txteditipdaddressbackup;
    EditText txteditipdaddresssync;

    EditText txtedittypeofdevice;

    EditText txteditcurrentdate;

    CheckBox cbusesystemdate;
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
        txteditcurrentdate = rootView.findViewById(R.id.txteditcurrentdate);
        cbusesystemdate = rootView.findViewById(R.id.cbusesystemdate);

        //String ipAddresssync = Settings.getStringSetting(IPSERVERSYNC);
        txteditipdaddresssync.setText(Settings.getStringSetting(IPSERVERSYNC));
        //String ipAddressbackup= Settings.getStringSetting(IPSERVERBACKUP);
        txteditipdaddressbackup.setText(Settings.getStringSetting(IPSERVERBACKUP));
        txtedittypeofdevice.setText(Settings.getStringSetting(TYPEDEVICE));
        txteditcurrentdate.setText(Settings.getLongSetting(CURRENTDATE) == 0 ? "Не установлена" : Utils.dateToString(DEFAULT_DATEFORMAT, new Date(Settings.getLongSetting(CURRENTDATE))));
        cbusesystemdate.setChecked(Settings.getBooleanSetting(USECURRENTSYSTEMDATE));

        cbusesystemdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Settings.setLongSettings(CURRENTDATE, 0L);
                    Settings.setBooleanSettings(USECURRENTSYSTEMDATE, isChecked);
                    getDateBegin("", Settings.getLongSetting(CURRENTDATE));
                } else {
                    //Settings.setBooleanSettings(USECURRENTSYSTEMDATE, isChecked);
                    Dialogs.showDateBeginDialog(getActivity(), new Runnable() {

                        @Override
                        public void run() {
                            //tagsRunnable.run();
                            //EventBus.getDefault().post(new NotifyAllFragments());
                        }
                    });
                }
            }
        });

        txteditcurrentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.showDateBeginDialog(getActivity(), new Runnable() {

                    @Override
                    public void run() {
                        //tagsRunnable.run();
                        //EventBus.getDefault().post(new NotifyAllFragments());
                    }
                });
            }
        });


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

    @Override
    public void getDateBegin(String date, long datemls) {

        //
        Settings.setLongSettings(CURRENTDATE, datemls);
        txteditcurrentdate.setText(Settings.getLongSetting(CURRENTDATE) == 0 ? "Не установлена" : Utils.dateToString(DEFAULT_DATEFORMAT, new Date(Settings.getLongSetting(CURRENTDATE))));
        Settings.setBooleanSettings(USECURRENTSYSTEMDATE, (datemls == 0) ? true : false);
        cbusesystemdate.setChecked(Settings.getBooleanSetting(USECURRENTSYSTEMDATE));

        //txteditcurrentdate.setText(Settings.getStringSetting(CURRENTDATE));
    }
}
