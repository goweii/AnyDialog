package per.goweii.anydialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.FloatRange
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.view.*
import android.widget.FrameLayout
import kotlin.math.max

open class AnyDialog(context: Context) : Dialog(context, R.style.Dialog) {

    @LayoutRes
    private var layoutRes: Int = 0
    @StyleRes
    private var animRes: Int = R.style.DialogAnimDef
    private var dimBehind: Boolean = true
    @FloatRange(from = 0.0, to = 1.0)
    private var dimAmount: Float? = null

    private var gravity: Int? = null
    private var fullscreen: Boolean? = null
    private var width: Int? = null
    private var height: Int? = null
    private var horizontalMargin: Float = 0F
    private var verticalMargin: Float = 0F

    private var content: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let { w ->
            val contentParent = w.decorView.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
            content = layoutInflater.inflate(getLayoutRes(), contentParent, false)
            initAttributes()
            content?.let { setContentView(it) }
            initWindow(w)
        }
        initView()
    }

    private fun initAttributes() {
        content?.let {
            val params = it.layoutParams
            if (params is FrameLayout.LayoutParams) {
                if (gravity == null) gravity = params.gravity
                if (width == null) width = params.width
                if (height == null) height = params.height
                horizontalMargin = max(params.leftMargin, params.rightMargin).toFloat()
                verticalMargin = max(params.topMargin, params.bottomMargin).toFloat()
            }
        }
    }

    protected open fun initWindow(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        val attr = window.attributes
        gravity()?.let { attr.gravity = it }
        width()?.let { attr.width = it }
        height()?.let { attr.height = it }
        fullscreen()?.let {
            if (it) {
                attr.width = WindowManager.LayoutParams.MATCH_PARENT
                attr.height = WindowManager.LayoutParams.MATCH_PARENT
            }
        }
        attr.horizontalMargin = horizontalMargin
        attr.verticalMargin = verticalMargin
        window.attributes = attr
        if (dimBehind()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        dimAmount()?.let { window.setDimAmount(it) }
        window.setWindowAnimations(animation())
        val decorView: View = window.decorView
        decorView.setPadding(0, 0, 0, 0)
        decorView.setBackgroundColor(Color.TRANSPARENT)
    }

    protected open fun initView() {
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

    fun contentView(layoutRes: Int): AnyDialog {
        this.layoutRes = layoutRes
        return this
    }

    fun style(style: Style): AnyDialog {
        this.gravity = style.gravity
        this.animRes = style.animRes
        this.width = style.width
        this.height = style.height
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
        return this
    }

    fun cancelableOnTouchOutside(cancelable: Boolean): AnyDialog {
        setCanceledOnTouchOutside(cancelable)
        return this
    }

    enum class Style(
            val gravity: Int,
            val animRes: Int,
            val width: Int,
            val height: Int
    ) {
        DEFAULT(Gravity.CENTER, R.style.DialogAnimDef,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT),
        BOTTOM(Gravity.BOTTOM, R.style.DialogAnimBottom,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT),
        TOP(Gravity.TOP, R.style.DialogAnimTop,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT),
        LEFT(Gravity.LEFT, R.style.DialogAnimLeft,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.MATCH_PARENT),
        RIGHT(Gravity.RIGHT, R.style.DialogAnimRight,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.MATCH_PARENT)
    }
}