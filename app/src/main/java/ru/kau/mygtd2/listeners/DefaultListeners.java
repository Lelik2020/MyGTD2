package ru.kau.mygtd2.listeners;

import android.app.Activity;

import java.io.File;

import ru.kau.mygtd2.AppDB;
import ru.kau.mygtd2.adapters.CommentAdapter;
import ru.kau.mygtd2.adapters.FileMetaAdapter;
import ru.kau.mygtd2.adapters.InformationAdapter;
import ru.kau.mygtd2.adapters.SprProjectTreeAdapter;
import ru.kau.mygtd2.adapters.TasksAdapter2;
import ru.kau.mygtd2.adapters.TasksAdapterShort;
import ru.kau.mygtd2.dialogs.FileInformationDialog;
import ru.kau.mygtd2.dialogs.ShareDialog;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.interfaces.ResultResponse2;
import ru.kau.mygtd2.objects.Comment;
import ru.kau.mygtd2.objects.FileMeta;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.Task;
import ru.kau.mygtd2.utils.AppData;
import ru.kau.mygtd2.utils.Clouds;
import ru.kau.mygtd2.utils.ExtUtils;
import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.Playlists;
import ru.kau.mygtd2.utils.SimpleMeta;
import ru.kau.mygtd2.utils.TempHolder;

public class DefaultListeners {

    public static void bindAdapter(final Activity a, final InformationAdapter informationAdapter, final Runnable onClick) {
        informationAdapter.setOnItemClickListener(new ResultResponse<Information>() {

            @Override
            public boolean onResultRecive(final Information result) {
                onClick.run();
                /*
                dc.onCloseActivityFinal(new Runnable() {

                    @Override
                    public void run() {
                        ExtUtils.showDocumentWithoutDialog(a, new File(result.getPath()), -1);

                    }
                });
                */
                return false;
            }

        });
        //informationAdapter.setOnItemLongClickListener(getOnItemLongClickListener(a, searchAdapter));
        //Log.e("1111111111111111111111", "222222222222");
        informationAdapter.setOnMenuClickListener(getOnMenuClick(a, informationAdapter));
        //informationAdapter.setOnStarClickListener(getOnStarClick(a));
    }

    public static void bindAdapter(Activity a, InformationAdapter informationAdapter) {
        //searchAdapter.setOnItemClickListener(getOnItemClickListener(a));
        //searchAdapter.setOnItemLongClickListener(getOnItemLongClickListener(a, searchAdapter));

        informationAdapter.setOnMenuClickListener(getOnMenuClick(a, informationAdapter));
        //searchAdapter.setOnStarClickListener(getOnStarClick(a));
        //Log.e("555555555", "44444444444");
    }
    public static void bindAdapter2(Activity a, TasksAdapterShort tasksAdapter) {
        //searchAdapter.setOnItemClickListener(getOnItemClickListener(a));
        //searchAdapter.setOnItemLongClickListener(getOnItemLongClickListener(a, searchAdapter));

        tasksAdapter.setOnMenuClickListener(getOnMenuClick2(a, tasksAdapter));
        //searchAdapter.setOnStarClickListener(getOnStarClick(a));
        //Log.e("555555555", "44444444444");
    }

    public static void bindAdapter2(Activity a, TasksAdapter2 tasksAdapter) {
        //searchAdapter.setOnItemClickListener(getOnItemClickListener(a));
        //searchAdapter.setOnItemLongClickListener(getOnItemLongClickListener(a, searchAdapter));

        tasksAdapter.setOnMenuClickListener(getOnMenuClick2(a, tasksAdapter));
        //searchAdapter.setOnStarClickListener(getOnStarClick(a));
        //Log.e("555555555", "44444444444");
    }

    public static void bindAdapter3(Activity a, SprProjectTreeAdapter projectAdapter) {
        //searchAdapter.setOnItemClickListener(getOnItemClickListener(a));
        //searchAdapter.setOnItemLongClickListener(getOnItemLongClickListener(a, searchAdapter));

        projectAdapter.setOnMenuClickListener(getOnMenuClick(a, projectAdapter));
        //searchAdapter.setOnStarClickListener(getOnStarClick(a));
        //Log.e("555555555", "44444444444");
    }

    public static void bindAdapter4(Activity a, CommentAdapter commentAdapter) {


        commentAdapter.setOnMenuClickListener(getOnMenuClick(a, commentAdapter));

    }

    public static void bindAdapter5(Activity a, TasksAdapter2 tasksAdapter) {
        //searchAdapter.setOnItemClickListener(getOnItemClickListener(a));
        //searchAdapter.setOnItemLongClickListener(getOnItemLongClickListener(a, searchAdapter));

        tasksAdapter.setOnMenuClickListener(getOnMenuClick3(a, tasksAdapter));
        //searchAdapter.setOnStarClickListener(getOnStarClick(a));
        //Log.e("555555555", "44444444444");
    }

