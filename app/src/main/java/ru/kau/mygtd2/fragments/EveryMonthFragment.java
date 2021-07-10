package ru.kau.mygtd2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.R;

public class EveryMonthFragment extends Fragment {

    private SmartMaterialSpinner spinner1;
    private List<String> provinceList;

    private SmartMaterialSpinner spinner2;
    private List<String> spinner2source;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.everymonth_fragment, null);

        spinner1 = (SmartMaterialSpinner) rootView.findViewById(R.id.spinner1);
        spinner2 = (SmartMaterialSpinner) rootView.findViewById(R.id.spinner2);

        provinceList = new ArrayList<>();

        provinceList.add("1");
        provinceList.add("2");
        provinceList.add("3");
        provinceList.add("4");

        spinner1.setItem(provinceList);
        spinner1.setSelection(0);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getActivity(), provinceList.get(position), Toast.LENGTH_SHORT).show();
                spinner1.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner2source = new ArrayList<>();
        spinner2source.add(getActivity().getString(R.string.day_1));
        spinner2source.add(getActivity().getString(R.string.day_2));
        spinner2source.add(getActivity().getString(R.string.day_3));
        spinner2source.add(getActivity().getString(R.string.day_4));
        spinner2source.add(getActivity().getString(R.string.day_5));
        spinner2source.add(getActivity().getString(R.string.day_6));
        spinner2source.add(getActivity().getString(R.string.day_7));

        spinner2.setItem(spinner2source);
        spinner2.setSelection(0);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getActivity(), provinceList.get(position), Toast.LENGTH_SHORT).show();
                spinner2.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return rootView;
    }


}
