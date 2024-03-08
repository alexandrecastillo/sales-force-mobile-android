package biz.belcorp.salesforce.modules.developmentpath.features.dashboard

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_DASHBOARD_SYNC
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_LEGACY_SYNC
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.barraalerta.BarraAlertaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsDvPagerAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import kotlinx.android.synthetic.main.fragment_rdd_dashboard.*

class RddDashboardDvFragment : BaseFragment() {

    var navigator: Navigator? = null

    override fun getLayout() = R.layout.fragment_rdd_dashboard

    private var planId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    private fun recuperarArgs() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLiveObservers()
        setupView()
    }

    private fun setupView() {
        instanciarVistas()
        configurarTabs()
    }

    private fun setupLiveObservers() {
        observeEventSubject(
            DEVELOPMENT_PATH_DASHBOARD_SYNC,
            DEVELOPMENT_PATH_LEGACY_SYNC,
            observer = syncStateObserver
        )
    }

    private fun instanciarVistas() {
        val avisos = BarraAlertaFragment()
            .conVistaRaiz(fl_aviso)
            .alertarAlEditarFocos()
            .alertarAlEditarHabilidades()

        val cabecera = CabeceraFragment.newInstance(planId)
        cabecera.navigator = navigator

        childFragmentManager
            .beginTransaction()
            .replace(R.id.fl_aviso, avisos)
            .replace(R.id.fl_cabecera, cabecera)
            .commit()
    }

    private fun configurarTabs() {
        pager.adapter = TabsDvPagerAdapter(planId, requireContext(), childFragmentManager)
            .also { it.navigator = navigator }

        tabs.setupWithViewPager(pager)
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        setupView()
    }

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(plandId: Long): RddDashboardDvFragment {
            val fragment = RddDashboardDvFragment()
            val args = Bundle()
            args.putLong(PLAN_ID, plandId)
            fragment.arguments = args
            return fragment
        }
    }
}
