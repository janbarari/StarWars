package io.github.janbarari.starwars.data.repository

import io.github.janbarari.starwars.data.network.Api
import io.github.janbarari.starwars.domain.Planet
import io.reactivex.Observable

class PlanetRepository(private val api: Api) {

    fun getPlanet(id: Int): Observable<Planet> = api.fetchPlanet(id)

}