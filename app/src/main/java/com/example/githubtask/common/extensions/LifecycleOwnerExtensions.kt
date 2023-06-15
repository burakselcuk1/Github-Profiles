package com.example.githubtask.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.githubtask.common.Event

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t -> observer(t) }
    })
}

fun <T> LifecycleOwner.observe(mutableLiveData: MutableLiveData<T>, observer: (T) -> Unit) {
    mutableLiveData.observe(this, Observer {
        it?.let { t -> observer(t) }
    })
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer {
        it?.let { t -> observer(t) }
    })
}
fun <T> LifecycleOwner.observeEventLifecycle(liveData: LiveData<Event<T>>, observer: (T) -> Unit) {
    liveData.observe(this) {
        it?.getContentIfNotHandled()?.let { t -> observer(t) }
    }
}
fun <T> LifecycleOwner.observeEventLifecycle2(mutableLiveData: MutableLiveData<Event<T>>, observer: (T) -> Unit) {
    mutableLiveData.observe(this) {
        it?.getContentIfNotHandled()?.let { t -> observer(t) }
    }
}

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}