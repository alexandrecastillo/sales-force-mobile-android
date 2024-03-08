package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.digital.GetDigitalSaleUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper.DigitalSaleModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleViewState
import kotlinx.coroutines.launch

class DigitalSaleViewModel(
    private val useCase: GetDigitalSaleUseCase,
    private val mapper: DigitalSaleModelMapper,
    private val sessionUseCase: ObtenerSesionUseCase
) : ViewModel() {

    val session get() = sessionUseCase.obtener()

    private val mViewState = MutableLiveData<DigitalSaleViewState>()
    val viewState: LiveData<DigitalSaleViewState>
        get() = mViewState


    fun getDigitalSaleByRole(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val data = useCase.getDigitalSaleByRole(personIdentifier)
                val state = if (data.digitalSaleList.isNotEmpty()) {
                    DigitalSaleViewState.Success(mapper.map(data, session.countryIso))
                } else {
                    DigitalSaleViewState.Empty
                }
                mViewState.postValue(state)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            mViewState.value = DigitalSaleViewState.Error(message)
        }
    }
}
