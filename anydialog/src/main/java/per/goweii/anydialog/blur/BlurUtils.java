package per.goweii.anydialog.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

/**
 * @author Cuizhen
 * @date 2018/4/27
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public final class BlurUtils {

    public static Bitmap blurByPercent(Context context, Bitmap originalBitmap, float percent, float scaleFactor) {
        if (percent <= 0) {
            return originalBitmap;
        }
        int w = originalBitmap.getWidth();
        int h = originalBitmap.getHeight();
        int min = Math.min(w, h);
        float radius = min * percent;
        return blur(context, originalBitmap, radius, scaleFactor);
    }

    public static Bitmap blur(Context context, Bitmap originalBitmap, float radius, float scaleFactor) {
        if (originalBitmap == null) {
            return null;
        }
        if (radius < 0) {
            radius = 0;
        }
        if (scaleFactor < 1) {
            scaleFactor = 1;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return GaussianBlur.get(context).blur(originalBitmap, radius, scaleFactor, false, false);
        } else {
            return FastBlur.get().blur(originalBitmap, (int) radius, scaleFactor, false, false);
        }
    }
}
