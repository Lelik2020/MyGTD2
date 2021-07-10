package ru.kau.mygtd2.fragments.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.ListMeetingsAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.common.interfaces.ClickListener;

public class ListMeetingsFragment extends Fragment implements ClickListener {

    private RecyclerView recyclerView;
    private ListMeetingsAdapter listMeetingsAdapter;

    public static ListMeetingsFragment newInstance(String s) {
        ListMeetingsFragment listMeetingsFragment = new ListMeetingsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("", s);
        listMeetingsFragment.setArguments(bundle);
        return listMeetingsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.listmeeting_fragment, null);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.meetings_recyclerview);



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMeetingsAdapter = new ListMeetingsAdapter(getActivity(), MyApplication.getDatabase().meetingDao().getAllMeetings());
        listMeetingsAdapter.setClickListener(this);

        recyclerView.setAdapter(listMeetingsAdapter);

        //((MainActivity)getActivity()).setVisibleFloatingMenuControls(View.VISIBLE);




        return rootView;
    }

    @Override
    public void itemClicked(View view, int position, int grp) {

    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
