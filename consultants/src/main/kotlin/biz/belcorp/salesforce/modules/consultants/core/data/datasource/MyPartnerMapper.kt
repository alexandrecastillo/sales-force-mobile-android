package biz.belcorp.salesforce.modules.consultants.core.data.datasource

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerBillingInfoEntity
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerEntity
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerLevelEntity
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerNextLevelEntity
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerNextLevelOrdersEntity
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerNextLevelSalesEntity
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerPersonalInfoEntity
import biz.belcorp.salesforce.core.entities.mypartners.MyPartnerSuccessfulHistoryEntity
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersDto

class MyPartnerMapper {

    fun map(
        original: MyPartnersDto,
    ): List<MyPartnerEntity> {
        return original.newBusinessPartner.map(::map)
    }

    private fun map(data: MyPartnersDto.NewBusinessPartner): MyPartnerEntity = with(data) {
        val entity = MyPartnerEntity(
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            consultantId = consultantId,
            consultantCode = consultantCode,
            anniversaryDate = anniversaryDate,
            admissionCampaign = admissionCampaign,
            pendingDebt = pendingDebt,
            productivity = productivity,
            isSuccessful = isSuccessful,
        )

        entity.level.add(map(level))
        entity.nextLevel.add(map(nextLevel))
        entity.personalInfo.add(map(personalInfo))
        entity.billingInfo.add(map(billingInfo))
        entity.successfulHistory.addAll(isSuccessfulHistory.map(::map))

        return entity
    }

    private fun map(data: MyPartnersDto.NewBusinessPartner.Level): MyPartnerLevelEntity = with(data) {
        return MyPartnerLevelEntity(
            code = code,
            name = name
        )
    }

    private fun map(data: MyPartnersDto.NewBusinessPartner.NextLevel): MyPartnerNextLevelEntity =
        with(data) {
            val nextLevel = MyPartnerNextLevelEntity(
                name = name ?: Constant.EMPTY_STRING,
                accomplished = accomplished ?: false,
                campaignsAccomplished = campaignsAccomplished ?: Constant.NUMERO_CERO,
            )


            nextLevel.sales.add(map(sales))
            nextLevel.orders.add(map(orders))


            return nextLevel
        }

    private fun map(data: MyPartnersDto.NewBusinessPartner.NextLevel.Sales?): MyPartnerNextLevelSalesEntity =
        with(data) {
            return MyPartnerNextLevelSalesEntity(
                requirement = this?.requirement ?: Constant.NUMERO_CERO,
                real = this?.real ?: Constant.ZERO_DECIMAL,
                accomplished = this?.accomplished ?: false,
            )
        }

    private fun map(data: MyPartnersDto.NewBusinessPartner.NextLevel.Orders?): MyPartnerNextLevelOrdersEntity =
        with(data) {
            return MyPartnerNextLevelOrdersEntity(
                requirement = this?.requirement ?: Constant.NUMERO_CERO,
                real = this?.real ?: Constant.NUMERO_CERO,
                accomplished = this?.accomplished ?: false,
            )
        }

    private fun map(data: MyPartnersDto.NewBusinessPartner.BillingInfo): MyPartnerBillingInfoEntity =
        with(data) {
            return MyPartnerBillingInfoEntity(
                firstCampaign = firstCampaign,
                lastCampaign = lastCampaign,
            )
        }

    private fun map(data: MyPartnersDto.NewBusinessPartner.IsSuccessfulHistory): MyPartnerSuccessfulHistoryEntity =
        with(data) {
            return MyPartnerSuccessfulHistoryEntity(
                campaign = campaign,
                value = value,
            )
        }

    private fun map(data: MyPartnersDto.NewBusinessPartner.PersonalInfo): MyPartnerPersonalInfoEntity =
        with(data) {
            return MyPartnerPersonalInfoEntity(
                fullName = fullName,
                firstName = firstName,
                surname = surname,
                secondName = secondName,
                secondSurname = secondSurname,
                documentNumber = documentNumber,
                email = email,
                address = address,
                cellphoneNumber = cellphoneNumber,
                telephoneNumber = telephoneNumber,
                birthday = birthday,
            )
        }


}
