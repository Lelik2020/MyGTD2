package ru.kau.mygtd2.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;

import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.TintUtil;

public class FastScrollRecyclerView extends RecyclerView {

    private FastScroller mFastScroller;

    public FastScrollRecyclerView(Context context) {
        super(context);
        layout(context, null);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //setHasFixedSize(true);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layout(context, attrs);
        //setHasFixedSize(true);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter instanceof FastScroller.SectionIndexer) {
            mFastScroller.setSectionIndexer((FastScroller.SectionIndexer) adapter);
        } else {
            mFastScroller.setSectionIndexer(null);
        }
    }

    public void myConfiguration() {
        setBubbleColor(TintUtil.color);
        setTrackColor(TintUtil.color);
        setHandleColor(TintUtil.color);
        setBubbleTextColor(Color.WHITE);
        setTrackVisible(false);
        setHideScrollbar(true);
    }

    /**
     * Set the enabled state of fast scrolling.
     *
     * @param enabled
     *            True to enable fast scrolling, false otherwise
     */
    public void setFastScrollEnabled(boolean enabled) {
        mFastScroller.setEnabled(enabled);
    }

    /**
     * Hide the scrollbar when not scrolling.
     *
     * @param hideScrollbar
     *            True to hide the scrollbar, false to show
     */
    public void setHideScrollbar(boolean hideScrollbar) {
        mFastScroller.setHideScrollbar(hideScrollbar);
    }

    /**
     * Display a scroll track while scrolling.
     *
     * @param visible
     *            True to show scroll track, false to hide
     */
    public void setTrackVisible(boolean visible) {
        mFastScroller.setTrackVisible(visible);
    }

    /**
     * Set the color of the scroll track.
     *
     * @param color
     *            The color for the scroll track
     */
    public void setTrackColor(int color) {
        mFastScroller.setTrackColor(color);
    }

    /**
     * Set the color for the scroll handle.
     *
     * @param color
     *            The color for the scroll handle
     */
    public void setHandleColor(int color) {
        mFastScroller.setHandleColor(color);
    }

    /**
     * Set the background color of the index bubble.
     *
     * @param color
     *            The background color for the index bubble
     */
    public void setBubbleColor(int color) {
        mFastScroller.setBubbleColor(color);
    }

    /**
     * Set the text color of the index bubble.
     *
     * @param color
     *            The text color for the index bubble
     */
    public void setBubbleTextColor(int color) {
        mFastScroller.setBubbleTextColor(color);
    }

    /**
     * Set the fast scroll state change listener.
     *
     * @param fastScrollStateChangeListener
     *            The interface that will listen to fastscroll state change events
     */
    public void setFastScrollStateChangeListener(FastScrollStateChangeListener fastScrollStateChangeListener) {
        mFastScroller.setFastScrollStateChangeListener(fastScrollStateChangeListener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            mFastScroller.attachRecyclerView(this);

            ViewParent parent = getParent();

            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.addView(mFastScroller);
                mFastScroller.setLayoutParams(viewGroup);
            }
        } catch (Exception e) {
            LOG.e(e);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mFastScroller.detachRecyclerView();
        super.onDetachedFromWindow();
    }

    private void layout(Context context, AttributeSet attrs) {
        mFastScroller = new FastScroller(context, attrs);
    }
}
