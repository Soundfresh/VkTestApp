package chechetkin.yuri.vktestapp.screens.game.models

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
)