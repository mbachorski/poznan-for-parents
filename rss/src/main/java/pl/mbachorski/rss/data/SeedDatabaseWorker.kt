package pl.mbachorski.rss.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import timber.log.Timber

const val ARTICLES_DATA_FILENAME = "articles.json"

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) :
  Worker(context, workerParams) {
  private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

  override fun doWork(): Result {
    val articleType = object : TypeToken<List<Article>>() {}.type
    var jsonReader: JsonReader? = null

    return try {
      val inputStream = applicationContext.assets.open(ARTICLES_DATA_FILENAME)
      jsonReader = JsonReader(inputStream.reader())
      val articleList: List<Article> = Gson().fromJson(jsonReader, articleType)

      Timber.tag("RSS").v("Inserting to DB: " + articleList.size)

      val database = AppDatabase.getInstance(applicationContext)
      database.articleDao().insertAll(articleList)


      Timber.tag("RSS").v("Inserted to DB size: " + database.articleDao().getArticles().value?.size)

      Result.success()
    } catch (ex: Exception) {
      Log.e(TAG, "Error seeding database", ex)
      Result.failure()
    } finally {
      jsonReader?.close()
    }
  }
}
