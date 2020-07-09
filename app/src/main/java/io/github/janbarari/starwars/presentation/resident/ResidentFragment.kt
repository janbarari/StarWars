package io.github.janbarari.starwars.presentation.resident

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.janbarari.starwars.R
import io.github.janbarari.starwars.databinding.FragmentResidentBinding
import io.github.janbarari.starwars.domain.Resident
import io.github.janbarari.starwars.presentation.base.BaseFragment
import io.github.janbarari.starwars.presentation.common.util.imageloder.ImageLoaderContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

const val RESIDENT_ARGUMENT_KEY = "resident"

class ResidentFragment : BaseFragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: ResidentViewModelFactory by instance<ResidentViewModelFactory>()

    private lateinit var viewModel: ResidentViewModel
    private lateinit var binding: FragmentResidentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_resident, container, false)
        viewModel = ViewModelProvider(this, factory).get(ResidentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resident: Resident? = arguments?.getSerializable(RESIDENT_ARGUMENT_KEY) as Resident
        if (resident == null) {
            findNavController().popBackStack()
            return
        }

        with(binding) {
            title.text = resident.name
            ImageLoaderContext.loader.bind(thumbnail, R.drawable.placeholder, resident.getImageUrl())
            description.text =
                "birthYear: ${resident.birthYear} \n" +
                        "Eye Color: ${resident.eyeColor} \n" +
                        "Gender: ${resident.gender} \n" +
                        "Hair Color: ${resident.hairColor} \n" +
                        "Mass: ${resident.mass} \n" +
                        "Skin Color: ${resident.skinColor} \n"
            ImageLoaderContext.loader.bind(liveImage, resident.getImageUrl())
            close.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

}