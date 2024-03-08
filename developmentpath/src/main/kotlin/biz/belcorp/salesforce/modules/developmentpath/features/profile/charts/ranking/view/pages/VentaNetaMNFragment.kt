package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.pages

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.adapters.VentasNetaAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.common.BaseGraphicFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.ChartEntryModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.NetSaleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils.RankingChartUtil
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsViewState
import kotlinx.android.synthetic.main.fragment_venta_net_amn.*
import org.koin.android.ext.android.inject

class VentaNetaMNFragment : BaseGraphicFragment() {

    private val chartUtil: RankingChartUtil by inject()

    private var ventasNetaAdapter: VentasNetaAdapter? = null

    private val listaText: List<TextView?>
        get() = listOf(
            txtCX1, txtCX2, txtCX3,
            txtCX4, txtCX5, txtCX6
        )

    override fun getLayout() = R.layout.fragment_venta_net_amn

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupAdapter()
        setupViewModel()
        setupEvents()
    }

    private fun setupTitle() {
        tvTitle?.text = getString(R.string.ranking_sales_net)
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer<RankingChartsViewState> { state ->
        when (state) {
            is RankingChartsViewState.Success -> {
                setupChart(state.data.netSale.chartData)
                setupItems(state.data.netSale.data)
            }
        }
    }

    private fun setupAdapter() {
        ventasNetaAdapter =
            VentasNetaAdapter()
        rcylViewMetasGS?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        rcylViewMetasGS?.adapter = ventasNetaAdapter
    }

    private fun setupChart(list: List<ChartEntryModel>) {
        chartUtil.configurarBarChartVenta(barChart ?: return, list)
        list.forEachIndexed { index, value ->
            listaText.getOrNull(index)?.text = value.caption
        }
    }

    private fun setupItems(list: List<NetSaleModel>) {
        ventasNetaAdapter?.setElementsUpdate(list)
    }

    private fun setupEvents() {
        imgNex.setOnClickListener {
            contenedorFragment?.nextPage()
        }
        imgback.setOnClickListener {
            contenedorFragment?.previousPage()
        }
    }

}
