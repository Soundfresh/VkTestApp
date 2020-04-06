package chechetkin.yuri.vktestapp.views.swipeview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlin.math.abs

private const val SWIPE_OFFSET = 0.4f

class SwipeSnapHelper : SnapHelper() {

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        (layoutManager as? SwipeLayoutManager)?.let {
            val horizontalOffset = abs(targetView.translationX) / targetView.width.toFloat()
            val scrollType = when (horizontalOffset > SWIPE_OFFSET) {
                true -> SwipeSmoothScroller.SwipeType.Swipe
                else -> SwipeSmoothScroller.SwipeType.Cancel
            }
            val scroller = SwipeSmoothScroller(scrollType, layoutManager)
            scroller.targetPosition = layoutManager.currentPosition
            layoutManager.startSmoothScroll(scroller)
        }
        return IntArray(2)
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        return (layoutManager as? SwipeLayoutManager)?.currentPosition ?: RecyclerView.NO_POSITION
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return (layoutManager as? SwipeLayoutManager)?.findViewByPosition(layoutManager.currentPosition)
    }
}