package biz.belcorp.salesforce.modules.developmentpath.features.profile.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.info.GetProfileInfoUseCase
import kotlinx.coroutines.launch

class PerfilCabeceraViewModel(
    private val useCase: GetProfileInfoUseCase,
    private val mapper: PerfilCabeceraMapper
) : ViewModel() {

    val viewState: LiveData<PerfilCabeceraViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<PerfilCabeceraViewState>()

    fun obtener(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val person = useCase.getInfo(personIdentifier)
            val model = mapper.parse(person)
            _viewState.postValue(PerfilCabeceraViewState.ShowInfo(model))
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
