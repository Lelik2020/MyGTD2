package ru.kau.mygtd2.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.adapters.InformationAdapter;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.info.view.MyPopupMenu;
import ru.kau.mygtd2.listeners.DefaultListeners;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.utils.AppState;


public class InformationsFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Information> lstTask;
    private InformationAdapter informationAdapter;
    private ImageView onListGrid, starIcon, onSort, starIconDir, sortOrder;
    private ActionBar toolbar;
    private View rootView;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.information_fragment, null);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Информация");
        setHasOptionsMenu(true);
        //toolbar.menu  onCreateOptionsMenu

        ImageView onSorting = (ImageView) rootView.findViewById(R.id.onSorting);

        onSort = (ImageView) rootView.findViewById(R.id.onSort);
        //sortOrder = (ImageView) rootView.findViewById(R.id.sortOrder);

        /*sortOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("ERROR999", "ERROR999");
                AppState.get().sortByReverse = !AppState.get().sortByReverse;
                onSort.setImageResource(AppState.get().sortByReverse ? R.drawable.glyphicons_410_sort_by_attributes_alt : R.drawable.glyphicons_409_sort_by_attributes);
                sortOrder.setImageResource(AppState.get().sortByReverse ? R.drawable.glyphicons_601_chevron_up : R.drawable.glyphicons_602_chevron_down);

                //populate();

            }
        });*/

        /*onSort.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                AppState.get().isVisibleSorting = !AppState.get().isVisibleSorting;
                sortOrder.setVisibility(TxtUtils.visibleIf(AppState.get().isVisibleSorting));
                return true;
            }
        });*/

        //onSort.setImageResource(AppState.get().sortByReverse ? R.drawable.glyphicons_410_sort_by_attributes_alt : R.drawable.glyphicons_409_sort_by_attributes);
        //sortOrder.setImageResource(AppState.get().sortByReverse ? R.drawable.glyphicons_601_chevron_up : R.drawable.glyphicons_602_chevron_down);

        //Log.e("ERROR111", "ERROR111");

        onSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ERROR777", "ERROR777");
            }
        });



        recyclerView = (RecyclerView) rootView.findViewById(R.id.information_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Log.e("ПОЗИЦИЯ", "ПОЗИЦИЯ");
        informationAdapter = new InformationAdapter(getActivity(), MyApplication.getDatabase().informationDao().getAll());
        //informationAdapter.setOnMenuClickListener(getOnMenuClick(a, searchAdapter));
        bindAdapter(informationAdapter);
        /*
        informationAdapter.setOnItemClickListener(new ResultResponse<Information>() {
                                                      @Override
                                                      public boolean onResultRecive(Information result) {
                                                          DefaultListeners.getOnItemClickListener(getActivity()).onResultRecive(result);
                                                          return false;
                                                      }
                                                  });
        */
        recyclerView.setAdapter(informationAdapter);
        onSort.setClickable(true);
        onSort.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.e("ERROR", "ERROR");

                List<String> names = Arrays.asList(//
                        getActivity().getString(R.string.by_file_name), //
                        getActivity().getString(R.string.by_date), //
                        getActivity().getString(R.string.by_size), //
                        getActivity().getString(R.string.by_title), //
                        getActivity().getString(R.string.by_number_in_serie), //
                        getActivity().getString(R.string.by_number_of_pages), //
                        getActivity().getString(R.string.by_extension) //
                );//

                final List<Integer> ids = Arrays.asList(//
                        AppState.BR_SORT_BY_PATH, //
                        AppState.BR_SORT_BY_DATE, //
                        AppState.BR_SORT_BY_SIZE, //
                        AppState.BR_SORT_BY_TITLE, //
                        AppState.BR_SORT_BY_NUMBER, //
                        AppState.BR_SORT_BY_PAGES, //
                        AppState.BR_SORT_BY_EXT//
                );//

                MyPopupMenu menu = new MyPopupMenu(getActivity(), v);
                for (int i = 0; i < names.size(); i++) {
                    String name = names.get(i);
                    final int j = i;
                    menu.getMenu().add(name).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AppState.get().sortByBrowse = ids.get(j);
                            //populate();
                            return false;
                        }
                    });
                }
                menu.show();
            }
        });


        return rootView;
    }

    public void bindAdapter(InformationAdapter informationAdapter) {
        DefaultListeners.bindAdapter(getActivity(), informationAdapter);
    }

    public void onSortClick(View v){
        Log.e("ERROR", "ERROR");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_sort) {
            //Log.e("ERROR222", "ERROR222");
            List<String> names = Arrays.asList(//
                    getActivity().getString(R.string.by_title), //
                    getActivity().getString(R.string.by_date), //
                    getActivity().getString(R.string.by_status) //
            );//

            final List<Integer> ids = Arrays.asList(//
                    AppState.BR_SORT_BY_TITLE, //
                    AppState.BR_SORT_BY_DATE, //
                    AppState.BR_SORT_BY_STATUS //
            );//

            //MyPopupMenu menu = new MyPopupMenu(getActivity(), ((ImageView) toolbar.findViewById(R.id.onSort)));
            MyPopupMenu menu = new MyPopupMenu(getActivity(), onSort);
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                final int j = i;
                menu.getMenu().add(name).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //AppState.get().sortByBrowse = ids.get(j);
                        //populate();
                        // Сортируем в зависимости от выбранного пункта меню

                        return false;
                    }
                });
            }
            menu.show();
            //menu.show(1, false);
        }
        return true;
    }

}
