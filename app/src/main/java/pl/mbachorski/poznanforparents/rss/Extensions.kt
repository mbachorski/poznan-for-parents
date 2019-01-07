package pl.mbachorski.poznanforparents.rss

import pl.mbachorski.rss.domain.RssFeedItem
import pl.mbachorski.ui.simplelist.SimpleListItem


fun List<RssFeedItem>.toSimpleListItemList(): List<SimpleListItem> {
  return this.map { it ->
    object : SimpleListItem {
      override fun getTitle(): String {
        return it.title!!
      }
    }
  }
}