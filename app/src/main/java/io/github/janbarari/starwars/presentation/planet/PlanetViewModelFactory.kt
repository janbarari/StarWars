package io.github.janbarari.starwars.presentation.planet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.janbarari.starwars.data.repository.PlanetRepository

class PlanetViewModelFactory(private val planetRepository: PlanetRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlanetViewModel(planetRepository) as T
    }

}