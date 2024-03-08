package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.GraphicSEFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.models.GraphicActivesRetentionModel
import kotlinx.android.synthetic.main.fragment_retencion_activas.*
import kotlinx.android.synthetic.main.include_empty_grafico.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class ActivesRetentionFragment : GraphicSEFragment() {

    private val viewModel by viewModel<ActivesRetentionViewModel>()

    override fun getLayout(): Int = R.layout.fragment_retencion_activas

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAttached()) return
        setupView()
    }

    private fun setupView() {
        viewModel.getActivesRetention(personIdentifier)
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private val viewStateObserver = Observer<ActivesRetentionViewState> {
        when (it) {
            is ActivesRetentionViewState.Success -> updateData(it.model)
            else -> showEmptyView()
        }
    }

    private fun updateData(model: GraphicActivesRetentionModel) = with(model) {
        setupChart(maxValue, minValue)
        showChartLegend(indicatorValue.toInt().toString(), year)
        showDataInChart(barEntrySet, indicatorValue)
    }

    private fun setupChart(maxValue: Float, minValue: Float) {
        barChartRetActivas?.maxValue = maxValue
        barChartRetActivas?.minValue = minValue
    }

    private fun showChartLegend(value: String, anio: String) {
        legendActivas?.descriptionText = getString(R.string.retencionactivas_18, value, anio)
    }

    private fun showDataInChart(barEntrySet: BarEntrySet, indicadorValue: Float) {
        barEntrySet.colors = getColors()
        barChartRetActivas?.indicatorValue = indicadorValue
        barChartRetActivas?.refreshData(barEntrySet)
        graficoGroup?.visible()
    }

    private fun showEmptyView() {
        layoutEmpty?.visible()
    }

    private fun getColors(): List<Int> {
        return listOfNotNull(
            context?.getCompatColor(BaseR.color.magenta)
        )
    }
}
