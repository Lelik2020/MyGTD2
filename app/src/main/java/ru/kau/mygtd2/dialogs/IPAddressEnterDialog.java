package ru.kau.mygtd2.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.interfaces.IPAddressEnterDialogListener;

public class IPAddressEnterDialog extends DialogFragment {

    public TextInputEditText getTxtEditIPAddess() {
        return txtEditIPAddess;
    }

    public void setTxtEditIPAddess(TextInputEditText txtEditIPAddess) {
        this.txtEditIPAddess = txtEditIPAddess;
    }

    com.google.android.material.textfield.TextInputEditText txtEditIPAddess;

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        //txtEditIPAddess = (com.google.android.material.textfield.TextInputEditText) inflater.inflate(R.id.txtEditIPAddess2, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_editipaddress, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        listener.onDialogPositiveClick(((com.google.android.material.textfield.TextInputEditText) inflater.inflate(R.id.txtEditIPAddess2, null)).getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(IPAddressEnterDialog.this);
                    }
                });
        return builder.create();
    }

    IPAddressEnterDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (IPAddressEnterDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
