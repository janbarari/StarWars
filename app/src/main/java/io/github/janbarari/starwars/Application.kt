package io.github.janbarari.starwars

import android.app.Application
import io.fabric.sdk.android.Fabric
import io.github.janbarari.starwars.data.network.Api
import io.github.janbarari.starwars.data.network.NetworkConnectionInterceptor
import io.github.janbarari.starwars.data.repository.PlanetRepository
import io.github.janbarari.starwars.data.repository.ResidentRepository
import io.github.janbarari.starwars.presentation.host.HostViewModelFactory
import io.github.janbarari.starwars.presentation.planet.PlanetViewModelFactory
import io.github.janbarari.starwars.presentation.resident.ResidentViewModelFactory
import io.github.janbarari.starwars.presentation.residents.ResidentsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

const val NETWORK_BASE_URL = "https://private-anon-9c59348131-starwars2.apiary-mock.com/"

class Application : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@Application))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { Api(instance()) }
        bind() from singleton { PlanetRepository(instance()) }
        bind() from singleton { ResidentRepository(instance()) }
        bind() from provider { HostViewModelFactory() }
        bind() from provider { PlanetViewModelFactory(instance()) }
        bind() from provider { ResidentsViewModelFactory(instance()) }
        bind() from provider { ResidentViewModelFactory() }
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this)
    }

}