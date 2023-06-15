package com.example.githubtask.ui.follows

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubtask.R
import com.example.githubtask.base.BaseFragment
import com.example.githubtask.databinding.FragmentFollowsBinding
import com.example.githubtask.model.GithubUser


class FollowsFragment : BaseFragment<FragmentFollowsBinding, FollowsFragmentViewModes>(
    layoutId = R.layout.fragment_follows,
    viewModelClass = FollowsFragmentViewModes::class.java
) {
    override fun onInitDataBinding() {

        arguments?.let {
            val user = it.getSerializable("user") as GithubUser
            // İşlem yapmak için 'user' nesnesini kullanabilirsiniz
            Log.d("burak",user.following.toString())
        }
    }
    }
