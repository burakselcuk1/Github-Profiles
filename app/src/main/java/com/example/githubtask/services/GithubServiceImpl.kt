package com.example.githubtask.services

import com.example.githubtask.model.GithubUser
import javax.inject.Inject

class GithubServiceImpl @Inject constructor(private val githubService: GithubService) {

    suspend fun getUsers(userName: String) = githubService.getUser(userName)

}