package chechetkin.yuri.vktestapp.screens.game.models

import com.google.gson.annotations.SerializedName

data class GameItem(
    @SerializedName("serial") val serial: String,
    @SerializedName("items") val items: ArrayList<CharacterResponse>
)