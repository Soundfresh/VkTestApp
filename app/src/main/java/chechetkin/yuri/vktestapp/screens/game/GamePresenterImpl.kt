package chechetkin.yuri.vktestapp.screens.game

import android.view.View
import chechetkin.yuri.vktestapp.core.Command
import chechetkin.yuri.vktestapp.core.async
import chechetkin.yuri.vktestapp.core.mainThread
import chechetkin.yuri.vktestapp.core.mvp.BasePresenterImpl
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.screens.game.models.Serial

private const val GAME_JSON = "game.json"

class GamePresenterImpl(
    private val getCharactersCommand: Command<String, List<Character>>
) : BasePresenterImpl<GameView>(), GamePresenter {

    private val characterItems = mutableListOf<Character>()

    override fun loadCharacterItems() {
        async {
            getCharactersCommand.request(GAME_JSON).run {
                characterItems.addAll(this)
            }
            mainThread { view?.showCharacterItems(characterItems) }
        }
    }

    override fun onPositionSerialPicked(position: Int, correctSerial: Serial) {
        characterItems[position].result = characterItems[position].serial == correctSerial
        if (position == characterItems.size.dec()) {
            view?.onGameFinished(characterItems)
        }
    }
}