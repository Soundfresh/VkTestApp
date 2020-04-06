package chechetkin.yuri.vktestapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import chechetkin.yuri.vktestapp.R

private const val PRIORITY_DELIMITER = ','
private const val ASPECT_RATIO_DELIMITER = ':'
private const val NO_ASPECT_RATIO = -1.0

fun View.readCornerRadiusAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    val attrArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedView, defStyleAttr, 0)
    try {
        tryReadCornerRadiusAspectRatio(attrArray) ?: tryReadCornerRadiusPixelSize(attrArray)
    } finally {
        attrArray.recycle()
    }
}

private fun View.tryReadCornerRadiusAspectRatio(attrArray: TypedArray): Unit? {
    val cornerRadiusString = attrArray.getString(R.styleable.RoundedView_corner_radius)
    val cornerRadiusAspectRatio = readAspectRatio(cornerRadiusString)
    val cornerRadiusAspectRatioPriority = readPriority(cornerRadiusString)
    return cornerRadiusAspectRatioPriority?.let { setCornerRadius(it, cornerRadiusAspectRatio) }
}

private fun View.tryReadCornerRadiusPixelSize(attrArray: TypedArray) =
    setCornerRadius(
        attrArray.getDimensionPixelSize(
            R.styleable.RoundedView_corner_radius,
            0
        ).toFloat()
    )

fun View.setCornerRadius(cornerRadiusPixelSize: Float) =
    initRoundedCornersOutlineProvider { cornerRadiusPixelSize }

fun View.setCornerRadius(priority: Priority, aspectRatio: Double) {
    if (!isValidAspectRatio(aspectRatio)) {
        return
    }
    initRoundedCornersOutlineProvider {
        when (priority) {
            Priority.WIDTH -> it.width * aspectRatio
            Priority.HEIGHT -> it.height * aspectRatio
        }.toFloat()
    }
}

private fun View.initRoundedCornersOutlineProvider(cornerRadiusPixelSize: (view: View) -> Float) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val right = view.width
            val bottom = view.height
            outline.setRoundRect(0, 0, right, bottom, cornerRadiusPixelSize(view))
        }
    }
    clipToOutline = true
}

fun isValidAspectRatio(aspectRatio: Double) = aspectRatio > 0

@SuppressLint("DefaultLocale")
fun readPriority(aspectRatioString: String?, defaultPriority: Priority? = null): Priority? {
    return aspectRatioString
        ?.substringBefore(PRIORITY_DELIMITER, "")
        ?.takeIf { it.isNotEmpty() }
        ?.let {
            when (it.toLowerCase()) {
                in Priority.WIDTH.values -> Priority.WIDTH
                in Priority.HEIGHT.values -> Priority.HEIGHT
                else -> defaultPriority
            }
        }
}

fun readAspectRatio(aspectRatioString: String?): Double {
    return aspectRatioString
        ?.substringAfter(PRIORITY_DELIMITER)
        ?.takeIf { it.isNotEmpty() }
        ?.split(ASPECT_RATIO_DELIMITER)
        ?.mapNotNull { it.toDoubleOrNull() }
        ?.filter { it > 0 }
        ?.takeIf { it.size == 2 }
        ?.let { it[0] / it[1] }
        ?: NO_ASPECT_RATIO
}

enum class Priority(vararg val values: String) { WIDTH("w", "width"), HEIGHT("h", "height") }