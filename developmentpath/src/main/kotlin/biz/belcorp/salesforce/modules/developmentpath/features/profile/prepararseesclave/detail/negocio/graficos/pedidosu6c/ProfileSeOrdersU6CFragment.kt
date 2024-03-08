package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.GraphicSEFragment
import kotlinx.android.synthetic.main.fragment_pedidos_seccion.*
import kotlinx.android.synthetic.main.include_empty_grafico.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileSeOrdersU6CFragment : GraphicSEFragment() {

    private val viewModel by viewModel<ProfileSeOrdersU6CViewModel>()

    private val observerDataResponse = Observer<ProfileSeOrdersU6CViewState> { state ->
        when (state) {
            is ProfileSeOrdersU6CViewState.Success -> doOnSuccess(state.data)
            is ProfileSeOrdersU6CViewState.Failure -> doOnFailure()
        }
    }

    override fun getLayout(): Int = R.layout.fragment_pedidos_seccion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        viewModel.getOrdersU6C(personIdentifier)
    }

    private fun doOnSuccess(data: OrderU6CGraphicModel) = with(data) {
        if (!isAttached()) return
        setupGraphic(minMaxValues)
        showGraphicValues(values)
    }

    private fun doOnFailure() {
        showEmptyMessage()
    }

    private fun setupGraphic(minMaxValues: Pair<Float, Float>) = with(minMaxValues) {
        barChartPedidosSeccion?.maxValue = second
        barChartPedidosSeccion?.minValue = first
    }

    private fun showGraphicValues(barEntrySet: BarEntrySet) {
        barEntrySet.colors = getGraphicColor()
        barChartPedidosSeccion?.refreshData(barEntrySet)
        graficoGroup?.visible()
    }

    private fun showEmptyMessage() {
        layoutEmpty?.visible()
    }

    private fun getGraphicColor() = listOfNotNull(context?.getCompatColor(R.color.grafico_pedidos))

}
