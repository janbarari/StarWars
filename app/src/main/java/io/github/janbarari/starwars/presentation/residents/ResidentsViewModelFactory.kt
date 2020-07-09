package io.github.janbarari.starwars.presentation.residents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.janbarari.starwars.data.network.Api
import io.github.janbarari.starwars.data.repository.ResidentRepository

class ResidentsViewModelFactory(private val residentRepository: ResidentRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResidentsViewModel(residentRepository) as T
    }

}