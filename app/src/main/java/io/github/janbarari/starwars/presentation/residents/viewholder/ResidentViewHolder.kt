package io.github.janbarari.starwars.presentation.residents.viewholder

import android.view.View
import io.github.janbarari.genericrecyclerview.listener.GenericUriListener
import io.github.janbarari.genericrecyclerview.model.GenericViewModel
import io.github.janbarari.genericrecyclerview.viewholder.GenericViewHolder

class ResidentViewHolder(itemView: View, uriListener: GenericUriListener) :
    GenericViewHolder(itemView, uriListener) {

    override fun bind(dataModel: GenericViewModel) {
        super.bind(dataModel)

    }
}