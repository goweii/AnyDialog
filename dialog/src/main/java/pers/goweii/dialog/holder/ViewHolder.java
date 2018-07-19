package pers.goweii.dialog.holder;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;

import pers.goweii.dialog.listener.OnDialogClickListener;
import pers.goweii.dialog.surface.AnyDialog;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class ViewHolder {

    private final AnyDialog anyDialog;
    private View contentWrapper;
    private SparseArray<View> views = null;
    private SparseArray<OnDialogClickListener> onClickListeners = null;

    public ViewHolder(AnyDialog anyDialog){
        this.anyDialog = anyDialog;
    }

    public void bindListener(View contentWrapper){
        this.contentWrapper = contentWrapper;
        if (onClickListeners == null){
            return;
        }
        for (int i = 0; i < onClickListeners.size(); i++) {
            int viewId = onClickListeners.keyAt(i);
            final OnDialogClickListener listener = onClickListeners.valueAt(i);
            getView(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(anyDialog, v);
                }
            });
        }
    }

    public  <V extends View> V getView(@IdRes int viewId) {
        if (views == null) {
            views = new SparseArray<>();
        }
        if (views.indexOfKey(viewId) < 0) {
            V view = contentWrapper.findViewById(viewId);
            views.put(viewId, view);
            return view;
        }
        return (V) views.get(viewId);
    }

    public void addOnClickListener(@IdRes int viewId, OnDialogClickListener listener) {
        if (onClickListeners == null) {
            onClickListeners = new SparseArray<>();
        }
        if (onClickListeners.indexOfKey(viewId) < 0) {
            onClickListeners.put(viewId, listener);
        }
    }
}