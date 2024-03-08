package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view.AsignarHabilidadFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.adapter.DetallesHabilidadAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.presenter.DetalleHabilidadesPresenter
import kotlinx.android.synthetic.main.fragment_detalle_habilidades.*

class DetalleHabilidadesFragment : BaseFragment(), DetalleHabilidadesView {

    private var personaId: Long = -1
    private lateinit var rol: Rol
    private val presenter by injectFragment<DetalleHabilidadesPresenter>()
    private val recargarHabilidadesAsignadasReceiver = RecargarHabilidadesAsignadasReceiver()

    override fun getLayout() = R.layout.fragment_detalle_habilidades

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registrarRecargarHabilidadesAsignadasReceiver()
        configurarRecyclerView()
        ll_asignar_habilidades?.setOnClickListener { abrirAsignarHabilidades() }
        presenter.obtener(personaId, rol)
    }

    override fun cargar(habilidadAsignadaDetalles: List<HabilidadAsignadaDetalleModel>) {
        (rv_habilidades?.adapter as? DetallesHabilidadAdapter)?.actualizar(habilidadAsignadaDetalles)
    }

    override fun mostrarSinHabilidades(mostrar: Boolean) {
        card_sin_habilidades?.visible(mostrar)
    }

    override fun habilitarAsignacion(habilitar: Boolean) {
        ll_asignar_habilidades?.visible(habilitar)
    }

    override fun mostrarDetalleHabilidades(mostrar: Boolean) {
        rv_habilidades?.visible(mostrar)
    }

    private fun abrirAsignarHabilidades() {
        val habilidadesFragment = AsignarHabilidadFragment.newInstance(
            AsignarHabilidadFragment.Request.EditarHabilidadesAOtro(
                personaId
            )
        )
        fragmentManager?.let { habilidadesFragment.show(it, habilidadesFragment.tag) }
    }

    private fun configurarRecyclerView() {
        val context = context ?: return

        val adapter = DetallesHabilidadAdapter()

        rv_habilidades?.isNestedScrollingEnabled = false
        rv_habilidades?.layoutManager = LinearLayoutManager(context)
        rv_habilidades?.adapter = adapter
    }

    private fun registrarRecargarHabilidadesAsignadasReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_HABILIDADES_EQUIPO)
        activity?.registerReceiver(recargarHabilidadesAsignadasReceiver, filter)
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recargarHabilidadesAsignadasReceiver)
        super.onDestroyView()
    }

    inner class RecargarHabilidadesAsignadasReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.obtener(personaId, rol)
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = DetalleHabilidadesFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
