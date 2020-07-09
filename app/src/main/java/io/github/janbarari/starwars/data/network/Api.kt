package io.github.janbarari.starwars.data.network

import io.github.janbarari.starwars.NETWORK_BASE_URL
import io.github.janbarari.starwars.data.network.response.LikeResponse
import io.github.janbarari.starwars.data.network.response.PlanetResponse
import io.github.janbarari.starwars.data.network.response.ResidentResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

const val NETWORK_PROCESS_TIMEOUT = 15L

interface Api {

    @GET("planets/{id}")
    fun fetchPlanet(@Path("id") id: Int): Observable<PlanetResponse>

    @POST("planets/{id}/like")
    fun likePlanet(@Path("id") id: Int): Observable<LikeResponse>

    @GET("residents/{id}")
    fun fetchResident(@Path("id") id: Int): Observable<ResidentResponse>

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): Api {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .callTimeout(NETWORK_PROCESS_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(NETWORK_PROCESS_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NETWORK_PROCESS_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_PROCESS_TIMEOUT, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NETWORK_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

}