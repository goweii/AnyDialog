package per.goweii.anydialog

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import androidx.core.view.*

import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs

internal class DragLayout3 : FrameLayout, NestedScrollingParent3 {

    private val _dismissDuration = 300
    private val _dismissVelocity = 2000F
    private val _dismissFraction = 0.5F

    private val mDragHelper: ViewDragHelper = ViewDragHelper.create(this, DragCallback())
    private val mNestedHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private val mScroller: Scroller = Scroller(context, DecelerateInterpolator())
    private var mDragStyle = DragStyle.None

    private var mInnerScrollViews: List<View>? = null
    private var mHandleTouchEvent = false
    private var mDownX: Float = 0.toFloat()
    private var mDownY: Float = 0.toFloat()
    private var mLeft: Int = 0
    private var mTop: Int = 0

    private var usingNested = false

    private var mDragFraction = 0f

    private var onDragStart: (() -> Unit)? = null
    private var onDragging: ((f: Float) -> Unit)? = null
    private var onDragEnd: (() -> Unit)? = null

    private val isEnable: Boolean
        get() = mDragStyle != DragStyle.None

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun onDragStart(listener: () -> Unit) {
        onDragStart = listener
    }

    fun onDragging(listener: (f: Float) -> Unit) {
        onDragging = listener
    }

    fun onDragEnd(listener: () -> Unit) {
        onDragEnd = listener
    }

