package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view.ReconocerHabilidadesFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel
import kotlinx.android.synthetic.main.fragment_campania_a_campania.*

class CampaniaACampaniaFragment : BaseFragment(), CampaniaCampaniaView,
    AcuerdosHabilidadesAdapter.CampaniaCampaniaListener {

    private var personaId: Long = -1
    private lateinit var rol: Rol
    private val campaniaACampaniaPresenter by injectFragment<CampaniaACampaniaPresenter>()

    private val recargarHaabilidadesReceiver = RecargarHabilidadesReceiver()
    private val registroVisitaReceiver = RegistroVisitaReceiver()

    override fun getLayout() = R.layout.fragment_campania_a_campania

    private val acuerdosHabilidadesAdapter by lazy {
        AcuerdosHabilidadesAdapter().apply {
            listener = this@CampaniaACampaniaFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaId = arguments!!.getLong(ARG_PERSONA_ID)
        rol = arguments!!.getSerializable(ARG_ROL) as Rol
        campaniaACampaniaPresenter.view = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suscribirRecargarHabilidadesReceiver()
        suscribirARegistroVisita()
        campaniaACampaniaPresenter.obtener(personaId, rol)
        configurarRecyclerView()
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recargarHaabilidadesReceiver)
        activity?.unregisterReceiver(registroVisitaReceiver)
        super.onDestroyView()
    }

    private fun configurarRecyclerView() {
        rv_acuerdos_reconocimientos?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        rv_acuerdos_reconocimientos?.adapter = acuerdosHabilidadesAdapter
    }

    override fun cargar(campaniaCampanias: List<CampaniaCampaniaModel>) {
        (rv_acuerdos_reconocimientos?.adapter as? AcuerdosHabilidadesAdapter)?.actualizar(
            campaniaCampanias
        )
    }

    override fun alHacerClickEnVerHabilidadesReconocidas(campania: String) {
        val habilidadesFragment =
            ReconocerHabilidadesFragment.crearParaVisualizacion(personaId, campania, rol)
        fragmentManager?.let { habilidadesFragment.show(it, habilidadesFragment.tag) }
    }

    override fun alHacerClickEnAsignarHabilidades() {
        val habilidadesFragment = ReconocerHabilidadesFragment.crearParaInsercion(personaId, rol)
        fragmentManager?.let { habilidadesFragment.show(it, habilidadesFragment.tag) }
    }

    private fun suscribirARegistroVisita() {
        val filter = IntentFilter(Constant.BROADCAST_REGISTRO_VISITA)
        activity?.registerReceiver(registroVisitaReceiver, filter)
    }

    private fun suscribirRecargarHabilidadesReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_HABILIDADES)
        activity?.registerReceiver(recargarHaabilidadesReceiver, filter)
    }

    override fun alHacerClickGuardar(acuerdo: AcuerdoModel) {
        campaniaACampaniaPresenter.editar(acuerdo)
    }

    override fun alHacerClickEliminar(acuerdo: AcuerdoModel) {
        campaniaACampaniaPresenter.eliminar(acuerdo)
    }

    override fun alEliminarAcuerdo() = Unit

    override fun alEditarAcuerdo() = Unit

    inner class RecargarHabilidadesReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            campaniaACampaniaPresenter.obtener(personaId, rol)
        }
    }

    inner class RegistroVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            campaniaACampaniaPresenter.obtener(personaId, rol)
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "id"
        private const val ARG_ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol): CampaniaACampaniaFragment {
            val fragment = CampaniaACampaniaFragment()
            val args = Bundle()
            args.putLong(ARG_PERSONA_ID, personaId)
            args.putSerializable(ARG_ROL, rol)
            fragment.arguments = args
            return fragment
        }
    }
}
