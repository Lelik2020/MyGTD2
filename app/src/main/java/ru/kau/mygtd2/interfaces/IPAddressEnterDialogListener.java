package ru.kau.mygtd2.interfaces;

import androidx.fragment.app.DialogFragment;

public interface IPAddressEnterDialogListener {

    public void onDialogPositiveClick(String txtIPAddress);
    public void onDialogNegativeClick(DialogFragment dialog);

}
