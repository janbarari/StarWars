package io.github.janbarari.starwars.presentation.planet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.janbarari.starwars.R
import io.github.janbarari.starwars.data.network.exception.LikeException
import io.github.janbarari.starwars.databinding.FragmentPlanetBinding
import io.github.janbarari.starwars.presentation.base.BaseFragment
import io.github.janbarari.starwars.presentation.common.util.imageloder.ImageLoaderContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class PlanetFragment : BaseFragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: PlanetViewModelFactory by instance<PlanetViewModelFactory>()

    private lateinit var viewModel: PlanetViewModel
    private lateinit var binding: FragmentPlanetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_planet, container, false)
        viewModel = ViewModelProvider(this, factory).get(PlanetViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.kaminoPlanet.observe(viewLifecycleOwner, Observer { planet ->
            with(binding) {
                ImageLoaderContext.loader.bind(
                    thumbnail,
                    R.drawable.placeholder,
                    planet.getImageUrl()
                )
                ImageLoaderContext.loader.bind(liveImage, planet.getImageUrl())
                title.text = planet.getName()
                description.text =
                    "Climate: ${planet.climate} \n" +
                            "Population: ${planet.population} \n" +
                            "Surface Water: ${planet.surfaceWater} \n" +
                            "Gravity: ${planet.gravity} \n" +
                            "Diameter: ${planet.diameter} \n" +
                            "Terrian: ${planet.terrain} \n" +
                            "Orbital Period: ${planet.orbitalPeriod} \n" +
                            "Rotation Period: ${planet.rotationPeriod}"

                residents.setOnClickListener {
                    val direction = PlanetFragmentDirections.residentsDirection(
                        planet
                    )
                    findNavController().navigate(direction)
                }

                thumbnail.setOnClickListener {
                    val directions = PlanetFragmentDirections.pictureDirection(
                        planet.getImageUrl()
                    )
                    findNavController().navigate(directions)
                }

                like.setOnClickListener {
                    viewModel!!.like()
                }

            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it is LikeException) {
                Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
            } else {
                with(binding) {
                    error.apply {
                        visibility = View.VISIBLE
                        text = it.message
                    }
                    progress.apply {
                        visibility = View.GONE
                    }
                }
            }
        })

        viewModel.like.observe(viewLifecycleOwner, Observer {
            with(binding) {
                like.apply {
                    setImageResource(R.drawable.thumb_up)
                    isClickable = false
                }
                likeCount.text = it.likes.toString()
            }
        })

    }

}