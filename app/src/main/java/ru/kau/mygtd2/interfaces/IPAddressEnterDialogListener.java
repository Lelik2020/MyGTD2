package ru.kau.mygtd2.interfaces;

import androidx.fragment.app.DialogFragment;

public interface IPAddressEnterDialogListener {

    public void onDialogPositiveClick(String txtIPAddress, String type);
    public void onDialogNegativeClick(DialogFragment dialog);

}
