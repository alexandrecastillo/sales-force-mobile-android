package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.focos.AsignarFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view.AsignarHabilidadFragment
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_focos.*
import biz.belcorp.salesforce.core.constants.Constant as CoreConstant

class FocosHabilidadesDeEquipoFragment : BaseFragment(),
    FocosHabilidadesView {

    override fun getLayout() = R.layout.fragment_rdd_dashboard_focos

    val presenter: FocosHabilidadesPresenter by injectFragment()
    private val adapter by lazy {
        FocosHabilidadesAdapter()
    }
    val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    private val recargarFocosReceiver = RecargarFocosReceiver()
    private val recargarHabilidadesAsignadasReceiver = RecargarHabilidadesAsignadasReceiver()

    var focoSelect: FocoSeleccionado? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_rdd_dashboard_focos,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecyclerView()
        registrarRecargarFocosReceiver()
        registrarRecargarHabilidadesAsignadasReceiver()

        tv_titulo_focos?.text = getString(R.string.rdd_dashboard_focos_region_titulo)
        ll_asignar_focos?.setOnClickListener { abrirAsignarFocosMultiples() }

        Handler().postDelayed({ presenter.recargar() }, 400)
    }

    private fun abrirAsignarFocosMultiples() {
        if (presenter.obtenerRol().toUpperCase() == Rol.Builder.COD_GERENTE_REGION) {
            tagAnalyticsAsignarMultiple()
        }
        AsignarFragment
            .newInstance(
                AsignarFragment.Request.EditarTodos(),
                TagAnalytics.EVENTO_SELECCION_MI_EQUIPO_MULTIPLE
            )
            .show(childFragmentManager, "AsignarFocosFragment")
    }

    private fun tagAnalyticsAsignarMultiple() {
        focoSelect =
            FocoSeleccionado(CoreConstant.EMPTY_STRING, Constant.EVENTO_EDITAR_FOCOS, ArrayList())
        firebaseAnalyticsPresenter.enviarFocosSeleccionado(
            TagAnalytics.EVENTO_FOCOS_MI_EQUIPO__MULTIPLE_ASIGNAR,
            focoSelect!!
        )
    }

    private fun abrirAsignarFocosSimple(personaId: Long) {
        if (presenter.obtenerRol().toUpperCase() == Rol.Builder.COD_GERENTE_REGION) {
            tagAnalyticsAsignar()
        }
        AsignarFragment
            .newInstance(
                AsignarFragment.Request.EditarUno(personaId),
                TagAnalytics.EVENTO_SELECCION_MI_EQUIPO
            )
            .show(childFragmentManager, "AsignarFocosFragment")
    }

    override fun cargar(modelos: List<FocosYHabilidadesPorUa>) {
        adapter.actualizar(modelos)
    }

    override fun updateItem(modelos: List<FocosYHabilidadesPorUa>, position: Int) {
        adapter.updateItem(modelos, position)
    }

    private fun tagAnalyticsAsignar() {
        focoSelect =
            FocoSeleccionado(CoreConstant.EMPTY_STRING, Constant.EVENTO_EDITAR_FOCOS, ArrayList())
        firebaseAnalyticsPresenter.enviarFocosSeleccionado(
            TagAnalytics.EVENTO_FOCOS_MI_EQUIPO_ASIGNAR_EDITAR,
            focoSelect!!
        )
    }


    private fun configurarRecyclerView() {
        val context = context ?: return
        adapter.setEditarFocoListener { personaId -> abrirAsignarFocosSimple(personaId) }
        adapter.setAsignarHabilidadesListener { personaId, tipoevento, position ->
            abrirHabilidades(
                personaId,
                tipoevento,
                position
            )
        }
        rv_secciones?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        rv_secciones?.adapter = adapter

    }

    private fun abrirHabilidades(personaId: Long, tipoEvento: Int, position: Int) {
        if (presenter.obtenerRol().toUpperCase() == Rol.Builder.COD_GERENTE_REGION) {
            tagAnalyticsSeleccion(tipoEvento)
        }
        val habilidadesFragment = AsignarHabilidadFragment.newInstance(
            request = AsignarHabilidadFragment.Request.EditarHabilidadesAOtro(
                personaId
            ), itemPosition = position
        )
        habilidadesFragment.show(childFragmentManager, habilidadesFragment.tag)
    }

    private fun tagAnalyticsSeleccion(tipoEvento: Int) {
        focoSelect = FocoSeleccionado(CoreConstant.EMPTY_STRING, tipoEvento, ArrayList())
        firebaseAnalyticsPresenter.enviarFocosSeleccionado(
            TagAnalytics.EVENTO_EDITAR_ASIGNAR_HABILIDADES_MI_EQUIPO,
            focoSelect!!
        )
    }

    private fun registrarRecargarFocosReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_FOCOS)
        activity?.registerReceiver(recargarFocosReceiver, filter)
    }

    private fun registrarRecargarHabilidadesAsignadasReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_HABILIDADES_EQUIPO)
        activity?.registerReceiver(recargarHabilidadesAsignadasReceiver, filter)
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recargarFocosReceiver)
        activity?.unregisterReceiver(recargarHabilidadesAsignadasReceiver)
        super.onDestroyView()
    }

    inner class RecargarFocosReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recargar()
        }
    }

    inner class RecargarHabilidadesAsignadasReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val itemSelected = intent.getIntExtra(Constant.BROADCAST_ITEM_POSITION_EXTRA, -1)
            presenter.recargar(itemSelected)
        }
    }
}
