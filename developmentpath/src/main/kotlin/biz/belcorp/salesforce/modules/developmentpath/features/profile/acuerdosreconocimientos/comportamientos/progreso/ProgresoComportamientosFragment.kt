package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle.ProgresoReconocimientoFragment
import kotlinx.android.synthetic.main.fragment_comportamientos_uc.*

class ProgresoComportamientosFragment : BaseFragment(),
    ProgresoComportamientosView {

    private var personaId = -1L
    private lateinit var rol: Rol

    private val presenter by injectFragment<ProgresoComportamientosPresenter>()

    private val adapter
        by lazy { ProgresoComportamientosAdapter() }

    private val progresoComportamientosFragment
        by lazy { ProgresoReconocimientoFragment.newInstance(personaId, rol) }

    override fun getLayout(): Int = R.layout.fragment_comportamientos_uc

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
        configurarRecycler()
        configurarClickEnCard()
        cargarComportamientosUC()
    }

    private fun configurarRecycler() {
        recyclerViewComportamientos.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewComportamientos.isNestedScrollingEnabled = false
        recyclerViewComportamientos.adapter = adapter
    }

    private fun configurarClickEnCard() {
        cardComportamientosUC.setOnClickListener {
            progresoComportamientosFragment.show(childFragmentManager, null)
        }
    }

    private fun cargarComportamientosUC() {
        presenter.getComportamientosUltimasCampanias(personaId, rol)
    }

    override fun pintarComportamientosUC(comportamientos: List<ProgresoComportamientoModel>) {
        adapter.actualizar(comportamientos)
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = ProgresoComportamientosFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
