package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.dimen
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoAdapter
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoDecoration
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.GridManagerBuilder
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import kotlinx.android.synthetic.main.fragment_datos_cobranza_v2.*
import biz.belcorp.salesforce.base.R as BaseR

class DatosCobranzaFragment : BaseFragment(), DatosCobranzaContract.View {

    private var personaId: Long = -1L
    private var rol = Rol.NINGUNO

    private val adapter by lazy { EtiquetaInfoAdapter() }
    private val presenter by injectFragment<DatosCobranzaContract.Presenter>()

    override fun getLayout(): Int = R.layout.fragment_datos_cobranza_v2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun pintarContenedorInfo(modelo: ContenedorInfoBasica) {
        if (!isAttached())
            return
        recyclerCobranza?.layoutManager =
            GridManagerBuilder.buildForContainer(requireContext(), modelo)
        adapter.actualizar(modelo.grupos)
    }

    private fun inicializar() {
        configurarRecyclerView()
        presenter.obtener(personaId, rol)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(PARAM_PERSONA_ID)
            rol = it.getSerializable(PARAM_ROL) as Rol
        }
    }

    private fun configurarRecyclerView() {
        recyclerCobranza?.apply {
            isNestedScrollingEnabled = false
            addItemDecoration(EtiquetaInfoDecoration(dimen(BaseR.dimen.content_inset_medium)))
            adapter = this@DatosCobranzaFragment.adapter
        }
    }

    companion object {

        private const val PARAM_PERSONA_ID = "PERSONA_ID"
        private const val PARAM_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): DatosCobranzaFragment {
            return DatosCobranzaFragment().withArguments(
                PARAM_PERSONA_ID to personaId,
                PARAM_ROL to rol
            )
        }
    }
}
