package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.pages

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.common.BaseGraphicFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.ChartEntryModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils.RankingChartUtil
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils.RankingUtil
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsViewState
import kotlinx.android.synthetic.main.fragment_meta_ret_activas.*
import kotlinx.android.synthetic.main.grafico_base_gz.*
import org.koin.android.ext.android.inject

class MetaRetActivasFragment : BaseGraphicFragment() {

    private val chartUtil: RankingChartUtil by inject()

    private val listaText: List<TextView?>
        get() = listOf(
            txtValCx1, txtValCx2, txtValCx3,
            txtValCx4, txtValCx5, txtValCx6
        )

    override fun getLayout() = R.layout.fragment_meta_ret_activas

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupViewModel()
        setupEvents()
    }

    private fun setupTitle() {
        tvTitle?.text = getString(R.string.ranking_retention_goal)
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer<RankingChartsViewState> { state ->
        when (state) {
            is RankingChartsViewState.Success -> setupChart(state.data.activesRetention.chartData)
        }
    }

    private fun setupChart(list: List<ChartEntryModel>) {
        chartUtil.configurarLineChart(graficoLineal ?: return, list)
        val percentageMax = list.firstOrNull()?.maxValue?.toInt()
        txtMaxValue?.text = getString(R.string.valor_porcentaje, percentageMax)
        list.forEachIndexed { index, element ->
            listaText.getOrNull(index)?.also { RankingUtil.getPercentage(it, element) }
        }
    }

    private fun setupEvents() {
        imgNex?.setOnClickListener {
            contenedorFragment?.nextPage()
        }
        imgback?.setOnClickListener {
            contenedorFragment?.previousPage()
        }
    }

}
