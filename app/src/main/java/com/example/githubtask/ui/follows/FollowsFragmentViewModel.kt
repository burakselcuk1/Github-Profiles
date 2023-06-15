package com.example.githubtask.ui.follows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubtask.base.BaseViewModel
import com.example.githubtask.model.GithubUser
import com.example.githubtask.repository.GithubUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowsFragmentViewModel @Inject constructor(private val repository: GithubUsersRepository) : BaseViewModel() {

    val followingUsers: LiveData<List<GithubUser>>
        get() = _followingUsers
    private val _followingUsers = MutableLiveData<List<GithubUser>>()

    fun getFollowing(user: String) {
        viewModelScope.launch {
            try {
                _followingUsers.value = repository.getFollowing(user)
            } catch (e: Exception) {
                // Error handling
            }
        }
    }
}