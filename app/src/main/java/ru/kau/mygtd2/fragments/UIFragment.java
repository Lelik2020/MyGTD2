package ru.kau.mygtd2.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import ru.kau.mygtd2.R;
import ru.kau.mygtd2.ui.FastScrollRecyclerView;
import ru.kau.mygtd2.ui.FastScrollStateChangeListener;
import ru.kau.mygtd2.utils.AppState;
import ru.kau.mygtd2.utils.Dips;
import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.TempHolder;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.info.AppsConfig;
import ru.kau.mygtd2.utils.info.wrapper.DocumentController;
import ru.kau.mygtd2.utils.msg.UpdateAllFragments;

public abstract class UIFragment<T> extends Fragment {

    public static String EXTRA_SEACH_TEXT = "EXTRA_SEACH_TEXT";

    public static String INTENT_TINT_CHANGE = "INTENT_TINT_CHANGE";
    protected volatile ru.kau.mygtd2.ui.MyProgressBar MyProgressBar;
    protected RecyclerView recyclerView;
    Handler handler;
    View adFrame;
    SwipeRefreshLayout swipeRefreshLayout;
    int listHash = 0;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String txt = intent.getStringExtra(EXTRA_SEACH_TEXT);
            if (TxtUtils.isNotEmpty(txt)) {
                onTextRecive(txt);
            } else {
                onTintChanged();
            }
        }
    };
    AsyncTask<Object, Object, List<T>> execute;
    volatile boolean inProgress = false;

    public abstract Pair<Integer, Integer> getNameAndIconRes();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        handler = new Handler();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TxtUtils.updateAllLinks(view);
        if (AppState.get().appTheme == AppState.THEME_INK) {
            TxtUtils.setInkTextView(view);
        }

        if (recyclerView instanceof FastScrollRecyclerView) {
            swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);

            ((FastScrollRecyclerView) recyclerView).setFastScrollStateChangeListener(new FastScrollStateChangeListener() {

                @Override
                public void onFastScrollStop() {

                }

                @Override
                public void onFastScrollStart() {
                    //IMG.pauseRequests(getContext());
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setEnabled(false);
                    }

                }
            });
        }

        if (recyclerView != null) {
            recyclerView.setAccessibilityDelegate(new View.AccessibilityDelegate());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacksAndMessages(null);
    }

    public boolean isBackPressed() {
        return false;
    }

    public abstract void notifyFragment();

    public abstract void resetFragment();

    public final void onSelectFragment() {
        if (getActivity() == null) {
            return;
        }
        if (listHash != TempHolder.listHash) {
            LOG.d("TempHolder.listHash", listHash, TempHolder.listHash);
            resetFragment();
            listHash = TempHolder.listHash;
        } else {
            notifyFragment();

            try {
                if (adFrame == null) {
                    adFrame = getActivity().findViewById(R.id.adFrame);
                }

                if (adFrame != null && adFrame.getVisibility() == View.INVISIBLE) {
                    adFrame.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                LOG.e(e);
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyUpdateFragment(UpdateAllFragments event) {
        TempHolder.listHash++;
        onSelectFragment();
    }

    /*public void bindAdapter(FileMetaAdapter searchAdapter) {
        DefaultListeners.bindAdapter(getActivity(), searchAdapter);
    }*/

    /*public void bindAuthorsSeriesAdapter(FileMetaAdapter searchAdapter) {
        DefaultListeners.bindAdapterAuthorSerias(getActivity(), searchAdapter);
    }*/

    private List<T> prepareDataInBackgroundSync() {
        return prepareDataInBackground();
    }

    public List<T> prepareDataInBackground() {
        return null;
    }

    public void populateDataInUI(List<T> items) {

    }

    public void onTintChanged() {

    }

    public void sendNotifyTintChanged() {
        Intent itent = new Intent(INTENT_TINT_CHANGE);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(itent);
        DocumentController.setNavBarTintColor(getActivity());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        TempHolder.listHash++;
        onSelectFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Safe.clearAll();
        try {
            //Glide.with(LibreraApp.context).resumeRequests();
        } catch (Exception e) {
            LOG.e(e);
        }
        notifyFragment();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter(INTENT_TINT_CHANGE));
        EventBus.getDefault().register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recyclerView != null) {
            try {
                recyclerView.setAdapter(null);
            } catch (Exception e) {
                LOG.e(e);
            }
        }
    }

    public void onTextRecive(String txt) {

    }

    public boolean isInProgress() {
        return MyProgressBar != null && MyProgressBar.getVisibility() == View.VISIBLE;
    }

    public void populate() {
        if (inProgress) {
            LOG.d("IN_PROGRESS");
            return;
        }

        final Runnable target = () -> {

            if (getActivity() == null) {
                return;
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (MyProgressBar != null) {
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                MyProgressBar.setVisibility(View.VISIBLE);
                            }
                        }, 100);
                    }
                }
            });


            final List<T> result;
            try {
                inProgress = true;
                result = prepareDataInBackgroundSync();
            } finally {
                inProgress = false;

            }
            /*if (isDetached() || Apps.isDestroyed(getActivity())) {
                return;
            }*/

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isAdded()) {
                        if (MyProgressBar != null) {
                            handler.removeCallbacksAndMessages(null);
                            MyProgressBar.setVisibility(View.GONE);
                        }
                        try {
                            populateDataInUI(result);
                        } catch (Exception e) {
                            LOG.e(e);
                        }
                    }

                }
            });
        };
        AppsConfig.executorService.submit(target);
    }



    public boolean onKeyDown(int keyCode) {
        if (recyclerView == null) {
            return false;
        }
        View childAt = recyclerView.getChildAt(0);
        if (childAt == null) {
            return false;
        }
        int size = childAt.getHeight() + childAt.getPaddingTop() + Dips.dpToPx(2);

        if (AppState.get().getNextKeys().contains(keyCode)) {
            recyclerView.scrollBy(0, size);
            return true;

        }
        if (AppState.get().getPrevKeys().contains(keyCode)) {
            recyclerView.scrollBy(0, size * -1);
            return true;
        }
        return false;
    }

}
