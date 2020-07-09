package io.github.janbarari.starwars.presentation.common.util.imageloder

import android.widget.ImageView

interface IImageLoader {
    fun bind(imageView: ImageView, url: String?)
    fun bind(imageView: ImageView, placeholder: Int, url: String?)
}