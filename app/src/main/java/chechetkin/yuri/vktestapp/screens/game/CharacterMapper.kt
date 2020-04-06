package chechetkin.yuri.vktestapp.screens.game

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DrawableRes
import chechetkin.yuri.vktestapp.core.Mapper
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.screens.game.models.GameResponse
import chechetkin.yuri.vktestapp.screens.game.models.Serial

private const val DEF_TYPE_DRAWABLE = "drawable"

class CharacterMapper(
    private val context: Context
) : Mapper<GameResponse, MutableList<Character>> {

    override fun map(from: GameResponse): MutableList<Character> {
        val characterItems = mutableListOf<Character>()
        val gameItems = from.gameItems
        gameItems.forEach { gameItem ->
            gameItem.items.forEach {
                characterItems.add(
                    Character(
                        getDrawableByName(it.image),
                        it.name,
                        gameItem.serial.toSerial()
                    )
                )
            }
        }
        return characterItems
    }

    private fun String.toSerial(): Serial {
        return when (this) {
            context.getString(Serial.LOR.serialName) -> Serial.LOR
            else -> Serial.GOT
        }
    }

    @DrawableRes
    private fun getDrawableByName(name: String): Int {
        val resources: Resources = context.resources
        return resources.getIdentifier(
            name,
            DEF_TYPE_DRAWABLE,
            context.packageName
        )
    }
}