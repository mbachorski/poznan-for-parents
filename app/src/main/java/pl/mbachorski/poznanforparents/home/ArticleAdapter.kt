/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.mbachorski.poznanforparents.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.mbachorski.poznanforparents.R
import pl.mbachorski.poznanforparents.databinding.ListItemArticleBinding
import pl.mbachorski.rss.data.Article

/**
 * Adapter for the [RecyclerView] in [ArticleListFragment].
 */
class ArticleAdapter :
  ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

  override fun onBindViewHolder(holderArticle: ArticleViewHolder, position: Int) {
    val article = getItem(position)
    holderArticle.apply {
      bind(createOnClickListener(article.articleId, position), article)
      Log.v("RSS", " bind(createOnClickListener(article.articleId), article): " + article.articleId)
      itemView.tag = article
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
    return ArticleViewHolder(
      ListItemArticleBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  }

  private fun createOnClickListener(articleId: String, position: Int): View.OnClickListener {
    return View.OnClickListener {
      val view = it.findViewById<ImageView>(R.id.article_item_image)
      Log.v("RSS", "createOnClickListener: $articleId")
      Log.v("RSS", "createOnClickListener: $view")
      Log.v("RSS", "setting TRANSITION:[test$position]")
      ViewCompat.setTransitionName(view, articleId)

      val extras = FragmentNavigatorExtras(view to "test$position")
      val direction = HomeFragmentDirections.ActionHomeDestToArticleDetailsDest(articleId, "test$position")
      it.findNavController().navigate(direction, extras)
    }
  }

  class ArticleViewHolder(
    private val binding: ListItemArticleBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: View.OnClickListener, item: Article) {
      binding.apply {
        clickListener = listener
        article = item
        executePendingBindings()
      }
    }
  }
}

private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

  override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
    return oldItem.articleId == newItem.articleId
  }

  override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
    return oldItem == newItem
  }
}