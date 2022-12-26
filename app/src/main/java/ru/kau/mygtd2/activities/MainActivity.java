package ru.kau.mygtd2.activities;

//import android.support.v4.app.FragmentActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

//import ru.kau.mygtd2.BuildConfig;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.fragments.MainFragment;
import ru.kau.mygtd2.utils.Dips;
import ru.kau.mygtd2.utils.LOG;

//import com.getbase.floatingactionbutton.FloatingActionsMenu;

//import com.getbase.floatingactionbutton.FloatingActionButton;
//import com.getbase.floatingactionbutton.FloatingActionsMenu;

//import com.getbase.floatingactionbutton.FloatingActionsMenu;

//import com.getbase.floatingactionbutton.FloatingActionButton;

//import android.support.v7.widget.Toolbar;

//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;

//import android.support.v4.app.Fragment;
//import android.support.v4.widget.DrawerLayout;

//import android.support.annotation.NonNull;
//import android.support.design.widget.NavigationView;

//import android.util.Log;
//import ru.kau.mygtd.common.MyApplication;
//import ru.kau.mygtd.objects.Project;

// https://developer.android.com/training/data-storage/room/referencing-data
// https://stackoverrun.com/ru/q/7684648

public class MainActivity extends AppCompatActivity {//implements NavigationView.OnNavigationItemSelectedListener{

    //Fragment fragment;

    /*public FloatingActionButton getFab() {
        return fab;
    }*/

    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    FloatingActionButton fab4;

    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    Animation show_fab_4;
    Animation hide_fab_4;

    private boolean FAB_Status = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //LOG.d("BuildConfig.BUILD_TYPE", BuildConfig.BUILD_TYPE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //FloatingActionsMenu menuMultipleActions;
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        /*switch (menuItem.getItemId()){
                            case R.id.drawer_item_settings:
                                //Log.e("ОШИБКА", menuItem.getTitle().toString() + "  " + menuItem.getItemId());
                                return true;
                            default:
                                return false;
                        }*/

