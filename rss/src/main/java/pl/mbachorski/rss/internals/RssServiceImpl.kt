package pl.mbachorski.rss.internals

import io.reactivex.subjects.PublishSubject
import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import me.toptas.rssconverter.RssItem
import pl.mbachorski.rss.RssService
import pl.mbachorski.rss.domain.RssFeedItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber

internal class RssServiceImpl : RssService {

  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://www.cnet.com/rss/")
    .addConverterFactory(RssConverterFactory.create())
    .build()

  override fun subscribeForFeed(subject: PublishSubject<List<RssFeedItem>>) {
    Timber.tag("RSS").v("Subscribe for feed")

    val rssApi = retrofit.create(RssApi::class.java)
    rssApi.getRss("news")
      .enqueue(object : Callback<RssFeed> {
        override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
          Timber.tag("RSS").v("onSuccess")
          Timber.tag("RSS").v("Items size: ${response.body().items}")

          convertAndNotifySubject(response, subject)
        }

        override fun onFailure(call: Call<RssFeed>, t: Throwable) {
          // Show failure message
          Timber.tag("RSS").v("onFailure")
          t.printStackTrace()
        }
      })
  }

  private fun convertAndNotifySubject(
    response: Response<RssFeed>,
    subject: PublishSubject<List<RssFeedItem>>
  ) {
    val domainRssFeed = mutableListOf<RssFeedItem>()
    response.body().items?.iterator()?.forEach {
      domainRssFeed.add(it.convertToRssFeedItem())
    }
    subject.onNext(domainRssFeed)
  }

  private fun RssItem.convertToRssFeedItem(): RssFeedItem {
    return RssFeedItem(this.description, this.image, this.link, this.publishDate, this.title)
  }

}