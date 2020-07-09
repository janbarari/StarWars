package io.github.janbarari.starwars.domain

import java.io.Serializable

data class Planet(
    private val name: String?,
    val rotationPeriod: Int,
    val orbitalPeriod: Int,
    val diameter: Int,
    val climate: String?,
    val gravity: String?,
    val terrain: String?,
    val surfaceWater: Int,
    val population: Int,
    val residents: List<String>?,
    val created: String?,
    val edited: String?,
    private val imageUrl: String?,
    val likes: Int): Serializable {

    fun getImageUrl(): String? {
        return imageUrl?.replace("http://", "https://")
    }

    fun getName(): String {
        return "$name Planet"
    }

}