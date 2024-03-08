package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.decorations.DividerLinearDecoration
import biz.belcorp.mobile.components.core.extensions.getDrawableAttr
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_datos_estado_cuenta_v2.*
import biz.belcorp.salesforce.base.R as BaseR

class DatosEstadoCuentaFragment : BaseFragment(), DatosEstadoCuentaContract.View {

    private var personaId: Long = -1L
    private var rol = Rol.NINGUNO

    private val adapter by lazy { EstadoCuentaAdapter() }
    private val presenter by injectFragment<DatosEstadoCuentaContract.Presenter>()

    override fun getLayout(): Int = R.layout.fragment_datos_estado_cuenta_v2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrarData(data: List<CuentaCorrienteModel>) {
        if (!isAttached()) return
        if (!establecerVisibilidadLista(data)) return
        adapter.actualizar(data)
    }

    private fun inicializar() {
        configurarRecyclerView()
        presenter.obtener(personaId)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(PARAM_PERSONA_ID)
            rol = it.getSerializable(PARAM_ROL) as Rol
        }
    }

    private fun configurarRecyclerView() {
        recyclerEstadoCuenta?.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerLinearDecoration(
                    getDrawableAttr(BaseR.attr.listDivider),
                    showFirstDivider = false,
                    showLastDivider = false
                )
            )
            adapter = this@DatosEstadoCuentaFragment.adapter
        }
    }

    private fun establecerVisibilidadLista(data: List<CuentaCorrienteModel>): Boolean {
        if (data.isNullOrEmpty()) {
            recyclerEstadoCuenta?.visibility = View.GONE
            txtSinEstadoCuenta?.visibility = View.VISIBLE
            return false
        }
        recyclerEstadoCuenta?.visibility = View.VISIBLE
        txtSinEstadoCuenta?.visibility = View.GONE
        return true
    }

    companion object {

        private const val PARAM_PERSONA_ID = "PERSONA_ID"
        private const val PARAM_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): DatosEstadoCuentaFragment {
            return DatosEstadoCuentaFragment()
                .withArguments(
                    PARAM_PERSONA_ID to personaId,
                    PARAM_ROL to rol
                )
        }
    }
}
