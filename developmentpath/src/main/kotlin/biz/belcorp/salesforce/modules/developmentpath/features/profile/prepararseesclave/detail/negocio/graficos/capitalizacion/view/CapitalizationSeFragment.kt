package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.GraphicSEFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.adapter.CapitalizationSeAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model.CapitalizationSeModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model.GraphicCapitalizationSeModel
import kotlinx.android.synthetic.main.fragment_capitalizacion_seccion.*
import kotlinx.android.synthetic.main.include_empty_grafico.*
import org.koin.android.viewmodel.ext.android.viewModel

class CapitalizationSeFragment : GraphicSEFragment() {

    private val viewModel by viewModel<CapitalizationSeViewModel>()

    private var capitalizationSeAdapter: CapitalizationSeAdapter? = null

    override fun getLayout(): Int = R.layout.fragment_capitalizacion_seccion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        capitalizationSeAdapter = CapitalizationSeAdapter()
        rclvCapitalizacion?.isNestedScrollingEnabled = false
        rclvCapitalizacion?.layoutManager = LinearLayoutManager(context)
        rclvCapitalizacion?.adapter = capitalizationSeAdapter
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.obtenerCapitalizacion(personIdentifier)
    }

    private val viewStateObserver = Observer<CapitalizationSeViewState> {
        when (it) {
            is CapitalizationSeViewState.Success -> updateData(it.model)
            else -> showEmptyView()
        }
    }

    private fun updateData(model: GraphicCapitalizationSeModel) = with(model) {
        if (!isAttached()) return
        setupChart(model.maxValue, model.minValue)
        showDataInChart(model.barEntrySet)
        showDataInList(model.items)
    }

    private fun showDataInList(capitalizacionModel: List<CapitalizationSeModel>) {
        capitalizationSeAdapter?.setElementsUpdate(capitalizacionModel)
    }

    private fun showDataInChart(barEntrySet: BarEntrySet) {
        barEntrySet.colors = obtenerColores()
        barChartCapitalizacion?.refreshData(barEntrySet)
        graficoGroup?.visible()
    }

    private fun setupChart(maxValue: Float, minValue: Float) {
        barChartCapitalizacion?.maxValue = maxValue
        barChartCapitalizacion?.minValue = minValue
    }

    private fun showEmptyView() {
        if (!isAttached()) return
        layoutEmpty?.visible()
    }

    private fun obtenerColores(): List<Int> {
        return listOfNotNull(
            context?.getCompatColor(R.color.color_ingresos),
            context?.getCompatColor(R.color.color_reingresos),
            context?.getCompatColor(R.color.color_egresos)
        )
    }
}
