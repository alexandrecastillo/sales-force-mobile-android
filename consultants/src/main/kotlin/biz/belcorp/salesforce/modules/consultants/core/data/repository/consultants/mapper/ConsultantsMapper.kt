package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.domain.entities.consultants.Consultant
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.digital.DigitalConsultantEntity
import biz.belcorp.salesforce.core.utils.SingleMapper
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.DigitalDto

class ConsultantsMapper : SingleMapper<ConsultantDto.Consultant, ConsultantEntity>() {

    private var countryIso = ""

    fun map(
        consultantList: List<ConsultantDto.Consultant>,
        digitalList: List<DigitalDto.OnlineStore>,
        country: String
    ): List<ConsultantEntity> {
        countryIso = country
        val consultants = map(consultantList)
        consultants.forEach { co ->
            digitalList.find { it.consultantCode == co.code }?.also {
                co.digitalInfo.add(map(it))
            }
        }
        return consultants
    }

    override fun map(value: ConsultantDto.Consultant) = ConsultantEntity(
        consultantId = value.id.zeroIfNull(),
        campaign = value.campaign,
        region = value.region,
        zone = value.zone,
        section = value.section,
        code = value.code.orEmpty(),
        checkDigit = value.personalInfo.checkDigit.trim(),
        multiMarca = value.multiMarca,
        name = value.personalInfo.fullName,
        firstName = value.personalInfo.firstName,
        surname = value.personalInfo.surname,
        secondName = value.personalInfo.secondName,
        secondSurname = value.personalInfo.secondSurname,
        document = value.personalInfo.documentNumber,
        address = value.personalInfo.address,
        addressReference = value.personalInfo.addressReference,
        phone = value.personalInfo.phone,
        email = value.personalInfo.email,
        birthday = value.personalInfo.birthday,
        anniversaryDate = value.anniversaryDate,
        campaignAdmission = value.campaignAdmission,
        constancyNew = value.constancy.new,
        constancyEstablished = value.constancy.established,
        constancyU6C = value.constancy.u6c,
        constancyU18C = value.constancy.u18c,
        shortSegmentCode = value.segment.code,
        segmentDescription = value.segment.description,
        brightSegmentCode = value.brightSegment.code,
        brightSegmentDescription = value.brightSegment.description,
        stateCode = value.state.code,
        stateDescription = value.state.description,
        isPeg = value.isPeg,
        isNew = value.isNew,
        isAuthorized = value.isAuthorized,
        isPotentialEntry = value.isPotentialEntry,
        isPotentialReentry = value.isPotentialReentry,
        billingFirstCampaign = value.billingInfo.firstCampaign,
        billingLastCampaign = value.billingInfo.lastCampaign,
        billingOrderStatus = value.billingInfo.orderStatus,
        catalogSales = value.salesInfo.catalogSale,
        orderRange = value.salesInfo.orderRange,
        isOrderSent = value.salesInfo.isOrderSent,
        pendingDebt = value.collectionInfo.pendingDebt,
        collectionPercentage = value.collectionInfo.percentage,
        reentriesU18C = value.reentriesU18C,
        orderAmount = value.billingInfo.amount,
        orderMtoAmount = value.billingInfo.orderMtoAmount,
        sbAmount = ZERO_DECIMAL,
        isNewInconstant = value.isNewInconstant,
        shareCatalog = value.digital.shareCatalog,
        suggestedMessage = value.digital.suggestedMessage,
        digitalSegment = if (value.isNew) {
            value.constancy.new
        } else if (listOf(CountryISO.COLOMBIA, CountryISO.ECUADOR, CountryISO.CHILE, CountryISO.COSTA_RICA, CountryISO.PERU).contains(countryIso)) {
            value.brightSegment.description
        } else {
            EMPTY_STRING
        },


        //value.digital.digitalSegment,
        hasCashPayment = value.hasCashPayment,
        lastModified = value.lastModified
    )

    fun map(entities: List<ConsultantEntity>, countryCode: String) =
        entities.map { map(it, countryCode) }

    fun map(entity: ConsultantEntity, countryCode: String) = with(entity) {
        Consultant(
            id = consultantId,
            name = name,
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            code = code,
            multiMarca = multiMarca,
            suggestedMessage = suggestedMessage,
            digitalSegment = digitalSegment,
            address = address,
            phone = phone,
            whatsApp = createWhatsApp(phone, countryCode),
            isPeg = isPeg,
            isNew = isNew,
            shortSegmentCode = shortSegmentCode,
            segmentDescription = segmentDescription,
            constancyNew = constancyNew,
            brightSegmentCode = brightSegmentCode,
            brightSegmentDescription = brightSegmentDescription,
            campaignAdmission = campaignAdmission,
            pendingDebt = pendingDebt,
            orderStatus = billingOrderStatus,
            document = document,
            orderAmount = orderAmount,
            sbAmount = sbAmount,
            orderMtoAmount = orderMtoAmount,
            catalogSaleAmount = catalogSales,
            isNewInconstant = isNewInconstant,
            digital = createDigitalInfo(digitalInfo)
        )
    }

    private fun createDigitalInfo(list: List<DigitalConsultantEntity>): Consultant.Digital {
        val digital = list.firstOrNull() ?: return Consultant.Digital()
        return Consultant.Digital(
            isSubscribed = digital.isSubscribed,
            share = digital.share,
            buy = digital.buy
        )
    }

    private fun createWhatsApp(phone: String, code: String): String {
        return "$code${phone}".takeIf { phone.isNotEmpty() }.orEmpty()
    }

    private fun map(dto: DigitalDto.OnlineStore): DigitalConsultantEntity {
        return DigitalConsultantEntity(
            campaign = dto.campaign,
            region = dto.region,
            zone = dto.zone,
            section = dto.section,
            consultantCode = dto.consultantCode,
            isSubscribed = dto.isSubscribed,
            share = dto.share,
            buy = dto.buy,
            sale = dto.sale,
            orders = dto.orders
        )
    }

}
