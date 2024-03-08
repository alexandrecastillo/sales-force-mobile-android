package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.progressprojection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.saleforcestatus.GetSaleForceStatusUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.lazyDeferred
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.GetCampaignProjectionInfoUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.GetResultsLastCampaignUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.SyncPartnerUseCase
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection.CampaignProjectionModelMapper
import kotlinx.coroutines.launch

class ProgressProjectionViewModel(
    private val getCampaignProjectionInfoUseCase: GetCampaignProjectionInfoUseCase,
    private val getConfigurationUseCase: ConfigurationUseCase,
    private val getSaleForceStatusUseCase: GetSaleForceStatusUseCase,
    private val getResultsLastCampaignUseCase: GetResultsLastCampaignUseCase,
    private val mapper: CampaignProjectionModelMapper,
    private val syncPartner: SyncPartnerUseCase
) : ViewModel() {

    val viewState: LiveData<ProgressProjectionViewState> get() = _viewState
    private val _viewState = MutableLiveData<ProgressProjectionViewState>()
    val viewStatusSEState: LiveData<ProgressStatusSEViewState> get() = _viewStatusSEState
    val viewResultsLastCampaignState: LiveData<ProgressResultsLastCampaignViewState>
        get()
        = _viewResultsLastCampaignState
    private val _viewStatusSEState = MutableLiveData<ProgressStatusSEViewState>()
    private val _viewResultsLastCampaignState =
        MutableLiveData<ProgressResultsLastCampaignViewState>()

    val viewSyncPartnerState: LiveData<Boolean> get() = _viewSyncPartnerState
    private val _viewSyncPartnerState = MutableLiveData<Boolean>()

    private val currency by viewModelScope.lazyDeferred {
        getConfigurationUseCase.getConfiguration().currencySymbol
    }

    fun fetchPartnerCampaignProjectionInfo(section: String) {
        viewModelScope.launch(handlerPartnerFetch) {
            io {
                syncPartner.sync(section)
                _viewSyncPartnerState.postValue(true)
            }
        }
    }

    fun fetchCampaignProjectionInfo(sectionPartner: String? = null) {
        viewModelScope.launch(handler) {
            io {
                val response = getCampaignProjectionInfoUseCase.getCampaignProjectionInfo(sectionPartner)
                _viewState.postValue(
                    ProgressProjectionViewState.Success(
                        mapper.mapToModel(currency.await(), response)
                    )
                )
            }
        }
    }


    private val handlerPartnerFetch = CoroutineExceptionHandler { _, _ ->
        io {
            _viewSyncPartnerState.postValue(false)
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        io {
            val message = exception.message.orEmpty()
            _viewState.postValue(
                ProgressProjectionViewState.Failed(
                    message
                )
            )
        }
    }

    fun loadSaleForceStatus() {
        viewModelScope.launch(saleForceStatusHandler) {
            io {
                val response = getSaleForceStatusUseCase.obtener()
                _viewStatusSEState.postValue(ProgressStatusSEViewState.Success(response))
            }
        }
    }

    fun getCampaignResults(section: String?) {
        viewModelScope.launch(campaignsResultHandler) {
            io {
                val response = getSaleForceStatusUseCase.obtener()
                val results = getResultsLastCampaignUseCase.getResults(section)
                _viewResultsLastCampaignState.postValue(ProgressResultsLastCampaignViewState.Success(results))
                _viewStatusSEState.postValue(ProgressStatusSEViewState.Success(response))
            }
        }
    }

    private val saleForceStatusHandler = CoroutineExceptionHandler { _, exception ->
        io {
            val message = exception.message.orEmpty()
            _viewStatusSEState.postValue(
                ProgressStatusSEViewState.Failed(
                    message
                )
            )
        }
    }

    private val campaignsResultHandler = CoroutineExceptionHandler { _, _ ->
        io {
            _viewResultsLastCampaignState.postValue(
                ProgressResultsLastCampaignViewState.Failure
            )
        }
    }

}
