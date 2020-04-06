package chechetkin.yuri.vktestapp.screens.game

import chechetkin.yuri.vktestapp.core.mvp.BasePresenter
import chechetkin.yuri.vktestapp.core.mvp.BaseView
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.screens.game.models.Serial


interface GameView : BaseView {

    fun showCharacterItems(items: List<Character>)
    fun onGameFinished(items: List<Character>)
}

interface GamePresenter : BasePresenter<GameView> {

    fun loadCharacterItems()
    fun onPositionSerialPicked(position: Int, correctSerial: Serial)
}
