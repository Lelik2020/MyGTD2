package ru.kau.mygtd2.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apg.mobile.roundtextview.RoundTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import ru.kau.mygtd2.R;
import ru.kau.mygtd2.activities.MainActivity;
import ru.kau.mygtd2.adapters.TasksAdapter3;
import ru.kau.mygtd2.common.MyApplication;
import ru.kau.mygtd2.db.dao.TaskDaoAbs;
import ru.kau.mygtd2.fragments.AddInformationFragment;
import ru.kau.mygtd2.fragments.AddTaskFragment;
import ru.kau.mygtd2.objects.Contekst;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.Tag;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.utils.Const;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.Utils;
import ru.kau.mygtd2.utils.info.wrapper.DocumentController;

import static ru.kau.mygtd2.utils.Const.DEFAULT_RTV_HEIGHT;
import static ru.kau.mygtd2.utils.Const.DEFAULT_RTV_WIDTH;
import static ru.kau.mygtd2.utils.Utils.dateToString;

public class ShareDialog {

    static AlertDialog infoDialog;

    public static void show(final Activity a, final Runnable onDeleteAction, final Information information) {
        List<String> items = new ArrayList<String>();
        //Log.e("333333333333", "44444444444");
        items.add(a.getString(R.string.editinfo));
        items.add(a.getString(R.string.add_task));
        items.add(a.getString(R.string.to_archive));
        //items.add(a.getString(R.string.export_bookmarks));
        final AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                //int i = 0;
                //Log.e("8888888888", Integer.toString(which));
                switch (which){
                    case 0:
                        AddInformationFragment addInformationFragment = new AddInformationFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("information", information);
                        addInformationFragment.setArguments(bundle);
                        FragmentManager fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("addInformationFragment").replace(R.id.frame_container,addInformationFragment).commit();
                        return;
                    case 1:
                        AddTaskFragment addTaskFragment = new AddTaskFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("information", information);
                        addTaskFragment.setArguments(bundle);
                        fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,addTaskFragment).commit();
                        return;

                    case 2:
                        information.setIdstatus(3);
                        try{
                            MyApplication.getDatabase().informationDao().update(information);
                            //Toasty.error(a, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
                            Toasty.success(a, a.getString(R.string.infotoarchive), Toast.LENGTH_LONG, true).show();

                            //ViewUtils.viewPositiveToast(a, a.getLayoutInflater(), String.valueOf(R.string.infotoarchive), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);
                        } catch (Exception e) {
                            Toasty.error(a, a.getString(R.string.infoupdatederror), Toast.LENGTH_LONG, true).show();
                            //ViewUtils.viewNegativeToast(a, a.getLayoutInflater(), String.valueOf(R.string.infoupdatederror), Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 0);

                        }
                        return;

                }

            }
        });
        AlertDialog create = builder.create();
        /*
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Keyboards.hideNavigation(a);
            }
        });
        */
        create.show();
    }

    public static void show2(final Activity a, final Runnable onDeleteAction, final Task task) {
        List<String> items = new ArrayList<String>();
        //Log.e("333333333333", "44444444444");

        items.add(a.getString(R.string.addsubtask));
        items.add(a.getString(R.string.clonetask));
        items.add(a.getString(R.string.clonetasktomorrow));
        items.add(a.getString(R.string.logworking));
        items.add(a.getString(R.string.add_comment));

        final AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {

                switch (which){
                    case 0:
                        AddTaskFragment addTaskFragment = new AddTaskFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("subtask", task);
                        addTaskFragment.setArguments(bundle);
                        FragmentManager fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,addTaskFragment).commit();
                        break;
                    case 1:
                        addTaskFragment = new AddTaskFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("clonetask", task);
                        addTaskFragment.setArguments(bundle);
                        fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,addTaskFragment).commit();
                        break;

                    case 2:
                        Utils.getCloneTaskTomorrow(task);
                        Toasty.success(MyApplication.getContext(), MyApplication.getContext().getString(R.string.taskcreated), Toast.LENGTH_SHORT, true).show();
                        break;

                    case 4:
                        Dialogs.addCommentDialog(a, null, task, null);
                }

            }
        });
        AlertDialog create = builder.create();

        create.show();

    }

    /*public static void show3(final Activity a, final Runnable onDeleteAction, final Project project) {
        List<String> items = new ArrayList<String>();
        //Log.e("333333333333", "44444444444");
        items.add(a.getString(R.string.addproject));
        items.add(a.getString(R.string.editproject));
        items.add(a.getString(R.string.add_task));

        final AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {

                if (which == 0){

                }

                switch (which){

                    case 0:
                        ((MainActivity)a).getFab().setVisibility(View.INVISIBLE);
                        AddProjectFragment addProjectFragment = new AddProjectFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("project", project);
                        addProjectFragment.setArguments(bundle);
                        FragmentManager fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("AddProjectFragment").replace(R.id.frame_container,addProjectFragment).commit();
                        break;


                    case 1:
                        ((MainActivity)a).getFab().setVisibility(View.INVISIBLE);
                        addProjectFragment = new AddProjectFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("project", project);
                        addProjectFragment.setArguments(bundle);
                        fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("AddProjectFragment").replace(R.id.frame_container,addProjectFragment).commit();
                        return;

                    case 2:
                        ((MainActivity)a).getFab().setVisibility(View.INVISIBLE);
                        AddTaskFragment addTaskFragment = new AddTaskFragment();
                        bundle = new Bundle();
                        bundle.putSerializable("project", project);
                        addTaskFragment.setArguments(bundle);
                        fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,addTaskFragment).commit();
                        return;


                }

            }
        });
        AlertDialog create = builder.create();

        create.show();

    }*/

    /*public static void show(final Activity a, final File file, final Runnable onDeleteAction, final int page, final DocumentController dc, final Runnable hideShow) {
        if (file == null) {
            Toast.makeText(a, R.string.file_not_found, Toast.LENGTH_LONG).show();
            return;
        }

        if (!ExtUtils.isExteralSD(file.getPath()) && ExtUtils.isNotValidFile(file)) {
            Toast.makeText(a, R.string.file_not_found, Toast.LENGTH_LONG).show();
            return;
        }
        final boolean isPDF = BookType.PDF.is(file.getPath());
        final boolean isLibrary = false;// a instanceof MainTabs2 ? false :
        // true;
        //final boolean isMainTabs = a instanceof MainTabs2;

        List<String> items = new ArrayList<String>();

        if (isLibrary) {
            items.add(a.getString(R.string.library));
        }

        if (dc != null) {
            if (a instanceof VerticalViewActivity || dc.isMusicianMode()) {
                items.add(AppState.get().nameHorizontalMode);
            }
            if (a instanceof HorizontalViewActivity || dc.isMusicianMode()) {
                items.add(AppState.get().nameVerticalMode);
            }

            if (dc.isMusicianMode() == false) {
                items.add(AppState.get().nameMusicianMode);
            }
        }

        if (isPDF) {
            items.add(a.getString(R.string.make_text_reflow));
        }

        if (dc != null) {
            items.add(a.getString(R.string.fast_reading));
        }

        items.add(a.getString(R.string.open_with));
        items.add(a.getString(R.string.send_file));
        final boolean isExternalOrCloud = ExtUtils.isExteralSD(file.getPath()) || Clouds.isCloud(file.getPath());
        boolean canDelete1 = ExtUtils.isExteralSD(file.getPath()) || Clouds.isCloud(file.getPath()) ? true : file.canWrite();
        final boolean canCopy = !ExtUtils.isExteralSD(file.getPath()) && !Clouds.isCloud(file.getPath());
        final boolean isShowInfo = !ExtUtils.isExteralSD(file.getPath());

        final boolean isRemovedFromLibrary = AppData.get().getAllExcluded().contains(new SimpleMeta(file.getPath()));

        if (file.getPath().contains(AppProfile.PROFILE_PREFIX)) {
            canDelete1 = false;
        }
        final boolean canDelete = canDelete1;

        if (isMainTabs) {
            if (canDelete) {
                items.add(a.getString(R.string.delete));
            }
            if (canCopy) {
                items.add(a.getString(R.string.copy));
            }
            if (!isRemovedFromLibrary) {
                items.add(a.getString(R.string.remove_from_library));
            }
        }

        if (!isExternalOrCloud) {
            items.add(a.getString(R.string.add_tags));
        }

        if (AppsConfig.isCloudsEnable) {
            items.add(a.getString(R.string.upload_to_cloud));
        }
        final boolean isPlaylist = file.getName().endsWith(Playlists.L_PLAYLIST);
        if (!isPlaylist) {
            items.add(a.getString(R.string.add_to_playlist));
        }

        final boolean isSyncronized = Clouds.isLibreraSyncFile(file);
        if (!isSyncronized) {
            items.add(a.getString(R.string.sync_book));
        }

        if(isMainTabs){
            items.add(a.getString(R.string.delete_reading_progress));
        }

        if (isShowInfo) {
            items.add(a.getString(R.string.file_info));
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                int i = 0;

                if (isLibrary && which == i++) {
                    a.finish();
                    MainTabs2.startActivity(a, UITab.getCurrentTabIndex(UITab.SearchFragment));
                }

                if (dc != null && (a instanceof HorizontalViewActivity || dc.isMusicianMode()) && which == i++) {
                    dc.onCloseActivityFinal(new Runnable() {

                        @Override
                        public void run() {
                            if (dc.isMusicianMode()) {
                                AppSP.get().readingMode = AppState.READING_MODE_BOOK;
                            } else {
                                AppSP.get().readingMode = AppState.READING_MODE_SCROLL;
                            }
                            ExtUtils.showDocumentWithoutDialog(a, file, a.getIntent().getStringExtra(DocumentController.EXTRA_PLAYLIST));

                        }
                    });

                }
                if (dc != null && (a instanceof VerticalViewActivity || dc.isMusicianMode()) && which == i++) {
                    if (dc != null) {
                        dc.onCloseActivityFinal(new Runnable() {

                            @Override
                            public void run() {
                                if (dc.isMusicianMode()) {
                                    AppSP.get().readingMode = AppState.READING_MODE_SCROLL;
                                } else {
                                    AppSP.get().readingMode = AppState.READING_MODE_BOOK;
                                }
                                ExtUtils.showDocumentWithoutDialog(a, file, a.getIntent().getStringExtra(DocumentController.EXTRA_PLAYLIST));
                            }
                        });
                    }
                }
                if (dc != null && dc.isMusicianMode() == false && which == i++) {
                    dc.onCloseActivityFinal(new Runnable() {

                        @Override
                        public void run() {
                            AppSP.get().readingMode = AppState.READING_MODE_MUSICIAN;
                            ExtUtils.showDocumentWithoutDialog(a, file, a.getIntent().getStringExtra(DocumentController.EXTRA_PLAYLIST));
                        }
                    });
                }
                if (isPDF && which == i++) {
                    ExtUtils.openPDFInTextReflow(a, file, page + 1, dc);
                }
                if (dc != null && which == i++) {
                    if (hideShow != null) {
                        AppState.get().isEditMode = false;
                        hideShow.run();
                    }
                    DialogSpeedRead.show(a, dc);
                } else if (which == i++) {
                    ExtUtils.openWith(a, file);
                } else if (which == i++) {
                    ExtUtils.sendFileTo(a, file);
                } else if (isMainTabs && canDelete && which == i++) {
                    FileInformationDialog.dialogDelete(a, file, onDeleteAction);
                } else if (isMainTabs && canCopy && which == i++) {
                    TempHolder.get().copyFromPath = file.getPath();
                    Toast.makeText(a, R.string.copy, Toast.LENGTH_SHORT).show();
                } else if (isMainTabs && !isRemovedFromLibrary && which == i++) {
                    FileMeta load = AppDB.get().load(file.getPath());
                    if (load != null) {
                        load.setIsSearchBook(false);
                        load.setIsStar(false);
                        load.setTag(null);
                        AppDB.get().update(load);

                        AppData.get().removeFavorite(load);
                        AppData.get().addExclue(load.getPath());

                    }


                    EventBus.getDefault().post(new UpdateAllFragments());
                } else if (!isExternalOrCloud && which == i++) {
                    Dialogs.showTagsDialog(a, file, false, null);
                } else if (AppsConfig.isCloudsEnable && which == i++) {
                    showAddToCloudDialog(a, file);
                } else if (!isPlaylist && which == i++) {
                    DialogsPlaylist.showPlaylistsDialog(a, null, file);
                } else if (!isSyncronized && which == i++) {
                    final File to = new File(AppProfile.SYNC_FOLDER_BOOKS, file.getName());
                    boolean result = IO.copyFile(file, to);
                    if (result && AppSP.get().isEnableSync) {

                        AppDB.get().setIsSearchBook(file.getPath(), false);
                        FileMetaCore.createMetaIfNeed(to.getPath(), true);

                        String tags = TagData.getTags(file.getPath());
                        TagData.saveTags(to.getPath(), tags);

                        boolean isRecent = AppData.contains(AppData.get().getAllRecent(), file.getPath());
                        LOG.d("isRecent", isRecent, file.getPath());

                        if (isRecent) {
                            AppData.get().removeRecent(new FileMeta(file.getPath()));

                            final FileMeta load = AppDB.get().load(file.getPath());
                            if (load != null && load.getIsRecentTime() != null) {
                                AppData.get().addRecent(new SimpleMeta(to.getPath(), load.getIsRecentTime()));
                            } else {
                                AppData.get().addRecent(new SimpleMeta(to.getPath()));
                            }
                        }

                        final List<AppBookmark> bookmarks = BookmarksData.get().getBookmarksByBook(file.getPath());
                        for (AppBookmark appBookmark : bookmarks) {
                            appBookmark.path = MyPath.toRelative(to.getPath());
                            BookmarksData.get().add(appBookmark);
                        }

                        GFile.runSyncService(a);
                    }


                    TempHolder.listHash++;
                    EventBus.getDefault().post(new UpdateAllFragments());

                } else if (isMainTabs && which == i++) {
                    SharedBooks.deleteProgress(file.getPath());
                    EventBus.getDefault().post(new UpdateAllFragments());

                } else if (isShowInfo && which == i++) {
                    FileInformationDialog.showFileInfoDialog(a, file, onDeleteAction);
                }

            }

        });
        AlertDialog create = builder.create();
        create.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Keyboards.hideNavigation(a);
            }

        });
        create.show();
//        MyPopupMenu menu = new MyPopupMenu(a, null);
//
//        menu.getMenu(R.drawable.glyphicons_basic_578_share, R.string.share, () -> ExtUtils.openPDFInTextReflow(a, file, page + 1, dc));
//        menu.getMenu(R.drawable.glyphicons_2_book_open, R.string.open_with, () -> ExtUtils.openPDFInTextReflow(a, file, page + 1, dc));
//
//        menu.show();
    }*/

    //public static void show(final Activity a, final Runnable onDeleteAction, final Information information) {
    public static void show(final Activity a, final File file, final Runnable onDeleteAction, final int page, final DocumentController dc, final Runnable hideShow) {
        List<String> items = new ArrayList<String>();
        //Log.e("333333333333", "44444444444");
        items.add(a.getString(R.string.add_task));
        items.add(a.getString(R.string.to_archive));
        //items.add(a.getString(R.string.export_bookmarks));
        final AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                //int i = 0;
                //Log.e("8888888888", Integer.toString(which));
                switch (which){
                    case 0:
                        /*AddTaskFragment addTaskFragment = new AddTaskFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("information", information);
                        addTaskFragment.setArguments(bundle);
                        FragmentManager fragmentManager = ((MainActivity)a).getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("AddTaskFragment").replace(R.id.frame_container,addTaskFragment).commit();*/


                }

            }
        });
        AlertDialog create = builder.create();
        /*
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Keyboards.hideNavigation(a);
            }
        });
        */
        create.show();
    }

    @SuppressLint("ResourceAsColor")
    public void showTaskInfoDialog(final AppCompatActivity a, final Task task){
        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setTitle(R.string.taskdetails);
        final View dialog = LayoutInflater.from(a).inflate(R.layout.dialog_taskinfo, null, false);
        TextView tasktitle = (TextView) dialog.findViewById(R.id.tasktitle);
        TextView tasktitle2 = (TextView) dialog.findViewById(R.id.tasktitle2);
        TextView taskdetail = (TextView) dialog.findViewById(R.id.taskdetail);
        ImageView typeTask = (ImageView) dialog.findViewById(R.id.tasktype);
        typeTask.setImageResource(Utils.getImageResourceTaskType(task.getTypeOfTask()));
        tasktitle2.setText(task.getTitle());
        taskdetail.setText(task.getDescription());
        tasktitle.setText(task.getTypeOfTask().name() + "-" + task.getId());
        TxtUtils.underlineTextView(tasktitle);

        builder.setView(dialog);

        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //ExtUtils.showDocument(a, file);
                //radioGroup.get

            }
        });

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.parentTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(a));

        List<Task> lstParentTask = TaskDaoAbs.getParentTasks(task);
        TasksAdapter3 tasksAdapterall = new TasksAdapter3(a, lstParentTask);
        recyclerView.setAdapter(tasksAdapterall);

        recyclerView = (RecyclerView) dialog.findViewById(R.id.childTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(a));
        TaskDaoAbs.lstReturn = null;
        List<Task> lstChildTask = TaskDaoAbs.getAllChildTasks(task);
        tasksAdapterall = new TasksAdapter3(a, lstChildTask);
        recyclerView.setAdapter(tasksAdapterall);

        Project project = MyApplication.getDatabase().projectDao().getProjectById(task.getProject_id());
        LinearLayoutCompat taskproject = (LinearLayoutCompat) dialog.findViewById(R.id.taskproject);
        taskproject.removeAllViews();
        LinearLayoutCompat.LayoutParams lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        taskproject.setLayoutParams(lParams);
        /*ImageView iv = new ImageView(a);
        iv.setImageResource(R.drawable.folder);
        //iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        iv.setLayoutParams(lParams);*/

        //LinearLayoutCompat.LayoutParams


        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        ImageView iv = new ImageView(a);
        iv.setImageResource(R.drawable.folder);
        iv.setLayoutParams(lParams);
        RoundTextView rtv1 = new RoundTextView(a);

        if (project != null) {
            taskproject.addView(iv);

            /*lParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/


            //rtv1.setText(lstTask.get(0).getTitle());

            rtv1.setCorner(20);
            rtv1.setPadding(10, 0, 10, 0);
            //rtv1.setCorner(5, 5, 5, 5);
            //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
            rtv1.setTextColor(R.color.black);
            rtv1.setTextSize(16);
            rtv1.setTypeface(Typeface.DEFAULT_BOLD);

            rtv1.setText(project.getTitle());


        //lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        //rtv1.setLayoutParams(lParams);

            taskproject.addView(rtv1);
        }


        LinearLayoutCompat taskpriority = (LinearLayoutCompat) dialog.findViewById(R.id.taskpriority);
        taskpriority.removeAllViews();
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        taskpriority.setLayoutParams(lParams);
        /*ImageView iv = new ImageView(a);
        iv.setImageResource(R.drawable.folder);
        //iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        iv.setLayoutParams(lParams);*/

        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
        iv = new ImageView(a);
        //iv.setImageResource(R.drawable.priority4);
        iv.setImageResource(Utils.getIconForPriority(MyApplication.getDatabase().priorityDao().getById(task.getPriority_id())));
        //int color = Color.parseColor(MyApplication.getDatabase().priorityDao().getById(task.getPriority_id()).getColor());
        //iv.setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
        //iv.setColorFilter(color);
        iv.setLayoutParams(lParams);

        taskpriority.addView(iv);

            /*lParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/

        rtv1 = new RoundTextView(a);
        //rtv1.setText(lstTask.get(0).getTitle());

        rtv1.setCorner(20);
        rtv1.setPadding(10, 0, 10, 0);
        //rtv1.setCorner(5, 5, 5, 5);
        //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
        rtv1.setTextColor(R.color.black);
        rtv1.setTextSize(16);
        rtv1.setTypeface(Typeface.DEFAULT_BOLD);

        rtv1.setText(MyApplication.getDatabase().priorityDao().getById(task.getPriority_id()).getTitle());

        //lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        //rtv1.setLayoutParams(lParams);

        taskpriority.addView(rtv1);

        //taskpriority


        LinearLayoutCompat taskDateBegin = (LinearLayoutCompat) dialog.findViewById(R.id.taskDateBegin);

        rtv1 = new RoundTextView(a);
        //rtv1.setText(lstTask.get(0).getTitle());

        rtv1.setCorner(20);
        rtv1.setPadding(10, 0, 10, 0);
        //rtv1.setCorner(5, 5, 5, 5);
        //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
        rtv1.setTextColor(R.color.black);
        rtv1.setTextSize(16);
        rtv1.setTypeface(Typeface.DEFAULT_BOLD);

        rtv1.setText(dateToString(task.getDateBegin()));

        //lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        //rtv1.setLayoutParams(lParams);

        taskDateBegin.addView(rtv1);

        LinearLayoutCompat taskDateEnd = (LinearLayoutCompat) dialog.findViewById(R.id.taskDateEnd);

        rtv1 = new RoundTextView(a);
        //rtv1.setText(lstTask.get(0).getTitle());

        rtv1.setCorner(20);
        rtv1.setPadding(10, 0, 10, 0);
        //rtv1.setCorner(5, 5, 5, 5);
        //rtv1.setBgColor(Color.parseColor(tags.get(j).getColor()));
        rtv1.setTextColor(R.color.black);
        rtv1.setTextSize(16);
        rtv1.setTypeface(Typeface.DEFAULT_BOLD);

        rtv1.setText(dateToString(task.getDateEnd()));

        //lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        //rtv1.setLayoutParams(lParams);

        taskDateEnd.addView(rtv1);

        LinearLayoutCompat taskTags = (LinearLayoutCompat) dialog.findViewById(R.id.taskTags);



        taskTags.removeAllViews();
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        taskTags.setLayoutParams(lParams);

        List<Tag> tags = MyApplication.getDatabase().tagDao().getAllByTask(task.getId());
        List<Tag> lsttags;
        if (tags != null) {
            lsttags = new ArrayList<Tag>();
            lsttags.addAll(tags);
            if (tags.size() > 0) {

                //LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(Const.LAYOUT_DEFAULT_WIDTH, Const.LAYOUT_DEFAULT_HEIGHT);


                //iv.setMinimumWidth(25);
                //iv.setMinimumHeight(25);
                //iv.getLayoutParams().width = 25;
                //iv.getLayoutParams().height = 25;
                //iv.setMaxWidth(25);
                //iv.setMaxHeight(25);
                //iv.setla



                //ltasktags.addView(iv);



                for (int j = 0; j < tags.size(); j++) {

                    //LinearLayoutCompat.LayoutParams lParams2 = new LinearLayoutCompat.LayoutParams(Const.LAYOUT_DEFAULT_WIDTH, Const.LAYOUT_DEFAULT_HEIGHT);

                    iv = new ImageView(a);
                    iv.setImageResource(R.drawable.merchandising);
                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT);
                    iv.setLayoutParams(lParams);
                    //iv.setLayoutParams(lParams2);
                    try {
                        iv.setColorFilter(Color.parseColor(tags.get(j).getColor()));
                    } catch (Exception e){
                        iv.setColorFilter(Color.parseColor("#000000"));
                    }

                    taskTags.addView(iv);
                    rtv1 = new RoundTextView(a);
                    //rtv1.setText(lstTask.get(0).getTitle());

                    rtv1.setCorner(20);
                    rtv1.setPadding(10, 0, 10, 0);
                    //rtv1.setCorner(5, 5, 5, 5);
                    rtv1.setBgColor(Utils.parseColor(tags.get(j).getColor()));
                    rtv1.setTextColor(R.color.black);
                    rtv1.setTextSize(16);
                    rtv1.setTypeface(Typeface.DEFAULT_BOLD);

                    //rtv1.setBgColor(Color.parseColor(lstTag.get(i).getColor()));
                    //rtv1.setCorner(2, 2, 2, 2);
                    //rtv1.setCorner(5);
                    rtv1.setText(tags.get(j).getTitle());
                    /*lParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/
                    taskTags.addView(rtv1);
                }
            }
        }

        List<Contekst> conteksts = MyApplication.getDatabase().contextDao().getAllByTask(task.getId());

        LinearLayoutCompat taskTarget = (LinearLayoutCompat) dialog.findViewById(R.id.taskTarget);

        taskTarget.removeAllViews();
        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        taskTarget.setLayoutParams(lParams);


        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
        iv = new ImageView(a);

        iv.setImageResource(R.drawable.target4);

        iv.setLayoutParams(lParams);

        taskTarget.addView(iv);



        rtv1 = new RoundTextView(a);


        rtv1.setCorner(20);
        rtv1.setPadding(10, 0, 10, 0);

        rtv1.setTextColor(R.color.black);
        rtv1.setTextSize(16);
        rtv1.setTypeface(Typeface.DEFAULT_BOLD);

        if (task.getTarget_id() > 0) {
            rtv1.setText(MyApplication.getDatabase().targetDao().getById(task.getTarget_id()).getTitle());
        }

        //lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        //rtv1.setLayoutParams(lParams);

        taskTarget.addView(rtv1);


        LinearLayoutCompat taskContexts = (LinearLayoutCompat) dialog.findViewById(R.id.taskContexts);

        taskContexts.removeAllViews();

        lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);
        taskContexts.setLayoutParams(lParams);

        if (conteksts != null) {
            List<Contekst> lstConteksts = new ArrayList<Contekst>();
            lstConteksts.addAll(conteksts);
            if (conteksts.size() > 0) {

                lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_LAYOUT_WIDTH, Const.DEFAULT_LAYOUT_HEIGHT);

                //taskContexts.setLayoutParams(lParams);





                for (int j = 0; j < conteksts.size(); j++) {

                    iv = new ImageView(a);
                    iv.setImageResource(R.drawable.context2);

                    lParams = new LinearLayoutCompat.LayoutParams(Const.DEFAULT_ICON_WIDTH, Const.DEFAULT_ICON_HEIGHT2);
                    iv.setLayoutParams(lParams);
                    //iv.setla
                    lParams.setMargins(2, 0, 0, 0);

                    iv.setLayoutParams(lParams);

                    taskContexts.addView(iv);


                    rtv1 = new RoundTextView(a);
                    //rtv1.setText(lstTask.get(0).getTitle());
                    lParams = new LinearLayoutCompat.LayoutParams(
                            DEFAULT_RTV_WIDTH, DEFAULT_RTV_HEIGHT);

                    rtv1.setLayoutParams(lParams);
                    rtv1.setCorner(20);
                    rtv1.setPadding(10, 0, 10, 0);
                    //rtv1.setCorner(5, 5, 5, 5);
                    rtv1.setBgColor(Color.parseColor(conteksts.get(j).getColor()));
                    rtv1.setTextColor(R.color.black);

                    //rtv1.setBgColor(Color.parseColor(lstTag.get(i).getColor()));
                    //rtv1.setCorner(2, 2, 2, 2);
                    //rtv1.setCorner(5);
                    rtv1.setText(conteksts.get(j).getTitle());

                    taskContexts.addView(rtv1);
                }
            }
        }


        infoDialog = builder.create();
        infoDialog.show();

    }



}
