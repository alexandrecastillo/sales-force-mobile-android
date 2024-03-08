package biz.belcorp.salesforce.base.features.home.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class UserViewModel(
    private val sessionUseCase: ObtenerSesionUseCase,
    private val mapper: UserInfoMapper
) : ViewModel() {

    val viewState: LiveData<UserInfoViewState>
        get() = viewModel

    private val viewModel = MutableLiveData<UserInfoViewState>()

    fun getUserInformation() {
        viewModelScope.launch(handler) {
            io {
                val userInformation = mapper.map(sessionUseCase.obtener())
                viewModel.postValue(UserInfoViewState.Success(userInformation))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            viewModel.value = UserInfoViewState.Failure(exception.message.orEmpty())
        }
    }
}
