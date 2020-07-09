package io.github.janbarari.starwars.presentation.residents

import io.github.janbarari.genericrecyclerview.model.GenericViewModel
import io.github.janbarari.starwars.domain.Resident
import io.github.janbarari.starwars.presentation.residents.model.ResidentAdapterModel

fun Resident.toGenericViewModel(): GenericViewModel {
    return ResidentAdapterModel(
        this.name,
        this.height,
        this.mass,
        this.hairColor,
        this.skinColor,
        this.eyeColor,
        this.birthYear,
        this.gender,
        this.homeWorld,
        this.created,
        this.edited,
        this.getImageUrl()
    )
}

fun ResidentAdapterModel.toResident(): Resident {
    return Resident(
        this.name,
        this.height,
        this.mass,
        this.hairColor,
        this.skinColor,
        this.eyeColor,
        this.birthYear,
        this.gender,
        this.homeWorld,
        this.created,
        this.edited,
        this.imageUrl
    )
}