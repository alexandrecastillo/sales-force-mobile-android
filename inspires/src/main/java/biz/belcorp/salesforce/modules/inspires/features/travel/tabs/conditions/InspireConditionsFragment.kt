package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.features.analitycs.AnalyticsInspireViewModel
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions.adapters.InspireConditionsAdapter
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions.adapters.InspireConditionsLegendAdapter
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesLeyendaDetalleModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesLeyendaModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraCondicionesModel
import kotlinx.android.synthetic.main.fragment_inspire_travel_page_conditions.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class InspireConditionsFragment : BaseFragment(), InspireConditionsView {

    private val presenter: InspireConditionsPresenter by inject()
    private val analyticsInspireViewModel by viewModel<AnalyticsInspireViewModel>()

    override fun getLayout(): Int = R.layout.fragment_inspire_travel_page_conditions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepare()
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            sendAnalitycsScreenView()
        }
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            sendAnalitycsScreenView()
        }
    }

    private fun sendAnalitycsScreenView() {
        analyticsInspireViewModel.sendInspireScreen(TagAnalytics.EVENTO_INSPIRA_SCREEN_VISTA_CONDICIONES)
    }

    override fun showHeadeIcon(active: Boolean) {
        ivwInspiraHeaderIcon?.imageResource = if (active) R.drawable.ic_medal else R.drawable.ic_inspira_lost
    }

    override fun showHeaderMessage(active: Boolean, name: String?) {
        tvwInspiraHeaderTitle?.text = if (active) getString(R.string.conditions_header, name) else getString(R.string.conditions_header_lost)
    }

    override fun hideRecommendatioonTitle() {
        clyInspiraConditionContent?.visibility = View.GONE
    }

    override fun showConditionsList(list: List<InspiraCondicionesModel>) {
        rvwInspiraCondiciones?.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = InspireConditionsAdapter(list)
            isNestedScrollingEnabled = false
        }
    }

    override fun hideLegendTitle() {
        clyInspiraConditionContent?.visibility = View.GONE
    }

    override fun showDetail(leyenda: List<InspiraCondicionesLeyendaModel>, detalle: List<InspiraCondicionesLeyendaDetalleModel>) {
        rvwInspiraCondicionesLegend?.apply {
            layoutManager = GridLayoutManager(context, Constant.NUMBER_ONE, GridLayoutManager.VERTICAL, false)
            adapter = InspireConditionsLegendAdapter(leyenda, detalle)
            isNestedScrollingEnabled = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): InspireConditionsFragment = InspireConditionsFragment()
    }
}
