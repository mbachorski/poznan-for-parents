package pl.mbachorski.rss.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
  @NonNull @PrimaryKey @ColumnInfo(name = "id") val articleId: String,
  val source: String,
  val description: String,
  val image: String,
  val link: String,
  val publishDate: String,
  val title: String
)