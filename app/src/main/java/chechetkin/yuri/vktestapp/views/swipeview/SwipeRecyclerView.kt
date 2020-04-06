package chechetkin.yuri.vktestapp.views.swipeview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("CustomViewStyleable")
class SwipeRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        SwipeSnapHelper().attachToRecyclerView(this)
        overScrollMode = OVER_SCROLL_NEVER
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            (layoutManager as? SwipeLayoutManager)?.updateProportion(event.y)
        }
        return super.onInterceptTouchEvent(event)
    }

    fun swipeLeft() {
        swipe(SwipeDirection.Left)
    }

    fun swipeRight() {
        swipe(SwipeDirection.Right)
    }

    private fun swipe(swipeDirection: SwipeDirection) {
        (layoutManager as? SwipeLayoutManager)?.let {
            it.swipeDirection = swipeDirection
            smoothScrollToPosition(it.currentPosition.inc())
        }
    }
}