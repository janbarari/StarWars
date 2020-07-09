package io.github.janbarari.starwars.domain

import java.io.Serializable

data class Resident (
    val name : String?,
    val height : String?,
    val mass : String?,
    val hairColor : String?,
    val skinColor : String?,
    val eyeColor : String?,
    val birthYear : String?,
    val gender : String?,
    val homeWorld : String?,
    val created : String?,
    val edited : String?,
    private val imageUrl : String?): Serializable {

    fun getImageUrl(): String? {
        return imageUrl?.replace("http://", "https://")
    }

}