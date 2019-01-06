package pl.mbachorski.rss

import io.reactivex.subjects.PublishSubject
import pl.mbachorski.rss.domain.RssFeedItem

interface RssService {

  fun subscribeForFeed(subject: PublishSubject<List<RssFeedItem>>)
}