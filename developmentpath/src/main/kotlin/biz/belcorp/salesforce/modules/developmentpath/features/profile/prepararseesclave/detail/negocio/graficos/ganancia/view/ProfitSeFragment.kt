package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.view

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
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.adapter.ProfitSeAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model.GraphicProfitSeModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model.ProfitSeModel
import kotlinx.android.synthetic.main.fragment_ganancia_se.*
import kotlinx.android.synthetic.main.include_empty_grafico.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class ProfitSeFragment : GraphicSEFragment() {

    private val viewModel by viewModel<ProfitSeViewModel>()

    private var profitSeAdapter: ProfitSeAdapter? = null

    override fun getLayout(): Int = R.layout.fragment_ganancia_se

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        profitSeAdapter = ProfitSeAdapter()
        rclvGananciaSE?.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(BackgroundPairTintDecorator(R.color.background_color, BaseR.color.white))
            adapter = profitSeAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getProfit(personIdentifier)
    }

    private val viewStateObserver = Observer<ProfitSeViewState> {
        when (it) {
            is ProfitSeViewState.Success -> updateData(it.model)
            else -> showEmptyView()
        }
    }

    private fun updateData(model: GraphicProfitSeModel) = with(model) {
        if (!isAttached()) return
        setupChart(maxValue, minValue)
        showDataInChart(barEntrySet)
        showDataInList(items, maxValue)
        showAverage(average)
    }

    private fun setupChart(maxValue: Float, minValue: Float) {
        barChartGananciaSe?.maxValue = maxValue
        barChartGananciaSe?.minValue = minValue
    }

    private fun showDataInChart(barEntrySet: BarEntrySet) {
        barEntrySet.colors = getColors()
        barChartGananciaSe?.refreshData(barEntrySet)
        graficoGroup?.visible()
    }

    private fun showDataInList(list: List<ProfitSeModel>, maxValue: Float) {
        profitSeAdapter?.update(list, maxValue)
    }

    private fun showAverage(average: String) {
        txtMontoPromedio?.text = average
    }

    private fun showEmptyView() {
        if (!isAttached()) return
        layoutEmpty?.visible()
    }

    private fun getColors(): List<Int> {
        return listOfNotNull(
            context?.getCompatColor(R.color.grafico_ganancia)
        )
    }
}
