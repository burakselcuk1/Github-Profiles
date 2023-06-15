package com.example.githubtask.common

import com.example.githubtask.common.enums.Status

data class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable?, val errorMessage: String?, val errorCode: Int?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null,null,null)
        }

        fun <T> error(throwable: Throwable?): Resource<T> {
            return Resource(Status.ERROR, null, throwable,null,null)
        }
        fun <T> error(throwable: Throwable?,data: T?): Resource<T> {
            return Resource(Status.ERROR, data, throwable,null,null)
        }
        fun <T> error(throwable: Throwable?,errorMessage: String, errorCode: Int,data: T?): Resource<T> {
            return Resource(Status.ERROR, data, throwable,errorMessage,errorCode)
        }
        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null,null,null)
        }
    }
}
