package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter.DesarrolloUaComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.model.DesarrolloComportamientoModel
import kotlinx.android.synthetic.main.fragment_desarrollo_ua_comportamientos.*

class DesarrolloUaComportamientosFragment : BaseDialogFragment(), DesarrolloUaComportamientosView {

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): DesarrolloUaComportamientosFragment {
            return DesarrolloUaComportamientosFragment()
                .withArguments(
                    PLAN_ID to planId
                )
        }
    }

    val presenter: DesarrolloUaComportamientosPresenter by injectFragment()
    private var planId = -1L
    private var adapter = DesarrolloComportamientosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArguments()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
        configurarRecycler()
        escucharAcciones()
        presenter.ejecutar(planId)
    }

    override fun pintarCampaniaActual(periodo: Campania.Periodo, nombreCorto: String) {
        val stringResId = recuperarStringIdPorEstado(periodo)

        textCampania?.text = getString(stringResId, nombreCorto)
    }

    private fun recuperarStringIdPorEstado(periodo: Campania.Periodo): Int {
        return when (periodo) {
            Campania.Periodo.VENTA -> R.string.campania_tipo_v
            Campania.Periodo.FACTURACION -> R.string.campania_tipo_f
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_desarrollo_ua_comportamientos
    }

    private fun recuperarArguments() {
        arguments?.apply {
            planId = getLong(PLAN_ID)
        }
    }

    private fun configurarRecycler() {
        recyclerComportamientos?.layoutManager = LinearLayoutManager(context)
        recyclerComportamientos?.adapter = adapter
    }

    private fun escucharAcciones() {
        imageRetroceder?.setOnClickListener { dismiss() }
    }

    override fun pintarCampaniaAnterior(unidadAdministrativa: String, nombreCorto: String) {
        textTituloCabecera?.text = getString(R.string.desarrollo_ua_avance, unidadAdministrativa, nombreCorto)
    }

    override fun pintarComportamientos(comportamientos: List<DesarrolloComportamientoModel>) {
        adapter.comportamientos = comportamientos
    }
}
