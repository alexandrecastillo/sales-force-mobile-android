package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.DesarrolloUaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.model.SeccionAvanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.presenter.AvanceZonaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view.AvanceVisitasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view.ReconocimientosASuperiorFragment
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_secciones_gz.layout_reconocimientos
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_secciones_gz.rv_main

class DashboardRddGz : BaseFragment(),
        AvanceView {
    override fun getLayout() = R.layout.fragment_rdd_dashboard_secciones_gz

    private val presenter: AvanceZonaPresenter by injectFragment()

    private var planId: Long = -1L

    private val recargarSeccionesReceiver = RecargarSeccionesReceiver()

    private val avanceVisitasFragment: Fragment
            by lazy { AvanceVisitasFragment.newInstance(planId,Rol.GERENTE_ZONA) }

    private val desarrolloUaFragment: Fragment
            by lazy { DesarrolloUaFragment.newInstance(planId) }

    private val reconocimientosASuperiorFragment: Fragment
            by lazy { ReconocimientosASuperiorFragment.newInstance() }


    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): DashboardRddGz {
            val bundle = Bundle()
            val fragment = DashboardRddGz()
            bundle.putLong(PLAN_ID, planId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.fragment_rdd_dashboard_secciones_gz,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        presenter.decidirVisibilidadReconocimientos(planId)
        Handler().postDelayed({ presenter.recargarSecciones(planId) }, 350)
    }

    private fun recuperarArgumentos() = arguments?.let {
        planId = it.getLong(PLAN_ID)
    }

    private fun inicializar() {
        configurarRecyclerView()
        registrarReceiver()
        agregarFragments()
    }

    private fun configurarRecyclerView() {
        rv_main?.layoutManager = NonScrollableLayoutManager()
                .withContext(context)
                .linearVertical()
        rv_main?.adapter = SeccionesAdapter(requireContext())
    }

    private fun registrarReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(recargarSeccionesReceiver, filter)
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(recargarSeccionesReceiver)
        super.onDestroy()
    }

    override fun cargarSeccionesSE(modelos: List<SeccionAvanceModel>) {
        (rv_main?.adapter as? SeccionesAdapter)?.actualizar(modelos)
    }

    override fun mostrarAreaReconocimientos() = mostrarReconocimientos()

    private fun mostrarReconocimientos() {
        if (!isAdded) return

        childFragmentManager
                .beginTransaction()
                .replace(R.id.layout_reconocimientos, reconocimientosASuperiorFragment)
                .commitAllowingStateLoss()
    }

    private fun agregarFragments() {
        if (!isAdded) return

        childFragmentManager
                .beginTransaction()
                .replace(R.id.layout_avance_visitas, avanceVisitasFragment)
                .replace(R.id.layout_desarrollo_zona, desarrolloUaFragment)
                .commitAllowingStateLoss()
    }

    override fun ocultarAreaReconocimientos() {
        layout_reconocimientos?.visibility = View.GONE
    }

    inner class RecargarSeccionesReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recargarSecciones(planId)
        }
    }
}
