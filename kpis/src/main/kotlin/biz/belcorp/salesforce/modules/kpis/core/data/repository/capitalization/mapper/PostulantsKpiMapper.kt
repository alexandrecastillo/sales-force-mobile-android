package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper

import biz.belcorp.salesforce.core.entities.capitalization.PostulantKpiEntity
import biz.belcorp.salesforce.core.utils.SingleMapper
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.PostulantsKpiDto
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.PostulantKpi

class PostulantsKpiMapper : SingleMapper<PostulantsKpiDto.PostulantKpi, PostulantKpiEntity>() {
    override fun map(value: PostulantsKpiDto.PostulantKpi) =
        PostulantKpiEntity(
            currentCampaign = value.currentCampaign,
            zone = value.zone.orEmpty(),
            region = value.region.orEmpty(),
            section = value.section.orEmpty(),
            name = value.name,
            inEvaluation = value.inEvaluation.zeroIfNull(),
            preApproved = value.preApproved.zeroIfNull(),
            approved = value.approved.zeroIfNull(),
            rejected = value.rejected.zeroIfNull(),
            conversion = value.conversion.zeroIfNull(),
            daysOnHold = value.daysOnHold.zeroIfNull(),
            anticipatedEntries = value.anticipatedIncomes.zeroIfNull(),
            preRegistered = value.preRegistered.zeroIfNull()
        )

    fun map(entity: PostulantKpiEntity) =
        PostulantKpi(
            currentCampaign = entity.currentCampaign,
            preApproved = entity.preApproved,
            approved = entity.approved,
            inEvaluation = entity.inEvaluation,
            anticipatedEntries = entity.anticipatedEntries
        )
}
