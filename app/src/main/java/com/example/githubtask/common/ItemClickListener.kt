package com.example.githubtask.common

import com.example.githubtask.model.GithubUser

interface ItemClickListener {
    fun onItemClicked(user: GithubUser)
}
