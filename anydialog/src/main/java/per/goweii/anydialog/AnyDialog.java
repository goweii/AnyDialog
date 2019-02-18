package per.goweii.anydialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import per.goweii.burred.Blurred;

/**
 * @author CuiZhen
 * @date 2018/4/30
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class AnyDialog extends Dialog {

    private static final long ANIM_DURATION_DEFAULT = 300L;

    private final ViewHolder mViewHolder;
    private final Context mContext;

    private float mBackgroundDimAmount = -1;
    private int mBackgroundColorInt = -1;
    private float mBackgroundBlurPercent = 0;
    private float mBackgroundBlurRadius = 0;
    private float mBackgroundBlurScale = 2;

    private IAnim mContentAnim = null;
    private AnimatorSet mInAnim = null;
    private AnimatorSet mOutAnim = null;

    private int mGravity = -1;
    private boolean mCancelableOnTouchOutside = true;

    private boolean mInsideStatusBar = false;
    private boolean mInsideNavigationBar = false;

    private int[] mInsideParamsOff = new int[]{0, 0, 0, 0};

    private IDataBinder mDataBinder = null;

    private OnDialogCreatedListener mOnDialogCreatedListener = null;
    private OnDialogEnterListener mOnDialogEnterListener = null;
    private OnDialogShownListener mOnDialogShownListener = null;
    private OnDialogExitListener mOnDialogExitListener = null;
    private OnDialogDismissedListener mOnDialogDismissedListener = null;

    public static AnyDialog with(@NonNull Context context) {
        return new AnyDialog(context);
    }

    private AnyDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        this.mViewHolder = new ViewHolder(this);
        Blurred.init(mContext);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        onDialogExit();
    }

    public AnyDialog contentAnim(IAnim contentAnim) {
        this.mContentAnim = contentAnim;
        return this;
    }

    public AnyDialog contentView(@NonNull View contentView) {
        mViewHolder.setContent(contentView);
        return this;
    }

    public AnyDialog contentView(@LayoutRes int contentViewId) {
        return contentView(LayoutInflater.from(mContext).inflate(contentViewId, mViewHolder.getContainer(), false));
    }

    /**
     * 背景变暗程度
     *
     * @param dimAmount 变暗程度
     * @return AnyDialog
     */
    public AnyDialog backgroundDimAmount(@FloatRange(from = 0, to = 1) float dimAmount) {
        this.mBackgroundDimAmount = dimAmount;
        return this;
    }

    /**
     * 背景色
     *
     * @param colorInt 背景色
     * @return AnyDialog
     */
    public AnyDialog backgroundColorInt(@ColorInt int colorInt) {
        this.mBackgroundColorInt = colorInt;
        return this;
    }

    /**
     * 背景色
     *
     * @param colorRes 背景色
     * @return AnyDialog
     */
    public AnyDialog backgroundColorRes(@ColorRes int colorRes) {
        this.mBackgroundColorInt = ContextCompat.getColor(mContext, colorRes);
        return this;
    }

    public AnyDialog backgroundBlurPercent(float backgroundBlurPercent) {
        mBackgroundBlurPercent = backgroundBlurPercent;
        return this;
    }

    public AnyDialog backgroundBlurRadius(float backgroundBlurRadius) {
        mBackgroundBlurRadius = backgroundBlurRadius;
        return this;
    }

    public AnyDialog backgroundBlurScale(float backgroundBlurScale) {
        mBackgroundBlurScale = backgroundBlurScale;
        return this;
    }

    public AnyDialog cancelableOnClickKeyBack(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public AnyDialog cancelableOnTouchOutside(boolean cancelable) {
        setCanceledOnTouchOutside(cancelable);
        this.mCancelableOnTouchOutside = cancelable;
        return this;
    }

    public AnyDialog insideStatusBar(boolean insideStatusBar) {
        this.mInsideStatusBar = insideStatusBar;
        return this;
    }

    public AnyDialog insideNavigationBar(boolean insideNavigationBar) {
        this.mInsideNavigationBar = insideNavigationBar;
        return this;
    }

    /**
     * {@link android.view.Gravity}
     *
     * @param gravity Gravity
     * @return AnyDialog
     */
    public AnyDialog gravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    public AnyDialog bindData(IDataBinder dataBinder) {
        this.mDataBinder = dataBinder;
        return this;
    }

    public AnyDialog onClick(@IdRes int viewId, OnDialogClickListener listener) {
        mViewHolder.addOnClickListener(viewId, listener);
        return this;
    }

    public AnyDialog onClick(OnDialogClickListener listener, @IdRes int... viewIds) {
        if (viewIds == null || viewIds.length == 0) {
            return this;
        }
        for (int viewId : viewIds) {
            mViewHolder.addOnClickListener(viewId, listener);
        }
        return this;
    }

    public AnyDialog onClickToDismiss(@IdRes int... viewIds) {
        return onClick(new OnDialogClickListener() {
            @Override
            public void onClick(AnyDialog anyDialog, View view) {
                dismiss();
            }
        }, viewIds);
    }

    public AnyDialog onDialogCreatedListener(OnDialogCreatedListener onDialogCreatedListener) {
        mOnDialogCreatedListener = onDialogCreatedListener;
        return this;
    }

    public AnyDialog setOnDialogEnterListener(OnDialogEnterListener onDialogEnterListener) {
        mOnDialogEnterListener = onDialogEnterListener;
        return this;
    }

    public AnyDialog setOnDialogShownListener(OnDialogShownListener onDialogShownListener) {
        mOnDialogShownListener = onDialogShownListener;
        return this;
    }

    public AnyDialog setOnDialogExitListener(OnDialogExitListener onDialogExitListener) {
        mOnDialogExitListener = onDialogExitListener;
        return this;
    }

    public AnyDialog setOnDialogDismissedListener(OnDialogDismissedListener onDialogDismissedListener) {
        mOnDialogDismissedListener = onDialogDismissedListener;
        return this;
    }

    public <V extends View> V getView(@IdRes int viewId) {
        return mViewHolder.getView(viewId);
    }

    public ViewHolder getViewHolder() {
        return mViewHolder;
    }

    public FrameLayout getContainer() {
        return mViewHolder.getContainer();
    }

    public ImageView getBackground() {
        return mViewHolder.getBackground();
    }

    public View getContentView() {
        return mViewHolder.getContent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mViewHolder.getContainer());
        initWindow();

        initContainer();
        calculateInsideParamsOff();
        initBackground();
        initContent();
        mViewHolder.bindListener();

        onDialogCreated();
    }

    private void initWindow() {
        Window window = getWindow();
        if (window == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        window.setWindowAnimations(0);
        window.clearFlags(WindowManager.LayoutParams.DIM_AMOUNT_CHANGED);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.dimAmount = 0;
        window.setAttributes(layoutParams);

        View decorView = window.getDecorView();
        decorView.setPadding(0, 0, 0, 0);
        decorView.setBackgroundColor(Color.TRANSPARENT);
    }

    protected void onDialogCreated() {
        if (mOnDialogCreatedListener != null) {
            mOnDialogCreatedListener.onCreated(AnyDialog.this);
        }
        if (mDataBinder != null) {
            mDataBinder.bind(AnyDialog.this);
        }
        mViewHolder.getContainer().post(new Runnable() {
            @Override
            public void run() {
                onDialogEnter();
            }
        });
    }

    protected void onDialogEnter() {
        if (mOnDialogEnterListener != null) {
            mOnDialogEnterListener.onEnter(AnyDialog.this);
        }
        startInAnim();
    }

    protected void onDialogShown() {
        if (mOnDialogShownListener != null) {
            mOnDialogShownListener.onShown(AnyDialog.this);
        }
    }

    protected void onDialogExit() {
        if (mOnDialogExitListener != null) {
            mOnDialogExitListener.onExit(AnyDialog.this);
        }
        startOutAnim();
    }

    protected void onDialogDismissed() {
        AnyDialog.super.dismiss();
        if (mOnDialogDismissedListener != null) {
            mOnDialogDismissedListener.onDismissed(AnyDialog.this);
        }
    }

    private void initContainer() {
        if (mCancelableOnTouchOutside) {
            mViewHolder.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private void initBackground() {
        if (mBackgroundBlurPercent > 0 || mBackgroundBlurRadius > 0) {
            Activity activity = Utils.getActivity(mContext);
            if (activity != null) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mViewHolder.getBackground().getLayoutParams();
                params.leftMargin += mInsideParamsOff[0];
                params.rightMargin += mInsideParamsOff[2];
                params.bottomMargin += mInsideParamsOff[3];
                final View activityDecor = activity.getWindow().getDecorView();
                Bitmap decorBitmap = Utils.snapshot(activityDecor);
                int x = mInsideParamsOff[0];
                int y = 0;
                int w = decorBitmap.getWidth() - x - mInsideParamsOff[2];
                int h = decorBitmap.getHeight() - y - mInsideParamsOff[3];
                Bitmap cutBitmap = Bitmap.createBitmap(decorBitmap, x, y, w, h);
                decorBitmap.recycle();
                Blurred blurred = Blurred.with(cutBitmap)
                        .recycleOriginal(true)
                        .keepSize(false)
                        .scale(mBackgroundBlurScale);
                if (mBackgroundBlurPercent > 0) {
                    blurred.percent(mBackgroundBlurPercent);
                } else if (mBackgroundBlurRadius > 0) {
                    blurred.radius(mBackgroundBlurRadius);
                }
                Bitmap blurBitmap = blurred.blur();
                if (blurBitmap != null) {
                    BitmapDrawable blurDrawable = new BitmapDrawable(mContext.getResources(), blurBitmap);
                    if (mBackgroundColorInt == -1) {
                        mBackgroundColorInt = Color.TRANSPARENT;
                    }
                    if (mBackgroundDimAmount == -1) {
                        mBackgroundDimAmount = 0;
                    }
                    mViewHolder.getBackground().setImageDrawable(blurDrawable);
                    mViewHolder.getBackground().setColorFilter(Utils.alphaColor(mBackgroundColorInt, mBackgroundDimAmount));
                }
            }
        } else {
            if (mBackgroundColorInt == -1) {
                mBackgroundColorInt = Color.BLACK;
                if (mBackgroundDimAmount == -1) {
                    mBackgroundDimAmount = 0.382F;
                }
            } else {
                if (mBackgroundDimAmount == -1) {
                    mBackgroundDimAmount = 0;
                }
            }
            ColorDrawable colorDrawable = new ColorDrawable(mBackgroundColorInt);
            colorDrawable.setAlpha((int) (mBackgroundDimAmount * 255));
            mViewHolder.getBackground().setImageDrawable(colorDrawable);
        }
    }

    private void initContent() {
        if (mViewHolder.getContent() == null) {
            return;
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mViewHolder.getContent().getLayoutParams();
        if (mGravity != -1) {
            params.gravity = mGravity;
        } else if (params.gravity == FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY) {
            params.gravity = Gravity.CENTER;
        }
        if (params.width == FrameLayout.LayoutParams.MATCH_PARENT) {
            params.gravity |= Gravity.LEFT;
        }
        if (params.height == FrameLayout.LayoutParams.MATCH_PARENT) {
            params.gravity |= Gravity.TOP;
        }
        if (mInsideStatusBar) {
            params.topMargin += mInsideParamsOff[1];
        }
        if (mInsideNavigationBar) {
            params.leftMargin += mInsideParamsOff[0];
            params.rightMargin += mInsideParamsOff[2];
            params.bottomMargin += mInsideParamsOff[3];
        }
        mViewHolder.getContent().setFocusable(true);
        mViewHolder.getContent().setClickable(true);
        mViewHolder.getContainer().addView(mViewHolder.getContent());
    }

    private void calculateInsideParamsOff() {
        mInsideParamsOff[1] = Utils.getStatusBarHeight(mContext);
        Activity activity = Utils.getActivity(mContext);
        if (activity != null) {
            final View activityDecor = activity.getWindow().getDecorView();
            final View activityContent = activityDecor.findViewById(android.R.id.content);
            int[] activityDecorLocation = new int[2];
            activityDecor.getLocationOnScreen(activityDecorLocation);
            final int adLeft = activityDecorLocation[0];
            final int adTop = activityDecorLocation[1];
            final int adRight = adLeft + activityDecor.getWidth();
            final int adBottom = adTop + activityDecor.getHeight();
            int[] activityContentLocation = new int[2];
            activityContent.getLocationOnScreen(activityContentLocation);
            final int acLeft = activityContentLocation[0];
            final int acTop = activityContentLocation[1];
            final int acRight = acLeft + activityContent.getWidth();
            final int acBottom = acTop + activityContent.getHeight();
            mInsideParamsOff[0] = acLeft - adLeft;
            mInsideParamsOff[2] = -(acRight - adRight);
            mInsideParamsOff[3] = -(acBottom - adBottom);
        }
    }

    private void startInAnim() {
        if (mInAnim != null && mInAnim.isRunning()) {
            return;
        }
        mInAnim = createInAnim();
        mInAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mInAnim = null;
                onDialogShown();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mInAnim = null;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mInAnim.start();
    }

    private AnimatorSet createInAnim() {
        Animator contentInAnimator = null;
        if (mContentAnim != null) {
            contentInAnimator = mContentAnim.inAnim(mViewHolder.getContent());
        }
        if (contentInAnimator == null) {
            contentInAnimator = AnimHelper.createZoomInAnim(mViewHolder.getContent());
        }
        if (contentInAnimator.getDuration() < 0) {
            contentInAnimator.setDuration(ANIM_DURATION_DEFAULT);
        }
        Animator backgroundInAnim = AnimHelper.createAlphaInAnim(mViewHolder.getBackground());
        backgroundInAnim.setDuration(contentInAnimator.getDuration());
        AnimatorSet inAnim = new AnimatorSet();
        inAnim.playTogether(contentInAnimator, backgroundInAnim);
        return inAnim;
    }

    private void startOutAnim() {
        if (mOutAnim != null && mOutAnim.isRunning()) {
            return;
        }
        mOutAnim = createOutAnim();
        mOutAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mOutAnim = null;
                onDialogDismissed();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mOutAnim = null;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mOutAnim.start();
    }

    private AnimatorSet createOutAnim() {
        Animator contentOutAnimator = null;
        if (mContentAnim != null) {
            contentOutAnimator = mContentAnim.outAnim(mViewHolder.getContent());
        }
        if (contentOutAnimator == null) {
            contentOutAnimator = AnimHelper.createZoomOutAnim(mViewHolder.getContent());
        }
        if (contentOutAnimator.getDuration() < 0) {
            contentOutAnimator.setDuration(ANIM_DURATION_DEFAULT);
        }
        Animator backgroundOutAnim = AnimHelper.createAlphaOutAnim(mViewHolder.getBackground());
        backgroundOutAnim.setDuration(contentOutAnimator.getDuration());
        AnimatorSet outAnim = new AnimatorSet();
        outAnim.playTogether(contentOutAnimator, backgroundOutAnim);
        return outAnim;
    }
}