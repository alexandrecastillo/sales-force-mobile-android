package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.model.MiFocoModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.presenter.MisFocosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.focos.AsignarFragment
import kotlinx.android.synthetic.main.fragment_mis_focos_rdd.*

class MisFocosFragment : BaseFragment(), MisFocosView {

    override fun getLayout() = R.layout.fragment_mis_focos_rdd

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    companion object {
        fun newInstance() = MisFocosFragment()
    }

    private val presenter: MisFocosPresenter by injectFragment()

    private var focosAdapter: MisFocosAdapter? = null
    private val recargarFocosReceiver = RecargarFocosReceiver()
    val listaFocos: MutableList<String> = ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        escucharAcciones()
    }

    private fun inicializar() {
        configurarRecycler()
        registrarRecargarFocosReceiver()
        presenter.establecerVista(this)
    }

    private fun configurarRecycler() {
        focosAdapter = MisFocosAdapter()
        recyclerMisFocos?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        recyclerMisFocos?.adapter = focosAdapter
    }

    private fun registrarRecargarFocosReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_MIS_FOCOS)
        activity?.registerReceiver(recargarFocosReceiver, filter)
    }

    private fun escucharAcciones() {
        layoutEditarMisFocos?.setOnClickListener { abrirAsignarMisFocos() }
    }

    override fun permitirAsignacion() {
        layoutEditarMisFocos?.visibility = View.VISIBLE
    }

    override fun pintarFocos(focos: List<MiFocoModel>) {
        cambiarTextoAEditar()
        cambiarIconoAEditar()
        hacerVisibleRecycler()
        focosAdapter?.actualizar(focos.toMutableList())
        for (foco in focos) {
            listaFocos.add(foco.descripcion)
        }

    }

    private fun cambiarTextoAEditar() {
        textEditarMisFocos?.text = getString(R.string.rdd_dashboard_btn_editar_focos_text)
    }

    private fun cambiarIconoAEditar() {
        if (context == null) return

        imageEditarMisFocos?.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_editar_focos
            )
        )
    }

    private fun hacerVisibleRecycler() {
        recyclerMisFocos?.visibility = View.VISIBLE
        cardPlaceHolderMisFocos?.visibility = View.GONE
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recargarFocosReceiver)
        super.onDestroyView()
    }

    private fun abrirAsignarMisFocos() {
        if (presenter.obtenerRol() == Rol.Builder.COD_GERENTE_REGION) {
            tagAnalytics()
        }

        AsignarFragment
            .newInstance(
                AsignarFragment.Request.EditarPropio(),
                TagAnalytics.EVENTO_SELECCION_MIS_FOCOS_CAMPANA
            )
            .show(childFragmentManager, "AsignarFocosFragment")
    }

    private fun tagAnalytics() {
        if (listaFocos.size != 0) {
            firebaseAnalyticsPresenter.enviarFocosCampania(
                TagAnalytics.EVENTO_MIS_FOCOS_CAMPANA_ASIGNAR_EDITAR,
                Constant.EVENTO_EDITAR_FOCOS
            )
        } else {
            firebaseAnalyticsPresenter.enviarFocosCampania(
                TagAnalytics.EVENTO_MIS_FOCOS_CAMPANA_ASIGNAR_EDITAR,
                Constant.EVENTO_ASIGNAR_FOCOS
            )
        }
    }

    inner class RecargarFocosReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recuperarRefrescarFocos()
        }
    }
}
