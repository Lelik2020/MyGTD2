package ru.kau.mygtd2.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.interfaces.IPAddressEnterDialogListener;

public class IPAddressEnterDialog extends DialogFragment {

    Context a;
    String type;

    public IPAddressEnterDialog(Context a, String type){
        this.a = a;
        this.type = type;
        callback4 = (IPAddressEnterDialogListener) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);
    }

    com.google.android.material.textfield.TextInputEditText txtEditIPAddess;
    EditText txtEditIPAddr;
    IPAddressEnterDialogListener callback4;// = (IPAddressEnterDialogListener) ((MainActivity) a).getSupportFragmentManager().findFragmentById(R.id.frame_container);

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //final AlertDialog alertDialog = (AlertDialog) dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        // Get the layout inflater
        //LayoutInflater inflater = requireActivity().getLayoutInflater();
        LayoutInflater inflater = ((MainActivity)a).getLayoutInflater();
        //txtEditIPAddess = (com.google.android.material.textfield.TextInputEditText) inflater.inflate(R.id.txtEditIPAddess2, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        //View v = inflater.inflate(R.layout.dialog_editipaddress2, null);
        //View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_editipaddress2, null);
        //View view = LayoutInflater.from(a).inflate(R.layout.dialog_editipaddress2, null, false);
        View view = inflater.inflate(R.layout.dialog_editipaddress, null, false);
        //txtEditIPAddess = (com.google.android.material.textfield.TextInputEditText) view.findViewById(R.id.txtEditIPAddess2);
        //txtEditIPAddr = (EditText) view.findViewById(R.id.txtEditIPAddr);
        builder.setView(inflater.inflate(R.layout.dialog_editipaddress, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //v.findViewById(R.id.btnYes).setOnClickListener(this);
                        //listener.onDialogPositiveClick(((com.google.android.material.textfield.TextInputEditText)v.findViewById(R.id.txtEditIPAddess2)).getText().toString());
                        //callback4.onDialogPositiveClick(String.valueOf(txtEditIPAddess.getText()));
                        //callback4.onDialogPositiveClick(txtEditIPAddess.get);
                        //txtEditIPAddess = (EditText) ((AlertDialog)dialog).findViewById(R.id.txtEditIPAddr);
                        txtEditIPAddess = (com.google.android.material.textfield.TextInputEditText) ((AlertDialog)dialog).findViewById(R.id.txtEditIPAddess2);
                        //System.out.printf("222  " + txtEditIPAddr.getText().toString());
                        callback4.onDialogPositiveClick(String.valueOf(txtEditIPAddess.getText()), type);
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

    /*@Override
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
    }*/


}
