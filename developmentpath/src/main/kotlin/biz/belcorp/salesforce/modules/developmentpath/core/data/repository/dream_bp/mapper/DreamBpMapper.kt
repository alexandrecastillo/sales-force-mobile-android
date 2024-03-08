package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.mapper

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.dreambp.DreamBpCampaignEntity
import biz.belcorp.salesforce.core.entities.dreambp.DreamBpEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.getbpdreams.DreamBpDto
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamCampaign

class DreamBpMapper {

    fun map(dto: DreamBpDto): List<DreamBpEntity> =
        dto.dreamBpList.map { mapToEntity(it) }

    fun map(dreamsEntity: List<DreamBpEntity?>): List<DreamBusinessPartner> =
        dreamsEntity.map { mapToDreamBusinessPartner(it) }

    private fun mapToDreamBusinessPartner(dreamBpEntity: DreamBpEntity?): DreamBusinessPartner =
        DreamBusinessPartner(
            dreamId = dreamBpEntity?.dreamId,
            amountToComplete = dreamBpEntity?.amountToComplete,
            numberCampaignsToComplete = dreamBpEntity?.numberCampaignsToComplete,
            comment = dreamBpEntity?.comment,
            dream = dreamBpEntity?.dream,
            zone = dreamBpEntity?.zone,
            region = dreamBpEntity?.region,
            section = dreamBpEntity?.region,
            campaignCreated = dreamBpEntity?.campaignCreated,
            bpCode = dreamBpEntity?.bpCode,
            status = dreamBpEntity?.status,
            campaignEnd = dreamBpEntity?.campaignEnd,
            dateCompleted = dreamBpEntity?.dateCompleted,
            dateCreated = dreamBpEntity?.dateCreated,
            dateEdited = dreamBpEntity?.dateEdited,
            totalGain = dreamBpEntity?.totalGain,
            campaignList = mapCampaignListEntityToModel(dreamBpEntity?.campaignList!!),
            bpName = Constant.EMPTY_STRING,
            currencySymbol = Constant.EMPTY_STRING,
        )


    private fun mapCampaignListEntityToModel(campaignList: List<DreamBpCampaignEntity>):
        List<DreamCampaign> {
        return campaignList.map {
            mapCampaignWithProgressEntityToModel(it)
        }
    }

    private fun mapCampaignWithProgressEntityToModel(
        campaignWithProgressEntity:
        DreamBpCampaignEntity
    ): DreamCampaign {
        return DreamCampaign(
            campaign = campaignWithProgressEntity.campaign,
            // this field remains with name gainAmount to avoid troubles with DB
            gainAmount = campaignWithProgressEntity.gainAmount
        )
    }

    private fun mapToEntity(dream: DreamBpDto.DreamBp): DreamBpEntity =
        DreamBpEntity(
            dreamId = dream.dreamId,
            bpCode = dream.bpCode,
            amountToComplete = dream.amountToComplete,
            numberCampaignsToComplete = dream.numberCampaignsToComplete,
            dream = dream.dream,
            comment = dream.comment,
            zone = dream.zone,
            section = dream.section,
            region = dream.region,
            campaignCreated = dream.campaignCreated,
            status = dream.status,
            totalGain = dream.progressByCampaigns.totalGain,
            campaignEnd = dream.campaignEnd
        ).apply {
            this.campaignList.addAll(mapCampaignListToEntity(dream.progressByCampaigns.list))
        }

    private fun mapCampaignListToEntity(list: List<DreamBpDto.DreamBp.ProgressByCampaigns.BpSale>):
        List<DreamBpCampaignEntity> {
        return list.map {
            mapCampaignWithProgressToDreamCampaignEntity(it)
        }
    }

    private fun mapCampaignWithProgressToDreamCampaignEntity(
        campaignWithProgress: DreamBpDto.DreamBp.ProgressByCampaigns.BpSale
    )
        : DreamBpCampaignEntity {
        return DreamBpCampaignEntity(
            campaign = campaignWithProgress.campaign,
            // this field remains with name gainAmount to avoid troubles with DB
            gainAmount = campaignWithProgress.total
        )
    }
}
