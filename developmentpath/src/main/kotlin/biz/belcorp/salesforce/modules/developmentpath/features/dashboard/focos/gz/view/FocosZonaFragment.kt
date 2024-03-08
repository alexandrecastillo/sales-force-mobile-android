package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.presenter.FocoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.focos.AsignarFragment
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.SeccionFocoModel
import kotlinx.android.synthetic.main.fragment_rdd_dashboard_focos.*

class FocosZonaFragment : BaseFragment(), FocosView {

    override fun getLayout() = R.layout.fragment_rdd_dashboard_focos

    private val presenter: FocoPresenter by injectFragment()
    private val recargarFocosReceiver = RecargarFocosReceiver()
    private val recargarHabilidadesAsignadasReceiver = RecargarHabilidadesAsignadasReceiver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecyclerView()
        registrarReceivers()

        tv_titulo_focos?.text = getString(R.string.rdd_dashboard_focos_zona_titulo)
        ll_asignar_focos?.setOnClickListener { irAAsignarFocosMultiples() }

        presenter.recargarSecciones()
    }

    private fun registrarReceivers() {
        registrarRecargarFocosReceiver()
        registrarRecargarHabilidadesAsignadasReceiver()
    }

    private fun irAAsignarFocosMultiples() {
        AsignarFragment
            .newInstance(AsignarFragment.Request.EditarTodos(), TagAnalytics.EVENTO_ZONA)
            .show(childFragmentManager, "AsignarFocosFragment")
    }

    private fun irAAsignarFocosSimple(personaId: Long) {
        AsignarFragment
            .newInstance(AsignarFragment.Request.EditarUno(personaId), TagAnalytics.EVENTO_ZONA)
            .show(childFragmentManager, "AsignarFocosFragment")
    }

    private fun configurarRecyclerView() {
        val context = context ?: return

        val adapter = SeccionesFocosAdapter()
        adapter.setEditarFocoListener { personaId -> irAAsignarFocosSimple(personaId) }

        rv_secciones?.isNestedScrollingEnabled = false
        rv_secciones?.layoutManager = LinearLayoutManager(context)
        rv_secciones?.adapter = adapter
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

    override fun cargarSecciones(modelos: List<SeccionFocoModel>) {
        (rv_secciones?.adapter as? SeccionesFocosAdapter)?.actualizar(modelos)
    }

    inner class RecargarFocosReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recargarSecciones()
        }
    }

    inner class RecargarHabilidadesAsignadasReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recargarSecciones()
        }
    }
}
