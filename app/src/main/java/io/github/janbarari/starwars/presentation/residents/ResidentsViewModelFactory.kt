package io.github.janbarari.starwars.presentation.residents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.janbarari.starwars.data.network.Api

class ResidentsViewModelFactory(private val api: Api): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResidentsViewModel(api) as T
    }

}