package chechetkin.yuri.vktestapp.screens.game.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import chechetkin.yuri.vktestapp.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    @DrawableRes val image: Int,
    val name: String,
    val serial: Serial,
    var result: Boolean? = null
): Parcelable


enum class Serial(@StringRes val serialName: Int) {
    GOT(R.string.game_of_throne_text),
    LOR(R.string.lord_of_the_ring_text)
}