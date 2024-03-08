package biz.belcorp.salesforce.modules.brightpath.features.container.consultants

import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.base.utils.isSe
import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.consultants.ConsultantsUseCase
import biz.belcorp.salesforce.modules.brightpath.features.container.consultants.header.ConsultantsHeaderDetailView
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.model.ConsultantHeaderDetailedKpiModel


class ConsultantsDetailPresenter(
    private val sessionUseCase: ObtenerSesionUseCase,
    private val consultantUseCase: ConsultantsUseCase
) : BasePresenter() {

    private val session by lazy { sessionUseCase.obtener() }

    private var shortCampaignName = session.campaign.nombreCorto

    private var detailedListView: ConsultantsDetailView? = null
    private var detailedHeaderView: ConsultantsHeaderDetailView? = null

    var prevUaSelected = EMPTY_STRING

    var model: ConsultantHeaderDetailedKpiModel? = null

    companion object {
        const val CAMPAIGN_ON_SALE = "VENTA"
        const val CAMPAIGN_ON_BILLING = "FACTURACIÓN"
    }

    fun create(
        consultantsDetailView: ConsultantsDetailView,
        headerView: ConsultantsHeaderDetailView
    ) {
        this.detailedListView = consultantsDetailView
        this.detailedHeaderView = headerView
    }

    fun destroy() {
        detailedListView = null
    }

    fun paintHeaderDescription() {
        handleRol()
        drawDetailedHeaderDescription()
        handleUAView()
    }

    private fun drawDetailedHeaderDescription() {
        handleCampaignDescription()
        drawCampaignHeaderDescription()
    }

    private fun drawCampaignHeaderDescription() {
        model?.apply {
            val title = title.orEmpty().replace("\n", Constant.BLANK_SPACE)
            detailedHeaderView?.showTitle(title)
            handlePossibleChangeLevel()
            handleDetailedConsultantListDescription()
        }
    }

    private fun handleCampaignDescription() {
        shortCampaignName.let {
            when (session.campaign.periodo) {
                Campania.Periodo.FACTURACION -> doForBillingPeriod(it)
                Campania.Periodo.VENTA -> doForSalesPeriod(it)
            }
        }
    }

    private fun handlePossibleChangeLevel() {
        model?.apply {
            doAsync {
                val quantityValue = if (id == Constants.ID_LIST_MAY_CHANGE_LEVEL) {
                    val quantity =
                        consultantUseCase.getConsultantsMayChangeLevelListSize().toString()
                    "$quantity ${Constants.CONSULTANTS}"
                } else {
                    consultantQuantity.orEmpty()
                }
                uiThread {
                    detailedHeaderView?.showConsultantsInf(quantityValue)
                }
            }
        }
    }

    private fun handleDetailedConsultantListDescription() {
        model?.apply {
            if (id == Constants.ID_LIST_MAY_CHANGE_LEVEL) {
                val desc = "$description $shortCampaignName"
                detailedHeaderView?.showDescriptionInf(desc)
            } else {
                detailedHeaderView?.showDescriptionInf(description.orEmpty())
            }
        }
    }

    private fun handleUAView() {
        if (session.rol.isGz()) {
            detailedListView?.showUaSegmentListPrevUaSelected()
        }
    }

    private fun handleRol() {
        session.rol.let {
            detailedListView?.apply {
                if (it.isGz()) {
                    defineGzFragment()
                } else if (it.isSe()) {
                    defineSeFragment()
                }
            }
        }
    }

    private fun doForSalesPeriod(shortCampaignName: String) {
        val fullCampaignInfo = parseToSalesCampaign(shortCampaignName)

        detailedHeaderView?.showCampaignInfo(fullCampaignInfo)
    }

    private fun doForBillingPeriod(shortCampaignName: String) {
        val fullCampaignInfo = parseToBillingCampaign(shortCampaignName)

        detailedHeaderView?.showCampaignInfo(fullCampaignInfo)
    }

    private fun parseToSalesCampaign(shotCampaignName: String) =
        "$CAMPAIGN_ON_SALE $shotCampaignName"

    private fun parseToBillingCampaign(shotCampaignName: String) =
        "$CAMPAIGN_ON_BILLING $shotCampaignName"

}
