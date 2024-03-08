package biz.belcorp.salesforce.modules.kpis.features.kpis

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.components.commons.UaStateObserver
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.events.EventSubject.*
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.states.UaInfoState
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.core.utils.isMultiProfile
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.KpiTypeMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiDashboardParams
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel
import biz.belcorp.salesforce.modules.kpis.utils.AnalyticUtils
import biz.belcorp.salesforce.modules.kpis.utils.KpiGridLayoutManagerBuilder
import biz.belcorp.salesforce.modules.kpis.utils.decorations.GridTopMarginDecoration
import biz.belcorp.salesforce.modules.kpis.utils.navigateToKpiDetail
import kotlinx.android.synthetic.main.fragment_kpis.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class KpisFragment : BaseFragment() {

    private val viewModel by viewModel<KpiDashboardViewModel>()

    private val stateObserver by inject<UaStateObserver>()
    private var kpiParams: KpiDashboardParams = KpiDashboardParams()

    private val adapter by lazy { KpiAdapter { kpiModel: KpiModel -> onKpiItemClick(kpiModel) } }

    override fun getLayout() = R.layout.fragment_kpis

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        initialize()
    }

    private fun initialize() = viewModel.getKpiInformation(kpiParams)

    private fun setup() {
        setupRecyclerView()
        setupViewModel()
        setupSyncObservers()
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    private fun setupUaStateObserver(role: Rol) {
        if (role.isMultiProfile() && !stateObserver.content.hasObservers()) {
            stateObserver.content.observe(viewLifecycleOwner, uaViewStateObserver)
        }
    }

    private fun setupSyncObservers() {
        observeEventSubject(
            subjects = *arrayOf(
                KPIS_COLLECTION_SYNC,
                KPIS_KPIS_SYNC,
                HOME_SYNC
            ),
            observer = syncStateObserver
        )
    }

    private fun setupRecyclerView() {
        rvKpis?.apply {
            isNestedScrollingEnabled = false
            addItemDecoration(
                GridTopMarginDecoration(
                    requireContext(),
                    SPAN_COUNT,
                    enableTopElement = false,
                    verticalDimension = BaseR.dimen.ds_margin_other_2,
                    horizontalDimension = BaseR.dimen.ds_margin_medium
                )
            )
            layoutManager = KpiGridLayoutManagerBuilder.build(
                context,
                SPAN_COUNT,
                allSame = true
            )
            adapter = this@KpisFragment.adapter
        }
    }

    private fun loadKpiHome(data: List<KpiModel>) {
        adapter.submitList(data)
    }

    private fun loadKpiHomeTitle(title: String) {
        if (title.isEmpty()) tvTitle?.gone()
        else tvTitle?.apply {
            text = title
            visible()
        }
    }

    private fun onKpiItemClick(kpiModel: KpiModel) {
        navigateToKpiDetail(
            kpiType = KpiTypeMapper.parse(kpiModel.type),
            kpiName = kpiModel.header
        )
        sendUsabilityLog(kpiModel)
    }

    private fun sendUsabilityLog(kpiModel: KpiModel) {
        AnalyticUtils.cardAvanceCampania(kpiModel.header)
    }

    private val viewStateObserver = Observer<KpiDashboardViewState> { kpiState ->
        when (kpiState) {
            is KpiDashboardViewState.Success  ->{
                setupUaStateObserver(kpiState.model.role)
                loadKpiHomeTitle(kpiState.model.title)
                loadKpiHome(kpiState.model.kpis)
            }
        }
    }

    private val uaViewStateObserver = consumableObserver<UaInfoState> {
        kpiParams = KpiDashboardParams(it.role, it.uaKey, it.person)
        initialize()
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        initialize()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}
