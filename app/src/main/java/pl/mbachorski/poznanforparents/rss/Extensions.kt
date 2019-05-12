package pl.mbachorski.poznanforparents.rss

import pl.mbachorski.rss.data.Article
import pl.mbachorski.rss.domain.RssFeedItem
import pl.mbachorski.ui.simplelist.SimpleListItem
import timber.log.Timber


fun List<RssFeedItem>.toSimpleListItemList(): List<SimpleListItem> {
  return this.map {
    object : SimpleListItem {
      override fun getTitle(): String {
        return it.title!!
      }
    }
  }
}

fun List<RssFeedItem>.printList() {
  this.forEach { Timber.tag("RSS").v("%s%s", it.publishDate + " ", it.title) }
}

fun List<RssFeedItem>.toArticles(source: String): List<Article> {
  return this.map {
    Article(
      it.publishDate + "_" + source,
      source,
      it.description!!,
      it.image!!,
      it.link!!,
      it.publishDate!!,
      it.title!!
    )
  }
}
