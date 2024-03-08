package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantConfiguration
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.core.utils.update
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.amount.FetchOrdersAmountUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncConsultantsUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetConsultantConfigurationUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetConsultantsUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetFilterParamsUseCase
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers.ConsultantsMapper
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers.ConsultantsMapper.ConsultantModelParams
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers.FilterConsultantMapper
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantContainerModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.SearchConsultantViewState.*
import kotlinx.coroutines.launch

class SearchConsultantViewModel(
    private val consultantsUseCase: GetConsultantsUseCase,
    private val ordersAmountUseCase: FetchOrdersAmountUseCase,
    private val syncConsultantsUseCase: SyncConsultantsUseCase,
    private val getFilterParamsUseCase: GetFilterParamsUseCase,
    private val consultantsConfigurationUseCase: GetConsultantConfigurationUseCase,
    private val filterMapper: FilterConsultantMapper,
    private val consultantsMapper: ConsultantsMapper,
    private val consultantsSearchable: ConsultantsSearchable
) : ViewModel() {

    val viewState: LiveData<SearchConsultantViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<SearchConsultantViewState>()

    private val localConsultants = arrayListOf<ConsultantModel>()

    fun getConsultants(consultantFilter: ConsultantFilter) {
        viewModelScope.launch(handler) {
            io {
                filterConsultants(consultantFilter)
                syncConsultants {
                    filterConsultants(consultantFilter)
                }
                ui { _viewState.value = HideLoading }
            }
        }
    }

    private suspend fun filterConsultants(consultantFilter: ConsultantFilter) =
        with(consultantFilter) {
            fetchAmounts(consultantFilter)
            val extraFilterParams = getFilterParamsUseCase.getExtraParams()
            val filter = filterMapper.map(extraFilterParams, this)
            val consultants = consultantsUseCase.getConsultants(filter)
            val configuration = getConsultantConfiguration(uaKey)
            val model = consultantsMapper.map(
                ConsultantModelParams(type, configuration, consultants, filters)
            )
            _viewState.postValue(onSuccess(model))
        }

    private suspend fun getConsultantConfiguration(uaKey: LlaveUA): ConsultantConfiguration {
        return consultantsConfigurationUseCase.getConsultantConfiguration(uaKey)
    }

    private suspend fun fetchAmounts(consultantFilter: ConsultantFilter) {
        if (SourceType.isBillingAdvancement(consultantFilter.type)) {
            try {
                ordersAmountUseCase.fetch(consultantFilter.uaKey)
            } catch (e: Exception) {
                _viewState.postValue(FetchAmountFailure(e.message.orEmpty()))
            }
        }
    }

    private suspend fun syncConsultants(onUpdated: suspend () -> Unit) {
        syncConsultantsUseCase.sync().let {
            if (it is SyncType.Updated) {
                onUpdated.invoke()
            }
        }
    }

    fun searchConsultants(text: String) {
        viewModelScope.launch(handler) {
            io {
                _viewState.postValue(consultantsSearchable.search(localConsultants, text))
            }
        }
    }

    private fun onSuccess(model: ConsultantContainerModel): SearchConsultantViewState {
        localConsultants.update(model.consultants)
        return if (model.consultants.isNotEmpty()) Success(model) else Empty(model)
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value = Failure(exception.toString())
        }
    }
}
