package com.example.githubtask.repository

import com.example.githubtask.model.GithubUser
import com.example.githubtask.services.GithubServiceImpl
import javax.inject.Inject

class GithubUsersRepository @Inject constructor(private val githubUser: GithubServiceImpl) {

    suspend fun getUsers(userName: String) = githubUser.getUsers(userName)

    suspend fun getFollowing(user: String): List<GithubUser> {
        return githubUser.getFollowing(user)
    }
}