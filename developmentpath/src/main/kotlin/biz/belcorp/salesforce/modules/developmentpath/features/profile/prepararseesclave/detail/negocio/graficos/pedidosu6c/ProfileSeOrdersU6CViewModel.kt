package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.pedidosu6c.ProfileSeOrdersU6CUseCase
import kotlinx.coroutines.launch

class ProfileSeOrdersU6CViewModel(
    private val useCase: ProfileSeOrdersU6CUseCase,
    private val mapper: OrdersBarEntriesModelMapper
) : ViewModel() {

    val viewState: LiveData<ProfileSeOrdersU6CViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ProfileSeOrdersU6CViewState>()

    fun getOrdersU6C(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val data = useCase.getGraphicOrdersSe(personIdentifier)
                if (data.isNotEmpty()) postSuccessState(mapper.map(data))
                else postFailureState(EMPTY_STRING)
            }
        }
    }

    private fun postSuccessState(data: OrderU6CGraphicModel) {
        _viewState.postValue(ProfileSeOrdersU6CViewState.Success(data))
    }

    private fun postFailureState(message: String) {
        _viewState.postValue(ProfileSeOrdersU6CViewState.Failure(message))
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui { postFailureState(exception.message.orEmpty()) }
    }
}
