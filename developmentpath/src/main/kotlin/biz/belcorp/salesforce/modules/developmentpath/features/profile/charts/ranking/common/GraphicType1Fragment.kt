package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.common

import android.os.Bundle
import android.view.View
import android.widget.TextView
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.model.ChartEntryModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils.RankingChartUtil
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils.RankingUtil
import kotlinx.android.synthetic.main.fragment_rankingu6c_type_1.*
import kotlinx.android.synthetic.main.grafico_base_gz.*
import org.koin.android.ext.android.inject


abstract class GraphicType1Fragment : BaseGraphicFragment() {

    private val chartUtil: RankingChartUtil by inject()

    private val listaText: List<TextView?>
        get() = listOf(
            txtValCx1, txtValCx2, txtValCx3,
            txtValCx4, txtValCx5, txtValCx6
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTitle()
        setupEvents()
    }

    private fun setupTitle() {
        tvTitle?.text =  getTitle()
    }

    protected abstract fun getTitle(): String

    override fun getLayout() = R.layout.fragment_rankingu6c_type_1

    protected fun setupChart(list: List<ChartEntryModel>) {
        chartUtil.configurarLineChart(graficoLineal ?: return, list)
        val percentageMax = list.firstOrNull()?.maxValue?.toInt()
        txtMaxValue.text = getString(R.string.valor_porcentaje, percentageMax)
        list.forEachIndexed { index, element ->
            listaText.getOrNull(index)?.also { RankingUtil.getPercentage(it, element) }
        }
    }

    private fun setupEvents() {
        imgNext?.setOnClickListener {
            contenedorFragment?.nextPage()
        }
        imgBack?.setOnClickListener {
            contenedorFragment?.previousPage()
        }
    }

}
