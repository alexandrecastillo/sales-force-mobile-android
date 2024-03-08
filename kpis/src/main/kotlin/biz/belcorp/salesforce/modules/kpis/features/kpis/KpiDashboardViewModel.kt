package biz.belcorp.salesforce.modules.kpis.features.kpis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.dashboard.KpiDashboardUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.KpiDashboardMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCapitalizationBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCapitalizationSaleModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiDashboardParams
import kotlinx.coroutines.launch
import kotlin.math.floor

class KpiDashboardViewModel(
    private val kpiDashboardUseCase: KpiDashboardUseCase,
    private val kpiDashboardMapper: KpiDashboardMapper,
    private val configurationUseCase: ConfigurationUseCase,
    private val capitalizationKpiUseCase: CapitalizationKpiUseCase
) : ViewModel() {

    val viewState: LiveData<KpiDashboardViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<KpiDashboardViewState>()

    fun getKpiInformation(params: KpiDashboardParams) {
        viewModelScope.launch(handler) {
            io {

                val isBilling = CampaignRules.isBilling(
                    configurationUseCase.session.campaign,
                    useFirstDayFlag = false
                )
                val config = configurationUseCase.getConfiguration()
                val kapi = capitalizationKpiUseCase.getKpiData(configurationUseCase.session.llaveUA)
                val kpiContainer = kpiDashboardUseCase.getKpiInformation(params)
                val data = kpiDashboardMapper.map(kpiContainer)

                if (isBilling && config.flagShowProactive && configurationUseCase.session.rol == Rol.SOCIA_EMPRESARIA) {
                    val identifier = "Retención y Capi"

                    data.kpis.forEach { item ->
                        if (item.header.equals(identifier) && item is KpiCapitalizationBillingModel) {
                            val successEntries =
                                floor((kapi.capitalizationEntries - kapi.capitalizationProactive + (kapi.capitalizationProactive / 2)).toDouble())

                            val X = kapi.capitalizationEntries

                            val Y = if ((kapi.capitalizationEntriesGoal - successEntries) < 0)
                                0
                            else
                                (kapi.capitalizationEntriesGoal - successEntries).toInt()

                            (item as? KpiCapitalizationBillingModel)?.subtitleFirst = "Vas logrando"
                            (item as? KpiCapitalizationBillingModel)?.descriptionFirst =
                                "$X ingresos de los cuales ${kapi.capitalizationProactive} son proactivos\nPara lograr el exito te faltan $Y"
                            (item as? KpiCapitalizationBillingModel)?.subtitleSecond = ""

                        }
                    }
                }

                _viewState.postValue(KpiDashboardViewState.Success(data))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
