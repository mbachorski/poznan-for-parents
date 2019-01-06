package pl.mbachorski.ui.simplelist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.mbachorski.ui.R

class SimpleListAdapter(private var items: MutableList<SimpleListItem>) :
  RecyclerView.Adapter<SimpleListAdapter.SimpleListViewHolder>() {

  class SimpleListViewHolder(val container: ViewGroup) : RecyclerView.ViewHolder(container) {
    internal val simpleListItemTitle: TextView = container.findViewById(R.id.simpleListItemTitle)
  }

  fun updateData(newItems: List<SimpleListItem>) {
    items.clear()
    items.addAll(newItems)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): SimpleListAdapter.SimpleListViewHolder {
    Log.v("LIST", "onCreateViewHolder")
    val container = LayoutInflater.from(parent.context)
      .inflate(R.layout.simple_list_item, parent, false) as ViewGroup
    return SimpleListAdapter.SimpleListViewHolder(container)
  }

  // Replace the contents of a view (invoked by the layout manager)
  override fun onBindViewHolder(holder: SimpleListViewHolder, position: Int) {
    Log.v("LIST", items[position].getTitle())
    holder.simpleListItemTitle.text = items[position].getTitle()
  }

  // Return the size of your dataset (invoked by the layout manager)
  override fun getItemCount() = items.size
}