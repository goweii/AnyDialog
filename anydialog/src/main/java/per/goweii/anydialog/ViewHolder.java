package per.goweii.anydialog;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class ViewHolder {

    private final AnyDialog mAnyDialog;

    private final FrameLayout mContainer;
    private final ImageView mBackground;
    private View mContent = null;

    private SparseArray<View> views = null;
    private SparseArray<OnDialogClickListener> onClickListeners = null;

    ViewHolder(AnyDialog anyDialog) {
        this.mAnyDialog = anyDialog;
        mContainer = new FrameLayout(anyDialog.getContext());
        mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mBackground = new ImageView(anyDialog.getContext());
        mBackground.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        mContainer.addView(mBackground);
    }

    public FrameLayout getContainer(){
        return mContainer;
    }

    public ImageView getBackground() {
        return mBackground;
    }

    public View getContent() {
        return mContent;
    }

    void setContent(View content) {
        mContent = content;
    }

    void bindListener() {
        if (onClickListeners == null) {
            return;
        }
        for (int i = 0; i < onClickListeners.size(); i++) {
            int viewId = onClickListeners.keyAt(i);
            final OnDialogClickListener listener = onClickListeners.valueAt(i);
            getView(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(mAnyDialog, v);
                }
            });
        }
    }

    <V extends View> V getView(@IdRes int viewId) {
        if (views == null) {
            views = new SparseArray<>();
        }
        if (views.indexOfKey(viewId) < 0) {
            V view = mContainer.findViewById(viewId);
            views.put(viewId, view);
            return view;
        }
        return (V) views.get(viewId);
    }

    void addOnClickListener(@IdRes int viewId, OnDialogClickListener listener) {
        if (onClickListeners == null) {
            onClickListeners = new SparseArray<>();
        }
        if (onClickListeners.indexOfKey(viewId) < 0) {
            onClickListeners.put(viewId, listener);
        }
    }
}