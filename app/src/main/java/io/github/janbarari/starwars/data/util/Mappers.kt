package io.github.janbarari.starwars.data.util

import io.github.janbarari.starwars.data.network.response.PlanetResponse
import io.github.janbarari.starwars.data.network.response.ResidentResponse
import io.github.janbarari.starwars.domain.Planet
import io.github.janbarari.starwars.domain.Resident

fun PlanetResponse.toPlanet(): Planet {
    return Planet(
        this.name,
        this.rotationPeriod,
        this.orbitalPeriod,
        this.diameter,
        this.climate,
        this.gravity,
        this.terrain,
        this.surfaceWater,
        this.population,
        this.residents,
        this.created,
        this.edited,
        this.imageUrl,
        this.likes
    )
}

fun ResidentResponse.toResident(): Resident {
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