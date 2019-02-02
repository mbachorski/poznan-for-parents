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
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater

class FlowStepFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    setHasOptionsMenu(true)

    arguments?.let {
      // Uses type-safe arguments:
      val safeArgs = FlowStepFragmentArgs.fromBundle(it)
      val flowStepNumber = safeArgs.flowStepNumber
    }

    val transition = TransitionInflater.from(this.activity).inflateTransition(android.R.transition.move)
    sharedElementEnterTransition = ChangeBounds().apply {
      enterTransition = transition
    }

    return inflater.inflate(R.layout.flow_step_one_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<View>(R.id.next_button).setOnClickListener(
      Navigation.createNavigateOnClickListener(R.id.next_action)
    )
  }
}
