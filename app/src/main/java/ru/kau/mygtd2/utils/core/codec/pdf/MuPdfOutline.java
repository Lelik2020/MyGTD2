package ru.kau.mygtd2.utils.core.codec.pdf;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.utils.AppState;
import ru.kau.mygtd2.utils.LOG;
import ru.kau.mygtd2.utils.TempHolder;
import ru.kau.mygtd2.utils.core.codec.OutlineLink;
import ru.kau.mygtd2.utils.ext.Fb2Extractor;

public class MuPdfOutline {

    private static final float[] temp = new float[4];

    private long docHandle;

    public List<OutlineLink> getOutline(final long dochandle) {
        final List<OutlineLink> ls = new ArrayList<OutlineLink>();
        docHandle = dochandle;
        TempHolder.lock.lock();
        try {
            final long outline = open(dochandle);
            ttOutline(ls, outline, 0);
            free(dochandle);

            ls.add(new OutlineLink("", "", -1, dochandle, ""));
        } finally {
            TempHolder.lock.unlock();
        }
        return ls;
    }

    private void ttOutline(final List<OutlineLink> ls, long outline, final int level) {
        while (outline != -1) {
            String title = getTitle(outline);
            final String link = getLink(outline, docHandle);
            String linkUri = getLinkUri(outline, docHandle);
            LOG.d("linkUri", linkUri, title);
            if (title != null) {
                final OutlineLink outlineLink = new OutlineLink(title, link, level, docHandle, linkUri);

                boolean toAdd = true;
                if (AppState.get().outlineMode == AppState.OUTLINE_ONLY_HEADERS) {
                    if (outlineLink.getTitle().contains("[subtitle]")) {
                        toAdd = false;
                    }
                }
                outlineLink.setTitle(outlineLink.getTitle().replace("[title]", "").replace("[subtitle]", ""));

                if (outlineLink.getTitle().contains(Fb2Extractor.DIVIDER)) {
                    try {
                        String[] split = outlineLink.getTitle().split(Fb2Extractor.DIVIDER);
                        int level2 = Integer.parseInt(split[0]);
                        outlineLink.setLevel(level2);
                        outlineLink.setTitle(split[1]);
                    } catch (Exception e) {
                        LOG.e(e);
                    }
                }

                if (toAdd) {
                    ls.add(outlineLink);
                }
            }

            final long child = getChild(outline);
            ttOutline(ls, child, level + 1);

            outline = getNext(outline);
        }
    }

    private static native String getTitle(long outlinehandle);

    private static native String getLink(long outlinehandle, long dochandle);

    private static native String getLinkUri(long outlinehandle, long dochandle);

    private static native int fillLinkTargetPoint(long outlinehandle, float[] point);

    private static native long getNext(long outlinehandle);

    private static native long getChild(long outlinehandle);

    private static native long open(long dochandle);

    private static native void free(long dochandle);
}
