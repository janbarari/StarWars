package io.github.janbarari.starwars.presentation.planet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import io.github.janbarari.starwars.R
import io.github.janbarari.starwars.databinding.FragmentPlanetBinding
import io.github.janbarari.starwars.presentation.base.BaseFragment
import io.github.janbarari.starwars.presentation.common.util.imageloder.ImageLoaderContext
import io.github.janbarari.starwars.presentation.picture.PictureFragmentArgs
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PlanetFragment: BaseFragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: PlanetViewModelFactory by instance<PlanetViewModelFactory>()

    private lateinit var viewModel: PlanetViewModel
    private lateinit var binding: FragmentPlanetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_planet, container, false)
        viewModel = factory.create(PlanetViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.kaminoPlanet.observe(viewLifecycleOwner, Observer { planet ->
            with(binding) {
                ImageLoaderContext.loader.bind(thumbnail, R.drawable.placeholder, planet.imageUrl.replace("http", "https"))
                title.text = planet.name
                description.text =
                    """Climate: ${planet.climate} \n 
                       Population: ${planet.population} \n
                       Surface Water: ${planet.surfaceWater} \n
                       Gravity: ${planet.gravity} \n
                       Diameter: ${planet.diameter} \n
                       Terrian: ${planet.terrain} \n
                       Orbital Period: ${planet.orbitalPeriod} \n
                       Rotation Period: ${planet.rotationPeriod}"""
                residents.setOnClickListener {
                    val residents = planet.residents
                }
                Log.e("photo", planet.imageUrl.replace("http", "https"))
                thumbnail.setOnClickListener {
                    val directions = PlanetFragmentDirections.pictureDirection(
                        planet.imageUrl
                    )
                    val extras = FragmentNavigatorExtras(thumbnail to getString(R.string.planetImage_to_pictureImage))
                    findNavController().navigate(directions, extras)
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {

        })

    }

}