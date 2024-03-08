package biz.belcorp.salesforce.modules.developmentpath.features.dashboard

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_DASHBOARD_SYNC
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_LEGACY_SYNC
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.barraalerta.BarraAlertaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsGzPagerAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsView
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionRddFragment
import kotlinx.android.synthetic.main.fragment_rdd_dashboard.*

class RddDashboardGzFragment : BaseFragment(), TabsView {

    override fun getLayout() = R.layout.fragment_rdd_dashboard

    private val tabsPresenter: TabsPresenter by injectFragment()
    private val analyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    companion object {

        const val TAG = "Dashboard"
        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): RddDashboardGzFragment {
            val fragment = RddDashboardGzFragment()
            val args = Bundle()
            args.putLong(PLAN_ID, planId)
            fragment.arguments = args
            return fragment
        }
    }

    private var planId: Long = -1L

    var navigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsPresenter.enviarPantalla(TagAnalytics.RUTA_DESARROLLO_DASHBOARD, planId)
        setupLiveObservers()
        setupView()
    }

    private fun setupView() {
        incrustarFragments()
        tabsPresenter.validarDueniaDePlan(planId)
    }

    private fun setupLiveObservers() {
        observeEventSubject(
            DEVELOPMENT_PATH_DASHBOARD_SYNC,
            DEVELOPMENT_PATH_LEGACY_SYNC,
            observer = syncStateObserver
        )
    }

    private fun incrustarFragments() {
        val avisos = BarraAlertaFragment()
            .conVistaRaiz(fl_aviso)
            .alertarAlEditarFocos()
            .alertarAlEditarHabilidades()

        val barraNavegacion = BarraNavegacionRddFragment.newInstance(planId)
        val cabecera = CabeceraFragment.newInstance(planId)

        barraNavegacion.navigator = navigator
        cabecera.navigator = navigator

        childFragmentManager
            .beginTransaction()
            .replace(R.id.fl_aviso, avisos)
            .replace(R.id.fl_barra_navegacion, barraNavegacion)
            .replace(R.id.fl_cabecera, cabecera)
            .commit()
    }

    override fun configurarTabs(esDuenia: Boolean) {
        if (context == null) return

        val adapter = TabsGzPagerAdapter(
            planId,
            requireContext(),
            esDuenia,
            childFragmentManager
        )
        pager.adapter = adapter
    }

    override fun ocultarTabs() {
        tabs?.visibility = View.GONE
        viewEspacioCabecera?.visibility = View.VISIBLE
    }

    override fun mostrarTabs() {
        tabs?.setupWithViewPager(pager)
        tabs?.visibility = View.VISIBLE
        viewEspacioCabecera?.visibility = View.GONE
    }

    private fun recuperarArgs() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        setupView()
    }

}
