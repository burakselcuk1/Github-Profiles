package com.example.githubtask.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class Event<out T>(private val content: T) {
    private val handled = AtomicBoolean(false)

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

    fun consume(handleContent: (T) -> Unit) {
        if (!handled.get()) {
            handled.set(true)
            handleContent(content)
        }
    }
}

class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}