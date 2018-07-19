package pers.goweii.dialog.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 解決软键盘弹出遮挡输入框的问题
 *
 * @author Cuizhen
 * @version v1.0.0
 * @date 2018/3/30-上午9:06
 */
public class SoftInputUtils implements ViewTreeObserver.OnGlobalLayoutListener {

    private final Activity activity;
    private final View rootView;
    private final ViewTreeObserver observer;
    private int usableHeightPrevious;
    private long duration = 200;
    private OnSoftInputListener onSoftInputListener = null;
    private View scrollView = null;
    private View bottomView = null;
    private boolean raiseWithTranslation = false;

    private SoftInputUtils(@NonNull Activity activity) {
        this.activity = activity;
        this.rootView = activity.getWindow().getDecorView().getRootView();
        this.observer = this.rootView.getViewTreeObserver();
    }

    public static SoftInputUtils init(@NonNull Activity activity) {
        return new SoftInputUtils(activity);
    }

    public SoftInputUtils setOnSoftInputListener(OnSoftInputListener onSoftInputListener) {
        this.onSoftInputListener = onSoftInputListener;
        return this;
    }

    public SoftInputUtils duration(long duration) {
        this.duration = duration;
        return this;
    }

    public SoftInputUtils remove() {
        observer.addOnGlobalLayoutListener(this);
        return this;
    }

    public SoftInputUtils raise(View scrollView, @NonNull View bottomView) {
        raiseWithTranslation = false;
        this.scrollView = scrollView;
        this.bottomView = bottomView;
        observer.addOnGlobalLayoutListener(this);
        return this;
    }

    public SoftInputUtils raise(@NonNull final View bottomView) {
        raiseWithTranslation = true;
        this.bottomView = bottomView;
        observer.addOnGlobalLayoutListener(this);
        return this;
    }

    private void scrollTo(View view, int to) {
        int scrollY = view.getScrollY();
        ObjectAnimator.ofInt(view, "scrollY", scrollY, to)
                .setDuration(duration).start();
    }

    private void translationTo(View view, int to) {
        float translationY = view.getTranslationY();
        ObjectAnimator.ofFloat(view, "translationY", translationY, to)
                .setDuration(duration).start();
    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        int usableHeightNow = rect.bottom - rect.top;
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = rootView.getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 5)) {
                int[] bottomLocation = new int[2];
                bottomView.getLocationInWindow(bottomLocation);
                int bottomViewY = bottomLocation[1] + bottomView.getHeight();
                if (bottomViewY <= rect.bottom) {
                    return;
                }
                int scrollHeight = bottomViewY - rect.bottom;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    DisplayInfoUtils utils = DisplayInfoUtils.getInstance(activity);
                    if (utils.hasNavigationBar(activity.getWindowManager())) {
                        scrollHeight += utils.getNavigationBarHeight();
                    }
                }
                if (rect.bottom < bottomViewY) {
                    if (scrollView != null) {
                        scrollTo(scrollView, scrollHeight);
                    } else {
                        if (raiseWithTranslation){
                            translationTo(bottomView, -scrollHeight);
                        } else {
                            scrollTo(rootView, scrollHeight);
                        }
                    }
                    if (onSoftInputListener != null) {
                        onSoftInputListener.onOpen();
                    }
                }
            } else {
                if (scrollView != null) {
                    scrollTo(scrollView, 0);
                } else {
                    if (raiseWithTranslation){
                        translationTo(bottomView, 0);
                    } else {
                        scrollTo(rootView, 0);
                    }
                }
                if (onSoftInputListener != null) {
                    onSoftInputListener.onClose();
                }
            }
            rootView.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    public interface OnSoftInputListener {
        void onOpen();

        void onClose();
    }
}
