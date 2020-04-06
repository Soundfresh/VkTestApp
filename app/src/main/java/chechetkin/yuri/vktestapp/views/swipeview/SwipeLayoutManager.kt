package chechetkin.yuri.vktestapp.views.swipeview

import android.graphics.PointF
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import kotlin.math.abs

private const val VISIBLE_COUNT = 2
private const val MAX_DEGREE = 50.0f
private const val SCALE_NORMALIZATION_FACTOR = 3f

class SwipeLayoutManager(
    private val listener: ItemSwipeListener
) : RecyclerView.LayoutManager(), ScrollVectorProvider {

    var swipeDirection: SwipeDirection? = null
    var isSwipeAnimating = true
    var dx = 0
    var currentPosition = 0
    var proportion = 0.0f

    private fun isSwipeCompleted(): Boolean {
        return isSwipeAnimating && width < abs(dx)
    }

    override fun generateDefaultLayoutParams() = RecyclerView.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    override fun onLayoutChildren(recycler: Recycler, s: RecyclerView.State) {
        update(recycler)
    }

    override fun canScrollHorizontally() = true

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler,
        s: RecyclerView.State
    ): Int {
        return when (isSwipeAnimating) {
            true -> {
                this.dx -= dx
                update(recycler)
                dx
            }
            else -> 0
        }
    }

    override fun onScrollStateChanged(s: Int) {
        if (s == RecyclerView.SCROLL_STATE_IDLE) {
            isSwipeAnimating = true
        }
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? = null

    override fun scrollToPosition(position: Int) {
        currentPosition = position
        requestLayout()
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        s: RecyclerView.State,
        position: Int
    ) {
        proportion = 0.0f
        SwipeSmoothScroller(
            SwipeSmoothScroller.SwipeType.Swipe,
            this
        ).run {
            targetPosition = currentPosition
            startSmoothScroll(this)
        }
    }

    fun updateProportion(y: Float) {
        if (currentPosition < itemCount) {
            val view = findViewByPosition(currentPosition)
            if (view != null) {
                val half = height / 2.0f
                proportion = -(y - half - view.top) / half
            }
        }
    }

    private fun getDirection() = when (dx < 0.0f) {
        true -> SwipeDirection.Left
        false -> SwipeDirection.Right
    }

    private fun onSwipeCompleted(recycler: Recycler) {
        removeAndRecycleView(requireNotNull(topView), recycler)
        isSwipeAnimating = isSwipeAnimating.not()
        val direction = getDirection()
        currentPosition++
        dx = 0
        if (direction == SwipeDirection.Left) {
            listener.onItemSwipedLeft(currentPosition.dec())
        } else {
            listener.onItemSwipedRight(currentPosition.dec())
        }
    }

    private fun update(recycler: Recycler) {
        if (isSwipeCompleted()) {
            onSwipeCompleted(recycler)
        }

        detachAndScrapAttachedViews(recycler)
        val parentTop = paddingTop
        val parentLeft = paddingLeft
        val parentRight = width - paddingLeft
        val parentBottom = height - paddingBottom
        var i = currentPosition
        while (i < currentPosition + VISIBLE_COUNT && i < itemCount) {
            val child = recycler.getViewForPosition(i)
            addView(child, 0)
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, parentLeft, parentTop, parentRight, parentBottom)
            resetTranslation(child)
            resetRotation(child)
            if (i == currentPosition) {
                updateTranslation(child)
                updateRotation(child)
                updateButtonSize()
            }
            i++
        }
    }

    private fun updateButtonSize() {
        val absoluteScale = abs(dx) / width.toFloat()
        val normalizedScale = absoluteScale / SCALE_NORMALIZATION_FACTOR

        when {
            dx > 0 -> listener.onItemScaleRight(normalizedScale)
            dx < 0 -> listener.onItemScaleLeft(normalizedScale)
        }
    }

    private fun updateTranslation(view: View) {
        view.translationX = dx.toFloat()
    }

    private fun resetTranslation(view: View) {
        view.translationX = 0.0f
    }

    private fun updateRotation(view: View) {
        val degree = dx * MAX_DEGREE / width * proportion
        view.rotation = degree
    }

    private fun resetRotation(view: View) {
        view.rotation = 0.0f
    }

    private val topView: View?
        get() = findViewByPosition(currentPosition)
}