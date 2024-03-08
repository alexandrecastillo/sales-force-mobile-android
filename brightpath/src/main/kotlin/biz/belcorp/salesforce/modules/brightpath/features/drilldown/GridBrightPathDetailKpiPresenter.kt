package biz.belcorp.salesforce.modules.brightpath.features.drilldown

import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.DrillDownUseCase
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.utils.GridBuilder


class GridBrightPathDetailKpiPresenter(
    private val gridBuilder: GridBuilder,
    private val sessionUseCase: ObtenerSesionUseCase,
    private val useCase: DrillDownUseCase,
    private val configPreferences: ConfigSharedPreferences
) : BasePresenter() {

    private val session get() = requireNotNull(sessionUseCase.obtener())

    private var view: GridView? = null

    var uaSelected = EMPTY_STRING

    private var campaign = EMPTY_STRING


    fun create(view: GridView) {
        this.view = view
    }

    fun destroy() {
        view = null
        useCase.dispose()
    }

    fun getDrillDownData() {
        if (uaSelected.isNotBlank()) {
            getDetailedData()
        }
    }

    private fun getDetailedData() {
        getCampaignPeriod()
        handleGridDetailList()
    }


    private fun getCampaignPeriod() {
        useCase.getCampaignPeriod(CampaignDetailSubscriber())
    }

    private fun handleGridDetailList() {
        val grids = gridBuilder.build(configPreferences.flagHidePossibleChanges ?: false)
        view?.setupGridView(grids)
    }

    fun setListener() {
        if (session.rol.isGz()) view?.setListenerForGz()
    }

    fun showUaSegmentsView() {
        if (session.rol.isGz()) view?.drawUAListView()
    }

    fun handleViews() {
        getDetailedData()
    }

    inner class CampaignDetailSubscriber : BaseSingleObserver<Campania>() {
        override fun onSuccess(t: Campania) {
            super.onSuccess(t)
            campaign = t.nombreCorto
        }
    }

}
