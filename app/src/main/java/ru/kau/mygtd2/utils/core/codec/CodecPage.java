package ru.kau.mygtd2.utils.core.codec;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.List;

import ru.kau.mygtd2.enums.AnnotationType;
import ru.kau.mygtd2.utils.common.BitmapRef;
import ru.kau.mygtd2.utils.core.codec.pdf.TextWord;

public interface CodecPage {

    int getWidth();

    int getHeight();

    BitmapRef renderBitmap(int width, int height, RectF pageSliceBounds);

    BitmapRef renderBitmapSimple(int width, int height, RectF pageSliceBounds);


    Bitmap renderThumbnail(int width);

    Bitmap renderThumbnail(int width, int originW, int originH);

    List<PageLink> getPageLinks();

    List<Annotation> getAnnotations();

    public TextWord[][] getText();

    void recycle();

    boolean isRecycled();

    public void addAnnotation(float[] color, PointF[][] points, float width, float alpha);

    long getPageHandle();

    void addMarkupAnnotation(PointF[] quadPoints, AnnotationType type, float[] color);

    String getPageHTML();

    String getPageHTMLWithImages();

    int getCharCount();

}

