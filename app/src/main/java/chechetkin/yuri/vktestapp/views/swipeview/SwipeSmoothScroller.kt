package chechetkin.yuri.vktestapp.views.swipeview

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller

const val ANIMATION_DURATION_MS = 200

class SwipeSmoothScroller(
    private val type: SwipeType,
    private val layoutManager: SwipeLayoutManager
) : SmoothScroller() {

    enum class SwipeType {
        Swipe, Cancel
    }

    override fun onSeekTargetStep(dx: Int, dy: Int, state: RecyclerView.State, action: Action) = Unit

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun onTargetFound(
        targetView: View,
        state: RecyclerView.State,
        action: Action
    ) {
        val x = targetView.translationX.toInt()

        when (type) {
            SwipeType.Swipe -> {
                val dx = when (x) {
                    0 -> layoutManager.swipeDirection.intDirection() * layoutManager.width;
                    else -> x
                }
                action.update(
                    -dx,
                    -layoutManager.height,
                    ANIMATION_DURATION_MS,
                    AccelerateInterpolator()
                )
            }
            SwipeType.Cancel -> {
                action.update(
                    x,
                    0,
                    ANIMATION_DURATION_MS,
                    DecelerateInterpolator()
                )
            }
        }
    }

    private fun SwipeDirection?.intDirection(): Int {
        return when (this) {
            SwipeDirection.Left -> -1
            else -> 1
        }
    }
}