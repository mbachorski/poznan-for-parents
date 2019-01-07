package pl.mbachorski.poznanforparents.home

interface HelloRepository {
  fun giveHello(): String
}

class HelloRepositoryImpl : HelloRepository {
  override fun giveHello() = "Hello Koin"
}

class MyHelloPresenter(private val repo: HelloRepository) {

  fun sayHello() = "${repo.giveHello()} from $this"
}