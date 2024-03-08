package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.dashboard

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.ConfigurationRules
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.core.utils.isNone
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiRules
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.NewCycleRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.SaleOrdersRepository
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiDashboardParams

class KpiDashboardUseCase(
    private val saleOrdersRepository: SaleOrdersRepository,
    private val collectionRepository: CollectionRepository,
    private val newCycleRepository: NewCycleRepository,
    private val capitalizationRepository: CapitalizationRepository,
    private val configurationRepository: ConfigurationRepository,
    private val sessionRepository: SessionRepository,
    private val campaignRepository: CampaniasRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    private val campaigns get() = getCampaigns(session.llaveUA)

    suspend fun getKpiInformation(params: KpiDashboardParams): KpiContainer {

        val ua = getUaKey(params.uaKey)
        val campaignsCodes = campaigns.map { it.codigo }

        val saleOrders = getKpiSaleOrders(ua, campaignsCodes)
        val collections = getKpiCollection(ua, campaignsCodes)
        val newCycles = getKpiNewsCycle(ua, campaignsCodes)
        val capitalization = getKpiCapitalization(ua, campaignsCodes)
        val currentRole = getCurrentRole(params.role)
        val currentPerson = getCurrentPerson(currentRole, params.person)

        return KpiContainer(
            saleOrders,
            collections,
            newCycles,
            capitalization,
            getConfiguration(ua),
            isBright(currentPerson),
            currentRole,
            currentPerson,
            campaigns.first(),
            currentRole != session.rol,
            session.rol
        )
    }

    private suspend fun getKpiSaleOrders(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): KpiData<SaleOrdersIndicator> {
        val list = saleOrdersRepository.getSalesOrdersByCampaigns(uaKey, campaigns)
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, campaigns.first(), campaigns.last())
    }

    private suspend fun getKpiCollection(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): KpiData<CollectionIndicator> {
        val prevCampaign = campaigns.last()
        val list = collectionRepository.getCollectionByCampaigns(uaKey, listOf(prevCampaign))
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, campaigns.first(), campaigns.last())
    }

    private suspend fun getKpiNewsCycle(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): KpiData<NewCycleIndicator> {
        val list = newCycleRepository.getNewCycleByCampaigns(uaKey, campaigns)
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, campaigns.first(), campaigns.last())
    }

    private suspend fun getKpiCapitalization(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): KpiData<CapitalizationIndicator> {
        val list = capitalizationRepository.getKpiDataByCampaignsAndUa(uaKey, campaigns)
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, campaigns.first(), campaigns.last())
    }

    private fun getUaKey(uaKey: LlaveUA?) = uaKey ?: session.llaveUA

    private fun getCurrentRole(role: Rol) = if (role.isNone()) session.rol else role

    private fun getCurrentPerson(role: Rol, person: Person?): Person? {
        return if (role != session.rol) person
        else person ?: session.person
    }

    private suspend fun getConfiguration(uaKey: LlaveUA): Configuration {
        return configurationRepository.getConfiguration(uaKey)
    }

    private fun getCampaigns(uaKey: LlaveUA): List<Campania> {
        return campaignRepository.obtenerCampaniasSincrono(uaKey.formatHyphenIfNull())
            .take(KpiRules.MAXIMUM_CAMPAIGNS)
    }

    private fun isBright(person: Person?): Boolean {
        if (person == null) return false
        val levels = ConfigurationRules.getBrightLevelKpi(session.pais)
        return when (person) {
            is BusinessPartner -> person.levelType in levels
            else -> false
        }
    }

}
