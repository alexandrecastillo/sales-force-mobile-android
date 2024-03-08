package biz.belcorp.salesforce.modules.brightpath.features.container.detail

import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.modules.brightpath.features.utils.doPrefixWithShortCampaignName


class BrightPathIndicatorDetailPresenter(private val getSessionUseCase: ObtenerSesionUseCase) {

    private val session by lazy { getSessionUseCase.obtener() }

    private var view: BrightPathIndicatorDetailView? = null

    private var uaSegmentSelected = EMPTY_STRING
    private var levelSelected = EMPTY_STRING
    private var typeSelection = Constant.UNO_NEGATIVO

    fun create(view: BrightPathIndicatorDetailView) {
        this.view = view
    }

    fun initViews() {
        showHeader()
        showUaSegmentsView()
        showConstancyFilter()
        showConsultantsView()
    }

    fun getUaSegmentSelected() = uaSegmentSelected

    fun setUaIdSegmentSelected(uaIdSegmentSelected: String) {
        this.uaSegmentSelected = uaIdSegmentSelected
    }

    fun setTypeSelection(typeSelection: Int) {
        this.typeSelection = typeSelection
    }

    fun getTypeSelection(): Int {
        return this.typeSelection
    }

    fun checkEndPeriodTitle() {
        session?.campaign?.codigo?.let { cmpCode ->

            if (cmpCode.trim().isEmpty()) {
                view?.showEndPeriodTitle(END_PERIOD_DEFAULT_TITLE)
            } else {
                val intCampaign = cmpCode.substring((cmpCode.length - 2), (cmpCode.length))

                try {

                    var pastPeriod = -1

                    when (intCampaign.toInt()) {
                        3, 4, 5, 6, 7, 8 -> {
                            pastPeriod = 3
                        }

                        9, 10, 11, 12, 13, 14 -> {
                            pastPeriod = 1
                        }

                        15, 16, 17, 18, 1, 2 -> {
                            pastPeriod = 2
                        }
                    }

                    if (pastPeriod != -1) {
                        view?.showEndPeriodTitle("$END_PERIOD_DEFAULT_TITLE $pastPeriod")
                    } else {
                        view?.showEndPeriodTitle(END_PERIOD_DEFAULT_TITLE)
                    }
                } catch (ex: Exception) {
                    view?.showEndPeriodTitle(END_PERIOD_DEFAULT_TITLE)
                }
            } ?: run {
                view?.showEndPeriodTitle(END_PERIOD_DEFAULT_TITLE)
            }
        }
    }

    private fun showHeader() {
        doForCampaignAndPeriod()
        view?.showHeader()
    }

    private fun showUaSegmentsView() {
        val rol = getSessionUseCase.obtener().rol

        if (rol.isGz()) view?.showUaSegment()
    }

    private fun showConstancyFilter() {
        view?.showConsultancyFilter()
    }

    private fun showConsultantsView() {
        view?.showBeautyConsultantListView()
    }

    private fun doForCampaignAndPeriod() {
        val shortCampaignName = session.campaign.nombreCorto
        val period = session.campaign.periodo ?: Campania.Periodo.FACTURACION
        view?.showCampaign(shortCampaignName.doPrefixWithShortCampaignName(period))
    }

    fun setLevelSelectedOnFilter(constancyNumber: String) {
        this.levelSelected = constancyNumber
    }

    fun getLevelSelectedOnFilter() = this.levelSelected

    fun getBeautyConsultants() {
        view?.getBeautyConsultantList(uaSegmentSelected, levelSelected, typeSelection)
    }

    companion object {
        private const val END_PERIOD_DEFAULT_TITLE = "CIERRE DE PERIODO"
    }
}
