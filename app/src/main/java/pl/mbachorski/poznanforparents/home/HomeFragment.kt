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

package pl.mbachorski.poznanforparents.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.ext.android.inject
import pl.mbachorski.poznanforparents.R
import pl.mbachorski.poznanforparents.TempDi
import pl.mbachorski.poznanforparents.databinding.HomeFragmentBinding

/**
 * Fragment used to show how to navigate to another destination
 */
class HomeFragment : Fragment() {

  private lateinit var viewModel: ArticleListViewModel

  // Lazy injected MySimplePresenter
  val helloPresenter: MyHelloPresenter by inject()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = HomeFragmentBinding.inflate(inflater, container, false)
    val context = context ?: return binding.root

    val factory = TempDi.provideArticleListViewModelFactory(context)
    viewModel = ViewModelProviders.of(this, factory).get(ArticleListViewModel::class.java)

    val adapter = ArticleAdapter()
    binding.articleList.adapter = adapter
    subscribeUi(adapter)

    setHasOptionsMenu(true)
    return binding.root
  }

  private fun subscribeUi(adapter: ArticleAdapter) {
    viewModel.getArticles().observe(viewLifecycleOwner, Observer { articles ->
      if (articles != null) adapter.submitList(articles)
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    ViewCompat.setTransitionName(home_small_image, HOME_TO_STEP_ONE_TRANSITION_NAME)
    view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener {
      val extras = FragmentNavigatorExtras(home_small_image to HOME_TO_STEP_ONE_TRANSITION_NAME)
      val action = HomeFragmentDirections.nextAction()
      action.flowStepNumber = 1
      findNavController().navigate(action, extras)
    }
  }
}
