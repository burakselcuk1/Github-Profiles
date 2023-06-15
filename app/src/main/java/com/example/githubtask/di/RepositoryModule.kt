package com.example.githubtask.di

import com.example.githubtask.repository.GithubUsersRepository
import com.example.githubtask.services.GithubServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGithubUsersRepository(githubServiceImpl: GithubServiceImpl): GithubUsersRepository {
        return GithubUsersRepository(githubServiceImpl)
    }
}
