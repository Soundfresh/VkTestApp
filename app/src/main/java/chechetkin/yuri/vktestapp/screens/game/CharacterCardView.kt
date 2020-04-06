package chechetkin.yuri.vktestapp.screens.game

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.R
import chechetkin.yuri.vktestapp.core.PopulatableView

class CharacterCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), PopulatableView<Character> {

    init {
        inflate(context, R.layout.item_character, this)
    }

    private val nameView by lazy { findViewById<TextView>(R.id.item_name) }
    private val imageView by lazy { findViewById<ImageView>(R.id.item_image) }

    override fun populate(model: Character) {
        nameView.text = model.name
        imageView.setImageDrawable(ContextCompat.getDrawable(context, model.image))
    }
}