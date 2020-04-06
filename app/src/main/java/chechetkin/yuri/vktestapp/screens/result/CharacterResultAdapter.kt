package chechetkin.yuri.vktestapp.screens.result

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chechetkin.yuri.vktestapp.screens.game.models.Character
import chechetkin.yuri.vktestapp.core.PopulatableView

private const val CHARACTER_RESULT_RIGHT = 0
private const val CHARACTER_RESULT_WRONG = 1

class CharacterResultAdapter : RecyclerView.Adapter<CharacterResultAdapter.ViewHolder>() {

    private var items: List<Character> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return when (items[position].result) {
            true -> CHARACTER_RESULT_RIGHT
            else -> CHARACTER_RESULT_WRONG
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            CHARACTER_RESULT_RIGHT -> ViewHolder(CharacterResultRightView(parent.context))
            else -> ViewHolder(CharacterResultWrongView(parent.context))
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as? PopulatableView<Character>)?.populate(items[position])
    }

    fun setItems(newItems: List<Character>) {
        items = newItems
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}