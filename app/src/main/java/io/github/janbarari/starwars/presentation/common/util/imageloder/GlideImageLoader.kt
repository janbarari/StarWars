package io.github.janbarari.starwars.presentation.common.util.imageloder

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader : IImageLoader {

    override fun bind(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context).load(url).apply(RequestOptions().dontTransform())
                .into(imageView)
        }
    }

    override fun bind(imageView: ImageView, placeholder: Int, url: String?) {
        url?.let {
            Glide.with(imageView.context).load(url).placeholder(placeholder).apply(RequestOptions().dontTransform())
                .into(imageView)
        }
    }

}