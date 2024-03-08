package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.presenter.AvanceCampaniaPresenter
import biz.belcorp.salesforce.modules.developmentpath.utils.establecerDistintasFuentes
import kotlinx.android.synthetic.main.fragment_avance_pais_campania.*

class AvanceCampaniaFragment : BaseFragment(), AvanceCampaniaView {

    private var planId = -1L

    companion object {

        const val CARACTER_SEPARADOR = "|"
        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): AvanceCampaniaFragment {
            val bundle = Bundle()
            val fragment = AvanceCampaniaFragment()
            bundle.putLong(PLAN_ID, planId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayout() = R.layout.fragment_avance_pais_campania

    private val presenter: AvanceCampaniaPresenter by injectFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.recuperar(planId)
        presenter.obtenerCantidadIntencionDePedido(planId)
    }

    private fun recuperarArgumentos() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }

    override fun pintarPorcentaje(avance: Float) {
        velocimetro?.setProgress(avance, false)
    }

    override fun pintarDiasRestantes(dias: Int) {
        if (context == null) return
        val diasNumero = resources.getQuantityString(R.plurals.rdd_agregareventos_tiempo_dias, dias)
        val text = "$dias $diasNumero"
        textDiasCierre?.pintarConDistintasFuentes(
            getString(
                R.string.dashboard_dv_dias_para_cierre,
                text
            )
        )
    }

    override fun pintarHoyEsCierre() {
        if (context == null) return
        textDiasCierre?.pintarConDistintasFuentes(getString(R.string.dashboard_dv_hoy))
    }

    override fun pintarPedidosFacturados(cantidad: String) {
        textPedidosFacturadosValor?.text = cantidad
    }

    override fun pintarMetaFacturados(cantidad: String) {
        textMeta?.text = getString(R.string.dashboard_dv_pedidos_facturados_meta, cantidad)
    }

    override fun pintarPorcentajeFacturados(porcentaje: Int) {
        textPorcentajeLogrado?.text =
            getString(R.string.dashboard_dv_pedidos_facturados_porcentaje, porcentaje)
    }

    override fun ocultarPorcentajeLogrado() {
        viewDivisor?.visibility = View.GONE
        textPorcentajeLogrado?.visibility = View.GONE
    }

    override fun pintarCantidadIntencionPedido(cantidadIntencionPedido: String) {
        textIntencionPasePedidoValor?.text = cantidadIntencionPedido
    }

    private fun TextView.pintarConDistintasFuentes(texto: String) {
        val fontPrincipal = ResourcesCompat.getFont(
            context!!,
            biz.belcorp.salesforce.base.R.font.mulish_bold
        )

        val fontSecundario = ResourcesCompat.getFont(
            context!!,
            biz.belcorp.salesforce.base.R.font.mulish_regular
        )

        val colorPrimario = ContextCompat.getColor(requireContext(), R.color.rdd_evento)
        val colorSegundo = ContextCompat.getColor(requireContext(), R.color.titulo_avance_campania)

        text = texto.establecerDistintasFuentes(
            caracterSeparador = CARACTER_SEPARADOR,
            fontPrincipal = fontPrincipal,
            fontSecundario = fontSecundario,
            colorPrincipal = colorPrimario,
            colorSecundario = colorSegundo
        )
    }
}
