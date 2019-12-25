package per.goweii.anydialog

import androidx.annotation.IdRes
import android.util.SparseArray
import android.view.View

internal class ViewHolder {
    private var contentView: View? = null

    private val viewCache: SparseArray<View> by lazy { SparseArray<View>(1) }
    private val clickListeners: MutableMap<IntArray, AnyDialog.(view: View) -> Unit> by lazy {
        mutableMapOf<IntArray, AnyDialog.(view: View) -> Unit>()
    }

    internal fun attach(contentView: View){
        this.contentView = contentView
    }

    internal fun <V : View> find(@IdRes id: Int): V? {
        return viewCache[id]?.let {
            it as V
        } ?: contentView?.findViewById<V>(id)?.also {
            viewCache.put(id, it)
        }
    }

    internal fun addClickListener(ids: IntArray, listener: AnyDialog.(view: View) -> Unit) {
        clickListeners[ids] = listener
    }

    internal fun bindClickListener(anyDialog: AnyDialog) {
        clickListeners.iterator().forEach { map ->
            map.key.forEach { id ->
                find<View>(id)?.setOnClickListener {
                    map.value.invoke(anyDialog, it)
                }
            }
        }
    }

}