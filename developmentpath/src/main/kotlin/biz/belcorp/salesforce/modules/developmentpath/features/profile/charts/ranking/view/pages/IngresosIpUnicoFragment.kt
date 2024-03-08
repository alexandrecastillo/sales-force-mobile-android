package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.pages

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.common.GraphicType1Fragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsViewState

class IngresosIpUnicoFragment : GraphicType1Fragment() {

    override fun getTitle() = getString(R.string.ranking_unique_ip)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer<RankingChartsViewState> { state ->
        when (state) {
            is RankingChartsViewState.Success -> setupChart(state.data.uniqueIp.chartData)
        }
    }

}
