package io.github.janbarari.starwars.presentation.residents.viewholder

import android.view.View
import io.github.janbarari.genericrecyclerview.listener.GenericUriListener
import io.github.janbarari.genericrecyclerview.model.GenericViewModel
import io.github.janbarari.genericrecyclerview.viewholder.GenericViewHolder
import io.github.janbarari.starwars.presentation.common.util.imageloder.ImageLoaderContext
import io.github.janbarari.starwars.presentation.residents.model.ResidentAdapterModel
import kotlinx.android.synthetic.main.adapter_cell_resident.view.*

class ResidentViewHolder(private val view: View, private val listener: GenericUriListener) :
    GenericViewHolder(view, listener) {

    override fun bind(dataModel: GenericViewModel) {
        super.bind(dataModel)
        val data = dataModel as ResidentAdapterModel
        with(view) {
            title.text = data.name
            ImageLoaderContext.loader.bind(image, data.imageUrl)
            root.setOnClickListener {
                listener.onClick(data)
            }
        }
    }
}