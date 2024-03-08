package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.ConfigurationRules
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectoryRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.GridUaInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersConsolidated
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository

class GridConsolidatedUseCase(
    private val sessionRepository: SessionRepository,
    private val managerDirectoryRepository: ManagerDirectoryRepository,
    private val uaInfoUseCase: UaInfoUseCase,
    private val configurationRepository: ConfigurationRepository,
    private val campaignRepository: CampaniasRepository,
    private val saleOrdersRepository: SaleOrdersRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getConsolidatedInfo(uaKey: LlaveUA?): SaleOrdersConsolidated {
        val person = getPerson(uaKey)
        val ua = uaKey ?: session.llaveUA
        val uas = getUas(ua)
        val gridUas = getGridUaInfo(uas, session.pais)
        val configuration = getConfiguration()
        val campaign = getCampaign()
        val saleOrders = getSaleOrderInfo(ua, campaign.codigo)
        return SaleOrdersConsolidated(campaign, gridUas, configuration, saleOrders, person.role)
            .apply { isParent = ua.isEqual(session.llaveUA) }
    }

    private suspend fun getUas(uaKey: LlaveUA): List<UaInfo> {
        return uaInfoUseCase.getAssociatedUaListByUaKey(uaKey)
    }

    private suspend fun getConfiguration(): Configuration {
        return configurationRepository.getConfiguration()
    }

    private suspend fun getSaleOrderInfo(
        uaKey: LlaveUA,
        campaignCode: String
    ): List<SaleOrdersIndicator> {
        return saleOrdersRepository.getSalesOrdersByParent(uaKey, campaignCode)
    }

    private fun getCurrentCampaign(uaKey: LlaveUA): Campania {
        return requireNotNull(campaignRepository.obtenerCampaniaActual(uaKey.formatHyphenIfNull()))
    }

    private fun getLastCampaign(uaKey: LlaveUA): Campania {
        return campaignRepository.obtenerCampaniaInmediataAnterior(uaKey.formatHyphenIfNull())
    }

    private fun getCampaign(): Campania {
        val currentCampaign = getCurrentCampaign(session.llaveUA)
        val isBilling = CampaignRules.isBilling(currentCampaign)
        return if (isBilling) currentCampaign else getLastCampaign(session.llaveUA)
    }

    private fun isBright(person: Person?, country: Pais?): Boolean {
        if (person == null) return false
        val levels = ConfigurationRules.getBrightLevelKpi(country)
        return when (person) {
            is BusinessPartner -> person.levelType in levels
            else -> false
        }
    }

    private fun getGridUaInfo(uaInfo: List<UaInfo>, country: Pais?): List<GridUaInfo> {
        return uaInfo.map { GridUaInfo(it, isBright(it.person, country)) }
    }

    private suspend fun getPerson(uaKey: LlaveUA?): Person {
        return if (uaKey == null) session.person
        else managerDirectoryRepository.getManager(uaKey)
    }

}
