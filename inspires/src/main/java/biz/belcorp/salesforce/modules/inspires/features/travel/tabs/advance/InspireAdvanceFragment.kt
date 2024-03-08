package biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.inspires.R
import biz.belcorp.salesforce.modules.inspires.features.analitycs.AnalyticsInspireViewModel
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance.adapters.InspireAdvanceAdapter
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance.adapters.InspireAdvancePeriodAdapter
import biz.belcorp.salesforce.modules.inspires.model.InspiraAvancePeriodoModel
import biz.belcorp.salesforce.modules.inspires.model.InspiraAvancesModel
import kotlinx.android.synthetic.main.fragment_inspire_travel_page_advance.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

open class InspireAdvanceFragment : BaseFragment(), InspireAdvanceView {

    private val presenter: InspireAdvancePresenter by inject()
    private val analyticsInspireViewModel by viewModel<AnalyticsInspireViewModel>()

    override fun getLayout(): Int = R.layout.fragment_inspire_travel_page_advance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onPrepare()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            sendAnalitycsScreenView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            sendAnalitycsScreenView()
        }
    }

    private fun sendAnalitycsScreenView() {
        analyticsInspireViewModel.sendInspireScreen(TagAnalytics.EVENTO_INSPIRA_SCREEN_VISTA_MI_AVANCE)
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun showHeaderIcon(active: Boolean) {
        ivwInspiraHeaderIcon?.imageResource = if (active) R.drawable.ic_medal else R.drawable.ic_inspira_lost
    }

    override fun showHeaderMessage(active: Boolean, name: String?) {
        tvwInspiraHeaderTitle?.text = if (active) getString(R.string.progress_header) else getString(R.string.progress_header_lost, name)
    }

    override fun showTitle(campaign: String, active: Boolean) {
        tvwInspiraContentTitle?.text = if (active) getString(R.string.viaje_inspira_avance_header, campaign) else getString(R.string.viaje_inspira_avance_header_lost)
    }

    override fun showList(list: List<InspiraAvancesModel>, isLimited: Boolean, active: Boolean, hasPeriod: Boolean) {
        rvwInspiraAvance?.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = InspireAdvanceAdapter(list, isLimited, active, hasPeriod)
            isNestedScrollingEnabled = false
        }
    }

    override fun showPeriodList(list: List<InspiraAvancePeriodoModel>) {
        rvwInspiraAvancePeriodo?.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = InspireAdvancePeriodAdapter(list)
            isNestedScrollingEnabled = false
        }
    }

    override fun hideContent() {
        clyInspitaContent?.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance(): InspireAdvanceFragment = InspireAdvanceFragment()
    }
}
