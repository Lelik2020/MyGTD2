package ru.kau.mygtd2.fragments.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;

public class CalendarFragment extends Fragment implements BottomNavigationBar.OnTabSelectedListener{

    private DayFragment mDayFragment;
    private MonthFragment mMonthFragment;
    private WeekFragment mWeekFragment;
    private ListMeetingsFragment mlistMeetingsFragment;

    private BottomNavigationBar mBottomNavigationBar;

    public static CalendarFragment newInstance(String s) {
        CalendarFragment navigationFragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putString("args", s);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_bottom_navigation_bar, container, false);

        mBottomNavigationBar = (BottomNavigationBar) view.findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.today, getString(R.string.today)).setInactiveIconResource(R.drawable.today).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.week, getString(R.string.week)).setInactiveIconResource(R.drawable.week).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.month, getString(R.string.month)).setInactiveIconResource(R.drawable.month).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.drawable.list, getString(R.string.listmeetings)).setInactiveIconResource(R.drawable.list).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);

        setDefaultFragment();
        return view;
    }

    @Override
    public void onTabSelected(int position) {

        //FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        FragmentTransaction transaction;

        switch (position) {
            case 0:
                transaction = getChildFragmentManager().beginTransaction();//    getActivity().getFragmentManager().beginTransaction();
                DayFragment dayFragment = mDayFragment.newInstance(getString(R.string.today));
                transaction.replace(R.id.sub_content, dayFragment).commit();
                ((MainActivity)getActivity()).setVisibleFloatingMenuControls(View.GONE);
                break;
            case 1:
                transaction = getChildFragmentManager().beginTransaction();//    getActivity().getFragmentManager().beginTransaction();
                WeekFragment weekFragment = mWeekFragment.newInstance(getString(R.string.week));
                transaction.replace(R.id.sub_content, weekFragment).commit();
                ((MainActivity)getActivity()).setVisibleFloatingMenuControls(View.GONE);
                break;
            case 2:
                transaction = getChildFragmentManager().beginTransaction();//    getActivity().getFragmentManager().beginTransaction();
                MonthFragment monthFragment = mMonthFragment.newInstance(getString(R.string.month));
                transaction.replace(R.id.sub_content, monthFragment).commit();
                ((MainActivity)getActivity()).setVisibleFloatingMenuControls(View.GONE);
                break;
            case 3:
                transaction = getChildFragmentManager().beginTransaction();//    getActivity().getFragmentManager().beginTransaction();
                ListMeetingsFragment listMeetingsFragment = mlistMeetingsFragment.newInstance(getString(R.string.listmeetings));
                //transaction.replace(R.id.sub_content, listMeetingsFragment).commit();
                transaction.addToBackStack("ListMeetingsFragment").replace(R.id.sub_content,new ListMeetingsFragment()).commit();
                //((MainActivity)getActivity()).setVisibleFloatingMenuControls(View.GONE);
                break;

        }
        //beginTransaction.commit();

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();//    getActivity().getFragmentManager().beginTransaction();
        DayFragment dayFragment = mDayFragment.newInstance(getString(R.string.today));
        transaction.replace(R.id.sub_content, dayFragment).commit();
        ((MainActivity)getActivity()).setVisibleFloatingMenuControls(View.GONE);

    }
}
