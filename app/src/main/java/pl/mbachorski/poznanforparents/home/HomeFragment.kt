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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import pl.mbachorski.poznanforparents.R
import pl.mbachorski.ui.simplelist.SimpleListAdapter
import pl.mbachorski.ui.simplelist.SimpleListItem

/**
 * Fragment used to show how to navigate to another destination
 */
class HomeFragment : Fragment(), HomeView {
  private lateinit var presenter: HomePresenter
  private lateinit var adapter: SimpleListAdapter

  // Lazy injected MySimplePresenter
  val helloPresenter: MyHelloPresenter by inject()

  override fun showMessage() {
    Snackbar.make(view!!, "Message from presenter", Snackbar.LENGTH_SHORT).show()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    setHasOptionsMenu(true)
    return inflater.inflate(R.layout.home_fragment, container, false)
  }

  private fun setupPresenter() {
    presenter = HomePresenter(this)
    presenter.load()

    // testing koin
    Log.v("KOIN", helloPresenter.sayHello())
  }

  override fun updateData(newItems: List<SimpleListItem>) {
    adapter.updateData(newItems)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener {
      val action = HomeFragmentDirections.nextAction()
      action.flowStepNumber = 1
      findNavController().navigate(action)
    }

    fillList(view)

    setupPresenter()
  }

  private fun fillList(view: View) {
    Log.v("LIST", "fillList")
    val homeList = view.findViewById<RecyclerView>(R.id.home_list)
    val items = mutableListOf<SimpleListItem>()
    for (i in 0..100) {
      items.add(HomeListItem("item: [$i]"))
    }
    adapter = SimpleListAdapter(items)

    homeList.layoutManager = LinearLayoutManager(activity)
    homeList.adapter = adapter
    Log.v("LIST", "adapter set")
    adapter.notifyDataSetChanged()
  }
}
