package pl.mbachorski.rss.data

class ArticleRepository private constructor(private val articleDao: ArticleDao) {

  fun getArticles() = articleDao.getArticles()

  fun getArticle(articleId: String) = articleDao.getArticle(articleId)

  fun insertAll(articles: List<Article>) = articleDao.insertAll(articles)

  companion object {

    @Volatile
    private var instance: ArticleRepository? = null

    fun getInstance(articleDao: ArticleDao) =
      instance ?: synchronized(this) {
        instance ?: ArticleRepository(articleDao).also { instance = it }
      }
  }
}