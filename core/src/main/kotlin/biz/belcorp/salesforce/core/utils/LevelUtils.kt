package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.data.repository.businesspartners.mappers.BusinessPartnerMapper
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner

fun String.toLevelType(): BusinessPartner.Level {
    return when {
        startsWith(BusinessPartnerMapper.BRONZE, true) -> BusinessPartner.Level.Bronze
        startsWith(BusinessPartnerMapper.PRE_BRONZE, true) -> BusinessPartner.Level.PreBronze
        startsWith(BusinessPartnerMapper.GOLDEN, true) -> BusinessPartner.Level.Golden
        startsWith(BusinessPartnerMapper.SILVER, true) -> BusinessPartner.Level.Silver
        startsWith(BusinessPartnerMapper.PLATINUM, true) -> BusinessPartner.Level.Platinum
        startsWith(BusinessPartnerMapper.DIAMOND, true) -> BusinessPartner.Level.Diamond
        else -> BusinessPartner.Level.None
    }
}
