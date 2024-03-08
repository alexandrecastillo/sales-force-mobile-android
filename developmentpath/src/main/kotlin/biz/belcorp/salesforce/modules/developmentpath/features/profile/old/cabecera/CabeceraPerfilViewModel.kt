package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.info.GetProfileInfoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraMapper
import kotlinx.coroutines.launch

class CabeceraPerfilViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val cabeceraPerfilMapper: PerfilCabeceraMapper
) : ViewModel() {

    val viewState: LiveData<CabeceraPerfilViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<CabeceraPerfilViewState>()

    fun cargar(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val info = getProfileInfoUseCase.getInfo(personIdentifier)
            val model = cabeceraPerfilMapper.parse(info)
            _viewState.postValue(CabeceraPerfilViewState.ShowInfo(model))
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
