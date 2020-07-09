package io.github.janbarari.starwars.data.network.response

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("planet_id") val planet_id: Int,
    @SerializedName("likes ") val likes: Int
)