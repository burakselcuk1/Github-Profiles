package com.example.githubtask.ui.follows

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubtask.R
import com.example.githubtask.adapter.GithubUserAdapter
import com.example.githubtask.base.BaseFragment
import com.example.githubtask.common.ItemClickListener
import com.example.githubtask.databinding.FragmentFollowsBinding
import com.example.githubtask.model.GithubUser


class FollowsFragment : BaseFragment<FragmentFollowsBinding, FollowsFragmentViewModel>(
    layoutId = R.layout.fragment_follows,
    viewModelClass = FollowsFragmentViewModel::class.java
), ItemClickListener {
    private lateinit var adapter: GithubUserAdapter
    override fun onInitDataBinding() {

        arguments?.let {
            val user = it.getParcelable("user") as? GithubUser
            if(user != null) {
                user.login?.let { it1 -> viewModel.getFollowing(it1) }
                with(binding) {
                    userName.text = user.login
                    bio.text = user.bio
                    Glide.with(requireActivity())
                        .load(user.avatarUrl)
                        .into(userImage)
                }
            } else {
                // Handle null user scenario
            }
        }

        with(binding){
            toolbar.toolbarTitle.text = getString(R.string.follows)
            toolbar.backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupUI()
        setupObserver()

    }

    private fun setupUI() {
        binding.followersRecyclerview.layoutManager = LinearLayoutManager(context)
        adapter = GithubUserAdapter(arrayListOf(), this)

        binding.followersRecyclerview.addItemDecoration(
            DividerItemDecoration(
                binding.followersRecyclerview.context,
                (binding.followersRecyclerview.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.followersRecyclerview.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.followingUsers.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                renderList(it)
            }
        })
    }

    private fun renderList(users: List<GithubUser>) {
        adapter.clearData()
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(userTwo: GithubUser) {
        userTwo.let { user ->
            val bundle = Bundle().apply {
                putParcelable("userSelected", user)
            }
            findNavController().navigate(R.id.action_followsFragment_to_userDetailFragment, bundle)
        }
    }
}