    fun dragStyle(dragStyle: DragStyle) {
        mDragStyle = dragStyle
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isEnable) {
            mHandleTouchEvent = false
            return super.onInterceptTouchEvent(ev)
        }
        when (ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mDragHelper.abort()
                mScroller.abortAnimation()
                usingNested = false
                mDownX = ev.rawX
                mDownY = ev.rawY
            }
        }
        Log.d("DragLayout", "onInterceptTouchEvent->mDownY=$mDownY，mDownY=$mDownY")
        if (usingNested) {
            mHandleTouchEvent = false
            return super.onInterceptTouchEvent(ev)
        }
        val shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev)
        mHandleTouchEvent = shouldIntercept
        when (ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                if (!mHandleTouchEvent) {
                    when (mDragStyle) {
                        DragStyle.None -> {
                        }
                        DragStyle.Left -> if (!DragCompat.canViewScrollRight(mInnerScrollViews, mDownX, mDownY, false)) {
                            mHandleTouchEvent = true
                        }
                        DragStyle.Right -> if (!DragCompat.canViewScrollLeft(mInnerScrollViews, mDownX, mDownY, false)) {
                            mHandleTouchEvent = true
                        }
                        DragStyle.Top -> if (!DragCompat.canViewScrollDown(mInnerScrollViews, mDownX, mDownY, false)) {
                            mHandleTouchEvent = true
                        }
                        DragStyle.Bottom -> if (!DragCompat.canViewScrollUp(mInnerScrollViews, mDownX, mDownY, false)) {
                            mHandleTouchEvent = true
                        }
                    }
                }
            }
        }
        Log.d("DragLayout", "onInterceptTouchEvent->type=$shouldIntercept")
        return shouldIntercept
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (!isEnable) {
            return super.onTouchEvent(ev)
        }
        if (usingNested) {
            return super.onTouchEvent(ev)
        }
        mDragHelper.processTouchEvent(ev)
        return mHandleTouchEvent
    }

    override fun computeScroll() {
        if (!isEnable) {
            return
        }
        if (usingNested) {
            if (mScroller.computeScrollOffset()) {
                scrollTo(mScroller.currX, mScroller.currY)
                invalidate()
            }
        } else {
            if (mDragHelper.continueSettling(true)) {
                invalidate()
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        assert(childCount == 1)
        mInnerScrollViews = DragCompat.findAllScrollViews(this)
        mLeft = getChildAt(0).left
        mTop = getChildAt(0).top
    }

    override fun scrollTo(x: Int, y: Int) {
        if (!usingNested) {
            super.scrollTo(x, y)
            return
        }
        fun Int.range(from: Int, to: Int) = when {
            this < from -> from
            this > to -> to
            else -> this
        }

        var realx = 0
        var realy = 0
        when (mDragStyle) {
            DragStyle.Left -> realx = x.range(0, width)
            DragStyle.Right -> realx = x.range(-width, 0)
            DragStyle.Top -> realy = y.range(0, height)
            DragStyle.Bottom -> realy = y.range(-height, 0)
            DragStyle.None -> {
            }
        }
        super.scrollTo(realx, realy)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (!usingNested) return
        mDragFraction = when (mDragStyle) {
            DragStyle.Left -> abs(scrollX).toFloat() / width.toFloat()
            DragStyle.Right -> abs(scrollX).toFloat() / width.toFloat()
            DragStyle.Top -> abs(scrollY).toFloat() / height.toFloat()
            DragStyle.Bottom -> abs(scrollY).toFloat() / height.toFloat()
            DragStyle.None -> 0F
        }
        if (mDragFraction < 0) {
            mDragFraction = 0f
        } else if (mDragFraction > 1) {
            mDragFraction = 1f
        }
        onDragging()
        if (mDragFraction == 1F) onDragEnd()
    }

    private fun onDragStart() {
        onDragStart?.invoke()
    }

    private fun onDragging() {
        onDragging?.invoke(mDragFraction)
    }

    private fun onDragEnd() {
        onDragEnd?.invoke()
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        fun canScrollH() = target.canScrollHorizontally(-1) || target.canScrollHorizontally(1)
        fun canScrollV() = target.canScrollVertically(-1) || target.canScrollVertically(1)
        usingNested = if (target is NestedScrollingChild) {
            when (mDragStyle) {
                DragStyle.None -> false
                DragStyle.Left -> canScrollH()
                DragStyle.Right -> canScrollH()
                DragStyle.Top -> canScrollV()
                DragStyle.Bottom -> canScrollV()
            }
        } else {
            false
        }
        Log.d("DragLayout", "onStartNestedScroll->usingNested=$usingNested")
        return usingNested
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.d("DragLayout", "onNestedScrollAccepted->type=$type")
        mNestedHelper.onNestedScrollAccepted(child, target, axes, type)
        if (type == ViewCompat.TYPE_TOUCH) {
            mScroller.abortAnimation()
            velocity = 0F
            onDragStart()
        }
    }

    private var velocity: Float = 0F

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        velocity = when (mDragStyle) {
            DragStyle.None -> 0F
            DragStyle.Left -> velocityX
            DragStyle.Right -> velocityX
            DragStyle.Top -> velocityY
            DragStyle.Bottom -> velocityY
        }
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        target as ScrollingView
        when (mDragStyle) {
            DragStyle.None -> {
            }
            DragStyle.Left -> {
                consumed[0] = if (dx < 0) {
                    if (scrollX > 0) {
                        if (scrollX + dx < 0) {
                            -scrollX
                        } else {
                            dx
                        }
                    } else {
                        0
                    }
                } else if (dx > 0) {
                    if (scrollX > 0) {
                        dx
                    } else {
                        if (target.canScrollHorizontally(1)) {
                            0
                        } else {
                            if (type == ViewCompat.TYPE_NON_TOUCH) {
                                if (scrollX + dx < 0) {
                                    -scrollX
                                } else {
                                    0
                                }
                            } else {
                                dx
                            }
                        }
                    }
                } else {
                    0
                }
                consumed[1] = 0
            }
            DragStyle.Right -> {
                consumed[0] = if (dx > 0) {
                    if (scrollX < 0) {
                        if (scrollX + dx > 0) {
                            -scrollX
                        } else {
                            dx
                        }
                    } else {
                        0
                    }
                } else if (dx < 0) {
                    if (scrollX < 0) {
                        dx
                    } else {
                        if (target.canScrollHorizontally(-1)) {
                            0
                        } else {
                            if (type == ViewCompat.TYPE_NON_TOUCH) {
                                if (scrollX + dx > 0) {
                                    -scrollX
                                } else {
                                    0
                                }
                            } else {
                                dx
                            }
                        }
                    }
                } else {
                    0
                }
                consumed[1] = 0
            }
            DragStyle.Top -> {
                consumed[0] = 0
                consumed[1] = if (dy < 0) {
                    if (scrollY > 0) {
                        if (scrollY + dy < 0) {
                            -scrollY
                        } else {
                            dy
                        }
                    } else {
                        0
                    }
                } else if (dy > 0) {
                    if (scrollY > 0) {
                        dy
                    } else {
                        if (target.canScrollVertically(1)) {
                            0
                        } else {
                            if (type == ViewCompat.TYPE_NON_TOUCH) {
                                if (scrollY + dy < 0) {
                                    -scrollY
                                } else {
                                    0
                                }
                            } else {
                                dy
                            }
                        }
                    }
                } else {
                    0
                }
            }
            DragStyle.Bottom -> {
                consumed[0] = 0
                consumed[1] = if (dy > 0) {
                    if (scrollY < 0) {
                        if (scrollY + dy > 0) {
                            -scrollY
                        } else {
                            dy
                        }
                    } else {
                        0
                    }
                } else if (dy < 0) {
                    if (scrollY < 0) {
                        dy
                    } else {
                        if (target.canScrollVertically(-1)) {
                            0
                        } else {
                            if (type == ViewCompat.TYPE_NON_TOUCH) {
                                if (scrollY + dy > 0) {
                                    -scrollY
                                } else {
                                    0
                                }
                            } else {
                                dy
                            }
                        }
                    }
                } else {
                    0
                }
            }
        }
        scrollBy(consumed[0], consumed[1])
        Log.d("DragLayout", "onNestedPreScroll->consumed-y=${consumed[1]}")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        when (mDragStyle) {
            DragStyle.None -> {
            }
            DragStyle.Left -> {
                consumed[0] = if (scrollY + dxUnconsumed < 0) {
                    -scrollX
                } else {
                    if (type == ViewCompat.TYPE_NON_TOUCH) {
                        0
                    } else {
                        dxUnconsumed
                    }
                }
                consumed[1] = 0
            }
            DragStyle.Right -> {
                consumed[0] = if (scrollX + dxUnconsumed > 0) {
                    -scrollX
                } else {
                    if (type == ViewCompat.TYPE_NON_TOUCH) {
                        0
                    } else {
                        dxUnconsumed
                    }
                }
                consumed[1] = 0
            }
            DragStyle.Top -> {
                consumed[0] = 0
                consumed[1] = if (scrollY + dyUnconsumed < 0) {
                    -scrollY
                } else {
                    if (type == ViewCompat.TYPE_NON_TOUCH) {
                        0
                    } else {
                        dyUnconsumed
                    }
                }
            }
            DragStyle.Bottom -> {
                consumed[0] = 0
                consumed[1] = if (scrollY + dyUnconsumed > 0) {
                    -scrollY
                } else {
                    if (type == ViewCompat.TYPE_NON_TOUCH) {
                        0
                    } else {
                        dyUnconsumed
                    }
                }
            }
        }
        scrollBy(consumed[0], consumed[1])
        Log.d("DragLayout", "onNestedScroll->consumed-y=${consumed[1]}")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mNestedHelper.onStopNestedScroll(target, type)
        Log.d("DragLayout", "onStopNestedScroll->type=$type")
        if (type == ViewCompat.TYPE_TOUCH) {
            Log.d("DragLayout", "onStopNestedScroll->velocity=$velocity")
            val dismiss = when (mDragStyle) {
                DragStyle.None -> false
                DragStyle.Left -> scrollX > 0 && velocity > _dismissVelocity
                DragStyle.Right -> scrollX < 0 && -velocity > _dismissVelocity
                DragStyle.Top -> scrollY > 0 && velocity > _dismissVelocity
                DragStyle.Bottom -> scrollY < 0 && -velocity > _dismissVelocity
            } || mDragFraction >= _dismissFraction
            val f = abs(velocity) / _dismissVelocity
            val duration: Int = if (f <= 1) {
                _dismissDuration
            } else {
                (_dismissDuration / f).toInt()
            }
            Log.d("DragLayout", "onStopNestedScroll->dismiss=$dismiss")
            if (dismiss) {
                when (mDragStyle) {
                    DragStyle.None -> mScroller.startScroll(scrollX, scrollY, -scrollX, -scrollY, duration)
                    DragStyle.Left -> mScroller.startScroll(scrollX, scrollY, width - scrollX, -scrollY, duration)
                    DragStyle.Right -> mScroller.startScroll(scrollX, scrollY, -width - scrollX, -scrollY, duration)
                    DragStyle.Top -> mScroller.startScroll(scrollX, scrollY, -scrollX, height - scrollY, duration)
                    DragStyle.Bottom -> mScroller.startScroll(scrollX, scrollY, -scrollX, -height - scrollY, duration)
                }
            } else {
                mScroller.startScroll(scrollX, scrollY, -scrollX, -scrollY, duration)
            }
            invalidate()
        }
    }

    override fun getNestedScrollAxes(): Int {
        return mNestedHelper.nestedScrollAxes
    }

    private inner class DragCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            Log.d("DragLayout", "tryCaptureView")
            return isEnable && !usingNested
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            Log.d("DragLayout", "onViewCaptured")
            mDragFraction = 0f
            onDragStart()
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            Log.d("DragLayout", "getViewHorizontalDragRange")
            return when (mDragStyle) {
                DragStyle.Left -> mLeft + child.width
                DragStyle.Right -> width - mLeft
                DragStyle.Top, DragStyle.Bottom, DragStyle.None -> 0
            }
        }

        override fun getViewVerticalDragRange(child: View): Int {
            Log.d("DragLayout", "getViewVerticalDragRange")
            return when (mDragStyle) {
                DragStyle.Top -> mTop + child.height
                DragStyle.Bottom -> height - mTop
                DragStyle.Left, DragStyle.Right, DragStyle.None -> 0
            }
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            Log.d("DragLayout", "clampViewPositionHorizontal")
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
            Log.d("DragLayout", "clampViewPositionVertical")
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
            Log.d("DragLayout", "onViewPositionChanged->dx=$dx,dy=$dy")
            when (mDragStyle) {
                DragStyle.Left, DragStyle.Right -> {
                    val xoff = abs(left - mLeft).toFloat()
                    val xmax = getViewHorizontalDragRange(changedView).toFloat()
                    mDragFraction = xoff / xmax
                }
                DragStyle.Top, DragStyle.Bottom -> {
                    val yoff = abs(top - mTop).toFloat()
                    val ymax = getViewVerticalDragRange(changedView).toFloat()
                    mDragFraction = yoff / ymax
                }
                DragStyle.None -> {
                }
            }
            if (mDragFraction < 0) {
                mDragFraction = 0F
            } else if (mDragFraction > 1) {
                mDragFraction = 1F
            }
            onDragging()
            if (mDragFraction == 1F) onDragEnd()
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            Log.d("DragLayout", "onViewReleased")
            val dismiss = when (mDragStyle) {
                DragStyle.Left -> xvel < -_dismissVelocity
                DragStyle.Right -> xvel > _dismissVelocity
                DragStyle.Top -> yvel < -_dismissVelocity
                DragStyle.Bottom -> yvel > _dismissVelocity
                DragStyle.None -> false
            } || mDragFraction >= _dismissFraction
            var l = mLeft
            var t = mTop
            if (dismiss) {
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
    }

    enum class DragStyle {
        None, Left, Right, Top, Bottom
    }
}
