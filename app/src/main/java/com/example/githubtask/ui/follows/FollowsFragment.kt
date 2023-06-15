package com.example.githubtask.ui.follows

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
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
            val user = it.getSerializable("user") as GithubUser
            // İşlem yapmak için 'user' nesnesini kullanabilirsiniz
            Log.d("burak", user.following.toString())

            viewModel.getFollowing(user.login)
            with(binding) {
                userName.text = user.login
                bio.text = user.bio
                Glide.with(requireActivity())
                    .load(user.avatarUrl)
                    .into(userImage)
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

    override fun onItemClicked(user: GithubUser) {
        // You can handle a user click here if needed
    }
}

