package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaCampaniaAnterior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaCampaniaAnteriorUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.ObtenerFechaSyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.CobranzaCampaniaAnteriorModel
import kotlinx.coroutines.launch

class CollectionInfoViewModel(
    private val obtenerFechaSyncUseCase: ObtenerFechaSyncUseCase,
    private val obtenerDatosCobranzaCampaniaAnteriorUseCase: ObtenerDatosCobranzaCampaniaAnteriorUseCase
) : ViewModel() {

    val viewState: LiveData<CollectionInfoViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<CollectionInfoViewState>()

    fun getInfo(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val dateString = obtenerFechaSyncUseCase.obtenerFechaSync()
                val info = obtenerDatosCobranzaCampaniaAnteriorUseCase.obtener(personIdentifier)
                val collectionInfo = obtenerCobranzaCampaniaAnterior(info.cobranza)
                val campaignName = info.campaniaAnterior
                val state =
                    CollectionInfoViewState.Success(dateString, campaignName, collectionInfo)
                _viewState.postValue(state)
            }
        }
    }

    private fun obtenerCobranzaCampaniaAnterior(cobranzaCampaniaAnterior: CobranzaCampaniaAnterior):
        CobranzaCampaniaAnteriorModel {
        return CobranzaCampaniaAnteriorModel(
            montoFacturadoNeto = cobranzaCampaniaAnterior.montoFacturadoNeto,
            saldoDeuda = cobranzaCampaniaAnterior.saldoDeuda,
            recuperacion = cobranzaCampaniaAnterior.recuperacion,
            consultorasConDeuda = cobranzaCampaniaAnterior.consultorasConDeuda,
            montoRecuperado = cobranzaCampaniaAnterior.montoRecuperado
        )
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui {
            _viewState.postValue(CollectionInfoViewState.Failed)
        }
    }

}
