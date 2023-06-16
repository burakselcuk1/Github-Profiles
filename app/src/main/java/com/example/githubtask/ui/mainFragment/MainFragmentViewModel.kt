package com.example.githubtask.ui.mainFragment

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
class MainFragmentViewModel @Inject constructor(private val repository: GithubUsersRepository): BaseViewModel() {

    private val _user = MutableLiveData<GithubUser>()
    val user: LiveData<GithubUser> get() = _user


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    val isLoading = MutableLiveData<Boolean>()


    fun getUser(username: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.getUsers(username)
                _user.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

}
