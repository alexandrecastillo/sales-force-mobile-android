package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales

import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.mapper.CapitalizationKpiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.CapitalizationDetailKpiViewModel
import kotlinx.coroutines.launch

class CapitalizationKpiDetailSeOnSalesViewModel(
    capitalizationUseCase: CapitalizationKpiUseCase,
    mapper: CapitalizationKpiMapper
) : CapitalizationDetailKpiViewModel<CapitalizationKpiDetailSeOnSalesViewState>(
    capitalizationUseCase,
    mapper
) {

    fun getCapitalizationData(ua: LlaveUA) {
        viewModelScope.launch(handler) {
            val kpiData = getCapitalizationKpiData(ua)
            val isParent = capitalizationUseCase.isParent(ua)
            val kpiDataModel = mapper.map(isParent, kpiData)

            val state = CapitalizationKpiDetailSeOnSalesViewState.Success(kpiDataModel)
            _viewState.postValue(state)
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        io {
            _viewState.postValue(
                CapitalizationKpiDetailSeOnSalesViewState.Failed(
                    exception.message.orEmpty()
                )
            )
        }
    }

}
