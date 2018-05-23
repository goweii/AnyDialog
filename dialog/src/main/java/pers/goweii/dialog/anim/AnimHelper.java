package pers.goweii.dialog.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class AnimHelper {

    public static void startZoomInAnim(View target, long duration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 0.618f, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 0.618f, 1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startZoomOutAnim(View target, long duration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1, 0.618f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1, 0.618f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startAlphaInAnim(View target, long duration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        alpha.setDuration(duration);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.start();
    }

    public static void startAlphaOutAnim(View target, long duration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        alpha.setDuration(duration);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.start();
    }

    public static void startTopInAnim(View target, long duration) {
        float height = target.getMeasuredHeight();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float y = height + location[1];
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", -y, 0);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(duration);
        translationY.start();
    }

    public static void startTopOutAnim(View target, long duration) {
        float height = target.getMeasuredHeight();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float y = height + location[1];
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, -y);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(duration);
        translationY.start();
    }

    public static void startTopAlphaInAnim(View target, long duration) {
        float y = (1 - 0.618f) * target.getMeasuredHeight();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", -y, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startTopAlphaOutAnim(View target, long duration) {
        float y = (1 - 0.618f) * target.getMeasuredHeight();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, -y);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startBottomInAnim(View target, long duration) {
        float height = target.getMeasuredHeight();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float y = height + location[1];
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", y, 0);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(duration);
        translationY.start();
    }

    public static void startBottomOutAnim(View target, long duration) {
        float height = target.getMeasuredHeight();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float y = height + location[1];
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, y);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(duration);
        translationY.start();
    }

    public static void startBottomAlphaInAnim(View target, long duration) {
        float y = (1 - 0.618f) * target.getMeasuredHeight();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", y, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startBottomAlphaOutAnim(View target, long duration) {
        float y = (1 - 0.618f) * target.getMeasuredHeight();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, y);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startLeftInAnim(View target, long duration) {
        float width = target.getMeasuredWidth();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float x = width + location[0];
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", -x, 0);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.setDuration(duration);
        translationX.start();
    }

    public static void startLeftOutAnim(View target, long duration) {
        float width = target.getMeasuredWidth();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float x = width + location[0];
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, -x);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.setDuration(duration);
        translationX.start();
    }

    public static void startLeftAlphaInAnim(View target, long duration) {
        float x = (1 - 0.618f) * target.getMeasuredWidth();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", -x, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationX);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startLeftAlphaOutAnim(View target, long duration) {
        float x = (1 - 0.618f) * target.getMeasuredWidth();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, -x);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationX);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startRightInAnim(View target, long duration) {
        float width = target.getMeasuredWidth();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float x = width + location[0];
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", x, 0);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.setDuration(duration);
        translationX.start();
    }

    public static void startRightOutAnim(View target, long duration) {
        float width = target.getMeasuredWidth();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float x = width + location[0];
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, x);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.setDuration(duration);
        translationX.start();
    }

    public static void startRightAlphaInAnim(View target, long duration) {
        float x = (1 - 0.618f) * target.getMeasuredWidth();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", x, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationX);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startRightAlphaOutAnim(View target, long duration) {
        float x = (1 - 0.618f) * target.getMeasuredWidth();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, x);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationX);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }
}
