package com.example.githubtask.ui.userDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubtask.R
import com.example.githubtask.base.BaseFragment
import com.example.githubtask.databinding.FragmentUserDetailBinding


class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserDetailViewModel>(
    layoutId = R.layout.fragment_user_detail,
    viewModelClass = UserDetailViewModel::class.java
) {
    override fun onInitDataBinding() {
    }
}