package io.github.janbarari.starwars.presentation.planet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.janbarari.starwars.data.repository.PlanetRepository
import io.github.janbarari.starwars.data.util.SafeRequestCallback
import io.github.janbarari.starwars.data.util.SafeRxRequest
import io.github.janbarari.starwars.domain.Planet

const val KAMINO_PLANET_ID = 10

class PlanetViewModel(private val planetRepository: PlanetRepository) : ViewModel(), SafeRxRequest {

    val progressState: MutableLiveData<Boolean> = MutableLiveData()
    val kaminoPlanet: MutableLiveData<Planet> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()

    init {
        fetchKaminoPlanet()
    }

    private fun fetchKaminoPlanet() {
        progressState.postValue(true)
        observableRequest(planetRepository.getPlanet(KAMINO_PLANET_ID), object : SafeRequestCallback<Planet> {
                override fun onSuccess(response: Planet) {
                    kaminoPlanet.postValue(response)
                    progressState.postValue(false)
                }

                override fun onFailed(exception: Throwable) {
                    error.postValue(exception)
                    //progressState.postValue(false)
                }
            })
    }

}