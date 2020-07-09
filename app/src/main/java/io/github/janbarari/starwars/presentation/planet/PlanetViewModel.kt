package io.github.janbarari.starwars.presentation.planet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.janbarari.starwars.data.network.exception.LikeException
import io.github.janbarari.starwars.data.repository.PlanetRepository
import io.github.janbarari.starwars.data.util.SafeRequestCallback
import io.github.janbarari.starwars.data.util.SafeRxRequest
import io.github.janbarari.starwars.domain.Planet
import io.github.janbarari.starwars.presentation.planet.model.LikeModel

const val KAMINO_PLANET_ID = 10

class PlanetViewModel(private val planetRepository: PlanetRepository) : ViewModel(), SafeRxRequest {

    val progressState: MutableLiveData<Boolean> = MutableLiveData(true)
    val kaminoPlanet: MutableLiveData<Planet> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()
    val like: MutableLiveData<LikeModel> = MutableLiveData()

    init {
        fetchKamino()
    }

    private fun fetchKamino() {
        observableRequest(
            planetRepository.fetchPlanet(KAMINO_PLANET_ID),
            object : SafeRequestCallback<Planet> {
                override fun onSuccess(response: Planet) {
                    kaminoPlanet.postValue(response)
                    progressState.postValue(false)
                }

                override fun onFailed(exception: Throwable) {
                    error.postValue(exception)
                }
            })
    }

    fun like() {
        observableRequest(
            planetRepository.likePlanet(KAMINO_PLANET_ID),
            object : SafeRequestCallback<Int> {
                override fun onSuccess(response: Int) {
                    like.postValue(
                        LikeModel(
                            true,
                            response
                        )
                    )
                }

                override fun onFailed(exception: Throwable) {
                    error.postValue(LikeException(exception.message!!))
                }
            })
    }

}