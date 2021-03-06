package ru.kau.mygtd2.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.ImageView;

import androidx.core.widget.ImageViewCompat;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.flag.FlagView;

import ru.kau.mygtd2.R;

public class BubbleFlag extends FlagView {

    private ImageView bubble;

    /**
     * onBind Views
     *
     * @param context context
     * @param layout custom flagView's layout
     */
    public BubbleFlag(Context context, int layout) {
        super(context, layout);
        this.bubble = findViewById(R.id.imageView);
    }

    /**
     * invoked when selector moved
     *
     * @param colorEnvelope provide color, hexCode, argb
     */
    @Override
    @SuppressLint("SetTextI18n")
    public void onRefresh(ColorEnvelope colorEnvelope) {
        ImageViewCompat.setImageTintList(bubble, ColorStateList.valueOf(colorEnvelope.getColor()));
    }
}
