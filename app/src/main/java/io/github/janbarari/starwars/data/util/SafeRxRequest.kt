package io.github.janbarari.starwars.data.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

interface SafeRxRequest {

    fun <T : Any> observableRequest(call: Observable<T>, callback: SafeRequestCallback<T>) {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    callback.onSuccess(response)
                }, { error ->
                    callback.onFailed(error)
                },
                {
                    compositeDisposable.dispose()
                })
        )
    }

}