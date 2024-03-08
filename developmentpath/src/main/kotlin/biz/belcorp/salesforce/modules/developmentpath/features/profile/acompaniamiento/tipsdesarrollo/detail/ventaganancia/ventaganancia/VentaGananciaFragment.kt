package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia

import android.os.Bundle
import android.view.View
import android.widget.TextView
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.utils.MultiFontStyler
import kotlinx.android.synthetic.main.fragment_venta_ganancia.*

class VentaGananciaFragment : BaseFragment(), VentaGananciaContract.View {

    private val mPresenter by injectFragment<VentaGananciaContract.Presenter>()

    private var personaId: Long = -1L
    private lateinit var rol: Rol

    var opciones = emptyList<String>()

    override fun getLayout(): Int = R.layout.fragment_venta_ganancia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrarVentaGanancia(data: VentaGananciaModel) {
        if (!isAttached()) return
        fragment_venta_ganancia_container.visibility = View.VISIBLE
        txtSinVentaGanancia?.visibility = View.GONE
        clContenedorVentaGanancia?.visibility = View.VISIBLE
        pintarTexto(tvVentaMonto, data.ventaMonto?.texto, data.ventaMonto?.colores)
        pintarTexto(
            tvVentaDescripcion,
            data.ventaDescripcion?.texto,
            data.ventaDescripcion?.colores
        )
        pintarTexto(tvGananciaMonto, data.gananciaMonto?.texto, data.gananciaMonto?.colores)
        pintarTexto(
            tvGananciaDescripcion,
            data.gananciaDescripcion?.texto,
            data.gananciaDescripcion?.colores
        )
    }

    override fun mostrarVentaGananciaVacio() {
        if (!isAttached()) return
        fragment_venta_ganancia_container.visibility = View.GONE
        clContenedorVentaGanancia?.visibility = View.GONE
        txtSinVentaGanancia?.visibility = View.GONE
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun inicializar() {
        inicializarPresenter()
    }

    private fun inicializarPresenter() {
        val request = Params(personaId, rol, opciones)
        mPresenter.obtener(request)
    }

    private fun pintarTexto(textView: TextView?, texto: String?, colores: List<Int>?) {
        textView?.apply {
            text = MultiFontStyler.establecerTexto(texto ?: return)
                .establecerContexto(requireContext())
                .establecerDelimitador(Constant.PIPE)
                .establecerColorPrimario(textView.currentTextColor)
                .establecerColoresSencundarios(colores ?: return)
                .establecerFuentePrimaria(TipoFuente.LATO_REGULAR)
                .establecerFuenteSecundaria(TipoFuente.LATO_BOLD)
                .procesar()
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol): VentaGananciaFragment {
            return VentaGananciaFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
        }
    }
}
