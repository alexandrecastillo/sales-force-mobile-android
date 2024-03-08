package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.DesarrolloUaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.AvanceZonaModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter.AvanceRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view.AvanceVisitasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.broadcast.SenderClickUaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.intencionpedido.IntencionPedidoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view.ReconocimientosASuperiorFragment
import kotlinx.android.synthetic.main.fragment_rdd_avance_region.*

class DashboardRddGr : BaseFragment(), AvanceRegionView {

    override fun getLayout() = R.layout.fragment_rdd_avance_region

    private val presenter: AvanceRegionPresenter by injectFragment()

    private var planId = -1L
    private val cambioRDDReceiver = CambioRDDReceiver()
    private var avanceZonasAdapter: AvanceZonasAdapter? = null
    private var senderClickUaRdd: SenderClickUaRdd? = null
    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    private val intencionPedidoFragment by lazy { IntencionPedidoFragment.newInstance(planId) }


    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): DashboardRddGr {
            val bundle = Bundle()
            val fragment = DashboardRddGr()
            bundle.putLong(PLAN_ID, planId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() = arguments?.let {
        planId = it.getLong(PLAN_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        agregarFragments()
        configurarRecycler()
        configurarSenderClickUa()
        suscribirBroadcastReceiver()
        presenter.establecerVista(this)
        presenter.decidirVisibilidadDeReconocimientos(planId)
        Handler().postDelayed({ presenter.recuperarZonas(planId) }, 400)
    }

    private fun suscribirBroadcastReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(cambioRDDReceiver, filter)
    }

    private fun agregarFragments() {
        if (!isAdded) return

        childFragmentManager
                .beginTransaction()
                .replace(R.id.fl_ir_a_desarrollo_region, DesarrolloUaFragment.newInstance(planId))
                .replace(R.id.fl_avance_visitas, AvanceVisitasFragment.newInstance(planId))
                .replace(R.id.flIntencionPedido, intencionPedidoFragment)
                .commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        desuscribirBroadcastReceiver()
        super.onDestroyView()
    }

    private fun desuscribirBroadcastReceiver() {
        activity?.unregisterReceiver(cambioRDDReceiver)
    }

    override fun onDestroy() {
        presenter.eliminarVista()
        super.onDestroy()
    }

    private fun configurarRecycler() {
        context?.apply {
            recycler_avance_zonas?.layoutManager = NonScrollableLayoutManager()
                    .withContext(this)
                    .linearVertical()

            avanceZonasAdapter = AvanceZonasAdapter(this)
            avanceZonasAdapter?.setClickListener {
                senderClickUaRdd?.clickearUa(it)
                firebaseAnalyticsPresenter.enviarRegionAvancePorCodigo(TagAnalytics.EVENTO_AVANCE_POR_CODIGO, it.codigoZona!!)
            }

            recycler_avance_zonas.adapter = avanceZonasAdapter
        }
    }

    private fun configurarSenderClickUa() {
        context?.apply {
            senderClickUaRdd = SenderClickUaRdd(this)
        }
    }

    override fun pintarAvanceGerentesZona(avancesZona: List<AvanceZonaModel>) {
        avanceZonasAdapter?.actualizar(avancesZona)
    }

    override fun mostrarToast(mensaje: String) {
        toast(mensaje)
    }

    override fun mostrarReconocimientos() {
        if (!isAdded) return

        childFragmentManager
                .beginTransaction()
                .replace(R.id.frameReconocimientosASuperior, ReconocimientosASuperiorFragment.newInstance())
                .commitAllowingStateLoss()
    }

    override fun ocultarReconocimientos() {
        frameReconocimientosASuperior?.visibility = View.GONE
    }

    inner class CambioRDDReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recuperarZonas(planId)
        }
    }
}
