package pl.mbachorski.poznanforparents.home

import pl.mbachorski.ui.simplelist.SimpleListItem

class HomeListItem(private val title: String) : SimpleListItem {
  override fun getTitle() = title
}