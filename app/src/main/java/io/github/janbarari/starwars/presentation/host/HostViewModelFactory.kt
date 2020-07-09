package io.github.janbarari.starwars.presentation.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HostViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HostViewModel() as T
    }

}