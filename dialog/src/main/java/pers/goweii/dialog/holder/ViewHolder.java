package pers.goweii.dialog.holder;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;

/**
 * @author CuiZhen
 * @date 2018/5/20
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class ViewHolder {

    private View contentWrapper;
    private SparseArray<View> views = null;
    private SparseArray<View.OnClickListener> onClickListeners = null;

    public void bindListener(View contentWrapper){
        this.contentWrapper = contentWrapper;
        for (int i = 0; i < onClickListeners.size(); i++) {
            int viewId = onClickListeners.keyAt(i);
            View.OnClickListener onClickListener = onClickListeners.valueAt(i);
            getView(viewId).setOnClickListener(onClickListener);
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

    public void addOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        if (onClickListeners == null) {
            onClickListeners = new SparseArray<>();
        }
        if (onClickListeners.indexOfKey(viewId) < 0) {
            onClickListeners.put(viewId, onClickListener);
        }
    }
}
