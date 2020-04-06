package chechetkin.yuri.vktestapp.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

open class RoundedRelativeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        readCornerRadiusAttr(context, attrs, defStyleAttr)
    }
}