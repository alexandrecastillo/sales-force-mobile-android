package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.GraphicProfitSeUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.mapper.ProfitSeBarEntryMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.mapper.ProfitSeMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProfitSeViewModel(
    private val useCase: GraphicProfitSeUseCase,
    private val mapper: ProfitSeMapper,
    private val barEntryMapper: ProfitSeBarEntryMapper
) : ViewModel() {

    val viewState: LiveData<ProfitSeViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ProfitSeViewState>()

    fun getProfit(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val list = useCase.getGraphicProfit(personIdentifier)
                if (list.isNotEmpty()) {
                    val modelList = list.map { mapper.reverseMap(it) }
                    val barEntrySet = barEntryMapper.map(modelList)
                    val model = mapper.mapToModel(modelList, barEntrySet)
                    _viewState.postValue(ProfitSeViewState.Success(model))
                } else {
                    _viewState.postValue(ProfitSeViewState.Empty)
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ ->
        ui { _viewState.value = ProfitSeViewState.Failure }
    }

}
