package biz.belcorp.salesforce.modules.developmentpath.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.datos.ObtenerNombrePersonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.SyncProfileUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getNameUseCase: ObtenerNombrePersonaUseCase,
    private val syncUseCase: SyncProfileUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase
) : ViewModel() {

    val viewState: LiveData<ProfileViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ProfileViewState>()

    private var role = Rol.NINGUNO

    fun getInfo(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                role = personIdentifier.role
                getPersonInfo(personIdentifier)
                syncIfNeeded(personIdentifier)
            }
        }
    }

    fun getSesion(): Sesion {
        return obtenerSesionUseCase.obtener()
    }

    private fun getPersonInfo(personIdentifier: PersonIdentifier) {
        val person = getNameUseCase.get(personIdentifier)
        _viewState.postValue(ProfileViewState.Success(person.nombreCompleto))
    }

    private suspend fun syncIfNeeded(personIdentifier: PersonIdentifier) {
        syncUseCase.sync(personIdentifier)
        _viewState.postValue(ProfileViewState.SuccessSync)
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is NetworkConnectionException -> {
                _viewState.postValue(ProfileViewState.ShowNetworkError(role))
            }
            else -> {
                _viewState.postValue(ProfileViewState.ShowError)
            }
        }
    }

}
