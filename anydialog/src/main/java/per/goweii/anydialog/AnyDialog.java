package per.goweii.anydialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.AnimRes;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private final ViewHolder mViewHolder;
    private final Context mContext;

    private boolean isDismissing = false;

    private float dimAmount = 0.3F;
    private int backgroundColorInt = Color.BLACK;

    private IAnim contentAnim = null;
    private Animation contentInAnim = null;
    private Animation contentOutAnim = null;
    private long contentInAnimDuration = 300;
    private long contentOutAnimDuration = 300;

    private IDataBinder dataBinder = null;

    private int gravity = -1;
    private boolean cancelableOnTouchOutside = true;

    private OnDialogShowListener mOnDialogShowListener = null;
    private OnDialogDismissListener mOnDialogDismissListener = null;
    private OnDialogVisibleChangeListener mOnDialogVisibleChangeListener = null;

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
        if (isDismissing) {
            return;
        }
        isDismissing = true;
        if (mOnDialogDismissListener != null) {
            mOnDialogDismissListener.onDismissing(AnyDialog.this);
        }
        startBackgroundOutAnim();
        startContentOutAnim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnyDialog.super.dismiss();
                isDismissing = false;
                if (mOnDialogDismissListener != null) {
                    mOnDialogDismissListener.onDismissed(AnyDialog.this);
                }
                if (mOnDialogVisibleChangeListener != null) {
                    mOnDialogVisibleChangeListener.onDismiss(AnyDialog.this);
                }
            }
        }, contentOutAnimDuration);
    }

    public AnyDialog contentAnim(IAnim contentAnim) {
        this.contentAnim = contentAnim;
        return this;
    }

    public AnyDialog contentInAnim(@AnimRes int anim) {
        return contentInAnim(AnimationUtils.loadAnimation(mContext, anim));
    }

    public AnyDialog contentInAnim(@NonNull Animation anim) {
        contentInAnim = anim;
        contentInAnimDuration = contentInAnim.getDuration();
        return this;
    }

    public AnyDialog contentOutAnim(@AnimRes int anim) {
        return contentOutAnim(AnimationUtils.loadAnimation(mContext, anim));
    }

    public AnyDialog contentOutAnim(@NonNull Animation anim) {
        contentOutAnim = anim;
        contentOutAnimDuration = Math.max(contentOutAnimDuration, contentOutAnim.getDuration());
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

    public FrameLayout getContainer(){
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
                if (mOnDialogShowListener != null) {
                    mOnDialogShowListener.onShowing(AnyDialog.this);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnDialogShowListener != null) {
                            mOnDialogShowListener.onShown(AnyDialog.this);
                        }
                    }
                }, contentInAnimDuration);
                startBackgroundInAnim();
                startContentInAnim();
            }
        });
    }

    private void initBackground() {
        ColorDrawable colorDrawable = new ColorDrawable(backgroundColorInt);
        colorDrawable.setAlpha((int) (dimAmount * 255));
        mViewHolder.getBackground().setImageDrawable(colorDrawable);
    }

    private void initContent() {
        if (mViewHolder.getContent() != null) {
            mViewHolder.getContent().setFocusable(true);
            mViewHolder.getContent().setClickable(true);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mViewHolder.getContent().getLayoutParams();
            if (gravity != -1) {
                params.gravity = gravity;
            } else if (params.gravity == FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY) {
                params.gravity = Gravity.CENTER;
            }
            mViewHolder.getContainer().addView(mViewHolder.getContent());
        }
    }

    private void startBackgroundInAnim() {
        AnimHelper.startAlphaInAnim(mViewHolder.getBackground(), contentInAnimDuration);
    }

    private void startContentInAnim() {
        if (contentAnim != null) {
            contentInAnimDuration = contentAnim.inAnim(mViewHolder.getContent());
        } else {
            if (contentInAnim != null) {
                mViewHolder.getContent().startAnimation(contentInAnim);
            } else {
                AnimHelper.startZoomInAnim(mViewHolder.getContent(), contentInAnimDuration);
            }
        }
    }

    private void startBackgroundOutAnim() {
        AnimHelper.startAlphaOutAnim(mViewHolder.getBackground(), contentOutAnimDuration);
    }

    private void startContentOutAnim() {
        if (contentAnim != null) {
            contentOutAnimDuration = contentAnim.outAnim(mViewHolder.getContent());
        } else {
            if (contentOutAnim != null) {
                mViewHolder.getContent().startAnimation(contentOutAnim);
            } else {
                AnimHelper.startZoomOutAnim(mViewHolder.getContent(), contentOutAnimDuration);
            }
        }
    }
}