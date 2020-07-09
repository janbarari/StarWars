package io.github.janbarari.starwars.presentation.host

import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.ActivityNavigator
import io.github.janbarari.starwars.R
import io.github.janbarari.starwars.databinding.ActivityHostBinding
import io.github.janbarari.starwars.presentation.base.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HostActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: HostViewModelFactory by instance<HostViewModelFactory>()

    private lateinit var binding: ActivityHostBinding
    private lateinit var viewModel: HostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowFlag(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            true
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_host)
        viewModel = factory.create(HostViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

}