package pl.mbachorski.poznanforparents.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import pl.mbachorski.rss.data.Article
import pl.mbachorski.rss.data.ArticleRepository

class ArticleListViewModel internal constructor(
  private val articleRepository: ArticleRepository
) : ViewModel() {

  private val articleList = MediatorLiveData<List<Article>>()

  init {
    val articles = articleRepository.getArticles()
    articleList.addSource(articles, articleList::setValue)
  }

  fun getArticles() = articleList
}

