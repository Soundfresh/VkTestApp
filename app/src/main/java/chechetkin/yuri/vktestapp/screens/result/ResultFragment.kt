package chechetkin.yuri.vktestapp.screens.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chechetkin.yuri.vktestapp.R
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.screens.MainScreen

private const val ITEMS_KEY = "ITEMS_KEY"
private const val GRID_COLUMNS = 2
private const val DIFF_TO_ANIMATE = 700

class ResultsFragment : Fragment() {

    companion object {
        fun newInstance(result: List<Character>): ResultsFragment {
            val args = Bundle()
            args.putParcelableArrayList(ITEMS_KEY, ArrayList(result))
            return ResultsFragment().apply { arguments = args }
        }
    }

    private val items by lazy {
        requireNotNull(arguments?.getParcelableArrayList<Character>(ITEMS_KEY))
    }

    private val resultTextView by lazy { view?.findViewById<TextView>(R.id.result) }
    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.recycler_view) }
    private val scrollView by lazy { view?.findViewById<NestedScrollView>(R.id.scroll_view) }
    private val gradientView by lazy { view?.findViewById<View>(R.id.gradient_view) }
    private val tryAgainButton by lazy { view?.findViewById<View>(R.id.button_try_again) }

    private val adapter by lazy {
        CharacterResultAdapter().apply { setItems(items) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        initResultTitle()
        initList()
        initAnimationOnScroll()
        tryAgainButton?.setOnClickListener { repeatGame() }
    }

    private fun initAnimationOnScroll() {
        scrollView?.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { view, _, scrollY, _, _ ->
                val childViewBottom = view.getChildAt(0).bottom
                val viewHeight = view.height
                val diff = childViewBottom - (viewHeight + scrollY)
                val alpha = diff.toFloat() / DIFF_TO_ANIMATE
                if (alpha < 1f) {
                    gradientView?.alpha = alpha
                } else {
                    gradientView?.alpha = 1f
                }
            }
        )
    }

    private fun repeatGame() {
        (activity as MainScreen).onGameRepeat()
    }

    private fun initList() {
        recyclerView?.run {
            layoutManager = GridLayoutManager(context, GRID_COLUMNS)
            adapter = this@ResultsFragment.adapter
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }

    private fun initResultTitle() {
        val rightAnswersCount = items.filter { it.result == true }.size
        resultTextView?.text =
            resources.getString(R.string.result_pattern, rightAnswersCount, items.size)
    }
}