package ru.kau.mygtd2.fragments;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, null);

        txteditipdaddress = (EditText) rootView.findViewById(R.id.txteditipdaddress);

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
        txteditipdaddress.setText(txtIPAddress);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
