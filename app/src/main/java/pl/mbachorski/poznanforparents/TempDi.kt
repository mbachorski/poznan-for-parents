package pl.mbachorski.poznanforparents

import android.content.Context
import pl.mbachorski.poznanforparents.home.ArticleListViewModelFactory
import pl.mbachorski.rss.RssFactory
import pl.mbachorski.rss.RssFactory.getArticleRepository
import pl.mbachorski.rss.RssService

object TempDi {

  private val rssService: RssService = RssFactory.provideRssService()

  fun provideRssService() = rssService

  fun provideArticleListViewModelFactory(context: Context): ArticleListViewModelFactory {
    val repository = getArticleRepository(context)
    return ArticleListViewModelFactory(repository)
  }
}