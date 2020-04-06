package chechetkin.yuri.vktestapp.screens.result

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.R
import chechetkin.yuri.vktestapp.screens.game.models.Serial
import chechetkin.yuri.vktestapp.core.PopulatableView
import chechetkin.yuri.vktestapp.views.FixedAspectRatioRelativeLayout

class CharacterResultWrongView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FixedAspectRatioRelativeLayout(context, attrs, defStyleAttr), PopulatableView<Character> {

    init {
        inflate(context, R.layout.item_character_result_wrong, this)
    }

    private val nameView by lazy { findViewById<TextView>(R.id.character_name) }
    private val imageView by lazy { findViewById<ImageView>(R.id.image_view) }
    private val serialRightNameView by lazy { findViewById<TextView>(R.id.serial_right_name) }
    private val serialWrongNameView by lazy { findViewById<TextView>(R.id.serial_wrong_name) }

    override fun populate(model: Character) {
        nameView.text = model.name
        imageView.setImageDrawable(ContextCompat.getDrawable(context, model.image))
        serialRightNameView.text = resources.getString(model.serial.serialName)
        val serialWrong = when (model.serial) {
            Serial.GOT -> Serial.LOR
            Serial.LOR -> Serial.GOT
        }
        serialWrongNameView.text = resources.getString(serialWrong.serialName)
        serialWrongNameView.paintFlags = serialWrongNameView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}