package pl.mbachorski.poznanforparents

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.main_activity.*
import pl.mbachorski.poznanforparents.rss.printList
import pl.mbachorski.poznanforparents.rss.toArticles
import pl.mbachorski.rss.RssFactory
import pl.mbachorski.rss.data.Article
import pl.mbachorski.rss.domain.RssFeedItem
import timber.log.Timber

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setAppTheme()
    setContentView(R.layout.main_activity)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
      this,
      drawer_layout,
      toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()

    setupNavigationDrawer()

    testArticlesDbCnet()
  }

  // --------------
  private val subject = PublishSubject.create<List<RssFeedItem>>()

  private fun testArticlesDbCnet() {
    // INIT REPOSOTORY LOGGING
    val repository = RssFactory.getArticleRepository(this.applicationContext)
    repository.getArticles().observe(this, Observer { t -> logArticlesChange(t) })

    // INIT RSS DOWNLOADER LISTENER
    subject.subscribe {
      Timber.tag("RSS").v( "onNext: ${it.size}")
      it.printList()
      repository.insertAll(it.toArticles("CNET"))
    }

    // GET RSS FEED
    TempDi.provideRssService().subscribeForFeed(subject)
  }
//---------------


  private fun logArticlesChange(articles: List<Article>) {
    Timber.tag("RSS").v( "TEST: " + articles.size)
    articles.forEach { Timber.tag("RSS").v( it.toString()) }
  }

  private fun setAppTheme() {
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    val value = prefs.getString("theme", "0")
    Timber.tag("PREFS").v( value.toString())

    if (value.toInt() == 0) {
      setTheme(R.style.AppThemeLight_NoActionBar)
    } else {
      setTheme(R.style.AppThemeDark_NoActionBar)
    }
  }

  private fun setupNavigationDrawer() {
    val host: NavHostFragment = supportFragmentManager
      .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

    // Set up Action Bar
    val navController = host.navController
    val navigationDrawerLeft = findViewById<NavigationView>(R.id.navigation_drawer_left)

    navigationDrawerLeft?.setupWithNavController(navController)
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }
}
