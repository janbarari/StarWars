package io.github.janbarari.starwars.data.repository

import io.github.janbarari.starwars.data.network.Api
import io.github.janbarari.starwars.data.network.response.LikeResponse
import io.github.janbarari.starwars.data.network.response.PlanetResponse
import io.github.janbarari.starwars.data.util.toPlanet
import io.github.janbarari.starwars.domain.Planet
import io.reactivex.Observable

class PlanetRepository(private val api: Api) {

    fun getPlanet(id: Int): Observable<Planet> = api.fetchPlanet(id).map { response -> response.toPlanet() }

    fun likePlanet(id: Int): Observable<Int> = api.likePlanet(id).map { response -> response.likes }

}