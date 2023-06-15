package com.example.githubtask.ui.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubtask.R
import com.example.githubtask.adapter.GithubUserAdapter
import com.example.githubtask.base.BaseFragment
import com.example.githubtask.common.ItemClickListener
import com.example.githubtask.databinding.FragmentMainBinding
import com.example.githubtask.model.GithubUser

class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>(
    layoutId = R.layout.fragment_main,
    viewModelClass = MainFragmentViewModel::class.java
), ItemClickListener {
    private lateinit var adapter: GithubUserAdapter

    override fun onInitDataBinding() {

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.githubUsersRecyclerview.layoutManager = LinearLayoutManager(context)
        adapter = GithubUserAdapter(arrayListOf(), this)

        binding.githubUsersRecyclerview.addItemDecoration(
            DividerItemDecoration(
                binding.githubUsersRecyclerview.context,
                (binding.githubUsersRecyclerview.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.githubUsersRecyclerview.adapter = adapter

        binding.search.addTextChangedListener {
            viewModel.getUser(it.toString())   // Call viewModel
        }
    }

    private fun setupObserver() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                renderList(it)
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                showToast(getString(R.string.user_not_found)) // user not found
            }
        })
    }


    private fun renderList(user: GithubUser) {
        adapter.clearData()
        adapter.addData(listOf(user))
        adapter.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(user: GithubUser) {
        val bundle = Bundle().apply {
            putSerializable("user", user)
        }

        findNavController().navigate(R.id.action_mainFragment_to_followsFragment, bundle)
    }



}
