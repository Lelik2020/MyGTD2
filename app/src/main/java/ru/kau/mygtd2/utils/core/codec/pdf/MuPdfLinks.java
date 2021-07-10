package ru.kau.mygtd2.utils.core.codec.pdf;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.TempHolder;
import ru.kau.mygtd2.utils.TxtUtils;
import ru.kau.mygtd2.utils.core.codec.PageLink;

public class MuPdfLinks {

    // private static final int FZ_LINK_NONE = 0;
    // private static final int FZ_LINK_GOTO = 1;
    // private static final int FZ_LINK_URI = 2;
    // private static final int FZ_LINK_LAUNCH = 3;
    // private static final int FZ_LINK_NAMED = 4;
    // private static final int FZ_LINK_GOTOR = 5;

    private static final float[] temp = new float[4];

    static List<PageLink> getPageLinks(final long docHandle, final long pageHandle, final RectF pageBounds) {
        final List<PageLink> links = new ArrayList<PageLink>();

        for (long linkHandle = getFirstPageLink(docHandle, pageHandle); linkHandle != 0; linkHandle = getNextPageLink(linkHandle)) {

            final PageLink link = new PageLink();
            LOG.d("LINK GET", docHandle, linkHandle);
            final int type = getPageLinkType(docHandle, linkHandle);
            if (type == 1) {// external
                link.url = getPageLinkUrl(linkHandle);
                if (fillPageLinkSourceRect(linkHandle, temp)) {
                    link.sourceRect = new RectF();
                    link.sourceRect.left = (temp[0] - pageBounds.left) / pageBounds.width();
                    link.sourceRect.top = (temp[1] - pageBounds.top) / pageBounds.height();
                    link.sourceRect.right = (temp[2] - pageBounds.left) / pageBounds.width();
                    link.sourceRect.bottom = (temp[3] - pageBounds.top) / pageBounds.height();
                }
                links.add(link);
            } else if (type == 0) {// internal
                if (fillPageLinkSourceRect(linkHandle, temp)) {
                    link.sourceRect = new RectF();
                    link.sourceRect.left = (temp[0] - pageBounds.left) / pageBounds.width();
                    link.sourceRect.top = (temp[1] - pageBounds.top) / pageBounds.height();
                    link.sourceRect.right = (temp[2] - pageBounds.left) / pageBounds.width();
                    link.sourceRect.bottom = (temp[3] - pageBounds.top) / pageBounds.height();
                }

                try {
                    link.targetPage = getPageLinkTargetPage(docHandle, linkHandle);
                    LOG.d("getPageLinkTargetPage", link.targetPage);
                    // link.targetPage--;
                } catch (Exception e) {
                    link.targetPage = -1;
                }

                links.add(link);
            }

            LOG.d("LINK DROP", docHandle, linkHandle);
            // dropLink(docHandle, linkHandle);
        }
        return links;
    }

    private static native void dropLink(long dochandle, long linkhandle);

    private static native long getFirstPageLink(long dochandle, long pagehandle);

    private static native long getNextPageLink(long linkhandle);

    private static native int getPageLinkType(long dochandle, long linkhandle);

    private static native String getPageLinkUrl(long linkhandle);

    private static native boolean fillPageLinkSourceRect(long linkhandle, float[] bounds);

    private static native int getPageLinkTargetPage(long dochandle, long linkhandle);

    private static native int getLinkPage(long dochandle, String id);

    public static int getLinkPageWrapper(long dochandle, String id) {
        if (TxtUtils.isEmpty(id)) {
            return -1;
        }
        TempHolder.lock.lock();
        try {
            return getLinkPage(dochandle, id);
        } finally {
            TempHolder.lock.unlock();
        }
    }

    private static native int fillPageLinkTargetPoint(long linkhandle, float[] point);

}
