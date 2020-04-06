package chechetkin.yuri.vktestapp.screens.game

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chechetkin.yuri.vktestapp.screens.game.models.Character

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var items: List<Character> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CharacterCardView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as? CharacterCardView)?.populate(items[position])
    }

    fun setItems(newItems: List<Character>) {
        items = newItems
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(index: Int): Character {
        return items[index]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}