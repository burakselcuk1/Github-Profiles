package com.example.githubtask.common

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.util.*


fun <T> handleRequestFlow(call: suspend () -> T): Flow<Resource<T>> {
    return flow {
        emit(Resource.loading())
        try {
            val a = call() as Response<Any>

            for (b in StatusCode.values()) {
                if (a.code() in 200..299) {
                    emit(Resource.success(call()))
                    break
                } else if (a.code() in 300..399) {
                    if (a.code() == b.code) {
                        emit(Resource.error<T>(null, call()))
                        break;
                    }
                } else if (a.code() in 400..499) {
                    if (a.code() == b.code) {
                        emit(Resource.error<T>(null, call()))
                        break
                    }
                } else if (a.code() in 500..600) {
                    if (a.code() == b.code) {
                        emit(Resource.error<T>(null, b.name, a.code(), call()))
                        break;
                    }
                }
            }


        } catch (exception: Exception) {
            emit(Resource.error<T>(exception))
        }
    }.flowOn(Dispatchers.IO)
}

fun <T> handleRequestFlowDefault(call: suspend () -> T): Flow<Resource<T>> {
    return flow {
        emit(Resource.loading())
        try {
            val a = call() as Response<Any>
            if (a.code() == StatusCode.BadRequest.code)
                emit(Resource.error<T>(null, call()))
            else
                emit(Resource.success(call()))


        } catch (exception: Exception) {
            emit(Resource.error<T>(exception))
        }
    }.flowOn(Dispatchers.IO)
}

fun <T> handleRequestFlowDefault2(call: suspend () -> T): Flow<Resource<T>> {
    return flow {
        emit(Resource.loading())
        try {
            val response = call() as Response<Any>
            if (response.isSuccessful) {
                emit(Resource.success(call()))
            } else {
                emit(Resource.error<T>(null, call()))
            }

        } catch (exception: Exception) {
            emit(Resource.error<T>(exception))
        }
    }.flowOn(Dispatchers.IO)
}


fun <T> handleRequestFlowAdvance(call: suspend () -> T): Flow<Resource<T>> {
    return flow {
        emit(Resource.loading())
        try {
            val a = call() as Response<Any>

            if (a.code() in 200..299) {
                emit(Resource.success(call()))
            } else if (a.code() in 300..399) {
                emit(Resource.error<T>(null, call()))
            } else if (a.code() in 400..499) {
                emit(Resource.error<T>(null, call()))
            } else if (a.code() in 500..600) {
                emit(Resource.error<T>(null, "", a.code(), call()))
            }

        } catch (exception: Exception) {
            emit(Resource.error<T>(exception))
        }
    }.flowOn(Dispatchers.IO)
}


fun <T> toResultFlow(call: suspend () -> Response<T>?): Flow<ApiResult<T>?> {
    return flow {
        emit(ApiResult.loading)

        try {
            val c = call()
            c?.let {
                if (c.isSuccessful) {
                    emit(ApiResult.success(c.body()))
                } else {
                    c.errorBody()?.let {
                        val error = it.string()
                        it.close()
                        emit(ApiResult.error(error))
                    }
                }
            }
        } catch (e: Exception) {
            emit(ApiResult.error(e.toString()))
        }

    }.flowOn(Dispatchers.IO)
}

fun <T1 : Any, T2 : Any, R : Any> multipleLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> multipleLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> multipleLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> multipleLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5
    ) else null
}

inline fun <T> tryOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (_: Exception) {
        null
    }
}

inline fun <T> tryOrLog(block: () -> T) {
    try {
        block()
    } catch (e: Exception) {
        e.let {
            Log.e("TAG_Exception", e.message.orEmpty())
        }
    }
}

fun tickFlow(millis: Long) = callbackFlow {
    val timer = Timer()
    var time = 0
    timer.scheduleAtFixedRate(
        object : TimerTask() {
            override fun run() {
                try {
                    this@callbackFlow.trySend(time).isSuccess
                } catch (e: Exception) {
                }
                time += 1
            }
        },
        0,
        millis
    )
    awaitClose {
        timer.cancel()
    }
}

inline fun <reified T> castOrNull(from: Any?): T? = tryOrNull { from as? T }