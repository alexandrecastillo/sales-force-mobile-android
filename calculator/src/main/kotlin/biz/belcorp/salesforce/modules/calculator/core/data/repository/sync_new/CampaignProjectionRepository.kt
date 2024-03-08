package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionEntity
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.CampaignProjectionCloudDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.getprojection.CampaignProjectionConsultantParams
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.getprojection.CampaignProjectionConsultantQuery
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.saveprojection.SaveCampaignProjectionMutation
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.saveprojection.SaveCampaignProjectionParams
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.data.CampaignProjectionDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.mapper.CampaignProjectionMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.sync.SyncRepository

class CampaignProjectionRepository(
    private val campaignProjectionCloudStore: CampaignProjectionCloudDataStore,
    private val campaignProjectionDataStore: CampaignProjectionDataStore,
    private val campaignProjectionMapper: CampaignProjectionMapper,
) : SyncRepository {

    override suspend fun sync(uaKey: LlaveUA, country: String, campaign: String, phase: String, sectionPartner: String?) {
        val region = uaKey.codigoRegion?.deleteHyphen().orEmpty()
        val zone = uaKey.codigoZona?.deleteHyphen().orEmpty()
        val section = sectionPartner ?: uaKey.codigoSeccion?.deleteHyphen().orEmpty()
        val params =
            CampaignProjectionConsultantParams(country, region, zone, section, campaign, phase)
        val query = CampaignProjectionConsultantQuery(params)
        val response = campaignProjectionCloudStore.getCampaignProjectionInfo(query)
        val entity = campaignProjectionMapper.mapToEntity(response)
        campaignProjectionDataStore.save(entity)
    }

    override suspend fun getSavedProjectedCampaign(llaveUA: LlaveUA, sectionPartner: String?): CampaignProjectionEntity? =
        campaignProjectionDataStore.getSavedCampaignProjection(llaveUA, sectionPartner)

    override suspend fun saveProjectedCampaign(
        uaKey: LlaveUA,
        country: String,
        campaign: String,
        campaignProjectionInfoEntity: CampaignProjectionEntity,
        sectionPartner: String?
    ) {
        val campaignProjectionDto = campaignProjectionMapper.mapToDto(campaignProjectionInfoEntity)
        val region = uaKey.codigoRegion?.deleteHyphen().orEmpty()
        val zone = uaKey.codigoZona?.deleteHyphen().orEmpty()
        val section = sectionPartner ?: uaKey.codigoSeccion?.deleteHyphen().orEmpty()
        val params = SaveCampaignProjectionParams(
            country, region, zone, section, campaign,
            activityProjectedPercentage = campaignProjectionDto.activityProjectedPercentage,
            pegsProjectedNextCampaign = campaignProjectionDto.pegsProjectedNextCampaign,
            capitalization = campaignProjectionDto.capitalization,
            retention6D6 = campaignProjectionDto.retention6D6,
            orders = campaignProjectionDto.orders
        )
        val query = SaveCampaignProjectionMutation(params)
        val response = campaignProjectionCloudStore.saveCampaignProjectionInfo(query)
        val entity = campaignProjectionMapper.mapToEntity(response)
        campaignProjectionDataStore.save(entity)
    }

}
