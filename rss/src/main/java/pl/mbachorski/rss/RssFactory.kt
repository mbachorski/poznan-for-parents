package pl.mbachorski.rss

import pl.mbachorski.rss.internals.RssServiceImpl

object RssFactory {
  fun provideRssService(): RssService {
    return RssServiceImpl()
  }
}