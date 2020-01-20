package per.goweii.anydialog

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

import androidx.customview.widget.ViewDragHelper

internal class DragLayout : FrameLayout {

    private val mDragHelper: ViewDragHelper = ViewDragHelper.create(this, DragCallback())
    private var mDragStyle = DragStyle.None
    private var mOnDragListener: OnDragListener? = null

    private var mInnerScrollViews: List<View>? = null
    private var mHandleDragEvent = false
    private var mDownX: Float = 0.toFloat()
    private var mDownY: Float = 0.toFloat()
    private var mLeft: Int = 0
    private var mTop: Int = 0

    private var mDragFraction = 0f

    private val isEnable: Boolean
        get() = mDragStyle != DragStyle.None

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setOnDragListener(onDragListener: OnDragListener) {
        mOnDragListener = onDragListener
    }

    fun setDragStyle(dragStyle: DragStyle) {
        mDragStyle = dragStyle
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isEnable) {
            mHandleDragEvent = false
            return super.onInterceptTouchEvent(ev)
        }
        val shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev)
        mHandleDragEvent = shouldIntercept
        when (ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = ev.rawX
                mDownY = ev.rawY
                if (!mHandleDragEvent) {
                    when (mDragStyle) {
                        DragStyle.Left -> {
                            if (!DragCompat.canViewScrollRight(mInnerScrollViews, mDownX, mDownY, false)) {
                                mHandleDragEvent = true
                            }
                        }
                        DragStyle.Right -> {
                            if (!DragCompat.canViewScrollLeft(mInnerScrollViews, mDownX, mDownY, false)) {
                                mHandleDragEvent = true
                            }
                        }
                        DragStyle.Top -> {
                            if (!DragCompat.canViewScrollDown(mInnerScrollViews, mDownX, mDownY, false)) {
                                mHandleDragEvent = true
                            }
                        }
                        DragStyle.Bottom -> {
                            if (!DragCompat.canViewScrollUp(mInnerScrollViews, mDownX, mDownY, false)) {
                                mHandleDragEvent = true
                            }
                        }
                        DragStyle.None -> {
                        }
                    }
                }
            }
        }
        return shouldIntercept
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (!isEnable) {
            return super.onTouchEvent(ev)
        }
        mDragHelper.processTouchEvent(ev)
        return mHandleDragEvent
    }

    override fun computeScroll() {
        if (!isEnable) {
            return
        }
        if (mDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mInnerScrollViews = DragCompat.findAllScrollViews(this)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            if (childCount > 0) {
                mLeft = getChildAt(0).left
                mTop = getChildAt(0).top
            }
        }
    }

    private inner class DragCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return isEnable
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return when (mDragStyle) {
                DragStyle.Left -> mLeft + child.width
                DragStyle.Right -> width - mLeft
                DragStyle.Top, DragStyle.Bottom, DragStyle.None -> 0
            }
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return when (mDragStyle) {
                DragStyle.Top -> mTop + child.height
                DragStyle.Bottom -> height - mTop
                DragStyle.Left, DragStyle.Right, DragStyle.None -> 0
            }
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            mDragFraction = 0f
            if (mOnDragListener != null) {
                mOnDragListener!!.onDragStart()
            }
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            when (mDragStyle) {
                DragStyle.Left -> {
                    if (DragCompat.canViewScrollRight(mInnerScrollViews, mDownX, mDownY, false)) {
                        return mLeft
                    }
                    if (left > mLeft) {
                        return mLeft
                    }
                    val l = mLeft + child.width
                    return if (left < -l) {
                        -l
                    } else left
                }
                DragStyle.Right -> {
                    if (DragCompat.canViewScrollLeft(mInnerScrollViews, mDownX, mDownY, false)) {
                        return mLeft
                    }
                    if (left > width) {
                        return width
                    }
                    return if (left < mLeft) {
                        mLeft
                    } else left
                }
                DragStyle.Top, DragStyle.Bottom, DragStyle.None -> return mLeft
                else -> return mLeft
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            when (mDragStyle) {
                DragStyle.Top -> {
                    if (DragCompat.canViewScrollDown(mInnerScrollViews, mDownX, mDownY, false)) {
                        return mTop
                    }
                    if (top > mTop) {
                        return mTop
                    }
                    val t = mTop + child.height
                    return if (top < -t) {
                        -t
                    } else top
                }
                DragStyle.Bottom -> {
                    if (DragCompat.canViewScrollUp(mInnerScrollViews, mDownX, mDownY, false)) {
                        return mTop
                    }
                    if (top > height) {
                        return height
                    }
                    return if (top < mTop) {
                        mTop
                    } else top
                }
                DragStyle.Left, DragStyle.Right, DragStyle.None -> return mTop
                else -> return mTop
            }
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            when (mDragStyle) {
                DragStyle.Left, DragStyle.Right -> {
                    val xoff = Math.abs(left - mLeft).toFloat()
                    val xmax = getViewHorizontalDragRange(changedView).toFloat()
                    mDragFraction = xoff / xmax
                }
                DragStyle.Top, DragStyle.Bottom -> {
                    val yoff = Math.abs(top - mTop).toFloat()
                    val ymax = getViewVerticalDragRange(changedView).toFloat()
                    mDragFraction = yoff / ymax
                }
                DragStyle.None -> {
                }
            }
            if (mDragFraction < 0) {
                mDragFraction = 0f
            } else if (mDragFraction > 1) {
                mDragFraction = 1f
            }
            if (mOnDragListener != null) {
                mOnDragListener!!.onDragging(mDragFraction)
                if (mDragFraction == 1f) mOnDragListener!!.onDragEnd()
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            val dismissFactor = 0.5f
            val isDismiss = judgeDismissBySpeed(xvel, yvel) || mDragFraction >= dismissFactor
            var l = mLeft
            var t = mTop
            if (isDismiss) {
                when (mDragStyle) {
                    DragStyle.Left -> l = -(mLeft + releasedChild.width)
                    DragStyle.Right -> l = width
                    DragStyle.Top -> t = -(mTop + releasedChild.height)
                    DragStyle.Bottom -> t = height
                    DragStyle.None -> {
                    }
                }
            }
            mDragHelper.settleCapturedViewAt(l, t)
            invalidate()
        }

        private fun judgeDismissBySpeed(xvel: Float, yvel: Float): Boolean {
            val velocityLimit = 2000f
            when (mDragStyle) {
                DragStyle.Left -> return xvel < -velocityLimit
                DragStyle.Right -> return xvel > velocityLimit
                DragStyle.Top -> return yvel < -velocityLimit
                DragStyle.Bottom -> return yvel > velocityLimit
                DragStyle.None -> {
                }
            }
            return false
        }
    }

    enum class DragStyle {
        None, Left, Top, Right, Bottom
    }

    interface OnDragListener {
        fun onDragStart()

        fun onDragging(f: Float)

        fun onDragEnd()
    }
}
