package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.CobranzaCampaniaAnteriorModel
import biz.belcorp.salesforce.modules.developmentpath.utils.invertirVisibilidad
import kotlinx.android.synthetic.main.fragment_cobranza_campania_anterior.*
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionInfoFragment : BaseFragment(), DatosCobranzaCampaniaAnteriorView {

    private val personIdentifier by lazyPersonIdentifier()

    private val viewModel by viewModel<CollectionInfoViewModel>()

    override fun getLayout() = R.layout.fragment_cobranza_campania_anterior

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel()
        lyCabeceraCobranza?.setOnClickListener {
            lyCobranza?.invertirVisibilidad()
        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getInfo(personIdentifier)
    }

    private val viewStateObserver = Observer<CollectionInfoViewState> { state ->
        when (state) {
            is CollectionInfoViewState.Success -> {
                pintarCampaniaAnterior(state.campaignName)
                pintarDatos(state.collectionInfo)
                pintarFechaSincronizacion(state.dateString)
            }
        }
    }
    override fun pintarCampaniaAnterior(campaniaAnterior: String) {
        txtTitleCard?.text = getString(R.string.text_cobranza_campania, campaniaAnterior)
    }

    override fun pintarDatos(datos: CobranzaCampaniaAnteriorModel) {
        tv_perfil_cobranza_monto_facturado_neto?.text = datos.montoFacturadoNeto
        tv_perfil_cobranza_monto_recuperado?.text = datos.montoRecuperado
        tv_perfil_cobranza_saldo_deuda?.text = datos.saldoDeuda
        tv_perfil_cobranza_recuperacion?.text = "${datos.recuperacion}%"
        tv_perfil_cobranza_consultoras_deuda?.text = datos.consultorasConDeuda
    }

    override fun pintarFechaSincronizacion(fecha: String) {
        tvFechaSync?.text = getString(R.string.updated, fecha)
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier) =
            CollectionInfoFragment()
                .withPersonIdentifier(personIdentifier)
    }
}
