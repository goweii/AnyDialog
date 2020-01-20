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

open class AnyDialog(context: Context) : Dialog(context, R.style.Dialog) {
    private val viewHolder = ViewHolder()

    private var dataBind: (AnyDialog.() -> Unit)? = null

    private var content: View? = null
    @LayoutRes
    private var layoutRes: Int = 0
    @StyleRes
    private var animRes: Int = R.style.DialogAnimDef
    private var dimBehind: Boolean = true
    @FloatRange(from = 0.0, to = 1.0)
    private var dimAmount: Float? = null
    private var gravity: Int? = null
    private var width: Int? = null
    private var height: Int? = null
    private var fullscreen: Boolean? = null
    private var fitSystemWindow: Boolean? = null

    private var dragDirection: DragDirection? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentParent = window!!.decorView.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val contentView = onCreateView(contentParent, savedInstanceState)
        viewHolder.attach(contentView)
        onViewCreated(contentView, savedInstanceState)
        setContentView(contentView)
        onViewAttached(contentView, savedInstanceState)
    }

    @CallSuper
    protected open fun onCreateView(parent: ViewGroup, savedInstanceState: Bundle?): View {
        val container: ViewGroup = dragDirection?.let {
            DragLayout3(parent.context).apply {
                dragStyle(when (it) {
                    DragDirection.LEFT -> DragLayout3.DragStyle.Left
                    DragDirection.TOP -> DragLayout3.DragStyle.Top
                    DragDirection.RIGHT -> DragLayout3.DragStyle.Right
                    DragDirection.BOTTOM -> DragLayout3.DragStyle.Bottom
                })
                onDragging { f ->
                    dimAmount()?.let { d ->
                        window?.setDimAmount(d * (1F - f))
                    }
                }
                onDragEnd { dismiss() }
            }
        } ?: run {
            FrameLayout(parent.context)
        }
        val content = content ?: layoutInflater.inflate(getLayoutRes(), container, false)
        container.layoutParams = content.layoutParams
        content.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(content)
        return container
    }

    @CallSuper
    protected open fun onViewCreated(contentView: View, savedInstanceState: Bundle?) {
        fitSystemWindow()?.let { contentView.fitsSystemWindows = it }
        val params = contentView.layoutParams as FrameLayout.LayoutParams
        if (gravity == null && params.gravity != FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY)
            gravity = params.gravity
        if (width == null || height == null) {
            val size = Point()
            window!!.windowManager.defaultDisplay.getSize(size)
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
        dataBind?.invoke(this)
        viewHolder.bindClickListener(this)
    }

    @CallSuper
    protected open fun onViewAttached(contentView: View, savedInstanceState: Bundle?) {
        val w = window!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            w.statusBarColor = Color.TRANSPARENT
        }
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        w.setWindowAnimations(animation())
        val decorView: View = w.decorView
        decorView.setPadding(0, 0, 0, 0)
        decorView.setBackgroundColor(Color.TRANSPARENT)
        val attr = w.attributes
        gravity()?.let { attr.gravity = it }
        width()?.let { attr.width = it }
        height()?.let { attr.height = it }
        fullscreen()?.let {
            if (it) {
                attr.width = WindowManager.LayoutParams.MATCH_PARENT
                attr.height = WindowManager.LayoutParams.MATCH_PARENT
            }
        }
        w.attributes = attr
    }

    override fun onStart() {
        resetDim()
        super.onStart()
    }

    private fun resetDim() {
        val w = window!!
        if (dimBehind()) {
            w.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            w.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        dimAmount()?.let {
            w.setDimAmount(it)
        } ?: run {
            dimAmount = w.attributes.dimAmount
        }
    }

    protected open fun getLayoutRes() = layoutRes

    @StyleRes
    protected open fun animation(): Int = animRes

    protected open fun dimBehind(): Boolean = dimBehind

    protected open fun dimAmount(): Float? = dimAmount

    protected open fun gravity(): Int? = gravity

    protected open fun fullscreen(): Boolean? = fullscreen

    protected open fun width(): Int? = width

    protected open fun height(): Int? = height

    protected open fun fitSystemWindow(): Boolean? = fitSystemWindow

    fun contentView(layoutRes: Int): AnyDialog {
        this.layoutRes = layoutRes
        return this
    }

    fun contentView(content: View): AnyDialog {
        this.content = content
        return this
    }

    fun dragDirection(dragDirection: DragDirection?): AnyDialog {
        this.dragDirection = dragDirection
        return this
    }

    fun style(style: Style): AnyDialog {
        this.animRes = style.animRes
        this.gravity = style.gravity
        this.width = style.width
        this.height = style.height
        this.dragDirection = style.dragDirection
        return this
    }

    fun gravity(gravity: Int): AnyDialog {
        this.gravity = gravity
        return this
    }


    fun animation(@StyleRes animRes: Int): AnyDialog {
        this.animRes = animRes
        return this
    }

    fun fullscreen(fullscreen: Boolean): AnyDialog {
        this.fullscreen = fullscreen
        return this
    }

    fun fitSystemWindow(fitSystemWindow: Boolean): AnyDialog {
        this.fitSystemWindow = fitSystemWindow
        return this
    }

    fun width(width: Int): AnyDialog {
        this.width = width
        return this
    }

    fun height(height: Int): AnyDialog {
        this.height = height
        return this
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