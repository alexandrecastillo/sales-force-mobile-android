package biz.belcorp.salesforce.modules.digital.core.data.repository.digital.mappers

import biz.belcorp.salesforce.core.entities.digital.DigitalInfoEntity
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.dto.DigitalDto
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalInfo

class DigitalDataMapper {

    fun map(entity: DigitalInfoEntity): DigitalInfo {
        return DigitalInfo(
            region = entity.region,
            zone = entity.zone,
            section = entity.section,
            campaign = entity.campaign,
            actives = entity.actives,
            subscribed = entity.subscribed,
            share = entity.share,
            buy = entity.buy,
            subscribedActivesRatio = entity.subscribedActivesRatio,
            shareActivesRatio = entity.shareActivesRatio,
            shareSubscribedRatio = entity.shareSubscribedRatio,
            buyActivesRatio = entity.buyActivesRatio,
            buySubscribedRatio = entity.buySubscribedRatio
        )
    }

    fun map(entity: DigitalDto): List<DigitalInfoEntity> {
        return entity.info.map { map(it) }
    }

    private fun map(entity: DigitalDto.Digital): DigitalInfoEntity {
        return DigitalInfoEntity(
            campaign = entity.campaign,
            region = entity.region,
            zone = entity.zone,
            section = entity.section,
            profile = entity.profile,
            actives = entity.actives,
            subscribed = entity.subscribed,
            share = entity.share,
            buy = entity.buy,
            subscribedActivesRatio = entity.subscribedActivesRatio,
            shareActivesRatio = entity.shareActivesRatio,
            shareSubscribedRatio = entity.shareSubscribedRatio,
            buyActivesRatio = entity.buyActivesRatio,
            buySubscribedRatio = entity.buySubscribedRatio
        )
    }

}
