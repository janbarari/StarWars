package io.github.janbarari.starwars.presentation.residents.model

import io.github.janbarari.genericrecyclerview.model.GenericViewModel

class ResidentAdapterModel(
    val name: String?,
    val height: String?,
    val mass: String?,
    val hairColor: String?,
    val skinColor: String?,
    val eyeColor: String?,
    val birthYear: String?,
    val gender: String?,
    val homeWorld: String?,
    val created: String?,
    val edited: String?,
    val imageUrl: String?
) : GenericViewModel()