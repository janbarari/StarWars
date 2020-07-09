package io.github.janbarari.starwars.presentation.residents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.janbarari.genericrecyclerview.adapter.GenericAdapter
import io.github.janbarari.genericrecyclerview.listener.GenericUriListener
import io.github.janbarari.genericrecyclerview.model.GenericViewModel
import io.github.janbarari.starwars.R
import io.github.janbarari.starwars.databinding.FragmentResidentsBinding
import io.github.janbarari.starwars.domain.Planet
import io.github.janbarari.starwars.presentation.base.BaseFragment
import io.github.janbarari.starwars.presentation.residents.model.ResidentAdapterModel
import io.github.janbarari.starwars.presentation.residents.viewholder.ResidentViewHolder
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

const val RESIDENTS_PLANET_ARGUMENT_KEY = "planet"

class ResidentsFragment : BaseFragment(), KodeinAware, GenericUriListener {

    override val kodein: Kodein by kodein()
    private val factory: ResidentsViewModelFactory by instance<ResidentsViewModelFactory>()

    private lateinit var viewModel: ResidentsViewModel
    private lateinit var binding: FragmentResidentsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_residents, container, false)
        viewModel = ViewModelProvider(this, factory).get(ResidentsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val planet: Planet? = arguments?.getSerializable(RESIDENTS_PLANET_ARGUMENT_KEY) as Planet
        if (planet == null) {
            findNavController().popBackStack()
            return
        }

        viewModel.fetchResidents(planet)

        val residents: ArrayList<GenericViewModel> = arrayListOf()
        viewModel.residents.observe(viewLifecycleOwner, Observer {
            residents.add(it.toGenericViewModel())
            initializeRecyclerView(residents)
        })

        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initializeRecyclerView(residents: ArrayList<GenericViewModel>) {
        context?.let {
            binding.recyclerview.apply {
                setHasFixedSize(false)
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
                val adapter = GenericAdapter(it, residents, this@ResidentsFragment)
                adapter.addView(
                    ResidentViewHolder::class.java,
                    ResidentAdapterModel::class.java,
                    R.layout.adapter_cell_resident
                )
                this.layoutManager = GridLayoutManager(it, 2)
                this.adapter = adapter
            }
            if (viewModel.recyclerViewState != null) {
                (binding.recyclerview.layoutManager as LinearLayoutManager).onRestoreInstanceState(
                    viewModel.recyclerViewState
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.recyclerViewState = binding.recyclerview.layoutManager?.onSaveInstanceState()
    }

    override fun onClick(event: Any) {
        val clickedEvent = event as ResidentAdapterModel
//            val directions = SearchFragmentDirections.viewDrinkDetails(
//                clickedEvent.viewModel.idDrink!!,
//                clickedEvent.viewModel.strDrinkThumb!!,
//                clickedEvent.viewModel.strDrink!!
//            )
//            findNavController().navigate(directions, extras)
    }

}