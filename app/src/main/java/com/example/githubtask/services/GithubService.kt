package com.example.githubtask.services

import com.example.githubtask.model.GithubUser
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): GithubUser

    @GET("users/{user}/following")
    suspend fun getFollowing(@Path("user") user: String): List<GithubUser>

}