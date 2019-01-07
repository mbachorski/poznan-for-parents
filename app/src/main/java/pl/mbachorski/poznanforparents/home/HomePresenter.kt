package pl.mbachorski.poznanforparents.home

import io.reactivex.subjects.PublishSubject
import pl.mbachorski.poznanforparents.TempDi
import pl.mbachorski.poznanforparents.rss.toSimpleListItemList
import pl.mbachorski.rss.domain.RssFeedItem

class HomePresenter constructor(val view: HomeView) {

  private val subject = PublishSubject.create<List<RssFeedItem>>()

  fun load() {
    view.showMessage()

    subject.subscribe {
      System.out.println("onNext: ${it.size}")
      view.updateData(it.toSimpleListItemList())
    }

    TempDi.provideRssService().subscribeForFeed(subject)
  }
}