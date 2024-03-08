package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.mapper

import biz.belcorp.salesforce.core.entities.dream.DreamCampaignEntity
import biz.belcorp.salesforce.core.entities.dream.DreamEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.dto.DreamConsultantDto
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamCampaign

class DreamMapper {

    fun map(dto: DreamConsultantDto): List<DreamEntity> =
        dto.dreamConsultantList.map { mapToEntity(it) }

    fun map(dreamsEntity: List<DreamEntity?>): List<Dream> =
        dreamsEntity.map { mapToDream(it) }
    private fun mapToDream(dreamEntity: DreamEntity?): Dream {
        return Dream(
            dreamId = dreamEntity?.dreamId,
            amountToComplete = dreamEntity?.amountToComplete,
            numberCampaignsToComplete = dreamEntity?.numberCampaignsToComplete,
            comment = dreamEntity?.comment,
            dream = dreamEntity?.dream,
            zone = dreamEntity?.zone,
            region = dreamEntity?.region,
            section = dreamEntity?.region,
            campaignCreated = dreamEntity?.campaignCreated,
            consultantCode = dreamEntity?.consultantCode,
            status = dreamEntity?.status,
            campaignEnd = dreamEntity?.campaignEnd,
            dateCompleted = dreamEntity?.dateCompleted,
            dateCreated = dreamEntity?.dateCreated,
            dateEdited = dreamEntity?.dateEdited,
            totalGain = dreamEntity?.totalGain,
            campaignList = mapCampaignListEntityToModel(dreamEntity?.campaignList!!)
        )
    }

    private fun mapCampaignListEntityToModel(campaignList : List<DreamCampaignEntity>): List<DreamCampaign> {
        return campaignList.map {
            mapCampaignWithProgressEntityToModel(it)
        }
    }

    private fun mapCampaignWithProgressEntityToModel(campaignWithProgressEntity: DreamCampaignEntity): DreamCampaign {
        return DreamCampaign(campaign = campaignWithProgressEntity.campaign,
                gainAmount = campaignWithProgressEntity.gainAmount
            )
    }

    private fun mapToEntity(dream: DreamConsultantDto.DreamConsultant): DreamEntity =
        DreamEntity(
            dreamId = dream.dreamId,
            consultantCode = dream.consultantCode,
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

    private fun mapCampaignListToEntity(list: List<DreamConsultantDto.DreamConsultant.ProgressByCampaigns.ConsultantSale>): List<DreamCampaignEntity> {
        return list.map {
                mapCampaignWithProgressToDreamCampaignEntity(it)
        }
    }

    private fun mapCampaignWithProgressToDreamCampaignEntity(campaignWithProgress: DreamConsultantDto.DreamConsultant.ProgressByCampaigns.ConsultantSale): DreamCampaignEntity {
        return DreamCampaignEntity(
            campaign = campaignWithProgress.campaign,
            gainAmount = campaignWithProgress.gainAmount)
    }
}
