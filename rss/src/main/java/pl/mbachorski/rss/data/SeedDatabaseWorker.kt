package pl.mbachorski.rss.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

const val ARTICLES_DATA_FILENAME = "articles.json"

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) :
  Worker(context, workerParams) {
  private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

  override fun doWork(): Result {
    val plantType = object : TypeToken<List<Article>>() {}.type
    var jsonReader: JsonReader? = null

    return try {
      val inputStream = applicationContext.assets.open(ARTICLES_DATA_FILENAME)
      jsonReader = JsonReader(inputStream.reader())
      val plantList: List<Article> = Gson().fromJson(jsonReader, plantType)

      Log.v("RSS", "Inserting to DB: " + plantList.size)

      val database = AppDatabase.getInstance(applicationContext)
      database.articleDao().insertAll(plantList)


      Log.v("RSS", "Inserted to DB size: " + database.articleDao().getArticles().value?.size)

      Result.success()
    } catch (ex: Exception) {
      Log.e(TAG, "Error seeding database", ex)
      Result.failure()
    } finally {
      jsonReader?.close()
    }
  }
}
