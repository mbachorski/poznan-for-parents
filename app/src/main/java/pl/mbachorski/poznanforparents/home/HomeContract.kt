package pl.mbachorski.poznanforparents.home

import pl.mbachorski.ui.simplelist.SimpleListItem

interface HomeView {
  fun showMessage()
  fun updateData(newItems: List<SimpleListItem>)
}