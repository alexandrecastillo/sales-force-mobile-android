package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.info.GetProfileInfoUseCase
import kotlinx.coroutines.launch

class DatosPersonaViewModel(
    private val useCase: GetProfileInfoUseCase,
    private val mapper: DatosPersonaMapper
) : ViewModel() {

    val viewState: LiveData<DatosPersonaViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<DatosPersonaViewState>()

    fun obtener(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            val info = useCase.getInfo(personIdentifier)
            val model = mapper.map(info)
            _viewState.postValue(DatosPersonaViewState.ShowInfo(model))
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
