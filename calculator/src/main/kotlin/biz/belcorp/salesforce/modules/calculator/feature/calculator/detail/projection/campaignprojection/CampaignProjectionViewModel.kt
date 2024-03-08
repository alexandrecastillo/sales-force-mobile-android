package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection

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
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.SaveCampaignProjectionInfoUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.SyncPartnerUseCase
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionModel
import kotlinx.coroutines.launch

class CampaignProjectionViewModel(
    private val getCampaignProjectionInfoUseCase: GetCampaignProjectionInfoUseCase,
    private val saveCampaignProjectionInfoUseCase: SaveCampaignProjectionInfoUseCase,
    private val getConfigurationUseCase: ConfigurationUseCase,
    private val getSaleForceStatusUseCase: GetSaleForceStatusUseCase,
    private val getResultsLastCampaignUseCase: GetResultsLastCampaignUseCase,
    private val mapper: CampaignProjectionModelMapper,
    private val syncPartner: SyncPartnerUseCase
) : ViewModel() {

    val viewState: LiveData<CampaignProjectionViewState> get() = _viewState
    val viewStatusSEState: LiveData<StatusSEViewState> get() = _viewStatusSEState
    val viewSyncPartnerState: LiveData<Boolean> get() = _viewSyncPartnerState
    val viewResultsLastCampaignState: LiveData<ResultsLastCampaignViewState> get() = _viewResultsLastCampaignState
    private val _viewState = MutableLiveData<CampaignProjectionViewState>()
    private val _viewSyncPartnerState = MutableLiveData<Boolean>()
    private val _viewStatusSEState = MutableLiveData<StatusSEViewState>()
    private val _viewResultsLastCampaignState = MutableLiveData<ResultsLastCampaignViewState>()
    private val currency by viewModelScope.lazyDeferred {
        getConfigurationUseCase.getConfiguration().currencySymbol
    }


    fun fetchCampaignProjectionInfoPartner(sectionPartner: String) {
        viewModelScope.launch(handlerPartnerFetch) {
            io {
                syncPartner.sync(sectionPartner)
                _viewSyncPartnerState.postValue(true)
            }
        }
    }

    fun fetchCampaignProjectionInfo(sectionPartner: String? = null) {
        viewModelScope.launch(handler) {
            io {
                val response = getCampaignProjectionInfoUseCase.getCampaignProjectionInfo(sectionPartner)
                _viewState.postValue(
                    CampaignProjectionViewState.Success(
                        mapper.mapToModel(currency.await(), response)
                    )
                )
            }
        }
    }

    fun loadSaleForceStatus(section: String? = null) {
        viewModelScope.launch(saleForceStatusHandler) {
            io {
                getSaleForceStatusUseCase.saveSaleForceDataInPref(section)
                val response = getSaleForceStatusUseCase.obtener()
                _viewStatusSEState.postValue(StatusSEViewState.Success(response))
            }
        }
    }

    fun getCampaignResults(section: String? = null) {
        viewModelScope.launch(campaignsResultHandler) {
            io {
                val results = getResultsLastCampaignUseCase.getResults(section)
                _viewResultsLastCampaignState.postValue(ResultsLastCampaignViewState.Success(results))
            }
        }
    }


    fun saveCampaignProjectionInfo(campaignModel: CampaignProjectionModel, sectionPartner: String? = null) {
        io {
            saveCampaignProjectionInfoUseCase
                .saveCampaignInfo(
                    mapper.mapToEntity(
                        campaignModel.apply {
                            campaign =
                                getCampaignProjectionInfoUseCase.session.campaign.codigo
                        }),
                    sectionPartner
                )
            val response = getCampaignProjectionInfoUseCase.getCampaignProjectionInfo(sectionPartner)
            _viewState.postValue(
                CampaignProjectionViewState.Success(
                    mapper.mapToModel(currency.await(), response)
                )
            )
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        io {
            val message = exception.message.orEmpty()
            _viewState.postValue(
                CampaignProjectionViewState.Failed(
                    message
                )
            )
        }
    }

    private val handlerPartnerFetch = CoroutineExceptionHandler { _, _ ->
        io {
            _viewSyncPartnerState.postValue(false)
        }
    }

    private val saleForceStatusHandler = CoroutineExceptionHandler { _, exception ->
        io {
            val message = exception.message.orEmpty()
            _viewStatusSEState.postValue(
                StatusSEViewState.Failed(
                    message
                )
            )
        }
    }

    private val campaignsResultHandler = CoroutineExceptionHandler { _, _ ->
        io {
            _viewResultsLastCampaignState.postValue(
                ResultsLastCampaignViewState.Failure
            )
        }
    }
}
