package chechetkin.yuri.vktestapp.screens

import chechetkin.yuri.vktestapp.screens.game.models.Character

interface MainScreen {
    fun onGameFinished(result: List<Character>)
    fun onGameRepeat()
}