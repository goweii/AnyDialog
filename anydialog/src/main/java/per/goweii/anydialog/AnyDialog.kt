package per.goweii.anydialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.*

open class AnyDialog(
        context: Context,
        private val initializer: (AnyDialog.() -> Unit)? = null
) : Dialog(context, R.style.Dialog) {

    private val viewHolder = ViewHolder()

    lateinit var container: ViewGroup

    @LayoutRes
    open var contentRes: Int = 0
    open lateinit var content: View

    @StyleRes
    open var animation: Int = R.style.DialogAnimDef

    open var dimBehind: Boolean = true
    @FloatRange(from = 0.0, to = 1.0)
    open var dimAmount: Float? = null

    open var gravity: Int? = null

    open var width: Int? = null
    open var height: Int? = null
    open var fullscreen: Boolean = false

    open var fitSystemWindow: Boolean = false
    open var fitSystemLeft: Boolean = false
    open var fitSystemTop: Boolean = false
    open var fitSystemRight: Boolean = false
    open var fitSystemBottom: Boolean = false

    open var style: Style? = null
        set(value) {
            field = value?.also {
                animation = it.animRes
                gravity = it.gravity
                width = it.width
                height = it.height
                dragDirection = it.dragDirection
            }
        }

    open var dragDirection: DragDirection? = null

    var dataBind: (AnyDialog.() -> Unit)? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        initializer?.invoke(this)
        super.onCreate(savedInstanceState)
        container = onCreateContainer()
        content = onCreateView(container, savedInstanceState)
        container.layoutParams = content.layoutParams
        content.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(content)
        viewHolder.attach(container)
        onViewCreated(container, savedInstanceState)
        setContentView(container)
        onViewAttached(container, savedInstanceState)
    }

    @CallSuper
    protected open fun onCreateContainer(): ViewGroup {
        val parent = window!!.decorView.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        return dragDirection?.let {
            DragLayout3(parent.context).apply {
                dragStyle(when (it) {
                    DragDirection.LEFT -> DragLayout3.DragStyle.Left
                    DragDirection.TOP -> DragLayout3.DragStyle.Top
                    DragDirection.RIGHT -> DragLayout3.DragStyle.Right
                    DragDirection.BOTTOM -> DragLayout3.DragStyle.Bottom
                })
                onDragging { f ->
                    dimAmount?.let {
                        window?.setDimAmount(it * (1F - f))
                    }
                }
                onDragEnd { dismiss() }
            }
        } ?: run {
            FrameLayout(parent.context)
        }
    }

    @CallSuper
    protected open fun onCreateView(container: ViewGroup, savedInstanceState: Bundle?): View {
        return if (this::content.isInitialized) {
            this.content
        } else {
            layoutInflater.inflate(contentRes, container, false)
        }
    }

    @CallSuper
    protected open fun onViewCreated(content: View, savedInstanceState: Bundle?) {
        if (fitSystemWindow || fitSystemLeft || fitSystemTop || fitSystemRight || fitSystemBottom) {
            content.fitsSystemWindows = true
            if (content is ViewGroup) {
                content.clipToPadding = false
                if (!fitSystemWindow && (!fitSystemLeft || !fitSystemTop || !fitSystemRight || !fitSystemBottom)) {
                    content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                        override fun onPreDraw(): Boolean {
                            if (content.viewTreeObserver.isAlive) {
                                content.viewTreeObserver.removeOnPreDrawListener(this)
                            }
                            val l = if (fitSystemLeft) content.paddingLeft else 0
                            val t = if (fitSystemTop) content.paddingTop else 0
                            val r = if (fitSystemRight) content.paddingRight else 0
                            val b = if (fitSystemBottom) content.paddingBottom else 0
                            content.setPadding(l, t, r, b)
                            return true
                        }
                    })
                }
            }
        }
        val params = content.layoutParams as FrameLayout.LayoutParams
        if (gravity == null && params.gravity != FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY) {
            gravity = params.gravity
        }
        if (width == null || height == null) {
            Point().apply {
                window!!.windowManager.defaultDisplay.getSize(this)
            }.also { size ->
                if (width == null) {
                    width = if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                        if (params.leftMargin == 0 && params.rightMargin == 0) {
                            params.width
                        } else {
                            size.x - params.leftMargin - params.rightMargin
                        }
                    } else {
                        params.width
                    }
                }
                if (height == null) {
                    height = if (params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                        if (params.topMargin == 0 && params.bottomMargin == 0) {
                            params.height
                        } else {
                            size.y - params.topMargin - params.bottomMargin
                        }
                    } else {
                        params.height
                    }
                }
            }
        }
        dataBind?.invoke(this)
        viewHolder.bindClickListener(this)
    }

    @CallSuper
    protected open fun onViewAttached(content: View, savedInstanceState: Bundle?) {
        val w = window!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            w.statusBarColor = Color.TRANSPARENT
        }
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        val decor: View = w.decorView
        decor.setPadding(0, 0, 0, 0)
        decor.setBackgroundColor(Color.TRANSPARENT)
        w.setWindowAnimations(animation)
        gravity?.let { w.attributes.gravity = it }
        width?.let { w.attributes.width = it }
        height?.let { w.attributes.height = it }
        if (fullscreen) {
            w.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            w.attributes.height = WindowManager.LayoutParams.MATCH_PARENT
        }
    }

    override fun onStart() {
        resetDim()
        super.onStart()
    }

    private fun resetDim() {
        val w = window!!
        if (dimBehind) {
            w.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            w.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        dimAmount?.let {
            w.setDimAmount(it)
        } ?: run {
            dimAmount = w.attributes.dimAmount
        }
    }

    /**
     * 是否可背景变暗，所有dialog公用同一个背景遮罩
     * 为false时dimAmount属性失效
     * @see dimAmount
     */
    fun dimBehind(dimBehind: Boolean): AnyDialog {
        this.dimBehind = dimBehind
        return this
    }

    /**
     * 背景变暗，所有dialog公用同一个背景遮罩
     * 如果2个dialog的dimAmount不同，则以最后弹出的为准
     * 即，如果先弹出一个dimAmount=1的弹窗，背景会变为黑色
     * 再弹出一个dimAmount=0的弹窗，背景会变为透明，可以看到下面的Activity
     * 这时应该设置这个弹窗的dimBehind=false以达到透明背景效果
     * @see dimBehind
     */
    fun dimAmount(dimAmount: Float): AnyDialog {
        this.dimAmount = dimAmount
        return this
    }

    fun cancelable(cancelable: Boolean): AnyDialog {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
        return this
    }

    fun cancelableOnClickKeyBack(cancelable: Boolean): AnyDialog {
        setCancelable(cancelable)
        return this
    }

    fun cancelableOnTouchOutside(cancelable: Boolean): AnyDialog {
        setCanceledOnTouchOutside(cancelable)
        return this
    }

    fun click(vararg ids: Int, listener: AnyDialog.(view: View) -> Unit): AnyDialog {
        viewHolder.addClickListener(ids, listener)
        return this
    }

    fun clickDismiss(vararg ids: Int, listener: (AnyDialog.(view: View) -> Unit)? = null): AnyDialog {
        viewHolder.addClickListener(ids) {
            dismiss()
            listener?.invoke(this, it)
        }
        return this
    }

    fun bindData(dataBind: AnyDialog.() -> Unit): AnyDialog {
        this.dataBind = dataBind
        return this
    }

    fun onShow(listener: AnyDialog.() -> Unit): AnyDialog {
        setOnShowListener {
            listener.invoke(this)
        }
        return this
    }

    fun onDismiss(listener: AnyDialog.() -> Unit): AnyDialog {
        setOnDismissListener {
            listener.invoke(this)
        }
        return this
    }

    fun onKey(listener: AnyDialog.(code: Int, event: KeyEvent) -> Boolean): AnyDialog {
        setOnKeyListener { _, code, event ->
            listener.invoke(this, code, event)
        }
        return this
    }

    fun <V : View> find(@IdRes id: Int): V? {
        return viewHolder.find(id)
    }

    enum class Style(
            val animRes: Int,
            val gravity: Int,
            val width: Int?,
            val height: Int?,
            val dragDirection: DragDirection?
    ) {
        CENTER(R.style.DialogAnimDef, Gravity.CENTER,
                null, null,
                null),
        BOTTOM(R.style.DialogAnimBottom, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                WindowManager.LayoutParams.MATCH_PARENT, null,
                DragDirection.BOTTOM),
        TOP(R.style.DialogAnimTop, Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                WindowManager.LayoutParams.MATCH_PARENT, null,
                DragDirection.TOP),
        LEFT(R.style.DialogAnimLeft, Gravity.LEFT or Gravity.CENTER_VERTICAL,
                null, WindowManager.LayoutParams.MATCH_PARENT,
                DragDirection.LEFT),
        RIGHT(R.style.DialogAnimRight, Gravity.RIGHT or Gravity.CENTER_VERTICAL,
                null, WindowManager.LayoutParams.MATCH_PARENT,
                DragDirection.RIGHT)
    }

    enum class DragDirection {
        LEFT, TOP, RIGHT, BOTTOM
    }
}