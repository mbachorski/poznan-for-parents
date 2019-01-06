package pl.mbachorski.poznanforparents.home

import io.reactivex.subjects.PublishSubject
import pl.mbachorski.poznanforparents.TempDi
import pl.mbachorski.rss.domain.RssFeedItem
import pl.mbachorski.ui.simplelist.SimpleListItem

class HomePresenter constructor(val view: HomeView) {

  private val subject = PublishSubject.create<List<RssFeedItem>>()

  fun load() {
    view.showMessage()

    subject.subscribe {
      System.out.println("onNext: ${it.size}")
      view.updateData(convertToSimpleItems(it))
    }

    TempDi.provideRssService().subscribeForFeed(subject)
  }

  private fun convertToSimpleItems(rssList: List<RssFeedItem>): List<SimpleListItem> {
    val converted = mutableListOf<SimpleListItem>()
    rssList.forEach {
      converted.add(object : SimpleListItem {
        override fun getTitle(): String {
          return it.description!!
        }
      })
    }
    return converted
  }

}