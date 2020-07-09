package io.github.janbarari.starwars.data.repository

import io.github.janbarari.starwars.data.network.Api
import io.github.janbarari.starwars.data.network.response.LikeResponse
import io.github.janbarari.starwars.data.network.response.PlanetResponse
import io.github.janbarari.starwars.data.network.response.ResidentResponse
import io.github.janbarari.starwars.data.util.toPlanet
import io.github.janbarari.starwars.domain.Planet
import io.github.janbarari.starwars.domain.Resident
import io.reactivex.Observable

class ResidentRepository(private val api: Api) {

    fun fetchResident(id: Int): Observable<ResidentResponse> = api.fetchResident(id)

}