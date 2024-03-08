package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import kotlinx.android.synthetic.main.fragment_resultado_visitas.*

class ResultadoVisitasFragment : BaseFragment(), ResultadoVisitasView {

    override fun getLayout() = R.layout.fragment_resultado_visitas

    private val presenter: ResultadoVisitasPresenter by injectFragment()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
    var navigator: Navigator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_resultado_visitas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.recuperarResultado()
        configurarBotones()
    }

    private fun configurarBotones() {
        botonVerListaFacturadas?.setOnClickListener {
            navigator?.irAListaResultadoVisitasFacturaron()
            firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_VER_LISTA_FACTURA_CONSULTORA,
                Constant.CONSULTORAS_FACTURARON
            )
        }

        botonVerListaNoFacturadas?.setOnClickListener {
            navigator?.irAListaResultadoVisitasNoFacturaron()
            firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_VER_LISTA_FACTURA_CONSULTORA,
                Constant.CONSULTORAS_NO_FACTURARON
            )
        }
    }

    override fun mostrarContenedor() {
        container?.visible()
    }

    override fun ocultarContenedor() {
        container?.gone()
    }

    override fun pintarCantidadFacturadas(facturadas: Int) {
        textViewFacturaron?.text =
            resources.getQuantityString(
                R.plurals.rdd_resultado_visitas_titulo_consultoras_facturaron,
                facturadas,
                facturadas
            )
    }

    override fun pintarCantidadNoFacturadas(noFacturadas: Int) {
        textViewNoFacturaron?.text =
            resources.getQuantityString(
                R.plurals.rdd_resultado_visitas_titulo_consultoras_no_facturaron,
                noFacturadas,
                noFacturadas
            )
    }

    override fun mostrarBotonVerFacturadas() {
        botonVerListaFacturadas?.visible()
    }

    override fun ocultarBotonVerFacturadas() {
        botonVerListaFacturadas?.gone()
    }

    override fun mostrarFacturadas() {
        cardFacturaron?.visible()
    }

    override fun ocultarFacturadas() {
        cardFacturaron?.gone()
    }

    override fun mostrarNoFacturadas() {
        cardNoFacturaron?.visible()
    }

    override fun ocultarNoFacturadas() {
        cardNoFacturaron?.gone()
    }

    override fun mostrarFelicitaciones() {
        grupoFelicitacion?.visible()
    }

    override fun ocultarFelicitaciones() {
        imageViewFelicitacion?.gone()
        textViewTituloFelicitacion?.gone()
        textViewSubtituloFelicitacion?.gone()
    }
}
