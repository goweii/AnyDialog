package pers.goweii.dialog.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

/**
 * @author CuiZhen
 * @date 2018/2/8
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public final class ScreenShotUtils {

    private ScreenShotUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity Activity
     * @return Bitmap
     */
    public static Bitmap snapshotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        view.destroyDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        DisplayInfoUtils displayInfoUtils = DisplayInfoUtils.getInstance(activity);
        int width = displayInfoUtils.getWidthPixels();
        int height = displayInfoUtils.getHeightPixels();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        bmp = null;
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity Activity
     * @return Bitmap
     */
    public static Bitmap snapshotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        view.destroyDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        DisplayInfoUtils displayInfoUtils = DisplayInfoUtils.getInstance(activity);
        int width = displayInfoUtils.getWidthPixels();
        int height = displayInfoUtils.getHeightPixels();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        bmp = null;
        return bp;

    }
}
