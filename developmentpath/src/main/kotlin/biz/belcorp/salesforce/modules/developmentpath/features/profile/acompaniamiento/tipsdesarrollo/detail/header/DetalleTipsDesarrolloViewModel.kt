package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.TipsDesarrolloUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipsDesarrolloMapper
import kotlinx.coroutines.launch

class DetalleTipsDesarrolloViewModel(
    private val useCase: TipsDesarrolloUseCase,
    private val mapper: TipsDesarrolloMapper
) : ViewModel() {

    val viewState: LiveData<DetalleTipsDesarrolloViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<DetalleTipsDesarrolloViewState>()

    fun obtener(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val tips = useCase.obtener(personIdentifier)
                val model = mapper.parse(tips)
                _viewState.postValue(DetalleTipsDesarrolloViewState.ShowTips(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
