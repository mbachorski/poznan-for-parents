package pl.mbachorski.poznanforparents

import pl.mbachorski.rss.RssFactory
import pl.mbachorski.rss.RssService

object TempDi {

  private val rssService: RssService = RssFactory.provideRssService()

  fun provideRssService() = rssService
}