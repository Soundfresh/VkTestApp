package chechetkin.yuri.vktestapp.screens.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import chechetkin.yuri.vktestapp.screens.MainScreen
import chechetkin.yuri.vktestapp.R
import chechetkin.yuri.vktestapp.core.mvp.MvpFragment
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.screens.game.models.Serial
import chechetkin.yuri.vktestapp.views.swipeview.ItemSwipeListener
import chechetkin.yuri.vktestapp.views.swipeview.SwipeLayoutManager
import chechetkin.yuri.vktestapp.views.swipeview.SwipeRecyclerView
import com.google.gson.Gson

private const val RESET_ANIMATION_DURATION = 200L

class GameFragment : MvpFragment<GamePresenter>(), GameView, ItemSwipeListener {

    private val leftButton by lazy { view?.findViewById<View>(R.id.button_left) }
    private val rightButton by lazy { view?.findViewById<View>(R.id.button_right) }
    private val swipeRecyclerView by lazy { view?.findViewById<SwipeRecyclerView>(R.id.recycler_view) }
    private val layoutManager by lazy { SwipeLayoutManager(this) }
    private val adapter by lazy { CharacterAdapter() }

    override val presenter: GamePresenter by lazy {
        GamePresenterImpl(
            GetCharactersCommand(
                requireContext(),
                CharacterMapper(requireContext()),
                Gson()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.game_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.attach(this)
        presenter.loadCharacterItems()
    }

    override fun onDetach() {
        presenter.detach()
        super.onDetach()
    }

    override fun showCharacterItems(items: List<Character>) {
        swipeRecyclerView?.visibility = View.VISIBLE
        adapter.setItems(items)
    }

    override fun onGameFinished(items: List<Character>) {
        swipeRecyclerView?.visibility = View.INVISIBLE
        (activity as? MainScreen)?.onGameFinished(items)
    }

    override fun onItemScaleRight(scale: Float) {
        rightButton?.scaleView(scale)
        leftButton?.scaleView(-scale)
    }

    override fun onItemScaleLeft(scale: Float) {
        leftButton?.scaleView(scale)
        rightButton?.scaleView(-scale)
    }

    override fun onItemSwipedLeft(position: Int) {
        onPositionSerialPicked(position, Serial.LOR)
    }

    override fun onItemSwipedRight(position: Int) {
        onPositionSerialPicked(position, Serial.GOT)
    }

    private fun onPositionSerialPicked(position: Int, correctSerial: Serial) {
        resetButtonsScale()
        presenter.onPositionSerialPicked(position, correctSerial)
    }

    private fun resetButtonsScale() {
        leftButton?.scaleView(0f, RESET_ANIMATION_DURATION)
        rightButton?.scaleView(0f, RESET_ANIMATION_DURATION)
    }

    private fun View.scaleView(scale: Float, duration: Long = 0L) {
        this.animate()?.scaleX(1f + scale)?.scaleY(1f + scale)?.duration = duration
    }

    private fun initViews() {
        initListeners()
        initList()
    }

    private fun initListeners() {
        leftButton?.setOnClickListener { swipeRecyclerView?.swipeLeft() }
        rightButton?.setOnClickListener { swipeRecyclerView?.swipeRight() }
    }

    private fun initList() {
        swipeRecyclerView?.layoutManager = layoutManager
        swipeRecyclerView?.adapter = adapter
    }
}