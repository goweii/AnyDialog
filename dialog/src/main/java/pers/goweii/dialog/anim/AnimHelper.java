package pers.goweii.dialog.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class AnimHelper {

    public static void startDefaultContentInAnim(View content, long contentAnimDuration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(content, "alpha", 0, 1)
                .setDuration(contentAnimDuration);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(content, "scaleX", 0.5f, 1)
                .setDuration(contentAnimDuration);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(content, "scaleY", 0.5f, 1)
                .setDuration(contentAnimDuration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
    }

    public static void startDefaultContentOutAnim(View content, long contentAnimDuration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(content, "alpha", 1, 0)
                .setDuration(contentAnimDuration);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(content, "scaleX", 1, 0.5f)
                .setDuration(contentAnimDuration);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(content, "scaleY", 1, 0.5f)
                .setDuration(contentAnimDuration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
    }

    public static void startDefaultBackgroundInAnim(ImageView background, long backgroundAnimDuration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(background, "alpha", 0, 1)
                .setDuration(backgroundAnimDuration);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.start();
    }

    public static void startDefaultBackgroundOutAnim(ImageView background, long backgroundAnimDuration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(background, "alpha", 1, 0)
                .setDuration(backgroundAnimDuration);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.start();
    }
}
