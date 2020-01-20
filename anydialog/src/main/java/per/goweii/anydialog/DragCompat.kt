package per.goweii.anydialog

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.AbsListView
import android.widget.HorizontalScrollView
import android.widget.ScrollView

import androidx.core.view.ScrollingView
import androidx.viewpager.widget.ViewPager

import java.util.ArrayList

object DragCompat {

    fun canViewScrollUp(view: View?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        return if (view == null || !contains(view, x, y)) {
            defaultValueForNull
        } else view.canScrollVertically(-1)
    }

    fun canViewScrollUp(views: List<View>?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        if (views == null) {
            return defaultValueForNull
        }
        val contains = contains(views, x, y) ?: return defaultValueForNull
        var canViewScroll = false
        for (i in contains.indices.reversed()) {
            canViewScroll = ScrollCompat.canScrollVertically(contains[i], -1)
            if (canViewScroll) {
                break
            }
        }
        return canViewScroll
    }

    fun canScrollUpView(views: List<View>?, x: Float, y: Float): View? {
        if (views == null) {
            return null
        }
        val contains = contains(views, x, y) ?: return null
        for (i in contains.indices.reversed()) {
            val view = contains[i]
            if (ScrollCompat.canScrollVertically(view, -1)) {
                return view
            }
        }
        return null
    }

    fun canViewScrollDown(view: View?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        return if (view == null || !contains(view, x, y)) {
            defaultValueForNull
        } else view.canScrollVertically(1)
    }

    fun canViewScrollDown(views: List<View>?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        if (views == null) {
            return defaultValueForNull
        }
        val contains = contains(views, x, y) ?: return defaultValueForNull
        var canViewScroll = false
        for (i in contains.indices.reversed()) {
            canViewScroll = ScrollCompat.canScrollVertically(contains[i], 1)
            if (canViewScroll) {
                break
            }
        }
        return canViewScroll
    }

    fun canScrollDownView(views: List<View>?, x: Float, y: Float): View? {
        if (views == null) {
            return null
        }
        val contains = contains(views, x, y) ?: return null
        for (i in contains.indices.reversed()) {
            val view = contains[i]
            if (ScrollCompat.canScrollVertically(view, 1)) {
                return view
            }
        }
        return null
    }

    fun canViewScrollRight(view: View?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        return if (view == null || !contains(view, x, y)) {
            defaultValueForNull
        } else view.canScrollHorizontally(-1)
    }

    fun canViewScrollRight(views: List<View>?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        if (views == null) {
            return defaultValueForNull
        }
        val contains = contains(views, x, y) ?: return defaultValueForNull
        var canViewScroll = false
        for (i in contains.indices.reversed()) {
            canViewScroll = ScrollCompat.canScrollHorizontally(contains[i], 1)
            if (canViewScroll) {
                break
            }
        }
        return canViewScroll
    }

    fun canScrollRightView(views: List<View>?, x: Float, y: Float): View? {
        if (views == null) {
            return null
        }
        val contains = contains(views, x, y) ?: return null
        for (i in contains.indices.reversed()) {
            val view = contains[i]
            if (ScrollCompat.canScrollHorizontally(view, 1)) {
                return view
            }
        }
        return null
    }

    fun canViewScrollLeft(view: View?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        return if (view == null || !contains(view, x, y)) {
            defaultValueForNull
        } else view.canScrollHorizontally(1)
    }

    fun canViewScrollLeft(views: List<View>?, x: Float, y: Float, defaultValueForNull: Boolean): Boolean {
        if (views == null) {
            return defaultValueForNull
        }
        val contains = contains(views, x, y) ?: return defaultValueForNull
        var canViewScroll = false
        for (i in contains.indices.reversed()) {
            canViewScroll = ScrollCompat.canScrollHorizontally(contains[i], -1)
            if (canViewScroll) {
                break
            }
        }
        return canViewScroll
    }

    fun canScrollLeftView(views: List<View>?, x: Float, y: Float): View? {
        if (views == null) {
            return null
        }
        val contains = contains(views, x, y) ?: return null
        for (i in contains.indices.reversed()) {
            val view = contains[i]
            if (ScrollCompat.canScrollHorizontally(view, -1)) {
                return view
            }
        }
        return null
    }

    fun findAllScrollViews(viewGroup: ViewGroup): List<View> {
        val views = ArrayList<View>()
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view.visibility != View.VISIBLE) {
                continue
            }
            if (isScrollableView(view)) {
                views.add(view)
            }
            if (view is ViewGroup) {
                views.addAll(findAllScrollViews(view))
            }
        }
        return views
    }

    private fun isScrollableView(view: View): Boolean {
        return (view is ScrollView
                || view is HorizontalScrollView
                || view is AbsListView
                || view is ViewPager
                || view is WebView
                || view is ScrollingView)
    }

    fun contains(view: View, x: Float, y: Float): Boolean {
        val localRect = Rect()
        view.getGlobalVisibleRect(localRect)
        return localRect.contains(x.toInt(), y.toInt())
    }

    fun contains(views: List<View>?, x: Float, y: Float): List<View>? {
        if (views == null) {
            return null
        }
        val contains = ArrayList<View>(views.size)
        for (i in views.indices.reversed()) {
            val v = views[i]
            val localRect = Rect()
            val l = IntArray(2)
            v.getLocationOnScreen(l)
            localRect.set(l[0], l[1], l[0] + v.width, l[1] + v.height)
            if (localRect.contains(x.toInt(), y.toInt())) {
                contains.add(v)
            }
        }
        return contains
    }
}
