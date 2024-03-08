package biz.belcorp.salesforce.core.data.repository.businesspartners.mappers

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NEGATIVE_NUMBER_ONE
import biz.belcorp.salesforce.core.data.repository.businesspartners.cloud.dto.BusinessPartnerDto
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner.Level
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.SuccessfuHistoricEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerEntity
import biz.belcorp.salesforce.core.utils.toQuadruple

class BusinessPartnerMapper {

    fun map(data: BusinessPartnerDto): List<BusinessPartnerEntity> {
        return data.businessPartners.map { map(it) }
    }

    private fun map(entity: BusinessPartnerDto.BusinessPartner): BusinessPartnerEntity {
        with(entity) {
            val businessPartner = BusinessPartnerEntity(
                partnerCode = code,
                region = region,
                zone = zone,
                section = section,
                document = personalInfo.document,
                name = personalInfo.fullName,
                firstName = personalInfo.firstName,
                secondName = personalInfo.secondName,
                firstSurname = personalInfo.firstSurname,
                secondSurname = personalInfo.secondSurname,
                birthDate = personalInfo.birthDate.orEmpty(),
                anniversaryDate = anniversaryDate.orEmpty(),
                levelCode = level.levelCode,
                level = level.levelName,
                email = personalInfo.email,
                cellphone = personalInfo.cellphone,
                homephone = personalInfo.homephone,
                address = personalInfo.address,
                billingFirstCampaign = billingInfo.billingFirstCampaign,
                billingLastCampaign = billingInfo.billingLastCampaign,
                campaignAdmission = campaignAdmission,
                productivity = productivity.orEmpty(),
                pendingDebt = pendingDebt,
                status = status.orEmpty(),
                leaderClassification = leaderClassification.orEmpty(),
                successful = successful
            )
            val successfulHistoric = successfulHistoric.map { map(it) }
            businessPartner.successfuHistoric.addAll(successfulHistoric)
            return businessPartner
        }
    }

    private fun map(item: BusinessPartnerDto.SuccessfulHistoric): SuccessfuHistoricEntity {
        return SuccessfuHistoricEntity(
            campaign = item.campaign,
            value = item.value
        )
    }

    fun map(entity: BusinessPartnerEntity?): Person {
        if (entity == null) return mapPerson()
        val name = entity.name.toQuadruple()
        return BusinessPartner(
            id = 0,
            code = entity.partnerCode,
            document = entity.document,
            firstName = if (entity.firstName.isEmpty()) name.first else entity.firstName,
            secondName = if (entity.secondName.isEmpty()) name.second else entity.secondName,
            firstSurname = if (entity.firstSurname.isEmpty()) name.third else entity.firstSurname,
            secondSurname = if (entity.secondSurname.isEmpty()) name.fourth else entity.secondSurname,
            birthDate = entity.birthDate,
            anniversaryDate = entity.anniversaryDate,
            levelCode = entity.levelCode,
            level = entity.level,
            status = entity.status,
            leaderClassification = entity.leaderClassification
        ).apply {
            uaKey =
                LlaveUA(entity.region, entity.zone, entity.section, NEGATIVE_NUMBER_ONE.toLong())
            levelType = getLevelType(level)
        }
    }

    private fun getLevelType(level: String) =
        when {
            level.startsWith(BRONZE, true) -> Level.Bronze
            level.startsWith(PRE_BRONZE, true) -> Level.PreBronze
            level.startsWith(GOLDEN, true) -> Level.Golden
            level.startsWith(SILVER, true) -> Level.Silver
            level.startsWith(PLATINUM, true) -> Level.Platinum
            level.startsWith(DIAMOND, true) -> Level.Diamond
            else -> Level.None
        }


    private fun mapPerson(): Person {
        return Person(
            NEGATIVE_NUMBER_ONE.toLong(),
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING
        )
    }

    companion object {
        const val PRE_BRONZE = "PRE-BRONCE"
        const val BRONZE = "BRONCE"
        const val GOLDEN = "ORO"
        const val SILVER = "PLATA"
        const val PLATINUM = "PLATINUM"
        const val DIAMOND = "DIAMANTE"
        const val NONE = "NINGUNA"
    }
}
