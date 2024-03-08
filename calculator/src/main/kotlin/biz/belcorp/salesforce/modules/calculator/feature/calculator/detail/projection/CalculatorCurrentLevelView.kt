package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraDetalleConcursoVariableSociaModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijoModel
import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel
import biz.belcorp.salesforce.modules.calculator.model.SocialBonusModel

interface CalculatorCurrentLevelView {
    fun showCampaign(campaign: String?)
    fun showBonusSE(list: List<SocialBonusModel>)
    fun showDetailCompetitionSE(list: List<CalculadoraDetalleConcursoVariableSociaModel>)
    fun showStatus(successful: Boolean)
    fun showSalesGoal(metaVenta: String)
    fun showOrderGoal(metaPedido: String)
    fun hideGoal()
    fun showWarningMessageOrdersOrSalesGoal(isOrder: Boolean)
    fun showActiveConsultants(numeroActivasIniciales: Int)
    fun onBono(bonoSocia: SocialBonusModel)
    fun onDetalleConcurso(detalleConcursoVariable: CalculadoraDetalleConcursoVariableSociaModel)
    fun showBonusResult(result: CalculatingResultModel)
    fun showRecoveryGoalPercentage(percentage: Double?)
    fun showLevelChangeCard(change: Boolean, previusCampaign: String?, bonoCambioNivel: Double?)
    fun showContestDependencyAlert(type: String)
    fun showCalculationError()
    fun showDynamicInputsGoal(list: List<CalculadoraMontoFijoModel>)
    fun showStaticInputsGoal()
    fun setStaticViews()
    fun setDynamicViews()
    fun setViewHideIconInformation()
    fun actionCalculateProfitExtraDynamic()
    fun actionCalculateProfitExtraStatic()
    fun setDynamicListeners()
    fun setStaticListeners()
    fun openDialogInformation(list: List<CalculadoraMontoFijoModel>, sesion: Sesion, symbol: String?)
    fun showCurrencySymbol(symbol: String?)
}
