package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiRules
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.ProfitIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.RetentionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.ProfitRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.RetentionRepository
import java.util.Date

class GetGainPartnerUseCase(
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

        val prevCampaign = getPreviousCampaign(uaKey)

        val params = getKpiParams(uaKey)
        collectionRepository.sync(params)
        val collectionList = getKpiCollection(uaKey, prevCampaign.codigo)

        capitalizacionRepository.sync(params)
        val capitalizationList = getKpiCapitalization(uaKey, prevCampaign.codigo)

        profitRepository.sync(params)
        val profitList = getKpiProfit(uaKey, prevCampaign.codigo)

        retentionRepository.sync(params)
        val retentionList = getKpiRetention(uaKey, prevCampaign.codigo)

        return CollectionContainer(
            collectionList,
            profitList,
            retentionList,
            capitalizationList,
            getConfiguration(uaKey).currencySymbol,
            prevCampaign.shortNameOnly,
            uaKey.roleAssociated,
            getSyncDate(),
            !uaKey.isEqual(session.llaveUA)
        )
    }

    private fun getKpiParams(uaKey: LlaveUA): KpiQueryParams {

        val flags = Flags()
        val campaigns = campaignRepository.obtenerCampaniasSincrono(session.llaveUA)
            .take(KpiRules.MAXIMUM_CAMPAIGNS)

        return KpiQueryParams(
            country = session.countryIso,
            campaign = campaigns.map { it.codigo },
            profile = Rol.SOCIA_EMPRESARIA.codigoRol,
            region = uaKey.codigoRegion.orEmpty().deleteHyphen(),
            zone = uaKey.codigoZona.orEmpty().deleteHyphen(),
            section = uaKey.codigoSeccion.orEmpty().deleteHyphen(),
            showRegions = flags.showRegions,
            showZones = flags.showZones,
            showSection = flags.showSections
        )

    }

    private inner class Flags(
        val showRegions: Boolean = false,
        val showZones: Boolean = false,
        val showSections: Boolean = false
    )

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
    ): RetentionIndicator {
        val list = retentionRepository.getRetentionByCampaigns(uaKey, listOf(prevCampaign))
        return list.first()
    }

    suspend fun getConfiguration(uaKey: LlaveUA) = configurationRepository.getConfiguration(uaKey)

    private fun getSyncDate() = Date(collectionRepository.profitSyncDate())

    private fun getPreviousCampaign(uaKey: LlaveUA) =
        requireNotNull(campaignRepository.obtenerCampaniaInmediataAnterior(uaKey))

}
