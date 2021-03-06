package io.github.janbarari.starwars.presentation.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import io.github.janbarari.starwars.R
import io.github.janbarari.starwars.databinding.FragmentPictureBinding
import io.github.janbarari.starwars.presentation.base.BaseFragment
import io.github.janbarari.starwars.presentation.common.util.imageloder.ImageLoaderContext

const val PICTURE_ARGUMENT_KEY = "picture_url"

class PictureFragment : BaseFragment() {

    private lateinit var binding: FragmentPictureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_picture, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pictureUrl = arguments?.getString(PICTURE_ARGUMENT_KEY)
        if (pictureUrl == null) {
            findNavController().navigateUp()
            return
        }

        with(binding) {
            ImageLoaderContext.loader.bind(photo, R.drawable.placeholder, pictureUrl)
            close.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

}