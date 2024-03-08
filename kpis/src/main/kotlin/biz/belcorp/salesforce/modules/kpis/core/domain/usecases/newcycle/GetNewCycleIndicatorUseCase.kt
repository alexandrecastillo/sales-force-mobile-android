package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.BonusInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleGridContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicatorContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.BonificationRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.NewCycleRepository

class GetNewCycleIndicatorUseCase(
    private val newCycleRepository: NewCycleRepository,
    private val campaignsRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository,
    private val configurationRepository: ConfigurationRepository,
    private val bonificationRepository: BonificationRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }
    private val campaign by lazy { getCurrentCampaign() }
    private val previousCampaign by lazy { getLastCampaign() }

    suspend fun getNewCycleIndicator(uaKey: LlaveUA): NewCycleIndicatorContainer {
        val isBilling = CampaignRules.isBilling(session.campaign)
        val campaigns = getCampaigns(isBilling).map { it.codigo }
        return NewCycleIndicatorContainer(
            listIndicators = getNewCycleData(uaKey, campaigns),
            currencySymbol = configurationRepository.getConfiguration(uaKey).currencySymbol,
            isBilling = isBilling,
            role = session.rol,
            currentCampaignNameOnly = campaign.shortNameOnly,
            previousCampaignNameOnly = previousCampaign.shortNameOnly,
            bonusInfo = getSocialBonus(uaKey),
            isThirdPerson = uaKey.roleAssociated != session.rol
        )
    }

    private suspend fun getNewCycleData(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): KpiData<NewCycleIndicator> {
        val list = newCycleRepository.getNewCycleByCampaigns(uaKey, campaigns)
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, campaign.codigo, previousCampaign.codigo)
    }

    private suspend fun getSocialBonus(uaKey: LlaveUA): BonusInfo {
        return bonificationRepository.getBonusInfo(uaKey, campaign.codigo)
    }

    suspend fun getNewCycleGridData(uaKey: LlaveUA): NewCycleGridContainer {
        val isBilling = CampaignRules.isBilling(session.campaign)
        val campaigns = getCampaigns(isBilling).map { it.codigo }
        val childIndicatorList = newCycleRepository.getNewCycleListByParent(uaKey, campaigns)
        return NewCycleGridContainer(
            childIndicatorList = childIndicatorList,
            isBilling = isBilling,
            role = uaKey.roleAssociated,
            isParent = uaKey.isEqual(session.llaveUA)
        )
    }

    private fun getCampaigns(isBilling: Boolean): List<Campania> {
        return if (isBilling) listOf(campaign, previousCampaign) else listOf(previousCampaign)
    }

    private fun getCurrentCampaign() =
        requireNotNull(campaignsRepository.obtenerCampaniaActual(session.llaveUA))

    private fun getLastCampaign() =
        requireNotNull(campaignsRepository.obtenerCampaniaInmediataAnterior(session.llaveUA))

}
