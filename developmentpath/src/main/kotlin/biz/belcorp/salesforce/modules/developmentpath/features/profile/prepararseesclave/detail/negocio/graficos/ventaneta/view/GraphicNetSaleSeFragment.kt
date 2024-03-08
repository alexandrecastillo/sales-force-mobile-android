package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.components.utils.decoration.BackgroundPairTintDecorator
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.GraphicSEFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.adapter.GraphicNetSaleAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models.GraphicNetSaleModel
import kotlinx.android.synthetic.main.fragment_graphic_net_sale_se.*
import kotlinx.android.synthetic.main.include_empty_grafico.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class GraphicNetSaleSeFragment : GraphicSEFragment() {

    private val viewModel by viewModel<GraphicNetSaleSeViewModel>()

    override fun getLayout(): Int = R.layout.fragment_graphic_net_sale_se

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAttached()) return
        setup()
    }

    private fun updateGraphicValues(minValue: Float, maxValue: Float) {
        barChartVentaNeta?.apply {
            this.maxValue = maxValue
            this.minValue = minValue
        }
    }

    private fun updateBarChart(barEntrySet: BarEntrySet) {
        barEntrySet.colors = getColors()
        barChartVentaNeta?.refreshData(barEntrySet)
        graficoGroup?.visible()
    }

    private fun updateData(model: GraphicNetSaleModel) = with(model) {
        updateGraphicValues(minValue, maxValue)
        updateBarChart(barEntrySet)
        (rvVentaNeta?.adapter as? GraphicNetSaleAdapter)?.actualizar(netSales)
    }

    private fun showNoData() {
        layoutEmpty?.visible()
    }

    private fun setup() {
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getNetSalesSE(personIdentifier)
    }

    private fun setupRecyclerView() {
        rvVentaNeta?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            addItemDecoration(
                BackgroundPairTintDecorator(
                    R.color.background_color,
                    BaseR.color.white
                )
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GraphicNetSaleAdapter()
        }
    }

    private fun getColors() = listOf(requireContext().getCompatColor(R.color.colorGraphicNetSale))

    private val viewStateObserver = Observer<GraphicNetSaleSeViewState> {
        when (it) {
            is GraphicNetSaleSeViewState.Success -> updateData(it.model)
            is GraphicNetSaleSeViewState.Empty -> showNoData()
            is GraphicNetSaleSeViewState.Failure -> Unit
        }
    }
}
