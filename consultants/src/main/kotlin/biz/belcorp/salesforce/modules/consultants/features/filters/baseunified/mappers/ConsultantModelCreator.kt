package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers

import biz.belcorp.salesforce.components.R as ComponentsR
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.constants.SourceType.Companion.DIGITAL_BUY
import biz.belcorp.salesforce.core.constants.SourceType.Companion.DIGITAL_SHARE
import biz.belcorp.salesforce.core.constants.SourceType.Companion.DIGITAL_SUBSCRIBED
import biz.belcorp.salesforce.core.domain.entities.consultants.Consultant
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantConfiguration
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.formatWithThousands
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.BilledConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.DigitalKpiConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.GeneralConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.type.PendingBillingConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.utils.ConsultantsTextResolver

class ConsultantModelCreator(private val textResolver: ConsultantsTextResolver) {

    fun createModel(
        type: Int,
        configuration: ConsultantConfiguration,
        model: Consultant
    ): ConsultantModel =
        with(model) {
            val segmentPair = createSegment(configuration, model)
            return when {
                SourceType.isKpi(type) -> createGeneralConsultantModel(
                    segmentPair, configuration, this
                )
                SourceType.isBillingAdvancement(type) -> createBillingAdvancementModel(
                    segmentPair, configuration, this
                )
                SourceType.isDigital(type) -> createDigitalModel(
                    segmentPair, configuration, this, type
                )
                else -> createGeneralConsultantModel(segmentPair, configuration, this)
            }
        }

    private fun createBillingAdvancementModel(
        segmentPair: Pair<String, Int>,
        configuration: ConsultantConfiguration,
        model: Consultant
    ): ConsultantModel = with(model) {
        return if (hasBilledOrder) createBilledConsultantModel(
            segmentPair,
            configuration,
            model
        )
        else createPendingBillingModel(segmentPair, configuration, model)
    }

    private fun createBilledConsultantModel(
        segmentPair: Pair<String, Int>,
        configuration: ConsultantConfiguration,
        model: Consultant
    ): ConsultantModel = with(model) {
        return BilledConsultantModel(
            id = id.toLong(),
            code = code,
            uaKey = LlaveUA(region, zone, section),
            segment = segmentPair.first,
            colorSegment = segmentPair.second,
            name = WordUtils.capitalizeFully(name),
            whatsAppNumber = whatsApp,
            phoneNumber = phone,
            orderAmount = parseAmount(configuration.currency, catalogSaleAmount),
            orderMtoAmount = parseAmount(configuration.currency, orderMtoAmount),
            bonus = Constant.EMPTY_STRING,
            multiBrand = multiMarca
        )
    }

    private fun createPendingBillingModel(
        segmentPair: Pair<String, Int>,
        configuration: ConsultantConfiguration,
        model: Consultant
    ): ConsultantModel = with(model) {
        return PendingBillingConsultantModel(
            id = id.toLong(),
            code = code,
            uaKey = LlaveUA(region, zone, section),
            segment = segmentPair.first,
            colorSegment = segmentPair.second,
            name = WordUtils.capitalizeFully(name),
            whatsAppNumber = whatsApp,
            phoneNumber = phone,
            debAmount = parseAmount(configuration.currency, pendingDebt),
            sbAmount = parseAmount(configuration.currency, sbAmount),
            orderMtoAmount = parseAmount(configuration.currency, orderMtoAmount),
            bonus = Constant.EMPTY_STRING,
            multiBrand = multiMarca
        )
    }

