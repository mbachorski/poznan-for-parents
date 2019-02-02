package pl.mbachorski.poznanforparents.rss

import android.util.Log
import pl.mbachorski.rss.data.Article
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

fun List<RssFeedItem>.printList() {
  this.forEach { it -> Log.v("RSS", it.publishDate + " " + it.title) }
}

fun List<RssFeedItem>.toArticles(source: String): List<Article> {
  return this.map { it ->
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
