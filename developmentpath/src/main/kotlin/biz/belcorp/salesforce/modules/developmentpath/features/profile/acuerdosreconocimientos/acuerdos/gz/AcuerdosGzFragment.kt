package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view.AvanceHabilidadFragment
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view.ReconocerHabilidadesFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento.MostrarReconocimientoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento.MostrarReconocimientoView
import kotlinx.android.synthetic.main.fragment_acuerdos_gz.*

class AcuerdosGzFragment : BaseFragment(), MostrarReconocimientoView {


    private var personaId: Long = -1
    private lateinit var rol: Rol

    private val mostrarReconocimientoPresenter by injectFragment<MostrarReconocimientoPresenter>()
    private val registroVisitaReceiver = RegistroVisitaReceiver()

    override fun getLayout() = R.layout.fragment_acuerdos_gz

    companion object {
        private const val ARG_PERSONA_ID = "id"
        private const val ARG_ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol): AcuerdosGzFragment {
            val fragment = AcuerdosGzFragment()
            val args = Bundle()
            args.putLong(ARG_PERSONA_ID, personaId)
            args.putSerializable(ARG_ROL, rol)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaId = arguments!!.getLong(ARG_PERSONA_ID)
        rol = arguments!!.getSerializable(ARG_ROL) as Rol
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suscribirARegistroVisita()
        iniciarCampaniaCampania()
        escucharAcciones()
    }

    private fun suscribirARegistroVisita() {
        val filter = IntentFilter(Constant.BROADCAST_REGISTRO_VISITA)
        activity?.registerReceiver(registroVisitaReceiver, filter)
    }

    private fun desuscribirARegistroVisita() =
        activity?.unregisterReceiver(registroVisitaReceiver)

    private fun iniciarCampaniaCampania() {
        val fragment = CampaniaACampaniaFragment.newInstance(personaId, rol)
        childFragmentManager.beginTransaction()
            .replace(R.id.fl_campania_campania, fragment, "CampaniaCampania")
            .commit()
    }

    override fun mostrarReconocimientoComportamientos() = Unit

    override fun onDestroy() {
        desuscribirARegistroVisita()
        super.onDestroy()
    }

    override fun mostrarReconocimientoHabilidades() {
        if (!isResumed) return

        val habilidadesFragment = ReconocerHabilidadesFragment.crearParaInsercion(personaId, rol)
        habilidadesFragment.show(childFragmentManager, habilidadesFragment.tag)
    }

    private fun escucharAcciones() {
        card_avance_habilidades_u6c?.setOnClickListener { lanzarAvanceHabilidades() }
    }

    private fun lanzarAvanceHabilidades() {
        val fragment = AvanceHabilidadFragment.newInstance(personaId, rol)
        fragment.show(childFragmentManager, fragment.tag)
    }

    override fun mostrarMensaje(mensaje: String) {
        Log.d("Mostrar reconocimiento", mensaje)
    }

    private inner class RegistroVisitaReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            mostrarReconocimientoPresenter.validarYMostrar(personaId, rol)
        }
    }
}