    private fun createDigitalModel(
        segmentPair: Pair<String, Int>,
        configuration: ConsultantConfiguration,
        model: Consultant,
        @SourceType type: Int
    ): ConsultantModel = with(model) {
        val params = getDigitalParams(model.digital, configuration.storeTitle, type)
        return DigitalKpiConsultantModel(
            id = id.toLong(),
            code = code,
            uaKey = LlaveUA(region, zone, section),
            segment = segmentPair.first,
            colorSegment = segmentPair.second,
            name = WordUtils.capitalizeFully(name),
            whatsAppNumber = whatsApp,
            phoneNumber = phone,
            indicatorText = params.first,
            indicatorTextColor = params.second,
            indicatorBackground = params.third,
            orderMtoAmount = Constant.EMPTY_STRING,
            multiBrand = model.multiMarca
        )
    }

    private fun getDigitalParams(
        model: Consultant.Digital,
        storeTitle: String,
        @SourceType type: Int
    ): Triple<String, Int, Int> {
        return when {
            type == DIGITAL_SUBSCRIBED && model.isSubscribed -> Triple(
                textResolver.getSubscribedText(storeTitle),
                R.color.textColorIndicatorGreen,
                R.drawable.bg_indicator_green
            )
            type == DIGITAL_SUBSCRIBED && !model.isSubscribed -> Triple(
                textResolver.getNoSubscribedText(storeTitle),
                R.color.textColorIndicatorRed,
                R.drawable.bg_indicator_red
            )
            type == DIGITAL_SHARE && model.share -> Triple(
                textResolver.getShareText(storeTitle),
                R.color.textColorIndicatorGreen,
                R.drawable.bg_indicator_green
            )
            type == DIGITAL_SHARE && !model.share -> Triple(
                textResolver.getNoShareText(storeTitle),
                R.color.textColorIndicatorRed,
                R.drawable.bg_indicator_red
            )
            type == DIGITAL_BUY && model.buy -> Triple(
                textResolver.getBuyText(storeTitle),
                R.color.textColorIndicatorGreen,
                R.drawable.bg_indicator_green
            )
            type == DIGITAL_BUY && !model.buy -> Triple(
                textResolver.getNoBuyText(storeTitle),
                R.color.textColorIndicatorRed,
                R.drawable.bg_indicator_red
            )
            else -> throw IllegalArgumentException()
        }
    }

    private fun createGeneralConsultantModel(
        segmentPair: Pair<String, Int>,
        configuration: ConsultantConfiguration,
        model: Consultant
    ): ConsultantModel = with(model) {
        return GeneralConsultantModel(
            id = id.toLong(),
            code = code,
            uaKey = LlaveUA(region, zone, section),
            segment = segmentPair.first,
            colorSegment = segmentPair.second,
            name = WordUtils.capitalizeFully(name),
            whatsAppNumber = whatsApp,
            phoneNumber = phone,
            debAmount = parseAmount(configuration.currency, pendingDebt),
            bonus = Constant.EMPTY_STRING,
            multiBrand = model.multiMarca
        )
    }

    private fun createSegment(
        configuration: ConsultantConfiguration,
        model: Consultant
    ): Pair<String, Int> = with(model) {
        val segment = if (configuration.isPdv) brightSegmentDescription else segmentDescription
        return createConstancy(this, segment)
    }

    private fun createConstancyWithColor(
        constancy: String,
        isInconstant: Boolean
    ): Pair<String, Int> {
        val (newConstancy, color) =
            if (isInconstant) Pair(
                textResolver.getNewInconstant(),
                ComponentsR.color.color_consultants_segment
            )
            else Pair(constancy, ComponentsR.color.color_consultants_new_cycle)
        return Pair(textResolver.parseNewConstancy(newConstancy), color)
    }

    private fun createSegmentWithColor(segment: String) =
        Pair(segment, ComponentsR.color.color_consultants_segment)

    private fun createConstancy(
        model: Consultant,
        segment: String
    ): Pair<String, Int> = with(model) {
        return if (isNew) createConstancyWithColor(constancyNew, isNewInconstant)
        else createSegmentWithColor(segment)
    }

    private fun parseAmount(currency: String, amount: Double) =
        textResolver.parseAmountWithCurrency(currency, amount.formatWithThousands())
}
