package pl.mbachorski.rss.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

const val DATABASE_NAME = "articles.db"

//  @Database exportSchema = false -> could be used for in memory dbs
@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun articleDao(): ArticleDao

  companion object {

    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      return instance ?: synchronized(this) {
        instance ?: buildDatabase(context).also { instance = it }
      }
    }

    private fun buildDatabase(context: Context): AppDatabase {
      Log.v("RSS", "build db")
      return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .addCallback(object : RoomDatabase.Callback() {
          override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
            WorkManager.getInstance().enqueue(request)
            Log.v("RSS", "seed request enqueued")
          }
        })
        .allowMainThreadQueries() // TODO: remove in the future?
        .build()
    }
  }
}