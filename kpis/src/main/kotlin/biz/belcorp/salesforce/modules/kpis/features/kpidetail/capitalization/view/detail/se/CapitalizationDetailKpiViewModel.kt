package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.mapper.CapitalizationKpiMapper

abstract class CapitalizationDetailKpiViewModel<VS>(
    protected val capitalizationUseCase: CapitalizationKpiUseCase,
    protected val mapper: CapitalizationKpiMapper
) : ViewModel() {

    val viewState: LiveData<VS>
        get() = _viewState

    protected val _viewState = MutableLiveData<VS>()

    protected suspend fun getCapitalizationKpiData(ua: LlaveUA?): CapitalizationIndicator {
        return capitalizationUseCase.getKpiData(ua)
    }

}