    public static void bindAdapter7(final Activity a, final FileMetaAdapter searchAdapter) {
        searchAdapter.setOnItemClickListener(getOnItemClickListener7(a));
        searchAdapter.setOnItemLongClickListener(getOnItemLongClickListener(a, searchAdapter));
        searchAdapter.setOnMenuClickListener(getOnMenuClick(a, searchAdapter));
        searchAdapter.setOnStarClickListener(getOnStarClick(a));
        searchAdapter.setOnTagClickListner(new ResultResponse<String>() {

            @Override
            public boolean onResultRecive(String result) {
                showBooksByTag(a, result);
                return false;
            }

        });
    }

    public static ResultResponse<FileMeta> getOnMenuClick(final Activity a, final FileMetaAdapter searchAdapter) {
        return new ResultResponse<FileMeta>() {

            @Override
            public boolean onResultRecive(final FileMeta result) {

                //ADS.hideAdsTemp(a);

                File file = new File(result.getPath());

                if (Clouds.isCloud(file.getPath()) && Clouds.isCacheFileExist(file.getPath())) {
                    file = Clouds.getCacheFile(file.getPath());
                }

                Runnable onDeleteAction = new Runnable() {

                    @Override
                    public void run() {
                        //deleteFile(a, searchAdapter, result);
                    }

                };

                if (ExtUtils.isExteralSD(result.getPath())) {
                    ShareDialog.show(a, file, onDeleteAction, -1, null, null);
                } else {

                    if (ExtUtils.doifFileExists(a, result.getPath())) {

                        /*if (ExtUtils.isNotSupportedFile(file)) {
                            ShareDialog.showArchive(a, file, onDeleteAction);
                        } else {
                            ShareDialog.show(a, file, onDeleteAction, -1, null, null);
                        }*/
                    }
                }

                return false;
            }
        };
    }

    public static void showBooksByTag(final Activity a, String result) {
        /*Intent intent = new Intent(UIFragment.INTENT_TINT_CHANGE)//
                .putExtra(MainTabs2.EXTRA_PAGE_NUMBER, UITab.getCurrentTabIndex(UITab.SearchFragment));//
        LocalBroadcastManager.getInstance(a).sendBroadcast(intent);

        EventBus.getDefault().post(new OpenTagMessage(result));*/
    }


    public static ResultResponse2<FileMeta, FileMetaAdapter> getOnStarClick(final Activity a) {
        return new ResultResponse2<FileMeta, FileMetaAdapter>() {

            @Override
            public boolean onResultRecive(FileMeta fileMeta, FileMetaAdapter adapter) {
                Boolean isStar = fileMeta.getIsStar();

                if (isStar == null) {
                    isStar = AppDB.get().isStarFolder(fileMeta.getPath());

                } else {

                }


                fileMeta.setIsStar(!isStar);


                if (fileMeta.getIsStar()) {
                    AppData.get().addFavorite(new SimpleMeta(fileMeta.getPath(), System.currentTimeMillis()));
                } else {
                    AppData.get().removeFavorite(fileMeta);
                }

                fileMeta.setIsStarTime(System.currentTimeMillis());
                AppDB.get().updateOrSave(fileMeta);


                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                TempHolder.listHash++;

                return false;
            }
        };
    }

    public static ResultResponse<FileMeta> getOnItemClickListener7(final Activity a) {
        return new ResultResponse<FileMeta>() {

            @Override
            public boolean onResultRecive(final FileMeta result) {

                if (false) {
                    //Dialogs.testWebView(a, result.getPath());
                    return false;
                }

                if (result.getPath().endsWith(Playlists.L_PLAYLIST)) {
                    //DialogsPlaylist.showPlayList(a, result.getPath(), null);
                    return true;
                }

                /*boolean isFolder = AppDB.get().isFolder(result);

                if (!isFolder && result.getPath().startsWith(Clouds.PREFIX_CLOUD)) {

                    Downloader.openOrDownload(a, result, new Runnable() {

                        @Override
                        public void run() {
                            IMG.clearCache(result.getPath());

                            Intent intent = new Intent(UIFragment.INTENT_TINT_CHANGE)//
                                    .putExtra(MainTabs2.EXTRA_NOTIFY_REFRESH, true)//
                                    .putExtra(MainTabs2.EXTRA_PAGE_NUMBER, -2);//
                            LocalBroadcastManager.getInstance(a).sendBroadcast(intent);
                        }
                    });

                    return false;
                }*/

                /*if (isTagCicked(a, result)) {
                    return true;
                }*/

                // final File item = new File(result.getPath());
                /*if (isFolder) {
                    final int currentTabIndex = UITab.getCurrentTabIndex(UITab.BrowseFragment);
                    Intent intent = new Intent(UIFragment.INTENT_TINT_CHANGE)//

                            .putExtra(MainTabs2.EXTRA_PAGE_NUMBER, currentTabIndex);//

                    LOG.d("EXTRA_PAGE_NUMBER", AppState.get().tabsOrder7, currentTabIndex);

                    LocalBroadcastManager.getInstance(a).sendBroadcast(intent);

                    //EventBus.getDefault().post(new OpenDirMessage(result.getPath()));

                } else {
                    if(AppSP.get().readingMode == AppState.READING_MODE_OPEN_WITH ){
                        AppData.get().addRecent(new SimpleMeta(result.getPath()));
                        EventBus.getDefault().post(new NotifyAllFragments());
                        ExtUtils.openWith(a, new File(result.getPath()));
                        return  false;
                    }

                    if (AppSP.get().readingMode == AppState.READING_MODE_TAG_MANAGER && !ExtUtils.isExteralSD(result.getPath())) {
                        Dialogs.showTagsDialog(a, new File(result.getPath()), true, new Runnable() {

                            @Override
                            public void run() {
                                EventBus.getDefault().post(new NotifyAllFragments());
                            }
                        });
                    } else {
                        ExtUtils.openFile(a, result);
                    }
                }*/
                return false;
            }

        };
    }



