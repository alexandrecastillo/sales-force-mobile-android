package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection

import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.*
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.ProfitRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.RetentionRepository
import java.util.*
import kotlin.collections.HashMap

class GetGainUseCase(
    private val campaignRepository: CampaniasRepository,
    private val sessionManager: SessionRepository,
    private val collectionRepository: CollectionRepository,
    private val profitRepository: ProfitRepository,
    private val retentionRepository: RetentionRepository,
    private val capitalizacionRepository: CapitalizationRepository,
    private val configurationRepository: ConfigurationRepository,
    private val uaInfoUseCase: UaInfoUseCase
) {

    private val session by lazy { requireNotNull(sessionManager.getSession()) }

    suspend fun getGainInformation(uaKey: LlaveUA): CollectionContainer {
        val ua = getUaKey(uaKey)
        val prevCampaign = getPreviousCampaign()
        val collectionList = getKpiCollection(ua, prevCampaign.codigo)
        val capitalizationList = getKpiCapitalization(ua, prevCampaign.codigo)
        val profitList = getKpiProfit(ua, prevCampaign.codigo)
        val retentionList = getKpiRetention(ua, prevCampaign.codigo)
        return CollectionContainer(
            collectionList,
            profitList,
            retentionList,
            capitalizationList,
            getConfiguration().currencySymbol,
            prevCampaign.shortNameOnly,
            uaKey.roleAssociated,
            getSyncDate(),
            !uaKey.isEqual(session.llaveUA)
        )
    }

    private suspend fun getKpiCollection(
        uaKey: LlaveUA,
        prevCampaign: String
    ): KpiData<CollectionIndicator> {
        val list = collectionRepository.getCollectionByCampaigns(uaKey, listOf(prevCampaign))
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, previousCampaign = prevCampaign)
    }

    private suspend fun getKpiCapitalization(
        uaKey: LlaveUA,
        prevCampaign: String
    ): KpiData<CapitalizationIndicator> {
        val list = capitalizacionRepository.getKpiDataByCampaignsAndUa(uaKey, listOf(prevCampaign))
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, previousCampaign = prevCampaign)
    }

    private suspend fun getKpiProfit(
        uaKey: LlaveUA,
        prevCampaign: String
    ): KpiData<ProfitIndicator> {
        val list = profitRepository.getProfitByCampaigns(uaKey, listOf(prevCampaign))
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, previousCampaign = prevCampaign)
    }

    private suspend fun getKpiRetention(
        uaKey: LlaveUA,
        prevCampaign: String
    ): RetentionIndicator{
        val list = retentionRepository.getRetentionByCampaigns(uaKey, listOf(prevCampaign))
        return list.first()
    }

    suspend fun getConfiguration() = configurationRepository.getConfiguration(session.llaveUA)

    private fun getUaKey(uaKey: LlaveUA?) = uaKey ?: session.llaveUA

    private fun getSyncDate() = Date(collectionRepository.profitSyncDate())

    private fun getPreviousCampaign() =
        requireNotNull(campaignRepository.obtenerCampaniaInmediataAnterior(session.llaveUA))

    suspend fun getUaData(uaKey: LlaveUA, days: String): CollectionConsolidated {
        val prevCampaign = getPreviousCampaign()
        val collectionList = getCollectionsByParent(uaKey, prevCampaign.codigo, days)
        val uaList = getUas(uaKey)
        return CollectionConsolidated(
            collectionList = collectionList,
            uaList = uaList,
            currencySymbol = getConfiguration().currencySymbol,
            campaign = prevCampaign.codigo,
            role = uaKey.roleAssociated,
            isThirdPerson = !uaKey.isEqual(session.llaveUA)
        )
    }

    private suspend fun getUas(uaKey: LlaveUA): List<UaInfo> {
        return uaInfoUseCase.getAssociatedUaListByUaKey(uaKey)
    }

    private suspend fun getCollectionsByParent(
        uaKey: LlaveUA,
        campaign: String,
        days: String
    ): List<CollectionIndicator> {
        return collectionRepository.getCollectionsByParent(uaKey, campaign, days)
    }

}
