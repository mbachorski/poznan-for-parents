package pl.mbachorski.rss

import android.content.Context
import pl.mbachorski.rss.data.AppDatabase
import pl.mbachorski.rss.data.ArticleRepository
import pl.mbachorski.rss.internals.RssServiceImpl

object RssFactory {
  fun provideRssService(): RssService {
    return RssServiceImpl()
  }

  fun getArticleRepository(context: Context): ArticleRepository {
    return ArticleRepository.getInstance(AppDatabase.getInstance(context).articleDao())
  }

}