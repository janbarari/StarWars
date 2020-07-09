package io.github.janbarari.starwars.presentation.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResidentViewModelFactory(): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResidentViewModel() as T
    }

}