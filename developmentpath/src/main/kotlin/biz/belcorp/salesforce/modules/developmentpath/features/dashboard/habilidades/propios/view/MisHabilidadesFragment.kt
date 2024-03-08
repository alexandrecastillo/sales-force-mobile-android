package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.model.MiHabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.presenter.MisHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view.AsignarHabilidadFragment
import kotlinx.android.synthetic.main.fragment_mis_habilidades_rdd.*

class MisHabilidadesFragment : BaseFragment(),
                               MisHabilidadesView {

    override fun getLayout() = R.layout.fragment_mis_habilidades_rdd

    private val presenter: MisHabilidadesPresenter by injectFragment()
    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    private val recargarHabilidadesAsignadasReceiver = RecargarHabilidadesAsignadasReceiver()
    private val habilidadesAdapter: MisHabilidadesAdapter by lazy { MisHabilidadesAdapter() }
    val listHabilidades: MutableList<String> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    private fun inicializar() {
        registrarHabilidadesModificadasReceiver()
        configurarRecycler()
        eventosClick()
        presenter.establecerVista(this)
        presenter.recuperar()
    }

    private fun registrarHabilidadesModificadasReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_HABILIDADES_PROPIAS)
        activity?.registerReceiver(recargarHabilidadesAsignadasReceiver, filter)
    }

    private fun eventosClick() {
        layoutEditarMisHabilidades?.setOnClickListener { abrirHabilidades() }
    }

    private fun configurarRecycler() {
        recyclerMisHabilidades?.layoutManager = NonScrollableLayoutManager()
                .withContext(activity)
                .linearVertical()

        recyclerMisHabilidades?.adapter = habilidadesAdapter
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recargarHabilidadesAsignadasReceiver)
        super.onDestroyView()
    }

    override fun pintarHabilidades(habilidades: List<MiHabilidadModel>) {
        cambiarTextoAEditar()
        hacerVisibleLista()
        habilidadesAdapter.actualizar(habilidades)
        for (item in habilidades){
            listHabilidades.add(item .descripcion)
        }
    }

    private fun cambiarTextoAEditar() {
        textEditarMisHabilidades?.text = getString(R.string.rdd_dashboard_btn_editar_focos_text)
    }

    override fun permitirAsignacion() {
        layoutEditarMisHabilidades?.visibility = View.VISIBLE
    }

    private fun hacerVisibleLista() {
        cardListadoMisHabilidades?.visibility = View.VISIBLE
        cardPlaceHolderMisHabilidades?.visibility = View.GONE
    }

    private fun abrirHabilidades() {
        if(listHabilidades.size != 0){
            firebaseAnalyticsPresenter.enviarFocosCampania(TagAnalytics.EVENTO_FOCOS_HABILIDADES_ASIGNAR_EDITAR, Constant.EVENTO_EDITAR_FOCOS)
        } else {
            firebaseAnalyticsPresenter.enviarFocosCampania(TagAnalytics.EVENTO_FOCOS_HABILIDADES_ASIGNAR_EDITAR, Constant.EVENTO_ASIGNAR_FOCOS)
        }
        AsignarHabilidadFragment
            .newInstance(AsignarHabilidadFragment.Request.EditarHabilidadesPropias())
            .show(childFragmentManager, null)
    }

    inner class RecargarHabilidadesAsignadasReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recuperar()
        }
    }

    companion object {
        fun newInstance() = MisHabilidadesFragment()
    }

}
