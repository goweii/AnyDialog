package pers.goweii.dialog.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import pers.goweii.dialog.utils.DisplayInfoUtils;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class AnimHelper {

    public static void startZoomInAnim(View target, long duration) {
        target.setAlpha(1);
        target.setScaleX(1);
        target.setScaleY(1);
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
        float alphaLast = target.getAlpha();
        float scaleXLast = target.getScaleX();
        float scaleYLast = target.getScaleY();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", alphaLast, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", scaleXLast, 0.618f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", scaleYLast, 0.618f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, scaleX, scaleY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startAlphaInAnim(View target, long duration) {
        target.setAlpha(1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        alpha.setDuration(duration);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.start();
    }

    public static void startAlphaOutAnim(View target, long duration) {
        float alphaLast = target.getAlpha();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", alphaLast, 0);
        alpha.setDuration(duration);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.start();
    }

    public static void startTopInAnim(final View target, long duration) {
        target.setTranslationY(0);
        float height = target.getMeasuredHeight();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float y = height + location[1];
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", -y, 0);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(duration);
        translationY.start();
    }

    public static void startTopOutAnim(final View target, long duration) {
        target.setTranslationY(0);
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
        target.setAlpha(1);
        target.setTranslationY(0);
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
        target.setAlpha(1);
        target.setTranslationY(0);
        float y = (1 - 0.618f) * target.getMeasuredHeight();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, -y);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startBottomInAnim(final View target, long duration) {
        target.setTranslationY(0);
        int h = DisplayInfoUtils.getInstance(target.getContext()).getHeightPixels();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float y = h - location[1];
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", y, 0);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(duration);
        translationY.start();
    }

    public static void startBottomOutAnim(final View target, long duration) {
        target.setTranslationY(0);
        int h = DisplayInfoUtils.getInstance(target.getContext()).getHeightPixels();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float y = h - location[1];
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, y);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(duration);
        translationY.start();
    }

    public static void startBottomAlphaInAnim(final View target, long duration) {
        target.setAlpha(1);
        target.setTranslationY(0);
        float y = (1 - 0.618f) * target.getMeasuredHeight();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0, 1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", y, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startBottomAlphaOutAnim(final View target, long duration) {
        target.setAlpha(1);
        target.setTranslationY(0);
        float y = (1 - 0.618f) * target.getMeasuredHeight();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, "translationY", 0, y);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationY);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startLeftInAnim(final View target, long duration) {
        target.setTranslationX(0);
        float width = target.getMeasuredWidth();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        float x = width + location[0];
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", -x, 0);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.setDuration(duration);
        translationX.start();
    }

    public static void startLeftOutAnim(final View target, long duration) {
        target.setTranslationX(0);
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
        target.setAlpha(1);
        target.setTranslationX(0);
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
        target.setAlpha(1);
        target.setTranslationX(0);
        float x = (1 - 0.618f) * target.getMeasuredWidth();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, -x);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationX);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    public static void startRightInAnim(final View target, long duration) {
        target.setTranslationX(0);
        int w = DisplayInfoUtils.getInstance(target.getContext()).getWidthPixels();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        final float x = w - location[0];
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", x, 0);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.setDuration(duration);
        translationX.start();
    }

    public static void startRightOutAnim(final View target, long duration) {
        target.setTranslationX(0);
        int w = DisplayInfoUtils.getInstance(target.getContext()).getWidthPixels();
        int[] location = new int[2];
        target.getLocationInWindow(location);
        final float x = w - location[0];
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, x);
        translationX.setInterpolator(new DecelerateInterpolator());
        translationX.setDuration(duration);
        translationX.start();
    }

    public static void startRightAlphaInAnim(View target, long duration) {
        target.setAlpha(1);
        target.setTranslationX(0);
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
        target.setAlpha(1);
        target.setTranslationX(0);
        float x = (1 - 0.618f) * target.getMeasuredWidth();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 1, 0);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(target, "translationX", 0, x);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, translationX);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(duration);
        set.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startCircularRevealInAnim(View target, int centerX, int centerY, long duration) {
        int x = target.getMeasuredWidth();
        int y = target.getMeasuredHeight();
        int r = (int) Math.sqrt(x * x + y * y);
        Animator animator = ViewAnimationUtils.createCircularReveal(target, centerX, centerY, 0, r);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startCircularRevealOutAnim(View target, int centerX, int centerY, long duration) {
        int x = target.getMeasuredWidth();
        int y = target.getMeasuredHeight();
        int r = (int) Math.sqrt(x * x + y * y);
        Animator animator = ViewAnimationUtils.createCircularReveal(target, centerX, centerY, r, 0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(duration);
        animator.start();
    }
}