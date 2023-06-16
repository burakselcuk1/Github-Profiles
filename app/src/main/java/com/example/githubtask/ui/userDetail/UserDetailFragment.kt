package com.example.githubtask.ui.userDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.githubtask.R
import com.example.githubtask.base.BaseFragment
import com.example.githubtask.databinding.FragmentUserDetailBinding
import com.example.githubtask.model.GithubUser


class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserDetailViewModel>(
    layoutId = R.layout.fragment_user_detail,
    viewModelClass = UserDetailViewModel::class.java
) {
    override fun onInitDataBinding() {
        arguments?.let {
            val user = it.getParcelable("userSelected") as? GithubUser
            if(user != null) {
                // İşlem yapmak için 'user' nesnesini kullanabilirsiniz
                Log.d("burak", user.following.toString())

                with(binding) {
                    followUserName.text = user.login
                    followBio.text = user.bio
                    Glide.with(requireActivity())
                        .load(user.avatarUrl)
                        .into(followUserImage)
                }
            } else {
                // Handle null user scenario
                Log.d("burak", "User is null")
            }
        }

        with(binding){
            toolbar.toolbarTitle.text = getString(R.string.user_detail)
            toolbar.backButton.setOnClickListener {
                findNavController().popBackStack()

            }
        }
    }


}