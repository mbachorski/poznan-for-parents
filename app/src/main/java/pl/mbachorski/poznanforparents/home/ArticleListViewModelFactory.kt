@file:Suppress("UNCHECKED_CAST")

package pl.mbachorski.poznanforparents.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.mbachorski.rss.data.ArticleRepository

class ArticleListViewModelFactory(
  private val repository: ArticleRepository
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) = ArticleListViewModel(repository) as T
}