
package chechetkin.yuri.vktestapp.screens.game.models

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("group_title") val groupTitle: String,
    @SerializedName("game_items") val gameItems: ArrayList<GameItem>
)