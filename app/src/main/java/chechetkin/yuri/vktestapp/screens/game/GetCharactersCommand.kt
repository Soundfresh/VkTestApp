package chechetkin.yuri.vktestapp.screens.game

import android.content.Context
import chechetkin.yuri.vktestapp.core.Command
import chechetkin.yuri.vktestapp.core.Mapper
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.screens.game.models.GameResponse
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import kotlin.text.Charsets.UTF_8

class GetCharactersCommand(
    private val context: Context,
    private val mapper: Mapper<GameResponse, MutableList<Character>>,
    private val gson: Gson
) : Command<String, List<Character>> {

    override fun request(request: String): List<Character> {
        val jsonString = getAssetsJSON(context, request)
        val response = gson.fromJson<GameResponse>(jsonString, GameResponse::class.java)
        return mapper.map(response).apply { this.shuffle() }
    }

    private fun getAssetsJSON(context: Context, fileName: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }
}
