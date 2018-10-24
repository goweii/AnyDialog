package pers.goweii.dialog.surface;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import pers.goweii.dialog.R;
import pers.goweii.dialog.anim.AnimHelper;
import pers.goweii.dialog.base.IAnim;
import pers.goweii.dialog.base.IDataBinder;
import pers.goweii.dialog.holder.ViewHolder;
import pers.goweii.dialog.listener.OnDialogClickListener;
import pers.goweii.dialog.listener.OnDialogDismissListener;
import pers.goweii.dialog.listener.OnDialogShowListener;
import pers.goweii.dialog.utils.DisplayInfoUtils;
import pers.goweii.dialog.utils.ScreenShotUtils;
import pers.goweii.dialog.utils.SoftInputUtils;
import pers.goweii.dialog.utils.StatusBarUtils;
import pers.goweii.dialog.utils.blur.BlurUtils;

/**
 * @author CuiZhen
 * @date 2018/4/30
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class AnyDialog extends Dialog {

    private final Context context;
    private final ViewHolder viewHolder;

    private boolean isDismissing = false;

    private ImageView backgroundView;
    private RelativeLayout contentWrapper;

    @LayoutRes
    private int contentId = -1;
    private View contentView = null;

    private float dimAmount = 0;
    private float backgroundBlurRadius = 0;
    private float backgroundBlurScale = 1;
    @ColorInt
    private int backgroundColor = Color.TRANSPARENT;
    private Drawable backgroundDrawable = null;
    private Bitmap backgroundBitmap = null;
    @DrawableRes
    private int backgroundResource = -1;

    private long backgroundAnimDuration = 200;
    private long contentAnimDuration = 250;

    private IAnim backgroundAnim = null;
    private Animation backgroundInAnim = null;
    private Animation backgroundOutAnim = null;
    private IAnim contentAnim = null;
    private Animation contentInAnim = null;
    private Animation contentOutAnim = null;

    private IDataBinder dataBinder = null;

    private int gravity = -1;
    private boolean cancelableOnTouchOutside = true;

    private OnDialogShowListener mOnDialogShowListener = null;
    private OnDialogDismissListener mOnDialogDismissListener = null;

    private AnyDialog(@NonNull Context context) {
        super(context, R.style.DialogFullscreen);
        this.context = context;
        this.viewHolder = new ViewHolder(this);
    }

    public static AnyDialog with(@NonNull Context context) {
        return new AnyDialog(context);
    }

    @Override
    public void show() {
        super.show();
        if (mOnDialogShowListener != null) {
            mOnDialogShowListener.onShowing();
        }
    }

    @Override
    public void dismiss() {
        if (isDismissing) {
            return;
        }
        isDismissing = true;
        if (mOnDialogDismissListener != null) {
            mOnDialogDismissListener.onDismissing();
        }
        startBackgroundOutAnim();
        startContentOutAnim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (contentView != null) {
                    contentView.setAlpha(1);
                    contentView.setScaleX(1);
                    contentView.setScaleY(1);
                    contentView.setTranslationX(0);
                    contentView.setTranslationY(0);
                }
                AnyDialog.super.dismiss();
                isDismissing = false;
                if (mOnDialogDismissListener != null) {
                    mOnDialogDismissListener.onDismissed();
                }
            }
        }, getDuration());
    }

    public View getContentView() {
        return contentView;
    }

    private long getDuration() {
        return Math.max(backgroundAnimDuration, contentAnimDuration);
    }

    private void initWindow() {
        Window window = getWindow();
        if (window == null) {
            return;
        }

        window.setWindowAnimations(0);
        window.clearFlags(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 1f;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = DisplayInfoUtils.getInstance(context).getAppUsableScreenSize().y;
        layoutParams.dimAmount = dimAmount;
        window.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        window.setAttributes(layoutParams);

        View decorView = window.getDecorView();
        decorView.setPadding(0, 0, 0, 0);
        decorView.setBackgroundColor(Color.TRANSPARENT);
        StatusBarUtils.translucentStatusBar(window);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_full_screen);
        initWindow();

        backgroundView = findViewById(R.id.iv_background);
        if (backgroundView != null) {
            initBackground();
        }

        contentWrapper = findViewById(R.id.rl_content);
        if (contentWrapper != null) {
            initContentWrapper();
        }

        viewHolder.bindListener(contentWrapper);

        if (dataBinder != null) {
            dataBinder.bind(this);
        }
    }

    private void initContentWrapper() {
        contentWrapper.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                contentWrapper.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] locationOnScreen = new int[2];
                contentWrapper.getLocationOnScreen(locationOnScreen);
                int appUsableScreenWidth = DisplayInfoUtils.getInstance(context).getAppUsableScreenSize().x;
                int appUsableScreenHeight = DisplayInfoUtils.getInstance(context).getAppUsableScreenSize().y;
                ViewGroup.LayoutParams params1 = contentWrapper.getLayoutParams();
                params1.width = appUsableScreenWidth - locationOnScreen[0];
                params1.height = appUsableScreenHeight - locationOnScreen[1];
                contentWrapper.setLayoutParams(params1);
                ViewGroup.LayoutParams params2 = backgroundView.getLayoutParams();
                params2.width = appUsableScreenWidth - locationOnScreen[0];
                params2.height = appUsableScreenHeight - locationOnScreen[1];
                backgroundView.setLayoutParams(params2);
                if (backgroundBlurRadius > 0) {
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        Bitmap snapshot = ScreenShotUtils.snapshotWithStatusBar(activity);
                        int w;
                        if (params2.width > snapshot.getWidth() - locationOnScreen[0]) {
                            w = snapshot.getWidth() - locationOnScreen[0];
                        } else {
                            w = params2.width;
                        }
                        int h;
                        if (params2.height > snapshot.getHeight() - locationOnScreen[1]) {
                            h = snapshot.getHeight() - locationOnScreen[1];
                        } else {
                            h = params2.height;
                        }
                        Bitmap original = Bitmap.createBitmap(snapshot, locationOnScreen[0], locationOnScreen[1], w, h);
                        snapshot.recycle();
                        Bitmap blur = BlurUtils.blur(context, original, backgroundBlurRadius, backgroundBlurScale);
                        original.recycle();
                        backgroundView.setScaleType(ImageView.ScaleType.FIT_START);
                        backgroundView.setImageBitmap(blur);
                        backgroundView.setColorFilter(backgroundColor);
                    }
                }
                startContentInAnim();
                startBackgroundInAnim();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnDialogShowListener != null) {
                            mOnDialogShowListener.onShown();
                        }
                    }
                }, getDuration());
                return true;
            }
        });
        if (contentId != -1) {
            contentView = LayoutInflater.from(context).inflate(contentId, contentWrapper, false);
        }
        if (contentView != null) {
            contentView.setFocusable(true);
            contentView.setClickable(true);
            contentWrapper.addView(contentView);
        }
        if (gravity != -1) {
            contentWrapper.setGravity(gravity);
        }
    }

    private void initBackground() {
        if (cancelableOnTouchOutside) {
            backgroundView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        if (backgroundBlurRadius <= 0) {
            backgroundView.setVisibility(View.VISIBLE);
            if (backgroundBitmap != null) {
                backgroundView.setImageBitmap(backgroundBitmap);
                backgroundView.setColorFilter(backgroundColor);
            } else if (backgroundResource != -1) {
                backgroundView.setImageResource(backgroundResource);
                backgroundView.setColorFilter(backgroundColor);
            } else if (backgroundDrawable != null) {
                backgroundView.setImageDrawable(backgroundDrawable);
                backgroundView.setColorFilter(backgroundColor);
            } else if (backgroundColor != Color.TRANSPARENT) {
                backgroundView.setImageDrawable(new ColorDrawable(backgroundColor));
            }
        }
    }

    private void startContentInAnim() {
        if (contentAnim != null) {
            contentAnimDuration = contentAnim.inAnim(contentView);
        } else {
            if (contentInAnim != null) {
                contentView.startAnimation(contentInAnim);
            } else {
                AnimHelper.startZoomInAnim(contentView, contentAnimDuration);
            }
        }
    }

    private void startContentOutAnim() {
        if (contentAnim != null) {
            contentAnimDuration = contentAnim.outAnim(contentView);
        } else {
            if (contentOutAnim != null) {
                contentView.startAnimation(contentOutAnim);
            } else {
                AnimHelper.startZoomOutAnim(contentView, contentAnimDuration);
            }
        }
    }

    private void startBackgroundInAnim() {
        if (backgroundAnim != null) {
            backgroundAnimDuration = backgroundAnim.inAnim(backgroundView);
        } else {
            if (backgroundInAnim != null) {
                backgroundView.startAnimation(backgroundInAnim);
            } else {
                AnimHelper.startAlphaInAnim(backgroundView, backgroundAnimDuration);
            }
        }
    }

    private void startBackgroundOutAnim() {
        if (backgroundAnim != null) {
            backgroundAnimDuration = backgroundAnim.outAnim(backgroundView);
        } else {
            if (backgroundOutAnim != null) {
                backgroundView.startAnimation(backgroundOutAnim);
            } else {
                AnimHelper.startAlphaOutAnim(backgroundView, backgroundAnimDuration);
            }
        }
    }

    public AnyDialog contentAnim(IAnim contentAnim) {
        this.contentAnim = contentAnim;
        return this;
    }

    public AnyDialog contentInAnim(@AnimRes int anim) {
        contentInAnim(AnimationUtils.loadAnimation(context, anim));
        return this;
    }

    public AnyDialog contentInAnim(@NonNull Animation anim) {
        contentInAnim = anim;
        contentAnimDuration = Math.max(contentAnimDuration, contentInAnim.getDuration());
        return this;
    }

    public AnyDialog contentOutAnim(@AnimRes int anim) {
        contentOutAnim(AnimationUtils.loadAnimation(context, anim));
        return this;
    }

    public AnyDialog contentOutAnim(@NonNull Animation anim) {
        contentOutAnim = anim;
        contentAnimDuration = Math.max(contentAnimDuration, contentOutAnim.getDuration());
        return this;
    }

    public AnyDialog backgroundAnim(IAnim backgroundAnim) {
        this.backgroundAnim = backgroundAnim;
        return this;
    }

    public AnyDialog backgroundInAnim(@AnimRes int anim) {
        backgroundInAnim(AnimationUtils.loadAnimation(context, anim));
        return this;
    }

    public AnyDialog backgroundInAnim(@NonNull Animation anim) {
        backgroundInAnim = anim;
        backgroundAnimDuration = Math.max(backgroundAnimDuration, backgroundInAnim.getDuration());
        return this;
    }

    public AnyDialog backgroundOutAnim(@AnimRes int anim) {
        backgroundOutAnim(AnimationUtils.loadAnimation(context, anim));
        return this;
    }

    public AnyDialog backgroundOutAnim(@NonNull Animation anim) {
        backgroundOutAnim = anim;
        backgroundAnimDuration = Math.max(backgroundAnimDuration, backgroundOutAnim.getDuration());
        return this;
    }

    public AnyDialog defaultContentAnimDuration(long defaultAnimDuration) {
        this.contentAnimDuration = defaultAnimDuration;
        return this;
    }

    public AnyDialog defaultBackgroundAnimDuration(long defaultAnimDuration) {
        this.backgroundAnimDuration = defaultAnimDuration;
        return this;
    }

    public AnyDialog contentView(View contentView) {
        this.contentView = contentView;
        return this;
    }

    public AnyDialog contentView(@LayoutRes int contentViewId) {
        contentId = contentViewId;
        return this;
    }

    /**
     * 设置背景为当前activity的高斯模糊效果
     * 设置之后其他背景设置方法失效，仅{@link #backgroundColorInt(int)}生效
     * 且设置的backgroundColor值调用imageView.setColorFilter(backgroundColor)设置
     * 建议此时的{@link #backgroundColorInt(int)}为半透明颜色
     *
     * @param radius 模糊半径
     * @return AnyDialog
     */
    public AnyDialog backgroundBlurRadius(@FloatRange(from = 0, fromInclusive = false, to = 25) float radius) {
        backgroundBlurRadius = radius;
        return this;
    }

    public AnyDialog backgroundBlurScale(@FloatRange(from = 1) float scale) {
        backgroundBlurScale = scale;
        return this;
    }

    public AnyDialog backgroundBitmap(Bitmap bitmap) {
        backgroundBitmap = bitmap;
        return this;
    }

    public AnyDialog backgroundResource(@DrawableRes int resource) {
        backgroundResource = resource;
        return this;
    }

    public AnyDialog backgroundDrawable(Drawable drawable) {
        backgroundDrawable = drawable;
        return this;
    }

    /**
     * 背景变暗程度
     * 在刘海屏手机，Dialog无法全屏到刘海区域，所以ImageView背景层无法到刘海区域，
     * 但是Window默认的dimAmount可以生效，考虑到大部分情况都只是需要背景变暗，不需要
     * 显示一个图片或高斯模糊，所以增加这个参数控制
     * 不要和其他backgroundXxx()方法同时使用
     *
     * @param dimAmount 变暗程度
     * @return AnyDialog
     */
    public AnyDialog dimAmount(@FloatRange(from = 0, to = 1) float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    /**
     * 在调用了{@link #backgroundBitmap(Bitmap)}或者{@link #backgroundBlurRadius(float)}方法后
     * 该颜色值将调用imageView.setColorFilter(backgroundColor)设置
     * 建议此时传入的颜色为半透明颜色
     *
     * @param color ColorInt
     * @return AnyDialog
     */
    public AnyDialog backgroundColorInt(@ColorInt int color) {
        backgroundColor = color;
        return this;
    }

    public AnyDialog backgroundColorRes(@ColorRes int color) {
        backgroundColor = context.getResources().getColor(color);
        return this;
    }

    public AnyDialog cancelableOnClickKeyBack(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public AnyDialog cancelableOnTouchOutside(boolean cancelable) {
        setCanceledOnTouchOutside(cancelable);
        this.cancelableOnTouchOutside = cancelable;
        return this;
    }

    /**
     * {@link android.view.Gravity}
     *
     * @param gravity Gravity
     * @return AnyDialog
     */
    public AnyDialog gravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public AnyDialog bindData(IDataBinder dataBinder) {
        this.dataBinder = dataBinder;
        return this;
    }

    public AnyDialog onClick(@IdRes int viewId, OnDialogClickListener listener) {
        viewHolder.addOnClickListener(viewId, listener);
        return this;
    }

    public AnyDialog onClick(OnDialogClickListener listener, @IdRes int... viewIds) {
        if (viewIds == null || viewIds.length == 0) {
            return this;
        }
        for (int viewId : viewIds) {
            viewHolder.addOnClickListener(viewId, listener);
        }
        return this;
    }

    public AnyDialog onClickToDismiss(@IdRes int... viewIds) {
        if (viewIds == null || viewIds.length == 0) {
            return this;
        }
        for (int viewId : viewIds) {
            viewHolder.addOnClickListener(viewId, new OnDialogClickListener() {
                @Override
                public void onClick(AnyDialog anyDialog, View v) {
                    dismiss();
                }
            });
        }
        return this;
    }

    public <V extends View> V getView(@IdRes int viewId) {
        return viewHolder.getView(viewId);
    }

    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    public AnyDialog onDialogShowListener(OnDialogShowListener onDialogShowListener) {
        mOnDialogShowListener = onDialogShowListener;
        return this;
    }

    public AnyDialog onDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
        mOnDialogDismissListener = onDialogDismissListener;
        return this;
    }
}