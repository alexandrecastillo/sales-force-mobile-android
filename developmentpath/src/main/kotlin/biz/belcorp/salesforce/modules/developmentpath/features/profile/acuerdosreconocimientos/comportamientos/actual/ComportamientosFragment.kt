package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.actual.detalle.ReconocimientoCampaniaActualFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.model.ComportamientoModel
import kotlinx.android.synthetic.main.fragment_comportamientos_campania_actual.*

class ComportamientosFragment : BaseFragment(), ComportamientosView {

    private var personaId = -1L
    private var rol: Rol = Rol.NINGUNO

    private val adapter by lazy { ComportamientosAdapter() }

    private val presenter by injectFragment<ComportamientosPresenter>()

    private val visualizarReconocimientoFragment by lazy { ReconocimientoCampaniaActualFragment.newInstance(personaId, rol) }

    private val cambioReconocimientoReceiver = CambioReconocimientoReceiver()

    override fun getLayout(): Int = R.layout.fragment_comportamientos_campania_actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suscribirACambioReconocimiento()
        configurarRecycler()
        configurarClickEnCard()
        cargarComportamientos()
    }

    private fun suscribirACambioReconocimiento() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_RECONOCIMIENTO)
        activity?.registerReceiver(cambioReconocimientoReceiver, filter)
    }

    private fun configurarRecycler() {
        recyclerViewComportamientos.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewComportamientos.isNestedScrollingEnabled = false
        recyclerViewComportamientos.adapter = adapter
    }

    private fun configurarClickEnCard() {
        cardComportamientos.setOnClickListener {
            visualizarReconocimientoFragment.show(childFragmentManager, null)
        }
    }

    private fun cargarComportamientos() {
        presenter.obtenerComportamientosCampaniaActual(personaId, rol)
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(cambioReconocimientoReceiver)
        super.onDestroyView()
    }

    override fun pintarReconocimientos(comportamientos: List<ComportamientoModel>) {
        adapter.actualizar(comportamientos)
        cardComportamientos?.visibility = View.VISIBLE
    }

    override fun ocultarReconocimientos() {
        cardComportamientos?.visibility = View.GONE
    }

    override fun pintarCumplimiento(razon: String) {
        tvPorcentaje?.text = razon
    }

    override fun pintarProgreso(porcentaje: Float) {
        pgbPorcentaje?.progress = porcentaje
    }

    private inner class CambioReconocimientoReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.obtenerComportamientosCampaniaActual(personaId, rol)
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = ComportamientosFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
