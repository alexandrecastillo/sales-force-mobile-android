package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.saleforcestatus.GetSaleForceStatusUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.mapper.CapitalizationKpiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.CapitalizationKpiOnSalesModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.mapper.CollectionDetailMapper
import kotlinx.coroutines.launch

class GainDetailSeViewModel(
    private val getGainUseCase: GetGainUseCase,
    private val collectionMapper: CollectionDetailMapper,
    private val getSaleForceStatusUseCase: GetSaleForceStatusUseCase,
    private val capitalizationUseCase: CapitalizationKpiUseCase,
    private val mapper: CapitalizationKpiMapper
) : ViewModel() {

    val viewState: LiveData<GainDetailSeViewState> get() = _viewState
    private val _viewState = MutableLiveData<GainDetailSeViewState>()

    val saleForceStatusViewState: LiveData<SaleForceStatusViewState> get() = _saleForceStatusViewState
    private val _saleForceStatusViewState = MutableLiveData<SaleForceStatusViewState>()

    val capitalizationStatusViewState: LiveData<CapitalizationKpiOnSalesModel> get() = _capitalizationStatusViewState
    private val _capitalizationStatusViewState = MutableLiveData<CapitalizationKpiOnSalesModel>()

    fun getGainSe(uaKey: LlaveUA, saleForceStatus : SaleForceStatus, capitalizationKpiOnSaleModel: CapitalizationKpiOnSalesModel) {
        viewModelScope.launch(handler) {
            io {
                val data = getGainUseCase.getGainInformation(uaKey)
                val model = collectionMapper.mapToCollection(data, saleForceStatus,capitalizationKpiOnSaleModel)
                _viewState.postValue(GainDetailSeViewState.Success(model))
            }
        }
    }

    fun getGainSeFromGz(uaKey: LlaveUA) {
        viewModelScope.launch(handler) {
            io {
                val data = getGainUseCase.getGainInformation(uaKey)
                val model = collectionMapper.mapToCollection(data)
                _viewState.postValue(GainDetailSeViewState.Success(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = GainDetailSeViewState.Failed(message)
        }
    }

    private val saleForceStatusHandler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _saleForceStatusViewState.value = SaleForceStatusViewState.Failed(message)
        }
    }



    fun loadSaleForceStatus() {
        viewModelScope.launch(saleForceStatusHandler) {
            io {
                getSaleForceStatusUseCase.saveSaleForceDataInPref()
                val model = getSaleForceStatusUseCase.obtener()
                _saleForceStatusViewState.postValue(SaleForceStatusViewState.Success(model))
            }
        }
    }

    //region Capitalization

    fun getCapitalizationData(ua: LlaveUA) {
        viewModelScope.launch {
            val kpiData = getCapitalizationKpiData(ua)
            val isParent = capitalizationUseCase.isParent(ua)
            val kpiDataModel = mapper.map(isParent, kpiData)

            val state = CapitalizationKpiDetailSeOnSalesViewState.Success(kpiDataModel)
            _capitalizationStatusViewState.postValue(state.data)
        }
    }

    private suspend fun getCapitalizationKpiData(ua: LlaveUA?): CapitalizationIndicator {
        return capitalizationUseCase.getKpiData(ua)
    }

    //endregion Capitalization

}
