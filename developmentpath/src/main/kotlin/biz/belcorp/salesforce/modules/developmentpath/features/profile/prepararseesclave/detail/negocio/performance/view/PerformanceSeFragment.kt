package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.utils.decoration.BackgroundPairTintDecorator
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.adapter.PerformanceAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.model.PerformanceModel
import kotlinx.android.synthetic.main.fragment_desempenio_se.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class PerformanceSeFragment : BaseFragment() {

    private val viewModel by viewModel<PerformanceSeViewModel>()

    private val personIdentifier by lazyPersonIdentifier()

    private val performanceAdapter by lazy { PerformanceAdapter() }

    override fun getLayout(): Int = R.layout.fragment_desempenio_se

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        configurarRecycler()
    }

    private fun configurarRecycler() {
        rvDesempenio?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            addItemDecoration(
                BackgroundPairTintDecorator(
                    R.color.background_color,
                    BaseR.color.white
                )
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = performanceAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getPerformance(personIdentifier)
    }

    private val viewStateObserver = Observer<PerformanceSeViewState> {
        when (it) {
            is PerformanceSeViewState.Success -> showData(it.model)
        }
    }

    private fun showData(performance: PerformanceModel) = with(performance) {
        if (!isAttached()) return
        tvEstado?.text = productivity
        tvSinDataEstado?.visible(hasNotProductivity)
        performanceAdapter.update(successfulHistoric)
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier): PerformanceSeFragment {
            return PerformanceSeFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}
