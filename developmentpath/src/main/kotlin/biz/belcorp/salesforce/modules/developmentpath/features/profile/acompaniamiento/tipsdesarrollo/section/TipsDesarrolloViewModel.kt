package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.TipsDesarrolloUseCase
import kotlinx.coroutines.launch

class TipsDesarrolloViewModel(
    private val useCase: TipsDesarrolloUseCase,
    private val mapper: TipsDesarrolloMapper
) : ViewModel() {

    val viewState: LiveData<TipsDesarrolloViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<TipsDesarrolloViewState>()

    fun obtener(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val list = useCase.obtener(personIdentifier)
                val model = mapper.parse(list)
                val state = if (model.isEmpty()) {
                    TipsDesarrolloViewState.ShowEmptyView
                } else {
                    TipsDesarrolloViewState.ShowTips(model)
                }
                _viewState.postValue(state)
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
