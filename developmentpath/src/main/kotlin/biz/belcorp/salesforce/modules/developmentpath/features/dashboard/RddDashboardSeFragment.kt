package biz.belcorp.salesforce.modules.developmentpath.features.dashboard

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.events.EventSubject.*
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsSePagerAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import kotlinx.android.synthetic.main.fragment_rdd_dashboard.*

class RddDashboardSeFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_rdd_dashboard

    var navigator: Navigator? = null

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    private var planId = -1L

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): RddDashboardSeFragment {
            val fragment = RddDashboardSeFragment()
            val args = Bundle()
            args.putLong(PLAN_ID, planId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalyticsPresenter.enviarPantalla(TagAnalytics.RUTA_DESARROLLO_DASHBOARD, planId)
        setupLiveObservers()
        setupView()
    }

    private fun setupLiveObservers() {
        observeEventSubject(
            DEVELOPMENT_PATH_DASHBOARD_SYNC,
            DEVELOPMENT_PATH_LEGACY_SYNC,
            CONSULTANTS_SYNC,
            observer = syncStateObserver
        )
    }

    private fun setupView() {
        if (!isAttached()) return
        incrustarFragments()
        configurarTabs()
    }

    private fun recuperarArgs() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }

    private fun incrustarFragments() {
        val cabecera = CabeceraFragment.newInstance(planId)
        cabecera.navigator = navigator

        childFragmentManager
            .beginTransaction()
            .replace(R.id.fl_cabecera, cabecera)
            .commit()
    }

    private fun configurarTabs() {
        if (context == null) return

        val adapter = TabsSePagerAdapter(
            planId,
            requireContext(),
            childFragmentManager
        )
        adapter.navigator = navigator
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        setupView()
    }

}
