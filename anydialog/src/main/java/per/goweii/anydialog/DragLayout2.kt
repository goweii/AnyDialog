package per.goweii.anydialog

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Scroller
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ScrollingView
import androidx.core.view.ViewCompat
import androidx.core.widget.AutoScrollHelper

import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

internal class DragLayout2 : FrameLayout, NestedScrollingParent3 {

    private val mNestedHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private val mScroller: Scroller = Scroller(context)
    private var mDragStyle = DragStyle.None
    private var mOnDragListener: OnDragListener? = null

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
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(ev)
    }

    override fun computeScroll() {
        if (!isEnable) {
            return
        }
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun scrollTo(x: Int, y: Int) {
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
        mDragFraction = when (mDragStyle) {
            DragStyle.Left -> abs(scrollX).toFloat() / width.toFloat()
            DragStyle.Right -> abs(scrollX).toFloat() / width.toFloat()
            DragStyle.Top -> abs(scrollY).toFloat() / height.toFloat()
            DragStyle.Bottom -> abs(scrollY).toFloat() / height.toFloat()
            DragStyle.None -> 0F
        }
        onDragging()
        val dragEnd = when (mDragStyle) {
            DragStyle.None -> false
            DragStyle.Left -> scrollX >= width
            DragStyle.Right -> -scrollX >= width
            DragStyle.Top -> scrollY >= height
            DragStyle.Bottom -> -scrollY >= height
        }
        if (dragEnd) {
            onDragEnd()
        }
    }

    private fun onDragStart() {
        mOnDragListener?.onDragStart()
    }

    private fun onDragging() {
        mOnDragListener?.onDragging(mDragFraction)
    }

    private fun onDragEnd() {
        mOnDragListener?.onDragEnd()
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return isEnable
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
            val dismiss = if (velocity == 0F) {
                when (mDragStyle) {
                    DragStyle.None -> false
                    DragStyle.Left -> scrollX > width / 2
                    DragStyle.Right -> -scrollX > width / 2
                    DragStyle.Top -> scrollY > height / 2
                    DragStyle.Bottom -> -scrollY > height / 2
                }
            } else {
                abs(velocity) > 5000
            }
            Log.d("DragLayout", "onStopNestedScroll->dismiss=$dismiss")
            if (dismiss) {
                when (mDragStyle) {
                    DragStyle.None -> mScroller.startScroll(scrollX, scrollY, -scrollX, -scrollY)
                    DragStyle.Left -> mScroller.startScroll(scrollX, scrollY, width - scrollX, -scrollY)
                    DragStyle.Right -> mScroller.startScroll(scrollX, scrollY, -width - scrollX, -scrollY)
                    DragStyle.Top -> mScroller.startScroll(scrollX, scrollY, -scrollX, height - scrollY)
                    DragStyle.Bottom -> mScroller.startScroll(scrollX, scrollY, -scrollX, -height - scrollY)
                }
            } else {
                mScroller.startScroll(scrollX, scrollY, -scrollX, -scrollY)
            }
        }
    }

    override fun getNestedScrollAxes(): Int {
        return mNestedHelper.nestedScrollAxes
    }

    enum class DragStyle {
        None, Left, Right, Top, Bottom
    }

    interface OnDragListener {
        fun onDragStart()

        fun onDragging(f: Float)

        fun onDragEnd()
    }
}
