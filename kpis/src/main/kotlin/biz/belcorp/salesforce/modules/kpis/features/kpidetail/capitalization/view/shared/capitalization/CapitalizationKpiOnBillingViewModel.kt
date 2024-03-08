package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization

import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.showKeyboard
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.mapper.CapitalizationKpiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.CapitalizationDetailKpiViewModel
import kotlinx.coroutines.launch

class CapitalizationKpiOnBillingViewModel(
    capitalizationUseCase: CapitalizationKpiUseCase,
    mapper: CapitalizationKpiMapper,
    private val configurationUseCase: ConfigurationUseCase
) : CapitalizationDetailKpiViewModel<CapitalizationKpiOnBillingViewState>(
    capitalizationUseCase,
    mapper
) {

    fun getCapitalizationData(ua: LlaveUA) {
        viewModelScope.launch(handler) {
            val kpiData = getCapitalizationKpiData(ua)
            val config = configurationUseCase.getConfiguration()
            val isSE = configurationUseCase.session.rol == Rol.SOCIA_EMPRESARIA
            val mappedData = mapper.mapBillingPeriodKpiData(kpiData, config.flagShowProactive, isSE)

            val state = CapitalizationKpiOnBillingViewState.Success(mappedData)
            _viewState.postValue(state)
        }
    }

    fun getFlagShowProactive() {
        viewModelScope.launch(handler) {
            val flagShowProactive = configurationUseCase.getConfiguration().flagShowProactive
            val isSE = configurationUseCase.session.rol == Rol.SOCIA_EMPRESARIA
            val countryIso = configurationUseCase.session.countryIso

            val state = CapitalizationKpiOnBillingViewState.showProactive((flagShowProactive && isSE), countryIso)
            _viewState.postValue(state)
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        io {
            _viewState.postValue(
                CapitalizationKpiOnBillingViewState.Failed(
                    exception.message.orEmpty()
                )
            )
        }
    }

}
