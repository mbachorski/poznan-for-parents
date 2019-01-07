package pl.mbachorski.poznanforparents

import org.junit.Test
import pl.mbachorski.poznanforparents.rss.toSimpleListItemList
import pl.mbachorski.rss.domain.RssFeedItem

class ExtensionsTest {
  @Test
  fun `Should convert from RssFeedItem list to SimpleListItem list`() {
    val rssFeedItemList = mutableListOf<RssFeedItem>()
    val item = RssFeedItem("desc", "url", "link", "date", "title")
    rssFeedItemList.add(item)
    rssFeedItemList.add(item)

    val simpleListItemList = rssFeedItemList.toSimpleListItemList()
    assert(simpleListItemList.size == rssFeedItemList.size)
    assert(simpleListItemList[0].getTitle() == rssFeedItemList[0].title)
  }
}
