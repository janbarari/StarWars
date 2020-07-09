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

const val PLANET_ARGUMENT_KEY = "planet"

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

        val planet: Planet? = arguments?.getSerializable(PLANET_ARGUMENT_KEY) as Planet
        viewModel.planet = planet
        if (planet == null) {
            findNavController().popBackStack()
            return
        }

        val adapter = GenericAdapter(requireContext(),viewModel.residents, this@ResidentsFragment)
        adapter.addView(
            ResidentViewHolder::class.java,
            ResidentAdapterModel::class.java,
            R.layout.adapter_cell_resident
        )
        binding.recyclerview.apply {
            setHasFixedSize(true)
            this.layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
        }
        if (viewModel.recyclerViewState != null) {
            (binding.recyclerview.layoutManager as LinearLayoutManager).onRestoreInstanceState(
                viewModel.recyclerViewState
            )
        }

        viewModel.fetchResidents()
        viewModel.resident.observe(viewLifecycleOwner, Observer {
            viewModel.residents.add(it.toGenericViewModel())
            adapter.notifyItemInserted(viewModel.residents.size)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            binding.error.text = it.message
            binding.progress.visibility = View.GONE
        })

        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.disposeResidents()
        viewModel.recyclerViewState = binding.recyclerview.layoutManager?.onSaveInstanceState()
    }

    override fun onClick(event: Any) {
        val clickedEvent = event as ResidentAdapterModel
        val directions = ResidentsFragmentDirections.residentDirection(
            clickedEvent.toResident()
        )
        findNavController().navigate(directions)
    }

}