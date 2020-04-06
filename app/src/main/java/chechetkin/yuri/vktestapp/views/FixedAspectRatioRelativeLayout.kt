package chechetkin.yuri.vktestapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import chechetkin.yuri.vktestapp.R
import kotlin.math.min

/*

This widget was written for using aspect ratio views and avoid dependcy of contraint layout

 */

private const val NO_ASPECT_RATIO = -1.0

@SuppressLint("CustomViewStyleable")
open class FixedAspectRatioRelativeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RoundedRelativeLayout(context, attrs, defStyleAttr) {

    var aspectRatio: Double = NO_ASPECT_RATIO
        set(value) {
            field = value
            requestLayout()
        }
    var priority: Priority? = null

    init {
        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatioView, defStyleAttr, 0)
        try {
            val aspectRatioString = attrArray.getString(R.styleable.FixedAspectRatioView_aspect_ratio)
            priority = readPriority(aspectRatioString)
            aspectRatio = readAspectRatio(aspectRatioString)
        } finally {
            attrArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when {
            isValidAspectRatio(aspectRatio) -> {
                val originalWidth = MeasureSpec.getSize(widthMeasureSpec)
                val originalHeight = MeasureSpec.getSize(heightMeasureSpec)

                val calculatedWidth = (originalHeight * aspectRatio).toInt()
                val calculatedHeight = (originalWidth / aspectRatio).toInt()

                when (priority) {
                    Priority.WIDTH -> super.onMeasure(
                        MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY)
                    )
                    Priority.HEIGHT -> super.onMeasure(
                        MeasureSpec.makeMeasureSpec(calculatedWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(originalHeight, MeasureSpec.EXACTLY)
                    )
                    else -> {
                        val finalHeight = min(originalHeight, calculatedHeight)
                        val finalWidth: Int = when {
                            calculatedHeight > originalHeight -> calculatedWidth
                            else -> originalWidth
                        }
                        super.onMeasure(
                            MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY)
                        )
                    }
                }
            }
            else -> super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}