                        //Toast.makeText(getApplicationContext(), menuItem.getTitle().toString(), Toast.LENGTH_LONG);
                        //Log.e("ОШИБКА", menuItem.getTitle().toString() + "  " + menuItem.getItemId());
                        //selectDrawerItem(menuItem);
                        return false;
                    }
                });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //toggle.
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().addToBackStack("MainFragment").replace(R.id.frame_container,new MainFragment()).commit();


        /*show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);
        show_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_show);
        hide_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_hide);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab_4);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);
        //fab1.setTooltipText("ТЕСТ");
        //fab1.

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    //expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    //hideFAB();
                    FAB_Status = false;
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.INVISIBLE);
                //hideFAB();
                getSupportFragmentManager().beginTransaction().addToBackStack("AddInformationFragment").replace(R.id.frame_container,new AddInformationFragment()).commit();
                //Toast.makeText(getApplication(), "Floating Action Button 1", Toast.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.INVISIBLE);
                //hideFAB();
                getSupportFragmentManager().beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,new AddTaskFragment()).commit();
                //Toast.makeText(getApplication(), "Floating Action Button 2", Toast.LENGTH_SHORT).show();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.INVISIBLE);
                //hideFAB();
                getSupportFragmentManager().beginTransaction().addToBackStack("AddMeetingFragment").replace(R.id.frame_container,new AddMeetingFragment()).commit();
                //Toast.makeText(getApplication(), "Floating Action Button 3", Toast.LENGTH_SHORT).show();
            }
        });


        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.INVISIBLE);
                //hideFAB();
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("project", new Project());
                AddProjectFragment addProjectFragment = new AddProjectFragment();
                //addProjectFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().addToBackStack("AddProjectFragment").replace(R.id.frame_container,addProjectFragment).commit();
            }
        });*/

        /*
        menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        final FloatingActionButton addtask = (FloatingActionButton) findViewById(R.id.add_task);
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionA.setTitle("Action A clicked");
                setVisibleFloatingMenuControls(View.GONE);
                getSupportFragmentManager().beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,new AddTaskFragment()).commit();
            }
        });
*/
/*
        final FloatingActionButton addmeeting = (FloatingActionButton) findViewById(R.id.addmeet);
        addmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionA.setTitle("Action A clicked");
                setVisibleFloatingMenuControls(View.GONE);
                getSupportFragmentManager().beginTransaction().addToBackStack("AddMeetingFragment").replace(R.id.frame_container,new AddInformationFragment()).commit();
            }
        });
*/
/*
        final FloatingActionButton addinfo = (FloatingActionButton) findViewById(R.id.add_info);
        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actionA.setTitle("Action A clicked");
                setVisibleFloatingMenuControls(View.GONE);
                getSupportFragmentManager().beginTransaction().addToBackStack("AddInformationFragment").replace(R.id.frame_container,new AddInformationFragment()).commit();
            }
        });
*/


        //FloatingActionButton button = (FloatingActionButton) findViewById(R.id.setter);

        //getSupportFragmentManager().beginTransaction().addToBackStack("MainFragment").replace(R.id.frame_container,new MainFragment()).commit();

        /*
        Project project = new Project();
        project.setParentid(0);
        project.setTitle("Входящие");
        project.setSearchtitle(project.getTitle().toUpperCase());
        project.setDescription("99999");
        MyApplication.getDatabase().projectDao().insert(project);
        List<Project> lstproject = MyApplication.getDatabase().projectDao().getAll();
        Log.e("8888", "99999");
        */



    }

    public void setVisibleFloatingMenuControls(int visible){
        //menuMultipleActions.setVisibility(visible);
    }




    public void onFinishActivity() {
        finish();
    }


    Runnable onFinish = new Runnable() {

        @Override
        public void run() {
            onFinishActivity();
        }
    };

    Runnable closeActivityRunnable = new Runnable() {

        @Override
        public void run() {
            showInterstial();
        }
    };


    @Override
    public void onBackPressed() {

        //CloseAppDialog.show(this, closeActivityRunnable);

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            //finish();

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            LinearLayout l = new LinearLayout(this);
            l.setPadding(Dips.DP_5, Dips.DP_5, Dips.DP_5, Dips.DP_5);
            l.setOrientation(LinearLayout.VERTICAL);
            builder.setPositiveButton("Выход",  new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    // Delete Operation
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    //finish();
                }
            });

            builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(final DialogInterface dialog, final int id) {
                }
            });
            AlertDialog create = builder.create();
            create.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                /*if (ondissmiss != null) {
                    ondissmiss.run();
                }*/
                    //Keyboards.hideNavigation(c);
                }
            });

            create.show();

        }




        /*MaterialAlertDialogBuilder mDialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Выход из приложения")
                .setMessage("Вы уверены, что хотите выйти?")
                .setCancelable(false)
                .setPositiveButton("Выход",  new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Delete Operation
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                        //finish();
                    }
                })
                .setNegativeButton("Отмена", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();


        // Show Dialog
        mDialog.show();*/

    }

    public void showInterstial() {

            onFinish.run();

    }

    //private void setVisible

    //CoordinatorLayout.LayoutParams fablayoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

    /*private void expandFAB() {

        CoordinatorLayout.LayoutParams fablayoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        //layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin = (int) (fab.getHeight() + fablayoutParams.bottomMargin);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        //layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin = (int) (fab1.getHeight() + layoutParams.bottomMargin );
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        //layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin = (int) (fab2.getHeight() + layoutParams2.bottomMargin);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);

        //Floating Action Button 4

        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) fab4.getLayoutParams();
        //layoutParams4.rightMargin += (int) (fab4.getWidth() * 0.25);
        layoutParams4.bottomMargin = (int) (fab3.getHeight() + layoutParams3.bottomMargin);
        fab4.setLayoutParams(layoutParams4);
        fab4.startAnimation(show_fab_4);
        fab4.setClickable(true);


    }*/


    /*private void hideFAB() {

        CoordinatorLayout.LayoutParams fablayoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        //layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin = (int) (fablayoutParams.bottomMargin);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        //layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin = (int) (fablayoutParams.bottomMargin);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        //layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin = (int) (fablayoutParams.bottomMargin);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);

        //Floating Action Button 4

        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) fab4.getLayoutParams();
        //layoutParams4.rightMargin -= (int) (fab4.getWidth() * 0.25);
        layoutParams4.bottomMargin = (int) (fablayoutParams.bottomMargin);
        fab4.setLayoutParams(layoutParams4);
        fab4.startAnimation(hide_fab_4);
        fab4.setClickable(false);


    }*/





}
