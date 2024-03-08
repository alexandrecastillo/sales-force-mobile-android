package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.redireccion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.reconocer.ReconocerComportamientosFragment
import kotlinx.android.synthetic.main.fragment_ir_a_reconocimientos.*

class IrAReconocerFragment : BaseFragment(), IrAReconocerView {

    private var personaId = -1L

    private lateinit var rol: Rol

    private val presenter by injectFragment<IrAReconocerPresenter>()

    private val reconocimientoComportamientosFragment by lazy {
        ReconocerComportamientosFragment.newInstance(personaId, rol)
    }

    private val registroVisitaReceiver = RegistroVisitaReceiver()

    private val cambioReconocimientoReceiver = CambioReconocimientoReceiver()

    override fun getLayout(): Int = R.layout.fragment_ir_a_reconocimientos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
        calcularVisibilidad()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun calcularVisibilidad() {
        presenter.calcularVisibilidad(personaId, rol)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suscribirARegistroVisita()
        suscribirACambioReconocimiento()
        configurarBoton()
    }

    override fun onDestroyView() {
        desuscribirARegistroVisita()
        desuscribirACambioReconocimiento()
        super.onDestroyView()
    }

    private fun suscribirARegistroVisita() {
        val filter = IntentFilter(Constant.BROADCAST_REGISTRO_VISITA)
        activity?.registerReceiver(registroVisitaReceiver, filter)
    }

    private fun suscribirACambioReconocimiento() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_RECONOCIMIENTO)
        activity?.registerReceiver(cambioReconocimientoReceiver, filter)
    }

    private fun desuscribirARegistroVisita() =
        activity?.unregisterReceiver(registroVisitaReceiver)

    private fun desuscribirACambioReconocimiento() {
        activity?.unregisterReceiver(cambioReconocimientoReceiver)
    }

    private fun configurarBoton() {
        botonIrAReconocer.setOnClickListener {
            reconocimientoComportamientosFragment.show(childFragmentManager, null)
        }
    }

    override fun ocultar() {
        view?.visibility = View.GONE
    }

    override fun mostrar() {
        view?.visibility = View.VISIBLE
    }

    private inner class RegistroVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.calcularVisibilidad(personaId, rol)
        }
    }

    private inner class CambioReconocimientoReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.calcularVisibilidad(personaId, rol)
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = IrAReconocerFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
