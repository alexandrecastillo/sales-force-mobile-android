package biz.belcorp.salesforce.modules.developmentpath.features.dashboard

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_DASHBOARD_SYNC
import biz.belcorp.salesforce.core.events.EventSubject.DEVELOPMENT_PATH_LEGACY_SYNC
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.barraalerta.BarraAlertaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera.CabeceraFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsGrPagerAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.tabs.TabsView
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.BarraNavegacionRddFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_rdd_dashboard.*

class RddDashboardGrFragment : BaseFragment(), TabsView {

    override fun getLayout() = R.layout.fragment_rdd_dashboard

    private val tabsPresenter: TabsPresenter by injectFragment()
    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    var navigator: Navigator? = null
    private var planId = -1L

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

    private fun setupView() {
        incrustarFragments()
        calcularVisibilidadDeTabDeFocos()
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

        val barraNavegacion = BarraNavegacionRddFragment
                .newInstance(planId)
                .apply { navigator = this@RddDashboardGrFragment.navigator }

        val cabecera = CabeceraFragment
                .newInstance(planId)
                .apply { navigator = this@RddDashboardGrFragment.navigator }

        childFragmentManager
                .beginTransaction()
                .replace(R.id.fl_aviso, avisos)
                .replace(R.id.fl_barra_navegacion, barraNavegacion)
                .replace(R.id.fl_cabecera, cabecera)
                .commit()
    }

    private fun calcularVisibilidadDeTabDeFocos() {
        tabsPresenter.validarDueniaDePlan(planId)
    }

    private fun recuperarArgs() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }

    override fun configurarTabs(esDuenia: Boolean) {
        if (context == null) return

         val myAdapter = TabsGrPagerAdapter(planId,
                                         requireContext(),
                                         esDuenia,
                                         childFragmentManager)
        pager.adapter = myAdapter
        tabs.setupWithViewPager(pager)

    }

    override fun ocultarTabs() {
        tabs?.visibility = View.GONE
        viewEspacioCabecera?.visibility = View.VISIBLE
    }

    override fun mostrarTabs() {
        tabs?.setupWithViewPager(pager)
        tabs?.visibility = View.VISIBLE
        viewEspacioCabecera?.visibility = View.GONE
        tabs?.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    Constant.NUMBER_ZERO -> firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_AVANCE_DE_REGION)

                    Constant.NUMBER_ONE -> firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_FOCOS_DE_CAMPANIA)
                }
            }
        })
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        setupView()
    }

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): RddDashboardGrFragment {
            val fragment = RddDashboardGrFragment()
            val args = Bundle()
            args.putLong(PLAN_ID, planId)
            fragment.arguments = args
            return fragment
        }
    }

}
