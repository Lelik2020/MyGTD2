package ru.kau.mygtd2.utils.contexts;

import java.util.ArrayList;
import java.util.List;

import ru.kau.mygtd2.utils.TempHolder;
import ru.kau.mygtd2.utils.core.codec.OutlineLink;

public class DjvuOutline {

    private long docHandle;

    public List<OutlineLink> getOutline(final long dochandle) {
        TempHolder.lock.lock();
        try {
            final List<OutlineLink> ls = new ArrayList<OutlineLink>();
            docHandle = dochandle;
            final long expr = open(docHandle);
            ttOutline(ls, expr, 0);
            ls.add(new OutlineLink("", "", -1, dochandle, ""));
            return ls;
        }finally {
            TempHolder.lock.unlock();
        }
    }

    private void ttOutline(final List<OutlineLink> ls, long expr, int level) {
        while (expConsp(expr)) {
            final String title = getTitle(expr);
            final String link = getLink(expr, docHandle);
            if (title != null) {
                ls.add(new OutlineLink(title, link, level, docHandle, ""));
            }
            final long child = getChild(expr);
            ttOutline(ls, child, level + 1);

            expr = getNext(expr);
        }

    }

    private static native long open(long dochandle);

    private static native boolean expConsp(long expr);

    private static native String getTitle(long expr);

    private static native String getLink(long expr, long dochandle);

    private static native long getNext(long expr);

    private static native long getChild(long expr);

}