    public static ResultResponse<Information> getOnItemClickListener(final Activity a) {
        return new ResultResponse<Information>() {

            @Override
            public boolean onResultRecive(Information result) {
                //ShareDialog.show(a, null);
                return false;
            }
        };
    };

    public static ResultResponse<Information> getOnMenuClick(final Activity a, final InformationAdapter informationAdapter) {
        return new ResultResponse<Information>() {

            @Override
            public boolean onResultRecive(final Information result) {

                //final File file = new File(result.getPath());
                /*
                if (ExtUtils.doifFileExists(a, file)) {

                    Runnable onDeleteAction = new Runnable() {

                        @Override
                        public void run() {
                            deleteFile(a, searchAdapter, result);
                        }

                    };

                    if (ExtUtils.isNotSupportedFile(file)) {
                        ShareDialog.showArchive(a, file, onDeleteAction);
                    } else {
                        ShareDialog.show(a, file, onDeleteAction, -1, null, null);
                    }
                }
                */
                //Log.e("333333333333", "44444444444");
                ShareDialog.show(a, null, result);
                return false;
            }
        };
    }
    public static ResultResponse<Task> getOnMenuClick2(final Activity a, final TasksAdapterShort tasksAdapter) {
        return new ResultResponse<Task>() {

            @Override
            public boolean onResultRecive(final Task result) {


                //Log.e("333333333333", "44444444444");
                ShareDialog.show2(a, null, result);
                return false;
            }
        };
    }
    public static ResultResponse<Task> getOnMenuClick2(final Activity a, final TasksAdapter2 tasksAdapter) {
        return new ResultResponse<Task>() {

            @Override
            public boolean onResultRecive(final Task result) {


                //Log.e("333333333333", "44444444444");
                ShareDialog.show2(a, null, result);
                return false;
            }
        };
    }

    public static ResultResponse<Task> getOnMenuClick3(final Activity a, final TasksAdapter2 tasksAdapter) {
        return new ResultResponse<Task>() {

            @Override
            public boolean onResultRecive(final Task result) {


                //Log.e("333333333333", "44444444444");
                ShareDialog.show2(a, null, result);
                return false;
            }
        };
    }

    public static ResultResponse<Project> getOnMenuClick(final Activity a, final SprProjectTreeAdapter projectAdapter) {
        return new ResultResponse<Project>() {

            @Override
            public boolean onResultRecive(final Project result) {


                //Log.e("333333333333", "44444444444");
                //ShareDialog.show3(a, null, result);
                return false;
            }
        };
    }

    public static ResultResponse<Comment> getOnMenuClick(final Activity a, final CommentAdapter commentAdapter) {
        return new ResultResponse<Comment>() {

            @Override
            public boolean onResultRecive(final Comment result) {


                //Log.e("333333333333", "44444444444");
                //ShareDialog.show3(a, null, result);
                return false;
            }
        };
    }

    public static ResultResponse<FileMeta> getOnItemLongClickListener(final Activity a, final FileMetaAdapter searchAdapter) {
        return new ResultResponse<FileMeta>() {

            @Override
            public boolean onResultRecive(final FileMeta result) {
                LOG.d("getOnItemLongClickListener");

                if (result.getPath().endsWith(Playlists.L_PLAYLIST)) {
                    //ExtUtils.openFile(a, result);
                    return false;
                }

                if (ExtUtils.isExteralSD(result.getPath())) {
                    return false;
                }

                /*if (isTagCicked(a, result)) {
                    return true;
                }*/

                File file = new File(result.getPath());

                if (Clouds.isCloud(file.getPath()) && Clouds.isCacheFileExist(file.getPath())) {
                    file = Clouds.getCacheFile(file.getPath());
                }

                if (file.isDirectory()) {
                    /*Intent intent = new Intent(UIFragment.INTENT_TINT_CHANGE)//
                            .putExtra(MainTabs2.EXTRA_PAGE_NUMBER, UITab.getCurrentTabIndex(UITab.BrowseFragment));//
                    LocalBroadcastManager.getInstance(a).sendBroadcast(intent);

                    EventBus.getDefault().post(new OpenDirMessage(result.getPath()));*/
                    return true;
                }

                Runnable onDeleteAction = new Runnable() {

                    @Override
                    public void run() {

                        //deleteFile(a, searchAdapter, result);
                    }

                };
                if (ExtUtils.doifFileExists(a, file)) {
                    FileInformationDialog.showFileInfoDialog(a, file, onDeleteAction);
                }
                return true;
            }
        };
    }

}
