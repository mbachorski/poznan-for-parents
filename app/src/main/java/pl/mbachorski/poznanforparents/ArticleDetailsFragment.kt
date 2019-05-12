/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.mbachorski.poznanforparents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.article_details_fragment.*
import pl.mbachorski.rss.RssFactory
import timber.log.Timber


class ArticleDetailsFragment : Fragment() {

  lateinit var articleId: String
  lateinit var transitionName: String

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    setHasOptionsMenu(true)

    var imageTransitionName = ""

    arguments?.let {
      // Uses type-safe arguments:
      val safeArgs = ArticleDetailsFragmentArgs.fromBundle(it)
      articleId = safeArgs.articleId
      transitionName = safeArgs.transitionName
      Timber.tag("RSS").v("ArticleDetailsFragment articleId: $articleId")

      Timber.tag("RSS").v("received TRANSITION:[$transitionName]")
      imageTransitionName = transitionName
    }

    val view = inflater.inflate(R.layout.article_details_fragment, container, false)
    val imageView = view.findViewById<ImageView>(R.id.articleDetailImage)
    Timber.tag("RSS")
      .v(
        "ImageView in details found, and transition name($imageTransitionName) set: $imageView"
      )
    imageView.transitionName = imageTransitionName

    val transition =
      TransitionInflater.from(this.activity).inflateTransition(android.R.transition.move)
    sharedElementEnterTransition = ChangeBounds().apply {
      enterTransition = transition
    }


    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val repository = RssFactory.getArticleRepository(this.context!!)
    val article = repository.getArticle(articleId)
    article.observe(this, Observer { art ->
      text.text = art.description
      Glide.with(articleDetailImage.context)
        .load(art.image)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(articleDetailImage)
    })
  }
}
