package per.goweii.anydialog;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2018/10/25
 */
class Utils {

    /**
     * 从当前上下文获取Activity
     */
    @Nullable
    static Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        return null;
    }

    static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    static Bitmap snapshot(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        view.destroyDrawingCache();
        return view.getDrawingCache();
    }

    /**
     * 计算颜色
     *
     * @param color color值
     * @param alpha alpha值[0-1]
     * @return 最终的状态栏颜色
     */
    static int alphaColor(@ColorInt int color, @FloatRange(from = 0, to = 1) float alpha) {
        if (alpha == 1) {
            return color;
        }
        if (alpha == 0){
            return 0;
        }
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        int a = (int) (alpha * 255);
        return a << 24 | red << 16 | green << 8 | blue;
    }
}
