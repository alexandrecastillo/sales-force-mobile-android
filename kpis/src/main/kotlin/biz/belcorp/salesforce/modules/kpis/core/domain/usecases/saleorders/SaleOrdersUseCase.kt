package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.configuration.ConfigurationRules
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrderContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrderMultiProfileContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository

class SaleOrdersUseCase(
    private val saleOrdersRepository: SaleOrdersRepository,
    private val campaignsRepository: CampaniasRepository,
    private val sessionManager: SessionRepository,
    private val configurationRepository: ConfigurationRepository,
    private val businessPartnerRepository: BusinessPartnerRepository
) {
    private val session by lazy { requireNotNull(sessionManager.getSession()) }
    private val campaign by lazy { getCurrentCampaign() }
    private val previousCampaign by lazy { getLastCampaign() }

    suspend fun getSaleOrderMultiProfile(uaKey: LlaveUA?): SaleOrderMultiProfileContainer {
        val ua = getUaKey(uaKey)
        val isBilling = CampaignRules.isBilling(campaign)
        val configuration = configurationRepository.getConfiguration(ua)
        val saleOrders = getSaleOrderDataMultiProfile(ua, isBilling)
        return SaleOrderMultiProfileContainer(saleOrders, configuration.currencySymbol, isBilling)
    }

    suspend fun getSaleOrder(uaKey: LlaveUA): SaleOrderContainer {
        val isBilling = CampaignRules.isBilling(campaign)
        val configuration = configurationRepository.getConfiguration(uaKey)
        val saleOrders = getSaleOrderData(uaKey, isBilling)
        return SaleOrderContainer(
            saleOrders,
            configuration.currencySymbol,
            isBilling,
            configuration.isPdv && isBright(uaKey),
            !isSameUser(uaKey)
        )
    }

    private suspend fun getSaleOrderData(uaKey: LlaveUA, isBilling: Boolean): SaleOrdersIndicator {
        val campaigns = listOf(getCampaignCode(isBilling))
        val kpiData = saleOrdersRepository.getSalesOrdersByCampaigns(uaKey, campaigns)
        return kpiData.firstOrNull() ?: SaleOrdersIndicator()
    }

    private suspend fun getSaleOrderDataMultiProfile(
        uaKey: LlaveUA,
        isBilling: Boolean
    ): KpiData<SaleOrdersIndicator> {
        val campaigns = getCampaignsCodes(isBilling)
        val list = saleOrdersRepository.getSalesOrdersByCampaigns(uaKey, campaigns)
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, campaign.codigo, previousCampaign.codigo)
    }

    private fun getCampaignsCodes(isBilling: Boolean): List<String> {
        return if (isBilling) listOf(getCampaignCode(isBilling))
        else listOf(previousCampaign.codigo, campaign.codigo)
    }

    private fun getCampaignCode(isBilling: Boolean) =
        if (isBilling) campaign.codigo else previousCampaign.codigo

    private fun getCurrentCampaign() =
        requireNotNull(campaignsRepository.obtenerCampaniaActual(session.llaveUA))

    private fun getLastCampaign() =
        requireNotNull(campaignsRepository.obtenerCampaniaInmediataAnterior(session.llaveUA))

    private fun isSameUser(uaKey: LlaveUA) = uaKey.isEqual(session.llaveUA)

    private suspend fun getPerson(uaKey: LlaveUA): Person {
        return if (isSameUser(uaKey)) session.person
        else businessPartnerRepository.getBusinessPartner(uaKey)
    }

    private suspend fun isBright(uaKey: LlaveUA): Boolean {
        val levels = ConfigurationRules.getBrightLevelKpi(session.pais)
        return when (val person = getPerson(uaKey)) {
            is BusinessPartner -> person.levelType in levels
            else -> false
        }
    }

    private fun getUaKey(uaKey: LlaveUA?) = uaKey ?: session.llaveUA
}
