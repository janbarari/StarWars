package io.github.janbarari.starwars.presentation.residents

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.janbarari.starwars.data.network.Api
import io.github.janbarari.starwars.data.repository.ResidentRepository
import io.github.janbarari.starwars.data.util.SafeRxRequest
import io.github.janbarari.starwars.data.util.toResident
import io.github.janbarari.starwars.domain.Planet
import io.github.janbarari.starwars.domain.Resident
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ResidentsViewModel(private val residentRepository: ResidentRepository) : ViewModel(), SafeRxRequest {

    val residents: MutableLiveData<Resident> = MutableLiveData()
    val progressState: MutableLiveData<Boolean> = MutableLiveData(true)
    val error: MutableLiveData<Throwable> = MutableLiveData()
    var recyclerViewState: Parcelable? = null
    private val compositeDisposable = CompositeDisposable()

    fun fetchResidents(planet: Planet) {
        compositeDisposable.add(
            Observable
                .fromIterable(planet.residents)
                .concatMap {
                    val residentId =
                        it.trim().substring(it.lastIndexOf("/")).replace("/", "").toInt()
                    residentRepository.fetchResident(residentId)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        residents.postValue(it.toResident())
                        progressState.postValue(false)
                    },
                    {
                        error.postValue(it)
                    },
                    {
                        compositeDisposable.dispose()
                    })
        )
    }

}