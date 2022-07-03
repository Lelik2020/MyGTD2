package ru.kau.mygtd2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import ru.kau.mygtd2.R;

public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, null);

        final EditText txteditipdaddress = (EditText) rootView.findViewById(R.id.txteditipdaddress);

        //txteditipdaddress.setOnClickListener();


        return rootView;
    }

}
