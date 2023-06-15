package com.example.githubtask.ui.follows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubtask.R
import com.example.githubtask.base.BaseFragment
import com.example.githubtask.databinding.FragmentFollowsBinding


class FollowsFragment : BaseFragment<FragmentFollowsBinding, FollowsFragmentViewModes>(
    layoutId = R.layout.fragment_follows,
    viewModelClass = FollowsFragmentViewModes::class.java
) {
    override fun onInitDataBinding() {

    }
}