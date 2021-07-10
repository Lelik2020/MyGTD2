package ru.kau.mygtd2.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.AboutDevicesAdapter;
import ru.kau.mygtd2.adapters.DeviceInfoAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.interfaces.ClickListener;

public class AboutDevicesFragment extends Fragment implements ClickListener {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    @NonNull

    private AboutDevicesAdapter mainAdapter;
    private DeviceInfoAdapter mainAdapter2;
    //private View rootView2;
    View rootView;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aboutdevices_fragment, null);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_recyclerview);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        //ShapeDrawable drawable = new ShapeDrawable();
        //drawable.getPaint().setColor(Color.BLACK);
        //drawable.getPaint().setStrokeWidth(5);


        recyclerView.setBackground(drawable);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainAdapter = new AboutDevicesAdapter(getActivity(), MyApplication.getDatabase().deviceDao().getAll());
        //mainAdapter.setClickListener(this);
        //mainAdapter.

        recyclerView.setAdapter(mainAdapter);

        return rootView;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void itemClicked(View view, int position, int grp) {

        //View rootView = inflater.inflate(R.layout.aboutdevices_fragment, null);
        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.detail_recyclerview);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, R.color.black_1);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(20);

        recyclerView2.setBackground(drawable);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainAdapter2 = new DeviceInfoAdapter(getActivity(), MyApplication.getDatabase().deviceInfoDao().getById(1));
        //mainAdapter2.setClickListener(this);

        recyclerView2.setAdapter(mainAdapter2);



    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
