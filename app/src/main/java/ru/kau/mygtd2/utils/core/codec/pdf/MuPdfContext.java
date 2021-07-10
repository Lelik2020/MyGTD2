package ru.kau.mygtd2.utils.core.codec.pdf;

import android.graphics.Bitmap;

import ru.kau.mygtd2.utils.core.codec.AbstractCodecContext;

public abstract class MuPdfContext extends AbstractCodecContext {

    public static final Bitmap.Config NATIVE_BITMAP_CFG = Bitmap.Config.ARGB_8888;

    public MuPdfContext() {
    }

    @Override
    public Bitmap.Config getBitmapConfig() {
        return NATIVE_BITMAP_CFG;
    }


    @Override
    public boolean isParallelPageAccessAvailable() {
        return false;
    }



}

