package pl.mbachorski.rss.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
  @Query("SELECT * FROM articles ORDER BY id")
  fun getArticles(): LiveData<List<Article>>

  @Query("SELECT * FROM articles WHERE id = :articleId")
  fun getArticle(articleId: String): LiveData<Article>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(articles: List<Article>)
}