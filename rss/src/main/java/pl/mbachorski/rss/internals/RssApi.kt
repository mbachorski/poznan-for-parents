package pl.mbachorski.rss.internals

import me.toptas.rssconverter.RssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

internal interface RssApi {
  @GET
  fun getRss(@Url url: String): Call<RssFeed>
}