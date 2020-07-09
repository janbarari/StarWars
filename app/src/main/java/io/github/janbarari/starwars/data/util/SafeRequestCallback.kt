package io.github.janbarari.starwars.data.util

interface SafeRequestCallback<T> {
    fun onSuccess(response: T)
    fun onFailed(exception: Throwable)
}