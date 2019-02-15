package per.goweii.anydialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

    private float dimAmount = 0.382F;
    private int backgroundColorInt = Color.BLACK;

    private IAnim contentAnim = null;
    private AnimatorSet mInAnim = null;
    private AnimatorSet mOutAnim = null;

    private IDataBinder dataBinder = null;

    private int gravity = -1;
    private boolean cancelableOnTouchOutside = true;

    private OnDialogShowListener mOnDialogShowListener = null;
    private OnDialogDismissListener mOnDialogDismissListener = null;
    private OnDialogVisibleChangeListener mOnDialogVisibleChangeListener = null;

    private boolean insideStatusBar = false;
    private boolean insideNavigationBar = false;

    private AnyDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        this.mViewHolder = new ViewHolder(this);
    }

    public static AnyDialog with(@NonNull Context context) {
        return new AnyDialog(context);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        startOutAnim();
    }

    public AnyDialog contentAnim(IAnim contentAnim) {
        this.contentAnim = contentAnim;
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
    public AnyDialog dimAmount(@FloatRange(from = 0, to = 1) float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    /**
     * 背景色
     *
     * @param colorInt 背景色
     * @return AnyDialog
     */
    public AnyDialog backgroundColorInt(@ColorInt int colorInt) {
        this.backgroundColorInt = colorInt;
        return this;
    }

    /**
     * 背景色
     *
     * @param colorRes 背景色
     * @return AnyDialog
     */
    public AnyDialog backgroundColorRes(@ColorRes int colorRes) {
        this.backgroundColorInt = ContextCompat.getColor(mContext, colorRes);
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

    public AnyDialog insideStatusBar(boolean insideStatusBar) {
        this.insideStatusBar = insideStatusBar;
        return this;
    }

    public AnyDialog insideNavigationBar(boolean insideNavigationBar) {
        this.insideNavigationBar = insideNavigationBar;
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

    public AnyDialog onDialogShowListener(OnDialogShowListener onDialogShowListener) {
        mOnDialogShowListener = onDialogShowListener;
        return this;
    }

    public AnyDialog onDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
        mOnDialogDismissListener = onDialogDismissListener;
        return this;
    }

    public void onDialogVisibleChangeListener(OnDialogVisibleChangeListener onDialogVisibleChangeListener) {
        mOnDialogVisibleChangeListener = onDialogVisibleChangeListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mViewHolder.getContainer());

        initWindow();

        initContainer();
        initBackground();
        initContent();

        mViewHolder.bindListener();

        if (mOnDialogVisibleChangeListener != null) {
            mOnDialogVisibleChangeListener.onShow(AnyDialog.this);
        }
        if (dataBinder != null) {
            dataBinder.bind(this);
        }
    }

    private void initContainer() {
        if (cancelableOnTouchOutside) {
            mViewHolder.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        mViewHolder.getContainer().post(new Runnable() {
            @Override
            public void run() {
                startInAnim();
            }
        });
    }

    private void initBackground() {
        ColorDrawable colorDrawable = new ColorDrawable(backgroundColorInt);
        colorDrawable.setAlpha((int) (dimAmount * 255));
        mViewHolder.getBackground().setImageDrawable(colorDrawable);
    }

    private void initContent() {
        if (mViewHolder.getContent() == null) {
            return;
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mViewHolder.getContent().getLayoutParams();
        if (gravity != -1) {
            params.gravity = gravity;
        } else if (params.gravity == FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY) {
            params.gravity = Gravity.CENTER;
        }
        if (params.width == FrameLayout.LayoutParams.MATCH_PARENT) {
            params.gravity |= Gravity.LEFT;
        }
        if (params.height == FrameLayout.LayoutParams.MATCH_PARENT) {
            params.gravity |= Gravity.TOP;
        }
        if (insideStatusBar) {
            params.topMargin += getStatusBarHeight();
        }
        if (insideNavigationBar) {
            Activity activity = getActivity();
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
                params.leftMargin += acLeft - adLeft;
                params.rightMargin += -(acRight - adRight);
                params.bottomMargin += -(acBottom - adBottom);
            }
        }
        mViewHolder.getContent().setFocusable(true);
        mViewHolder.getContent().setClickable(true);
        mViewHolder.getContainer().addView(mViewHolder.getContent());
    }

    private AnimatorSet createInAnim() {
        Animator contentInAnimator = null;
        if (contentAnim != null) {
            contentInAnimator = contentAnim.inAnim(mViewHolder.getContent());
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

    private void startInAnim() {
        if (mInAnim != null && mInAnim.isRunning()) {
            return;
        }
        mInAnim = createInAnim();
        mInAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnDialogShowListener != null) {
                    mOnDialogShowListener.onShowing(AnyDialog.this);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mInAnim.cancel();
                mInAnim = null;
                if (mOnDialogShowListener != null) {
                    mOnDialogShowListener.onShown(AnyDialog.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mInAnim.start();
    }

    private AnimatorSet createOutAnim() {
        Animator contentOutAnimator = null;
        if (contentAnim != null) {
            contentOutAnimator = contentAnim.outAnim(mViewHolder.getContent());
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

    private void startOutAnim() {
        if (mOutAnim != null && mOutAnim.isRunning()) {
            return;
        }
        mOutAnim = createOutAnim();
        mOutAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnDialogDismissListener != null) {
                    mOnDialogDismissListener.onDismissing(AnyDialog.this);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                AnyDialog.super.dismiss();
                mOutAnim.cancel();
                mOutAnim = null;
                if (mOnDialogDismissListener != null) {
                    mOnDialogDismissListener.onDismissed(AnyDialog.this);
                }
                if (mOnDialogVisibleChangeListener != null) {
                    mOnDialogVisibleChangeListener.onDismiss(AnyDialog.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mOutAnim.start();
    }

    /**
     * 从当前上下文获取Activity
     */
    @Nullable
    private Activity getActivity() {
        if (mContext instanceof Activity) {
            return (Activity) mContext;
        }
        if (mContext instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) mContext).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        return null;
    }

    private int getStatusBarHeight() {
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}