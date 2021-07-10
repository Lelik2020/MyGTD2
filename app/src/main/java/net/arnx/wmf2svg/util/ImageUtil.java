package net.arnx.wmf2svg.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import ru.kau.mygtd2.utils.LOG;

//import com.foobnix.android.utils.LOG;

public class ImageUtil {

    public static String testOut;

    public static byte[] convert(byte[] image, String destType, boolean reverse) {
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, out);
        bmp.recycle();
        LOG.d("IMAGE-convert", image.length);

        if (testOut != null) {
            try {
                FileOutputStream outFile = new FileOutputStream(testOut);
                outFile.write(out.toByteArray());
                outFile.flush();
                outFile.close();
            } catch (Exception e) {
                LOG.e(e);
            }

        }

        return out.toByteArray();
    }

}
