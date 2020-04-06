package chechetkin.yuri.vktestapp.views.swipeview

interface ItemSwipeListener {
    fun onItemSwipedLeft(position: Int)
    fun onItemSwipedRight(position: Int)

    fun onItemScaleRight(scale: Float)
    fun onItemScaleLeft(scale: Float)
}