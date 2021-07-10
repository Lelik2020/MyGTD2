package ru.kau.mygtd2.listeners;

import android.app.Activity;

import ru.kau.mygtd2.adapters.CommentAdapter;
import ru.kau.mygtd2.adapters.InformationAdapter;
import ru.kau.mygtd2.adapters.SprProjectTreeAdapter;
import ru.kau.mygtd2.adapters.TasksAdapter2;
import ru.kau.mygtd2.dialogs.ShareDialog;
import ru.kau.mygtd2.interfaces.ResultResponse;
import ru.kau.mygtd2.objects.Comment;
import ru.kau.mygtd2.objects.Information;
import ru.kau.mygtd2.objects.Project;
import ru.kau.mygtd2.objects.Task;

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

}
