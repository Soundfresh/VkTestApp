package chechetkin.yuri.vktestapp.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import chechetkin.yuri.vktestapp.R
import chechetkin.yuri.vktestapp.screens.game.GameFragment
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.screens.result.ResultsFragment

class MainActivity : AppCompatActivity(), MainScreen {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNewScreen(GameFragment())
    }

    override fun onGameFinished(result: List<Character>) {
        startNewScreen(ResultsFragment.newInstance(result))
    }

    override fun onGameRepeat() {
        startNewScreen(GameFragment())
    }

    private fun startNewScreen(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
