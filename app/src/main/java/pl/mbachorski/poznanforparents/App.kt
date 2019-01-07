package pl.mbachorski.poznanforparents

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module
import pl.mbachorski.poznanforparents.home.HelloRepository
import pl.mbachorski.poznanforparents.home.HelloRepositoryImpl
import pl.mbachorski.poznanforparents.home.MyHelloPresenter

class App : Application() {
  private val appModule = module {

    // single instance of HelloRepository
    single<HelloRepository> { HelloRepositoryImpl() }

    // Simple Presenter Factory
    factory { MyHelloPresenter(get()) }
  }

  override fun onCreate() {
    super.onCreate()

    startKoin(this, listOf(appModule))
  }
}